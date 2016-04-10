package hoopray.safetypongandroid;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeTransform;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Marcus Hooper
 */
public class PlayerChangeActivity extends AppCompatActivity
{
	public static final String FIRST_NAME = "firstName";
	public static final String SECOND_NAME = "secondName";
	public static final String CHANGE = "change";
	public static final String P1_WINS = "winner";

	@Bind(R.id.first_challenger)
	TextView firstChallenger;
	@Bind(R.id.second_challenger)
	TextView secondChallenger;

	@Bind(R.id.player_one_state)
	TextView playerOneState;
	@Bind(R.id.player_one_change)
	TextView playerOneChange;
	@Bind(R.id.player_two_state)
	TextView playerTwoState;
	@Bind(R.id.player_two_change)
	TextView playerTwoChange;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player_change_activity);
		ButterKnife.bind(this);

		if(SafetyApplication.is21Plus)
		{
			getWindow().setEnterTransition(new ChangeTransform());
			getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
			postponeEnterTransition();
		}

		firstChallenger.setText(getIntent().getStringExtra(FIRST_NAME));
		secondChallenger.setText(getIntent().getStringExtra(SECOND_NAME));

		int change = getIntent().getIntExtra(CHANGE, 0);

		boolean p1Wins = getIntent().getBooleanExtra(P1_WINS, false);
		playerOneState.setText(p1Wins ? getString(R.string.winner) : getString(R.string.loser));
		playerTwoState.setText(p1Wins ? getString(R.string.loser) : getString(R.string.winner));

		int green = getResources().getColor(android.R.color.holo_green_dark);
		int red = getResources().getColor(android.R.color.holo_red_dark);
		playerOneState.setTextColor(p1Wins ? green : red);
		playerTwoState.setTextColor(p1Wins ? red : green);

		String p1Change = (p1Wins ? "+ " : "- ") + change;
		String p2Change = (p1Wins ? "- " : "+ ") + change;
		playerOneChange.setText(p1Change);
		playerTwoChange.setText(p2Change);

		if(SafetyApplication.is21Plus)
			startPostponedEnterTransition();

		AnimatorSet set = new AnimatorSet();
		ObjectAnimator firstStateAlpha = ObjectAnimator.ofFloat(playerOneState, "alpha", 1);
		firstStateAlpha.setDuration(400);
		ObjectAnimator firstStateTransition = ObjectAnimator.ofFloat(playerOneState, "translationX", -HelperFunctions.intDpToDisplayMetric(48), 0);
		firstStateTransition.setDuration(500);

		ObjectAnimator firstChangeAlpha = ObjectAnimator.ofFloat(playerOneChange, "alpha", 1);
		firstChangeAlpha.setDuration(400);
		firstChangeAlpha.setStartDelay(500);
		ObjectAnimator firstChangeTransition = ObjectAnimator.ofFloat(playerOneChange, "translationX", HelperFunctions.intDpToDisplayMetric(48), 0);
		firstChangeTransition.setDuration(500);
		firstChangeTransition.setStartDelay(500);

		ObjectAnimator secondStateAlpha = ObjectAnimator.ofFloat(playerTwoState, "alpha", 1);
		secondStateAlpha.setDuration(400);
		secondStateAlpha.setStartDelay(1000);
		ObjectAnimator secondStateTransition = ObjectAnimator.ofFloat(playerTwoState, "translationX", -HelperFunctions.intDpToDisplayMetric(48), 0);
		secondStateTransition.setDuration(500);
		secondStateTransition.setStartDelay(1000);

		ObjectAnimator secondChangeAlpha = ObjectAnimator.ofFloat(playerTwoChange, "alpha", 1);
		secondChangeAlpha.setDuration(400);
		secondChangeAlpha.setStartDelay(1500);
		ObjectAnimator secondChangeTransition = ObjectAnimator.ofFloat(playerTwoChange, "translationX", HelperFunctions.intDpToDisplayMetric(48), 0);
		secondChangeTransition.setDuration(500);
		secondChangeTransition.setStartDelay(1500);

		set.playTogether(firstStateAlpha, firstStateTransition, firstChangeAlpha, firstChangeTransition,
				secondStateAlpha, secondStateTransition, secondChangeAlpha, secondChangeTransition);
		set.start();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				end();
			}
		}, 3000);
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		end();
	}

	private void end()
	{
		Intent intent = new Intent(this, LeagueActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
