package hoopray.safetypongandroid;

import android.app.Application;
import android.os.Build;

import com.firebase.client.Firebase;

/**
 * @author Marcus Hooper
 */
public class SafetyApplication extends Application
{
	public static boolean is21Plus = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
	private static final String CURRENT_LEAGUE_KEY = "currentLeagueKey";
	private static SafetyApplication app;

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
		currentLeagueKey = PreferenceManager.getStringPref(CURRENT_LEAGUE_KEY, "c4da5223-2233-4aa4-bbc6-1e6660d80b74");
	}

	public static SafetyApplication getInstance()
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
