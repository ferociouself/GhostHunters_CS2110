package jhe3cd.cs2110.virginia.edu.ghosthunters;

/**
 * Created by Jimmy on 3/30/2015.
 */
public abstract class Item extends Entity
{
    double duration;

    public abstract void activate();
    public abstract void terminate();

    public Item(int duration, int fileID, float xPosition, float yPosition, float xMax, float yMax, float hitBoxWidth, float hitBoxHeight)
    {
        super(fileID,xPosition, yPosition, xMax, yMax,hitBoxWidth,hitBoxHeight);
        this.duration = duration;
    }

    public void randomlyGenerate()
    {
        super.xPosition = (float)Math.random()*(super.xMax+1);
        super.yPosition = (float)Math.random()*(super.yMax+1);
    }

    public void update(float xAccel, float yAccel)
    {

    }

    public void destroyer()
    {

    }

    public boolean collisionDetect()
    {
      return false;
    }

}
