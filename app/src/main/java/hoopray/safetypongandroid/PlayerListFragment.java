package hoopray.safetypongandroid;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import hoopray.safetypongandroid.firebaseviewholders.PlayerViewHolder;

/**
 * @author Dominic Murray
 */
public class PlayerListFragment extends Fragment implements PlusFragment
{
	@Bind(R.id.recyclerview)
	RecyclerView recyclerView;

	private FirebaseRecyclerAdapter<Player, PlayerViewHolder> adapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.player_list_fragment, container, false);
		ButterKnife.bind(this, view);

		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		Firebase ref = FirebaseHelper.getPlayerReferences();
		adapter = new FirebaseRecyclerAdapter<Player, PlayerViewHolder>(Player.class, R.layout.player_list_item, PlayerViewHolder.class, ref)
		{
			@Override
			protected void populateViewHolder(PlayerViewHolder playerViewHolder, Player player, int i)
			{
				playerViewHolder.position.setText(String.valueOf(i + 1) + ".");

				String name = player.getName();
				playerViewHolder.name.setText(TextUtils.isEmpty(name) ? getString(R.string.no_name) : name);
				playerViewHolder.rating.setText(String.valueOf(player.getRating()));
			}
		};
		recyclerView.setAdapter(adapter);

		((PlusFragmentManager) getActivity()).instantiatePlus();
		return view;
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
