package hoopray.safetypongandroid.firebaseviewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Dominic Murray 9/04/2016.
 */
public class SingleLineViewHolder extends RecyclerView.ViewHolder
{
    @Bind(android.R.id.text1)
    public TextView text;


    public SingleLineViewHolder(View itemView)
    {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
