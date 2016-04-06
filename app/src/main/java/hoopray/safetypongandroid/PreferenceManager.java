package hoopray.safetypongandroid;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Dominic Murray 4/04/2016.
 */
public class PreferenceManager
{
    public static final String ASSISTANT_MANAGER_PREFERENCES = "pongPreferences";

    public static SharedPreferences getSharedPreferences()
    {
        return SafetyApplication.getInstance().getSharedPreferences(ASSISTANT_MANAGER_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static int getIntPref(String key, int defaultValue)
    {
        return getSharedPreferences().getInt(key, defaultValue);
    }

    public static void setIntPref(String key, int value)
    {
        getSharedPreferences().edit().putInt(key, value).apply();
    }

    public static String getStringPref(String key, String defaultValue)
    {
        return getSharedPreferences().getString(key, defaultValue);
    }

    public static void setStringPref(String key, String value)
    {
        getSharedPreferences().edit().putString(key, value).apply();
    }
}
