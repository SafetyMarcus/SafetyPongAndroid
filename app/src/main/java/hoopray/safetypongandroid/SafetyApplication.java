package hoopray.safetypongandroid;

import android.app.Application;
import android.os.Build;

/**
 * @author Marcus Hooper
 */
public class SafetyApplication extends Application
{
	public static boolean is21Plus;

	@Override
	public void onCreate()
	{
		super.onCreate();
		is21Plus = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
	}
}
