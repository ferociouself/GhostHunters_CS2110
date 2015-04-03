package jhe3cd.cs2110.virginia.edu.ghosthunters;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by JacksonEkis on 3/30/15.
 */
public abstract class Entity {
    public int xPosition;
    public int yPosition;
    public int xMax, yMax;

    public Point centralPoint;
    public Rect hitBox;

    public int fileID;

    public Entity(int fileID, int xPosition, int yPosition, int xMax, int yMax,
                  int hitBoxWidth, int hitBoxHeight) {
        this.fileID = fileID;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xMax = xMax;
        this.yMax = yMax;
        this.centralPoint = new Point(xPosition + (hitBoxWidth/2), yPosition + (hitBoxHeight/2));
        this.hitBox = new Rect(xPosition, yPosition, xPosition + hitBoxWidth, yPosition + hitBoxHeight);
    }

    public abstract void update();

    public boolean collisionDetect() {
        return false;
    }

    public abstract void destroyer();
}
