package hoopray.safetypongandroid;

import com.firebase.client.Firebase;

import static hoopray.safetypongandroid.FirebaseConstants.FIREBASE_PATH;
import static hoopray.safetypongandroid.FirebaseConstants.GAMES;
import static hoopray.safetypongandroid.FirebaseConstants.PLAYERS;

/**
 * @author Dominic Murray 5/04/2016.
 */
public class FirebaseHelper
{
	public static void savePlayer(String key, Player player)
	{
		Firebase safetyPong = new Firebase(FirebaseConstants.FIREBASE_PATH + '/' + PLAYERS).child(SafetyApplication.getInstance().currentLeagueKey).child(key);
		safetyPong.setValue(player);
	}

	public static void saveGame(String key, Game game)
	{
		Firebase safetyPong = new Firebase(FirebaseConstants.FIREBASE_PATH + '/' + GAMES).child(SafetyApplication.getInstance().currentLeagueKey).child(key);
		safetyPong.setValue(game);
	}

	public static Firebase getPlayerReferences()
	{
		return new Firebase(FIREBASE_PATH + '/' + PLAYERS + '/' + SafetyApplication.getInstance().currentLeagueKey);
	}
}
