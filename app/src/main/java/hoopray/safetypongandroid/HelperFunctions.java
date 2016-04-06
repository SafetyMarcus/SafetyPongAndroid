package hoopray.safetypongandroid;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * @author Marcus Hooper
 */
public class HelperFunctions
{
    private static final int K_VALUE_RATING_LESS_2100 = 32;
    private static final int K_VALUE_RATING_LESS_2400 = 24;
    private static final int K_VALUE_RATING_REST = 16;

    public static int calculateRatingChange(int teamOneRating, int teamTwoRating, int teamOneScore, int teamTwoScore, int result)
    {
        double expectedResult = 1 / (1 + Math.pow(10, (teamTwoRating - teamOneRating) / 400));
        int kValue;
        if(teamOneRating < 2100)
            kValue = K_VALUE_RATING_LESS_2100;
        else if(teamOneRating < 2400)
            kValue = K_VALUE_RATING_LESS_2400;
        else
            kValue = K_VALUE_RATING_REST;

        int scoreDifference = teamOneScore - teamTwoScore;

        // Custom elo equation
        //newRating = teamOneRating + (kValue * (result - expectedResult)) + (scoreDifference * (1 - expectedResult))
        double ratingChange = (kValue * (result - expectedResult)) + Math.round(scoreDifference * 0.5);

        // standard elo equation
        //newRating = teamOneRating + (kValue * (result - expectedResult))

        return (int) Math.round(ratingChange);
    }

    public static int intDpToDisplayMetric(int expectedDp)
    {
        Resources resources = SafetyApplication.getInstance().getResources();
        int pixels = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, expectedDp, resources.getDisplayMetrics()));
        return pixels > 0 ? pixels : 1;
    }
}
