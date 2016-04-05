package hoopray.safetypongandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Dominic Murray 4/04/2016.
 */
public class NewPlayerFragment extends DialogFragment
{
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.start_rating)
    EditText rating;
    @Bind(R.id.save)
    Button save;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.new_player_layout, container, false);
        ButterKnife.bind(this, view);
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Player player = new Player();
                player.setName(name.getText().toString());
                player.setRating(Integer.valueOf(rating.getText().toString()));
                FirebaseHelper.savePlayer(UUID.randomUUID().toString(), player);
            }
        });
        return view;
    }
}
