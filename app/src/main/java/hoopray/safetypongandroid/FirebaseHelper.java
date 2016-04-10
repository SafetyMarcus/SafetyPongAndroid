package hoopray.safetypongandroid;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import static hoopray.safetypongandroid.FirebaseConstants.FIREBASE_PATH;
import static hoopray.safetypongandroid.FirebaseConstants.GAMES;
import static hoopray.safetypongandroid.FirebaseConstants.LEAGUES;
import static hoopray.safetypongandroid.FirebaseConstants.NAME;
import static hoopray.safetypongandroid.FirebaseConstants.OWNER;
import static hoopray.safetypongandroid.FirebaseConstants.PLAYERS;
import static hoopray.safetypongandroid.FirebaseConstants.ROLES;
import static hoopray.safetypongandroid.FirebaseConstants.USERS;
import static hoopray.safetypongandroid.FirebaseConstants.IMAGE;

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

	public static void createLeague(String key, String leagueName, String password)
	{
		Firebase leagueRef = new Firebase(FirebaseConstants.FIREBASE_PATH + '/' + LEAGUES).child(key);
		League league = new League();
		league.setName(leagueName);
		league.setPassword(password);
		leagueRef.setValue(league);

		// Set owner role
		Firebase firebase = new Firebase(FIREBASE_PATH);
		String authId = firebase.getAuth().getUid();
		leagueRef.child(ROLES).child(authId).setValue(OWNER);

		// Set add league to users leagues
		firebase.child(USERS).child(authId).child(LEAGUES).child(key).setValue(leagueName);
	}

	public static void addUserToLeague(String leagueKey, String leagueName, String role)
	{
		Firebase firebase = new Firebase(FIREBASE_PATH);
		String authId = firebase.getAuth().getUid();
		new Firebase(FirebaseConstants.FIREBASE_PATH).child(LEAGUES).child(leagueKey).child(ROLES).child(authId).setValue(role);

		firebase.child(USERS).child(authId).child(LEAGUES).child(leagueKey).setValue(leagueName);
	}

	public static void updateUsersDetails(AuthData authData)
	{
		Firebase firebase = new Firebase(FIREBASE_PATH).child(USERS).child(authData.getUid());
		firebase.child(NAME).setValue(authData.getProviderData().get("displayName"));
		firebase.child(IMAGE).setValue(authData.getProviderData().get("profileImageURL"));
	}

	public static Firebase getPlayerReferences()
	{
		return new Firebase(FIREBASE_PATH + '/' + PLAYERS + '/' + SafetyApplication.getInstance().currentLeagueKey);
	}

	public static Firebase getGamesReferences()
	{
		return new Firebase(FIREBASE_PATH + '/' + GAMES + '/' + SafetyApplication.getInstance().currentLeagueKey);
	}

	public static Firebase getPlayerLeaguesListReference()
	{
		Firebase ref = new Firebase(FIREBASE_PATH);
		return ref.child(USERS).child(ref.getAuth().getUid()).child(LEAGUES);
	}

	public static Firebase getAppRef()
	{
		return new Firebase(FIREBASE_PATH);
	}

	public static String getAuthDisplayName()
	{
		return (String) getAppRef().getAuth().getProviderData().get("displayName");
	}
}
