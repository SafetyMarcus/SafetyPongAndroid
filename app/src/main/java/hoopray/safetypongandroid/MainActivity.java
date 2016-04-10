package hoopray.safetypongandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class MainActivity extends AppCompatActivity
{
	private CallbackManager callbackManager;

	@Bind(R.id.updating)
	View updating;
	@Bind(R.id.login_button)
	LoginButton loginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		ButterKnife.bind(this);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		loginButton.setReadPermissions("public_profile");
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

	private void startAuthProcess(AccessToken token)
	{
		updating.setVisibility(View.VISIBLE);

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
		updating.setVisibility(View.GONE);
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
