package hoopray.safetypongandroid;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import hoopray.safetypongandroid.firebaseviewholders.PlayerViewHolder;

/**
 * @author Dominic Murray
 */
public class PlayerListFragment extends Fragment implements PlusFragment
{
	private FirebaseRecyclerAdapter<Player, PlayerViewHolder> adapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recyclerview_layout, container, false);
		ButterKnife.bind(this, recyclerView);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		Firebase ref = FirebaseHelper.getPlayerReferences();
		adapter = new FirebaseRecyclerAdapter<Player, PlayerViewHolder>(Player.class, R.layout.player_list_item, PlayerViewHolder.class, ref)
		{
			@Override
			protected void populateViewHolder(PlayerViewHolder playerViewHolder, Player player, int i)
			{
				playerViewHolder.name.setText((i + 1) + ". " + player.getName());
				playerViewHolder.rating.setText(String.valueOf(player.getRating()));
			}
		};
		recyclerView.setAdapter(adapter);

		((PlusFragmentManager) getActivity()).instantiatePlus();
		return recyclerView;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		adapter.cleanup();
	}

	@Override
	public void onPlusClicked()
	{
		new AddPlayerDialog().show(getActivity().getFragmentManager(), "AddPlayer");
	}

	@Override
	public Drawable getPlusDrawable()
	{
		return getResources().getDrawable(R.drawable.plus);
	}
}
