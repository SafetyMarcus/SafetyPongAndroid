package hoopray.safetypongandroid;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Dominic Murray 10/04/2016.
 */
public class AddLeagueDialog extends DialogFragment
{
    @Bind(R.id.league_name)
    EditText leagueName;
    @Bind(R.id.password)
    EditText password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.add_league_dialog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.save)
    void addLeague()
    {
        String id = UUID.randomUUID().toString();
        FirebaseHelper.createLeague(id, leagueName.getText().toString(), password.getText().toString());
        SafetyApplication.getInstance().currentLeagueKey = id;
        dismissAllowingStateLoss();
        startActivity(new Intent(getActivity(), LeagueActivity.class));
    }
}