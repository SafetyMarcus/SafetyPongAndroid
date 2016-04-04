package hoopray.safetypongandroid;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.challenge_activity);
		ButterKnife.bind(this);

		if(SafetyApplication.is21Plus)
			getWindow().setEnterTransition(new Explode());
	}

	@OnClick(R.id.p1_next) void onFirstNextClick()
	{
		AnimatorSet vsSet = new AnimatorSet();
		ObjectAnimator vAnimator = ObjectAnimator.ofFloat(v, "translationX",
				HelperFunctions.intDpToDisplayMetric(-48), HelperFunctions.intDpToDisplayMetric(-24));
		ObjectAnimator sAnimator = ObjectAnimator.ofFloat(s, "translationX",
				HelperFunctions.intDpToDisplayMetric(48), HelperFunctions.intDpToDisplayMetric(24));
		ObjectAnimator vAlpha = ObjectAnimator.ofFloat(v, "alpha", 0, 1);
		ObjectAnimator sAlpha = ObjectAnimator.ofFloat(s, "alpha", 0, 1);
		vsSet.playTogether(vAnimator, sAnimator, vAlpha, sAlpha);
		vsSet.start();

		AnimatorSet secondarySet = new AnimatorSet();
		ObjectAnimator secondPlayerAnimation = ObjectAnimator.ofFloat(secondChallengerLayout, "translationX",
				HelperFunctions.intDpToDisplayMetric(-48), 0);
		ObjectAnimator secondNextAnimation = ObjectAnimator.ofFloat(p2Next, "translationX",
				HelperFunctions.intDpToDisplayMetric(48), 0);

		ObjectAnimator secondPlayerAlpha = ObjectAnimator.ofFloat(secondChallengerLayout, "alpha", 0, 1);
		ObjectAnimator secondNextAlpha = ObjectAnimator.ofFloat(p2Next, "alpha", 0, 1);
		secondarySet.playTogether(secondPlayerAlpha, secondNextAlpha, secondPlayerAnimation, secondNextAnimation);
		secondarySet.setStartDelay(100);
		secondarySet.start();
	}
}
