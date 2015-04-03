package jhe3cd.cs2110.virginia.edu.ghosthunters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;

/**
 * Created by JacksonEkis on 3/30/15.
 */
public class Ball extends Entity{
    public float xVelocity, xAcceleration = 0.0f;
    public float yVelocity, yAcceleration = 0.0f;

    public float speedMod;
    public static float origSpeedMod;

    public boolean isTouching;
    public boolean isCharged;

    public int fileID;

    private Paint paint;

    public Ball (int fileID, int xPosition, int yPosition, float speedMod,
                 int xMax, int yMax, int hitBoxWidth, int hitBoxHeight) {
        super(fileID, xPosition, yPosition, xMax, yMax, hitBoxWidth, hitBoxHeight);
        this.speedMod = speedMod;
        origSpeedMod = speedMod;
        paint = new Paint();
    }

    public void updateAcceleration (float xAcceleration, float yAcceleration){
        this.xAcceleration = xAcceleration;
        this.yAcceleration = yAcceleration;
    }

    @Override
    public void update() {
        xVelocity += (xAcceleration * MainActivity.frameTime);
        yVelocity += (yAcceleration * MainActivity.frameTime);

        if (isTouching) {
            speedMod = 0.5f;
        } else {
            speedMod = origSpeedMod;
        }

        xVelocity *= speedMod;
        yVelocity *= speedMod;

        //Calc distance travelled in that time
        float xS = (xVelocity/2)*MainActivity.frameTime;
        float yS = (yVelocity/2)*MainActivity.frameTime;

        //Add to position negative due to sensor
        //readings being opposite to what we want!
        xPosition -= (int) xS;
        yPosition += (int) yS;

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

    public boolean filterChanger(ColorFilter cFilter) {
        paint.setColorFilter(cFilter);
        return paint.getColorFilter().equals(cFilter);
    }

    public void toggleTouching() {
        isTouching = !isTouching;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
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

    public void setTouching(boolean isTouching) {
        this.isTouching = isTouching;
    }

    public void setCharged(boolean isCharged) {
        this.isCharged = isCharged;
    }

    public Paint getPaint() {
        return paint;
    }
}
