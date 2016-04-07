package hoopray.safetypongandroid;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.easygoingapps.ThePolice;
import com.easygoingapps.annotations.Observe;
import com.easygoingapps.utils.State;

import java.util.UUID;

/**
 * @author Marcus Hooper
 */
public class AddPlayerDialog extends DialogFragment
{
	@Bind(R.id.player_name)
	EditText playerName;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.add_player_dialog, container, false);
		ButterKnife.bind(this, view);

		return view;
	}

	@OnClick(R.id.save)
	void addPlayer()
	{
		Player player = new Player();
		player.setName(playerName.getText().toString());
		player.setRating(1500);
		FirebaseHelper.savePlayer(UUID.randomUUID().toString(), player);
		dismissAllowingStateLoss();
	}
}
