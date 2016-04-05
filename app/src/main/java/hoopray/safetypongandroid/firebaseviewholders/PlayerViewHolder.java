package hoopray.safetypongandroid.firebaseviewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import hoopray.safetypongandroid.R;

/**
 * @author Dominic Murray 5/04/2016.
 */
public class PlayerViewHolder extends RecyclerView.ViewHolder
{
    @Bind(R.id.player_name)
    public TextView name;
    @Bind(R.id.rating)
    public TextView rating;

    public PlayerViewHolder(View itemView)
    {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
