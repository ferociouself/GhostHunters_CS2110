package jhe3cd.cs2110.virginia.edu.ghosthunters;

/**
 * Created by Jimmy on 3/30/2015.
 */
public class Shield extends Item
{
    public Shield (int duration, int fileID, int xPosition, int yPosition, int xMax, int yMax, int hitBoxWidth, int hitBoxHeight) {
        super (duration, fileID, xPosition, yPosition, xMax, yMax, hitBoxWidth, hitBoxHeight);
    }

    public void activate()
    {
        super.randomlyGenerate();

    }

    public void terminate()
    {

    }

    public void update() {

    }
}
