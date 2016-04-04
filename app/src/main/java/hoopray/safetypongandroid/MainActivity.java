package hoopray.safetypongandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		ButterKnife.bind(this);

		if(SafetyApplication.is21Plus)
			getWindow().setExitTransition(new Explode());
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
	}

	@OnClick(R.id.fab)
	void onClick()
	{
		startActivity(new Intent(MainActivity.this, ChallengeActivity.class));
	}
}
