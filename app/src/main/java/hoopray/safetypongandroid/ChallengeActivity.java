package hoopray.safetypongandroid;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.easygoingapps.ThePolice;
import com.easygoingapps.annotations.Observe;
import com.easygoingapps.utils.State;

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
	View firstEditText;
	@Bind(R.id.second_challenger)
	View secondEditText;

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

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.challenge_activity);
		ButterKnife.bind(this);
		ThePolice.watch(this);

		if(SafetyApplication.is21Plus)
			getWindow().setEnterTransition(new Explode());

		p2Next.setClickable(false);
		secondEditText.setEnabled(false);
	}

	@OnClick(R.id.p1_next)
	void onFirstNextClick()
	{
		firstChallengerFinalView.setText(firstChallenger.getValue());

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
		secondChallengerFinalView.setText(secondChallenger.getValue());

		finalSet = new AnimatorSet();
		ObjectAnimator hideFirstAlpha = ObjectAnimator.ofFloat(secondChallengerLayout, "alpha", 1, 0);
		ObjectAnimator showFirstFinalAlpha = ObjectAnimator.ofFloat(secondChallengerFinalView, "alpha", 0, 1);
		ObjectAnimator hideFirstNext = ObjectAnimator.ofFloat(p2Next, "alpha", 1, 0);
		finalSet.setStartDelay(100);
		finalSet.playTogether(hideFirstAlpha, showFirstFinalAlpha, hideFirstNext);
		finalSet.start();

		p2Next.setClickable(false);
		secondEditText.setEnabled(false);
		hasAnimatedSecond = true;
	}

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
