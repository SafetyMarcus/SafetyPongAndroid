package hoopray.safetypongandroid;

import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Dominic Murray 10/04/2016.
 */
public class DeepLinkProcessor
{
    public static final int LEAGUE_INVITE = 1;

    public static String LEAGUE_ID = "leagueInviteId";

    public static Pair<Integer, String> result = null;

    public static boolean checkForLeagueInvite(JSONObject jsonObject)
    {
        if(jsonObject.has(DeepLinkProcessor.LEAGUE_ID))
        {
            try
            {
                String id = jsonObject.getString(DeepLinkProcessor.LEAGUE_ID);
                SafetyApplication.getInstance().currentLeagueKey = id;
                result = new Pair<>(LEAGUE_INVITE, id);
                return true;
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
        }

        return false;
    }
}
