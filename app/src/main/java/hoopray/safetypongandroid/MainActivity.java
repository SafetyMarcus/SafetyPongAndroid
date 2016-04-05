package hoopray.safetypongandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import butterknife.Bind;

import static hoopray.safetypongandroid.FirebaseConstants.LEAGUES;

public class MainActivity extends AppCompatActivity
{
	@Bind(R.id.fab)
	FloatingActionButton fab;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportFragmentManager().beginTransaction().replace(R.id.container, new LeagueLoginFragment()).commit();

		if(!TextUtils.isEmpty(SafetyPong.getInstance().currentLeagueKey))
		{
			Query query = new Firebase(FirebaseConstants.FIREBASE_PATH).child(LEAGUES).equalTo(SafetyPong.getInstance().currentLeagueKey);
			query.addChildEventListener(new LeagueQueryListener());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if(id == R.id.action_settings)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private class LeagueQueryListener implements ChildEventListener
	{

		@Override
		public void onChildAdded(DataSnapshot dataSnapshot, String s)
		{
			SafetyPong.getInstance().currentLeague = dataSnapshot.getValue(League.class);
			Intent intent = new Intent(MainActivity.this, LeagueActivity.class);
			startActivity(intent);
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

		}
	}
}
