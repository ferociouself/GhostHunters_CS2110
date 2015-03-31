package jhe3cd.cs2110.virginia.edu.ghosthunters;

import android.graphics.Point;

/**
 * Created by JacksonEkis on 3/30/15.
 */
public class Ghost extends Entity{

    private float xVelocity;
    private float yVelocity;
    private float xAcceleration;
    private float yAcceleration;
    boolean isVisible;
    private Point target;
    private Item booty;
    private int health;
    private float x2Position;
    private float y2Position;

    public Ghost(float xPosition, float yPosition, int fileID, Point target, Item booty, int health,
                 int hitBoxWidth, int hitBoxHeight, float xMax, float yMax, float xAcceleration, float yAcceleration) {
        super(fileID, xPosition, yPosition, xMax, yMax, hitBoxWidth, hitBoxHeight);
        this.target = target;
        this.booty = booty;
        this.health = health;
        this.xVelocity = 0;
        this.yVelocity = 0;
        this.xAcceleration = xAcceleration;
        this.yAcceleration = yAcceleration;
        this.isVisible = false;
        this.x2Position = 0;
        this.y2Position = 0;
    }

    public void updateLocation(float x, float y) {
        this.x2Position = MainActivity.ball.xPosition;
        this.y2Position = MainActivity.ball.yPosition;
    }

    public void update(float xAcceleration, float yAcceleration) {
        updateLocation(xPosition, yPosition);
        this.xVelocity += (xAcceleration * MainActivity.frameTime);
        this.yVelocity += (yAcceleration * MainActivity.frameTime);

        if(MainActivity.ball.isTouching && !MainActivity.ball.isCharged) {
            if(this.xPosition < this.x2Position) {
                this.xPosition += this.xVelocity;
                this.hitBoxWidth = this.xPosition; // fix this when we have hitbox idea solidifed
            }
            if(this.xPosition > this.x2Position) {
                this.xPosition -= this.xVelocity;
                // hitbox change
            }
            if(this.yPosition < this.y2Position) {
                this.yPosition += this.yVelocity;
                // hitbox change
            }
            if(this.yPosition > this.y2Position) {
                this.yPosition -= this.yVelocity;
                // hitbox change
            }
        }
    }

    public void destroyer() {

    }
}
