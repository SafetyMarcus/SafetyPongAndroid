package hoopray.safetypongandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeTransform;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Marcus Hooper
 */
public class GameResultsActivity extends AppCompatActivity
{
	public static final String FIRST_NAME = "firstName";
	public static final String SECOND_NAME = "secondName";

	@Bind(R.id.first_challenger)
	TextView firstChallenger;
	@Bind(R.id.second_challenger)
	TextView secondChallenger;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_results_activity);
		ButterKnife.bind(this);

		if(SafetyApplication.is21Plus)
		{
			getWindow().setEnterTransition(new ChangeTransform());
			getWindow().setStatusBarColor(getColor(R.color.colorPrimaryDark));
			postponeEnterTransition();
		}

		firstChallenger.setText(getIntent().getStringExtra(FIRST_NAME));
		secondChallenger.setText(getIntent().getStringExtra(SECOND_NAME));

		if(SafetyApplication.is21Plus)
			startPostponedEnterTransition();
	}
}
