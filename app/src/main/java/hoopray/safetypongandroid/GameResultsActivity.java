package hoopray.safetypongandroid;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeTransform;
import android.widget.EditText;
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
	@Bind(R.id.first_score)
	EditText firstScore;
	@Bind(R.id.second_score)
	EditText secondScore;

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

		AnimatorSet set = new AnimatorSet();
		ObjectAnimator firstAlpha = ObjectAnimator.ofFloat(firstScore, "alpha", 0, 1);
		ObjectAnimator secondAlpha = ObjectAnimator.ofFloat(secondScore, "alpha", 0, 1);
		set.playTogether(firstAlpha, secondAlpha);
		set.setStartDelay(500);
		set.start();
	}
}
