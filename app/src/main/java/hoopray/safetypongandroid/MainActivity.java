package hoopray.safetypongandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

		if(SafetyApplication.is21Plus)
			getWindow().setExitTransition(new Explode());
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		getSupportFragmentManager().beginTransaction().replace(R.id.container, new LeagueLoginFragment()).commit();

		if(!TextUtils.isEmpty(SafetyApplication.getInstance().currentLeagueKey))
			startActivity(new Intent(this, LeagueActivity.class));
	}
}
