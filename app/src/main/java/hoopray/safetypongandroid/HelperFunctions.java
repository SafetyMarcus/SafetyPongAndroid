package hoopray.safetypongandroid;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * @author Marcus Hooper
 */
public class HelperFunctions
{
	public static int intDpToDisplayMetric(int expectedDp)
	{
		Resources resources = SafetyApplication.getInstance().getResources();
		int pixels = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, expectedDp, resources.getDisplayMetrics()));
		return pixels > 0 ? pixels : 1;
	}
}
