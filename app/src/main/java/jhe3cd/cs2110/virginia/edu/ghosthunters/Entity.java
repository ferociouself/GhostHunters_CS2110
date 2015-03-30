package jhe3cd.cs2110.virginia.edu.ghosthunters;

/**
 * Created by JacksonEkis on 3/30/15.
 */
public abstract class Entity {
    public float xPosition;
    public float yPosition;
    public float xMax, yMax;

    public float hitBoxWidth;
    public float hitBoxHeight;

    public int fileID;

    public Entity(int fileID, float xPosition, float yPosition, float xMax, float yMax, float hitBoxWidth, float hitBoxHeight) {
        this.fileID = fileID;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xMax = xMax;
        this.yMax = yMax;
        this.hitBoxWidth = hitBoxWidth;
        this.hitBoxHeight = hitBoxHeight;
    }

    public abstract void update(float xAcceleration, float yAcceleration);

    public abstract boolean collisionDetect();

    public abstract void destroyer();
}
