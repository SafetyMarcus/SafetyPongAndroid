package hoopray.safetypongandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

import static hoopray.safetypongandroid.FirebaseConstants.LEAGUES;

/**
 * @author Dominic Murray 4/04/2016.
 */
public class LeagueLoginFragment extends Fragment
{
    @Bind(R.id.league_id)
    EditText leagueId;
    @Bind(R.id.league_password)
    EditText password;
    @Bind(R.id.login)
    Button login;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.league_login_layout, container, false);
        ButterKnife.bind(this, view);
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final String id = leagueId.getText().toString();
                final String passwordText = password.getText().toString();
                Query query = new Firebase(FirebaseConstants.FIREBASE_PATH).orderByChild(LEAGUES);
                query.addChildEventListener(new LoginQueryListener(id, passwordText));
            }
        });
        return view;
    }

    private class LoginQueryListener implements ChildEventListener
    {
        private String id;
        private String passwordText;

        public LoginQueryListener(String id, String passwordText)
        {
            this.id = id;
            this.passwordText = passwordText;
        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s)
        {
            for(DataSnapshot snapshot : dataSnapshot.getChildren())
            {
                if(snapshot.getKey().equalsIgnoreCase(id))
                {
                    HashMap<String, String> map = (HashMap<String, String>) snapshot.getValue();
                    if(passwordText.equalsIgnoreCase(map.get(FirebaseConstants.PASSWORD)))
                    {
                        SafetyPong app = SafetyPong.getInstance();
                        app.setCurrentLeague(snapshot.getKey(), snapshot.getValue(League.class));
                        Intent intent = new Intent(getActivity(), LeagueActivity.class);
                        startActivity(intent);
                    }
                }

            }

            Log.d("pong", "added: " + s);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s)
        {
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot)
        {
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s)
        {
        }

        @Override
        public void onCancelled(FirebaseError firebaseError)
        {
            Log.d("pong", "error");
        }
    }
}
