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

	/**
	 * @param playerOne      The first player
	 * @param playerTwo      The second player
	 * @param firstId        The id of the first player
	 * @param secondId       The id of the second player
	 * @param playerOneScore The score in the current game of the first player
	 * @param playerTwoScore The score in the current game of the second player
	 * @param result         Binary value of player one win status. Player one wins == 1 Player 2 wins == 0
	 */
	public static int applyRatingChange(Player playerOne, Player playerTwo, String firstId, String secondId, int playerOneScore, int playerTwoScore, int result)
	{
		int playerOneRating = playerOne.getRating();
		int playerTwoRating = playerTwo.getRating();

		double expectedResult = 1 / (1 + Math.pow(10, (playerTwoRating - playerOneRating) / 400));
		int kValue;
		if(playerOneRating < 2100)
			kValue = K_VALUE_RATING_LESS_2100;
		else if(playerOneRating < 2400)
			kValue = K_VALUE_RATING_LESS_2400;
		else
			kValue = K_VALUE_RATING_REST;

		int scoreDifference = playerOneScore - playerTwoScore;

		// Custom elo equation
		//newRating = teamOneRating + (kValue * (result - expectedResult)) + (scoreDifference * (1 - expectedResult))
		double ratingChange = (kValue * (result - expectedResult)) + Math.round(scoreDifference * 0.5);

		// standard elo equation
		//newRating = teamOneRating + (kValue * (result - expectedResult))

		int change = (int) Math.round(ratingChange);

		//Always + for player 1 because if they lost it will be negative
		//Always - for player 2 because if they lost it will be a positive
		playerOne.setRating(playerOneRating + change);
		playerTwo.setRating(playerTwoRating - change);

		FirebaseHelper.savePlayer(firstId, playerOne);
		FirebaseHelper.savePlayer(secondId, playerTwo);

		return change;
	}

	public static int intDpToDisplayMetric(int expectedDp)
	{
		Resources resources = SafetyApplication.getInstance().getResources();
		int pixels = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, expectedDp, resources.getDisplayMetrics()));
		return pixels > 0 ? pixels : 1;
	}
}
