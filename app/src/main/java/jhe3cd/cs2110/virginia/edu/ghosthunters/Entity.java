package jhe3cd.cs2110.virginia.edu.ghosthunters;

/**
 * Created by JacksonEkis on 3/30/15.
 */
public abstract class Entity {
    public float xPosition;
    public float yPosition;
    public float xMax, yMax;

    public int fileID;

    public Entity(int fileID, float xPosition, float yPosition, float xMax, float yMax) {
        this.fileID = fileID;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xMax = xMax;
        this.yMax = yMax;
    }

    public abstract void update(float xAcceleration, float yAcceleration);
}
