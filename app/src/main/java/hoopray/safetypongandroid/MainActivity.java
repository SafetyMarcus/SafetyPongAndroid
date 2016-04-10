package hoopray.safetypongandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import org.json.JSONException;
import org.json.JSONObject;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;

public class MainActivity extends AppCompatActivity
{
    private CallbackManager callbackManager;
    private ProgressDialog progressDialog;
    private LoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if(SafetyApplication.is21Plus)
            getWindow().setExitTransition(new Explode());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile");
        // Other app specific specialization
        callbackManager = CallbackManager.Factory.create();

        AccessToken token = AccessToken.getCurrentAccessToken();
        if(token != null)
        {
            loginButton.setVisibility(View.GONE);
            startAuthProcess(token);
        }

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                startAuthProcess(loginResult.getAccessToken());
            }

            @Override
            public void onCancel()
            {
            }

            @Override
            public void onError(FacebookException exception)
            {
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        Branch branch = Branch.getInstance();
        branch.initSession(new Branch.BranchReferralInitListener()
        {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error)
            {
                if(error == null)
                {
                    // params are the deep linked params associated with the link that the user clicked before showing up
                    Log.i("BranchConfigTest", "deep link data: " + referringParams.toString());
                    if(DeepLinkProcessor.checkForLeagueInvite(referringParams))
                    {
                        if(progressDialog == null)
                            startActivity(new Intent(MainActivity.this, LeagueActivity.class));
                    }
                }
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    public void onNewIntent(Intent intent)
    {
        this.setIntent(intent);
    }

    private void startAuthProcess(AccessToken token)
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();

        Firebase ref = new Firebase(FirebaseConstants.FIREBASE_PATH);
        if(token == null)
        {
            ref.unauth();
            return;
        }

        ref.authWithOAuthToken("facebook", token.getToken(), new FirebaseAuthHandler());
    }

    private void handleAuthResultsReceived()
    {
        if(progressDialog != null)
        {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    private class FirebaseAuthHandler implements Firebase.AuthResultHandler
    {
        @Override
        public void onAuthenticated(AuthData authData)
        {
            FirebaseHelper.updateUsersDetails(authData);

            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new LeagueListFragment()).commit();
                    loginButton.setVisibility(View.GONE);
                    handleAuthResultsReceived();
                    if(DeepLinkProcessor.result != null && DeepLinkProcessor.result.first == DeepLinkProcessor.LEAGUE_INVITE)
                        startActivity(new Intent(MainActivity.this, LeagueActivity.class));
                }
            });
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError)
        {
            handleAuthResultsReceived();
        }
    }
}
