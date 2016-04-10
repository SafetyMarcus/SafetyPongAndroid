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
import butterknife.Bind;
import butterknife.ButterKnife;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import hoopray.safetypongandroid.firebaseviewholders.PlayerViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Dominic Murray
 */
public class PlayerListFragment extends Fragment implements PlusFragment
{
	@Bind(R.id.recyclerview)
	RecyclerView recyclerView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.player_list_fragment, container, false);
		ButterKnife.bind(this, view);

		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		Firebase ref = FirebaseHelper.getPlayerReferences();
		final ArrayList<Player> players = new ArrayList<>();

		final RecyclerView.Adapter<PlayerViewHolder> adapter = new RecyclerView.Adapter<PlayerViewHolder>()
		{
			@Override
			public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
			{
				return new PlayerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.player_list_item, parent, false));
			}

			@Override
			public void onBindViewHolder(PlayerViewHolder playerViewHolder, int i)
			{
				playerViewHolder.position.setText(String.valueOf(i + 1) + ".");

				String name = players.get(i).getName();
				playerViewHolder.name.setText(TextUtils.isEmpty(name) ? getString(R.string.no_name) : name);
				playerViewHolder.rating.setText(String.valueOf(players.get(i).getRating()));
			}

			@Override
			public int getItemCount()
			{
				return players.size();
			}
		};

		ref.addValueEventListener(new ValueEventListener()
		{
			@Override
			public void onDataChange(DataSnapshot dataSnapshot)
			{
				players.clear();
				for(DataSnapshot next : dataSnapshot.getChildren())
				{
					players.add(next.getValue(Player.class));
				}
				Collections.sort(players, new Comparator<Player>()
				{
					@Override
					public int compare(Player lhs, Player rhs)
					{
						int lhsRating = lhs.getRating();
						int rhsRating = rhs.getRating();

						return rhsRating < lhsRating ? -1 : (rhsRating == lhsRating ? 0 : 1);
					}
				});
				adapter.notifyDataSetChanged();
			}

			@Override
			public void onCancelled(FirebaseError firebaseError)
			{
			}
		});

		recyclerView.setAdapter(adapter);

		((PlusFragmentManager) getActivity()).instantiatePlus();
		return view;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
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
