package hoopray.safetypongandroid.firebaseviewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import hoopray.safetypongandroid.R;

/**
 * @author Marcus Hooper
 */
public class GamesViewHolder extends RecyclerView.ViewHolder
{
	@Bind(R.id.player_one_name)
	public TextView playerOneName;
	@Bind(R.id.player_one_score)
	public TextView playerOneScore;
	@Bind(R.id.player_two_name)
	public TextView playerTwoName;
	@Bind(R.id.player_two_score)
	public TextView playerTwoScore;

	public GamesViewHolder(View itemView)
	{
		super(itemView);
		ButterKnife.bind(this, itemView);
	}
}
