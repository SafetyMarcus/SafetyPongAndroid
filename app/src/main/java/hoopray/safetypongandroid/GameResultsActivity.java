package hoopray.safetypongandroid;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeTransform;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.firebase.client.DataSnapshot;

import java.util.UUID;

/**
 * @author Marcus Hooper
 */
public class GameResultsActivity extends AppCompatActivity
{
	public static final String FIRST_NAME = "firstName";
	public static final String SECOND_NAME = "secondName";
	public static final String FIRST_ID = "firstId";
	public static final String SECOND_ID = "secondId";

	@Bind(R.id.first_challenger)
	TextView firstChallenger;
	@Bind(R.id.second_challenger)
	TextView secondChallenger;
	@Bind(R.id.first_score)
	EditText firstScore;
	@Bind(R.id.second_score)
	EditText secondScore;
	@Bind(R.id.save)
	View save;
	@Bind(R.id.updating)
	View updating;

	private String firstId;
	private String secondId;

	private Player firstPlayer;
	private Player secondPlayer;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_results_activity);
		ButterKnife.bind(this);

		if(SafetyApplication.is21Plus)
		{
			getWindow().setEnterTransition(new ChangeTransform());
			getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
			postponeEnterTransition();
		}

		firstChallenger.setText(getIntent().getStringExtra(FIRST_NAME));
		secondChallenger.setText(getIntent().getStringExtra(SECOND_NAME));
		firstId = getIntent().hasExtra(FIRST_ID) ? getIntent().getStringExtra(FIRST_ID) : "";
		secondId = getIntent().hasExtra(SECOND_ID) ? getIntent().getStringExtra(SECOND_ID) : "";

		if(SafetyApplication.is21Plus)
			startPostponedEnterTransition();

		AnimatorSet set = new AnimatorSet();
		ObjectAnimator firstAlpha = ObjectAnimator.ofFloat(firstScore, "alpha", 0, 1);
		ObjectAnimator secondAlpha = ObjectAnimator.ofFloat(secondScore, "alpha", 0, 1);
		ObjectAnimator saveAlpha = ObjectAnimator.ofFloat(save, "alpha", 0, 1);
		set.playTogether(firstAlpha, secondAlpha, saveAlpha);
		set.setStartDelay(500);
		set.start();
	}

	@OnClick(R.id.save)
	void saveClicked()
	{
		updating.setVisibility(View.VISIBLE);

		FirebaseHelper.getPlayerReferences().addListenerForSingleValueEvent(new SingleUpdateListener()
		{
			@Override
			public void onDataChange(DataSnapshot dataSnapshot)
			{
				int firstPlayerScore = Integer.valueOf(firstScore.getText().toString());
				int secondPlayerScore = Integer.valueOf(secondScore.getText().toString());

				Game game = new Game();
				game.setPlayerOneId(firstId);
				game.setPlayerTwoId(secondId);
				game.setPlayerOneName(firstChallenger.getText().toString());
				game.setPlayerTwoName(secondChallenger.getText().toString());
				game.setPlayerOneScore(firstPlayerScore);
				game.setPlayerTwoScore(secondPlayerScore);
				FirebaseHelper.saveGame(UUID.randomUUID().toString(), game);

				for(DataSnapshot next : dataSnapshot.getChildren())
				{
					if(next.getKey().equals(firstId))
						firstPlayer = next.getValue(Player.class);
					else if(next.getKey().equals(secondId))
						secondPlayer = next.getValue(Player.class);
				}

				if(firstPlayer != null && secondPlayer != null)
					HelperFunctions.applyRatingChange(firstPlayer, secondPlayer, firstId, secondId,
							firstPlayerScore, secondPlayerScore, firstPlayerScore > secondPlayerScore ? 1 : 0);
				Intent intent = new Intent(GameResultsActivity.this, LeagueActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
	}
}
