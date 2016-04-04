package hoopray.safetypongandroid;

import java.util.ArrayList;

/**
 * @author Dominic Murray 4/04/2016.
 */
public class League
{
    public String name;
    public String password;
    public ArrayList<Player> players;

    public League()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    public void setPlayers(ArrayList<Player> players)
    {
        this.players = players;
    }
}
