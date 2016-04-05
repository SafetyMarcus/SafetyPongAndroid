package hoopray.safetypongandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;

import hoopray.safetypongandroid.firebaseviewholders.PlayerViewHolder;

import static hoopray.safetypongandroid.FirebaseConstants.FIREBASE_PATH;
import static hoopray.safetypongandroid.FirebaseConstants.GAMES;

/**
 * @author Dominic Murray 5/04/2016.
 */
public class GameListFragment extends Fragment
{
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<Game, PlayerViewHolder> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.recyclerview_layout, container, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Firebase ref = new Firebase(FIREBASE_PATH + '/' + GAMES + '/' + SafetyPong.getInstance().currentLeagueKey);
        adapter = new FirebaseRecyclerAdapter<Game, PlayerViewHolder>(Game.class, R.layout.player_list_item, PlayerViewHolder.class, ref)
        {
            @Override
            protected void populateViewHolder(PlayerViewHolder playerViewHolder, Game game, int i)
            {
                playerViewHolder.name.setText(game.getPlayerOneName() + ' ' + game.getPlayerOneScore() + '-' + game.getPlayerTwoScore() + ' ' + game.getPlayerTwoName());
//                playerViewHolder.rating.setText(String.valueOf(player.getRating()));
            }
        };
        recyclerView.setAdapter(adapter);

        return recyclerView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        getActivity().findViewById(R.id.fab).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        adapter.cleanup();
    }
}
