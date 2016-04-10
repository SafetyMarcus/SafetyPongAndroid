package hoopray.safetypongandroid;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.easygoingapps.ThePolice;
import com.easygoingapps.annotations.Observe;
import com.easygoingapps.utils.State;
import com.firebase.client.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcus Hooper
 */
public class ChallengeActivity extends AppCompatActivity
{
	@Bind(R.id.v)
	View v;
	@Bind(R.id.s)
	View s;
	@Bind(R.id.p2_input)
	TextInputLayout secondChallengerLayout;
	@Bind(R.id.p2_next)
	View p2Next;
	@Bind(R.id.p1_next)
	View p1Next;
	@Bind(R.id.p1_input)
	TextInputLayout firstChallengerLayout;
	@Bind(R.id.first_challenger_final)
	TextView firstChallengerFinalView;
	@Bind(R.id.second_challenger_final)
	TextView secondChallengerFinalView;

	@Bind(R.id.first_challenger)
	EditText firstEditText;
	@Bind(R.id.second_challenger)
	EditText secondEditText;

	@Bind(R.id.first_player_spinner)
	Spinner firstSpinner;
	@Bind(R.id.second_player_spinner)
	Spinner secondSpinner;

	AnimatorSet vsSet;
	AnimatorSet hideSet;
	AnimatorSet secondarySet;
	AnimatorSet finalSet;

	boolean hasAnimatedFirst;
	boolean hasAnimatedSecond;

	//Binding edit text to name
	@Observe(R.id.first_challenger)
	public State<String> firstChallenger = new State<>("");

	@Observe(R.id.second_challenger)
	public State<String> secondChallenger = new State<>("");

	private String firstId;
	private String secondId;

