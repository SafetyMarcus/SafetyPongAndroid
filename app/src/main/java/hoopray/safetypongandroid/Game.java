package hoopray.safetypongandroid;

/**
 * @author Dominic Murray 4/04/2016.
 */
public class Game
{
	private String playerOneId;
	private String playerTwoId;
	private String playerOneName;
	private String playerTwoName;
	private int playerOneScore;
	private int playerTwoScore;
	private int playerOneRatingBefore;
	private int playerTwoRatingBefore;
	private long dateInMilliseconds;

	public Game()
	{
		setDateInMilliseconds(System.currentTimeMillis());
	}

	public String getPlayerOneId()
	{
		return playerOneId;
	}

	public void setPlayerOneId(String playerOneId)
	{
		this.playerOneId = playerOneId;
	}

	public String getPlayerTwoId()
	{
		return playerTwoId;
	}

	public void setPlayerTwoId(String playerTwoId)
	{
		this.playerTwoId = playerTwoId;
	}

	public String getPlayerOneName()
	{
		return playerOneName;
	}

	public void setPlayerOneName(String playerOneName)
	{
		this.playerOneName = playerOneName;
	}

	public String getPlayerTwoName()
	{
		return playerTwoName;
	}

	public void setPlayerTwoName(String playerTwoName)
	{
		this.playerTwoName = playerTwoName;
	}

	public int getPlayerOneScore()
	{
		return playerOneScore;
	}

	public void setPlayerOneScore(int playerOneScore)
	{
		this.playerOneScore = playerOneScore;
	}

	public int getPlayerTwoScore()
	{
		return playerTwoScore;
	}

	public void setPlayerTwoScore(int playerTwoScore)
	{
		this.playerTwoScore = playerTwoScore;
	}

	public int getPlayerOneRatingBefore()
	{
		return playerOneRatingBefore;
	}

	public void setPlayerOneRatingBefore(int playerOneRatingBefore)
	{
		this.playerOneRatingBefore = playerOneRatingBefore;
	}

	public int getPlayerTwoRatingBefore()
	{
		return playerTwoRatingBefore;
	}

	public void setPlayerTwoRatingBefore(int playerTwoRatingBefore)
	{
		this.playerTwoRatingBefore = playerTwoRatingBefore;
	}

	public long getDateInMilliseconds()
	{
		return dateInMilliseconds;
	}

	public void setDateInMilliseconds(long dateInMilliseconds)
	{
		this.dateInMilliseconds = dateInMilliseconds;
	}
}
