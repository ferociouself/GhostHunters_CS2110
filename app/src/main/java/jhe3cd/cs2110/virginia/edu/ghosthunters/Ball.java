package jhe3cd.cs2110.virginia.edu.ghosthunters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by JacksonEkis on 3/30/15.
 */
public class Ball extends Entity{
    public float xPosition, xVelocity = 0.0f;
    public float yPosition, yVelocity = 0.0f;
    public float xMax, yMax;

    public float speedMod;

    public boolean isTouching;
    public boolean isCharged;

    public int fileID;

    public Ball (int fileID, float xPosition, float yPosition, float speedMod,
                 float xMax, float yMax, int hitBoxWidth, int hitBoxHeight) {
        super(fileID, xPosition, yPosition, xMax, yMax, hitBoxWidth, hitBoxHeight);
        this.speedMod = speedMod;
    }

    @Override
    public void update(float xAcceleration, float yAcceleration) {
        xVelocity += (xAcceleration * MainActivity.frameTime);
        yVelocity += (yAcceleration * MainActivity.frameTime);

        if (isTouching) {
            xVelocity = xVelocity / 2;
            yVelocity = yVelocity / 2;
        }

        xVelocity *= speedMod;
        yVelocity *= speedMod;

        //Calc distance travelled in that time
        float xS = (xVelocity/2)*MainActivity.frameTime;
        float yS = (yVelocity/2)*MainActivity.frameTime;

        //Add to position negative due to sensor
        //readings being opposite to what we want!
        xPosition -= xS;
        yPosition += yS;

        if (xPosition > xMax) {
            xPosition = xMax;
            xVelocity = -(xVelocity * 0.9f);
        } else if (xPosition < 0) {
            xPosition = 0;
            xVelocity = -(xVelocity * 0.9f);
        }
        if (yPosition > yMax) {
            yPosition = yMax;
            yVelocity = -(yVelocity * 0.9f);
        } else if (yPosition < 0) {
            yPosition = 0;
            yVelocity = -(yVelocity * 0.9f);
        }
    }

    public void destroyer() {
        //TODO DESTROY STUFF!
    }

    public void toggleTouching() {
        isTouching = !isTouching;
    }

    public float getxPosition() {
        return xPosition;
    }

    public float getyPosition() {
        return yPosition;
    }

    public float getSpeedMod() {
        return speedMod;
    }

    public boolean isCharged() {
        return isCharged;
    }

    public int getFileID() {
        return fileID;
    }

    public boolean isTouching() {
        return isTouching;
    }
}