	boolean firstLoaded;
	boolean secondLoaded;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.challenge_activity);
		ButterKnife.bind(this);
		ThePolice.watch(this);

		if(SafetyApplication.is21Plus)
			getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));

		p2Next.setClickable(false);
		secondEditText.setEnabled(false);

		FirebaseHelper.getPlayerReferences().addListenerForSingleValueEvent(new SingleUpdateListener()
		{
			@Override
			public void onDataChange(DataSnapshot dataSnapshot)
			{
				ArrayList<String> ids = new ArrayList<>();
				ArrayList<String> names = new ArrayList<>();

				for(DataSnapshot next : dataSnapshot.getChildren())
				{
					ids.add(next.getKey());
					names.add(next.getValue(Player.class).getName());
				}

				final PlayerSpinnerAdapter adapter = new PlayerSpinnerAdapter(names, ids);
				firstSpinner.setAdapter(adapter);
				secondSpinner.setAdapter(adapter);
				firstSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
				{
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
					{
						firstId = adapter.getId(position);
						firstEditText.setText(adapter.getItem(position));
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent)
					{
					}
				});

				secondSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
				{
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
					{
						secondId = adapter.getId(position);
						secondEditText.setText(adapter.getItem(position));
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent)
					{
					}
				});

				if(adapter.getCount() > 1)
					secondSpinner.setSelection(1, false);
			}
		});
	}

	@OnClick(R.id.first_challenger)
	void clickFirst()
	{
		firstSpinner.performClick();
	}

	@OnClick(R.id.second_challenger)
	void clickSecond()
	{
		secondSpinner.performClick();
	}

	@OnClick(R.id.p1_next)
	void onFirstNextClick()
	{
		String name = firstChallenger.getValue();
		firstChallengerFinalView.setText(TextUtils.isEmpty(name) ? getString(R.string.no_name) : name);

		vsSet = new AnimatorSet();
		ObjectAnimator vAnimator = ObjectAnimator.ofFloat(v, "translationX",
				HelperFunctions.intDpToDisplayMetric(-48), HelperFunctions.intDpToDisplayMetric(-24));
		ObjectAnimator sAnimator = ObjectAnimator.ofFloat(s, "translationX",
				HelperFunctions.intDpToDisplayMetric(48), HelperFunctions.intDpToDisplayMetric(24));
		ObjectAnimator vAlpha = ObjectAnimator.ofFloat(v, "alpha", 0, 1);
		ObjectAnimator sAlpha = ObjectAnimator.ofFloat(s, "alpha", 0, 1);

		vsSet.playTogether(vAnimator, sAnimator, vAlpha, sAlpha);
		vsSet.start();
		p1Next.setClickable(false);
		p2Next.setClickable(true);
		firstEditText.setEnabled(false);
		secondEditText.setEnabled(true);

		hideSet = new AnimatorSet();
		ObjectAnimator hideFirstAlpha = ObjectAnimator.ofFloat(firstChallengerLayout, "alpha", 1, 0);
		ObjectAnimator showFirstFinalAlpha = ObjectAnimator.ofFloat(firstChallengerFinalView, "alpha", 0, 1);
		ObjectAnimator hideFirstNext = ObjectAnimator.ofFloat(p1Next, "alpha", 1, 0);
		hideSet.setStartDelay(100);
		hideSet.playTogether(hideFirstAlpha, showFirstFinalAlpha, hideFirstNext);
		hideSet.start();

		secondarySet = new AnimatorSet();
		ObjectAnimator secondPlayerAnimation = ObjectAnimator.ofFloat(secondChallengerLayout, "translationX",
				HelperFunctions.intDpToDisplayMetric(-48), 0);
		ObjectAnimator secondNextAnimation = ObjectAnimator.ofFloat(p2Next, "translationX",
				HelperFunctions.intDpToDisplayMetric(48), 0);

		ObjectAnimator secondPlayerAlpha = ObjectAnimator.ofFloat(secondChallengerLayout, "alpha", 0, 1);
		ObjectAnimator secondNextAlpha = ObjectAnimator.ofFloat(p2Next, "alpha", 0, 1);
		secondarySet.playTogether(secondPlayerAlpha, secondNextAlpha, secondPlayerAnimation, secondNextAnimation);
		secondarySet.setStartDelay(100);
		secondarySet.start();

		hasAnimatedFirst = true;
	}

	@OnClick(R.id.p2_next)
	void onFinishClick()
	{
		String name = secondChallenger.getValue();
		secondChallengerFinalView.setText(TextUtils.isEmpty(name) ? getString(R.string.no_name) : name);

		finalSet = new AnimatorSet();
		ObjectAnimator hideFirstAlpha = ObjectAnimator.ofFloat(secondChallengerLayout, "alpha", 1, 0);
		ObjectAnimator showFirstFinalAlpha = ObjectAnimator.ofFloat(secondChallengerFinalView, "alpha", 0, 1);
		ObjectAnimator hideFirstNext = ObjectAnimator.ofFloat(p2Next, "alpha", 1, 0);
		finalSet.setStartDelay(100);

		p2Next.setClickable(false);
		secondEditText.setEnabled(false);
		hasAnimatedSecond = true;

		ObjectAnimator vAlpha = ObjectAnimator.ofFloat(v, "alpha", 1, 0);
		vAlpha.setStartDelay(400);
		ObjectAnimator sAlpha = ObjectAnimator.ofFloat(s, "alpha", 1, 0);
		sAlpha.setStartDelay(400);

		finalSet.playTogether(hideFirstAlpha, showFirstFinalAlpha, hideFirstNext, vAlpha, sAlpha);
		finalSet.start();

		finalSet.addListener(listener);
	}

	private SafetyAnimationListener listener = new SafetyAnimationListener()
	{
		@Override
		public void onAnimationEnd(Animator animation)
		{
			Intent intent = new Intent(ChallengeActivity.this, GameResultsActivity.class);

			List<Pair<View, String>> sharedElements = new ArrayList<>();
			if(getResources().getBoolean(R.bool.isLarge))
			{
				finalSet.removeListener(this);
				View cardLayout = findViewById(R.id.card_layout);
				String transitionName = getString(R.string.floating_card);
				sharedElements.add(Pair.create(cardLayout, transitionName));

				View background = findViewById(R.id.background);
				String backgroundName = getString(R.string.background);
				sharedElements.add(Pair.create(background, backgroundName));
			}

			sharedElements.add(Pair.create(findViewById(R.id.first_challenger_final), getString(R.string.first_challenger)));
			sharedElements.add(Pair.create(findViewById(R.id.second_challenger_final), getString(R.string.second_challenger)));

			intent.putExtra(GameResultsActivity.FIRST_NAME, firstChallengerFinalView.getText());
			intent.putExtra(GameResultsActivity.SECOND_NAME, secondChallengerFinalView.getText());
			intent.putExtra(GameResultsActivity.FIRST_ID, firstId);
			intent.putExtra(GameResultsActivity.SECOND_ID, secondId);
			Pair<View, String>[] views = sharedElements.toArray(new Pair[sharedElements.size()]);
			ActivityCompat.startActivity(ChallengeActivity.this, intent,
					ActivityOptionsCompat.makeSceneTransitionAnimation(ChallengeActivity.this,
							views).toBundle());
		}
	};

	@Override
	public void onBackPressed()
	{
		ReverseInterpolator interpolator = new ReverseInterpolator();

		if(hasAnimatedSecond)
		{
			finalSet.setInterpolator(interpolator);
			finalSet.start();
			hasAnimatedSecond = false;
			p2Next.setClickable(true);
			secondEditText.setEnabled(true);
			return;
		}
		if(hasAnimatedFirst)
		{
			vsSet.setInterpolator(interpolator);
			vsSet.setStartDelay(100);
			vsSet.start();

			hideSet.setStartDelay(100);
			hideSet.setInterpolator(interpolator);
			hideSet.start();

			secondarySet.setInterpolator(interpolator);
			secondarySet.setStartDelay(0);
			secondarySet.start();
			hasAnimatedFirst = false;

			p1Next.setClickable(true);
			firstEditText.setEnabled(true);
			p2Next.setClickable(false);
			secondEditText.setEnabled(false);
			return;
		}

		super.onBackPressed();
	}

	public class ReverseInterpolator implements Interpolator
	{
		@Override
		public float getInterpolation(float paramFloat)
		{
			return Math.abs(paramFloat - 1f);
		}
	}
}
