package hoopray.safetypongandroid;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * @author Dominic Murray 4/04/2016.
 */
public class SafetyPong extends Application
{
    private static final String CURRENT_LEAGUE_KEY = "currentLeagueKey";

    private static SafetyPong app;

    public String currentLeagueKey;
    public League currentLeague;

    @Override
    public void onCreate()
    {
        super.onCreate();
        app = this;
        Firebase.setAndroidContext(this);
//        Firebase safetyPong = firebase.child("leagues").child(UUID.randomUUID().toString());
//        League league = new League("SafetyPong", "SafetyWow");
//        safetyPong.setValue(league);
        currentLeagueKey = PreferenceManager.getStringPref(CURRENT_LEAGUE_KEY, "");
    }

    public static SafetyPong getInstance()
    {
        return app;
    }

    public void setCurrentLeague(String key, League league)
    {
        currentLeagueKey = key;
        currentLeague = league;
        PreferenceManager.setStringPref(CURRENT_LEAGUE_KEY, key);
    }
}
