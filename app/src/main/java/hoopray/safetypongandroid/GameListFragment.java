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
import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import hoopray.safetypongandroid.firebaseviewholders.PlayerViewHolder;

import static hoopray.safetypongandroid.FirebaseConstants.FIREBASE_PATH;
import static hoopray.safetypongandroid.FirebaseConstants.GAMES;

/**
 * @author Dominic Murray
 */
public class GameListFragment extends Fragment implements PlusFragment
{
    private FirebaseRecyclerAdapter<Game, PlayerViewHolder> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recyclerview_layout, container, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Firebase ref = new Firebase(FIREBASE_PATH + '/' + GAMES + '/' + SafetyApplication.getInstance().currentLeagueKey);
        adapter = new FirebaseRecyclerAdapter<Game, PlayerViewHolder>(Game.class, R.layout.player_list_item, PlayerViewHolder.class, ref)
        {
            @Override
            protected void populateViewHolder(PlayerViewHolder playerViewHolder, Game game, int i)
            {
                playerViewHolder.name.setText(game.getPlayerOneName() + ' ' + game.getPlayerOneScore() + '-' + game.getPlayerTwoScore() + ' ' + game.getPlayerTwoName());
            }
        };
        recyclerView.setAdapter(adapter);

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
        startActivity(new Intent(getActivity(), ChallengeActivity.class));
    }

    @Override
    public Drawable getPlusDrawable()
    {
        return getResources().getDrawable(R.drawable.ping_pong);
    }
}
