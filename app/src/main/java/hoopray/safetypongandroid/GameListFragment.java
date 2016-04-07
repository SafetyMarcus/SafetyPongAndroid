package hoopray.safetypongandroid;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import hoopray.safetypongandroid.firebaseviewholders.GamesViewHolder;

/**
 * @author Dominic Murray
 */
public class GameListFragment extends Fragment implements PlusFragment
{
	@Bind(R.id.recyclerview)
	RecyclerView recyclerView;

	private FirebaseRecyclerAdapter<Game, GamesViewHolder> adapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.game_list_fragment, container, false);
		ButterKnife.bind(this, view);

		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

		Firebase ref = FirebaseHelper.getGamesReferences();
		adapter = new FirebaseRecyclerAdapter<Game, GamesViewHolder>(Game.class, R.layout.games_list_item, GamesViewHolder.class, ref)
		{
			@Override
			protected void populateViewHolder(GamesViewHolder gamesViewHolder, Game game, int i)
			{
				gamesViewHolder.playerOneName.setText(game.getPlayerOneName());
				gamesViewHolder.playerOneScore.setText(String.valueOf(game.getPlayerOneScore()));
				gamesViewHolder.playerTwoName.setText(game.getPlayerTwoName());
				gamesViewHolder.playerTwoScore.setText(String.valueOf(game.getPlayerTwoScore()));
			}
		};
		recyclerView.setAdapter(adapter);

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
		startActivity(new Intent(getActivity(), ChallengeActivity.class));
	}

	@Override
	public Drawable getPlusDrawable()
	{
		return getResources().getDrawable(R.drawable.ping_pong);
	}
}
