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
import static hoopray.safetypongandroid.FirebaseConstants.PLAYERS;

/**
 * @author Dominic Murray 5/04/2016.
 */
public class PlayerListFragment extends Fragment
{
    private FirebaseRecyclerAdapter<Player, PlayerViewHolder> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recyclerview_layout, container, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Firebase ref = new Firebase(FIREBASE_PATH + '/' + PLAYERS + '/' + SafetyApplication.getInstance().currentLeagueKey);
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
                new NewPlayerFragment().show(getActivity().getSupportFragmentManager(), null);
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
