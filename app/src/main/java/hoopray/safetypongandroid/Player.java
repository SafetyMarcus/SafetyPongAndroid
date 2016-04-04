package hoopray.safetypongandroid;

/**
 * @author Dominic Murray 4/04/2016.
 */
public class Player
{
    public String id;
    public String name;
    public int rating;

    public Player()
    {
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getRating()
    {
        return rating;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }
}
