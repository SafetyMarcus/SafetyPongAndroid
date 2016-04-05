package hoopray.safetypongandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeTransform;

/**
 * @author Marcus Hooper
 */
public class GameResultsActivity extends AppCompatActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if(SafetyApplication.is21Plus)
		{
			getWindow().setEnterTransition(new ChangeTransform());
			getWindow().setStatusBarColor(getColor(R.color.colorPrimaryDark));
		}

		setContentView(R.layout.game_results_activity);
	}
}
