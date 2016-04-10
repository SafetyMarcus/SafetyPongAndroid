package hoopray.safetypongandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import hoopray.safetypongandroid.firebaseviewholders.SingleLineViewHolder;

/**
 * @author Dominic Murray 9/04/2016.
 */
public class LeagueListFragment extends Fragment
{
    @Bind(R.id.recyclerview)
    RecyclerView recyclerView;

    FloatingActionButton fab;

    private FirebaseRecyclerAdapter<String, SingleLineViewHolder> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.recyclerview, container, false);
        ButterKnife.bind(this, view);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new AddLeagueDialog().show(getActivity().getFragmentManager(), "AddLeague");
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Firebase ref = FirebaseHelper.getPlayerLeaguesListReference();
        adapter = new FirebaseRecyclerAdapter<String, SingleLineViewHolder>(String.class, android.R.layout.simple_list_item_2, SingleLineViewHolder.class, ref)
        {
            @Override
            protected void populateViewHolder(SingleLineViewHolder holder, String game, int i)
            {
                holder.itemView.setOnClickListener(new ItemClickListener(i));
                holder.text.setText(game);
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

    private class ItemClickListener implements View.OnClickListener
    {
        private int position;

        public ItemClickListener(int position)
        {
            this.position = position;
        }

        @Override
        public void onClick(View view)
        {
            SafetyApplication.getInstance().currentLeagueKey = adapter.getRef(position).getKey();
            startActivity(new Intent(getActivity(), LeagueActivity.class));
        }
    }
}
