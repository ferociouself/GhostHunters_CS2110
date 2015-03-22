package jhe3cd.cs2110.virginia.edu.ghosthunters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ShapeDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends ActionBarActivity implements SensorEventListener {

    CustomDrawableView customDrawView = null;
    ShapeDrawable drawnShape = new ShapeDrawable();
    public float xPosition, xAcceleration, xVelocity = 0.0f;
    public float yPosition, yAcceleration, yVelocity = 0.0f;
    public float xMax, yMax;
    private Bitmap mainBitmap;
    private Bitmap mainMetal;
    private SensorManager sensorManager = null;
    public float frameTime = 0.666f;

    private Sensor accelerometer;

    public Point size;

    public boolean isTouching;
    public boolean isCharged;
    public int initChargerX = 300;
    public int chargerX = initChargerX + 10;

    public Paint bgPaint;
    public Paint ballPaint;
    public Paint chargerPaint;

    public ColorFilter cFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);

        customDrawView = new CustomDrawableView(this);
        setContentView(customDrawView);

        // Finding the boundaries
        Display display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        xMax = size.x - 50;
        yMax = size.y - 100;

        xPosition = xMax/2;
        yPosition = yMax/2;

        bgPaint = new Paint();
        chargerPaint = new Paint();
        ballPaint = new Paint();

        cFilter = new LightingColorFilter(Color.YELLOW, 1);
        chargerPaint.setARGB(255, 255, 0, 0);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = MotionEventCompat.getActionMasked(event);
        int index = MotionEventCompat.getActionIndex(event);

        if (action == MotionEvent.ACTION_DOWN) {
            isTouching = true;
        } else if (action == MotionEvent.ACTION_UP) {
            isTouching = false;
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // This method required to implement SensorEventListener
        // Do something if the sensor accuracy changes. (Throw an error?)
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            yAcceleration = event.values[1];
            xAcceleration = event.values[0];
            updateBall();
        }
    }

    private void updateBall() {
        //Calculate new speed
        xVelocity += (xAcceleration * frameTime);
        yVelocity += (yAcceleration * frameTime);

        if (isTouching) {
            xVelocity = xVelocity / 2;
            yVelocity = yVelocity / 2;
        }

        //Calc distance travelled in that time
        float xS = (xVelocity/2)*frameTime;
        float yS = (yVelocity/2)*frameTime;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class CustomDrawableView extends View {
        public CustomDrawableView(Context context) {
            super(context);
            Bitmap ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
            final int dstWidth = 50;
            final int dstHeight = 50;
            mainBitmap = Bitmap.createScaledBitmap(ball, dstWidth, dstHeight, true);

        }

        protected void onDraw(Canvas canvas) {
            final Bitmap bitmap = mainBitmap;
            ThreadTest newThread = new ThreadTest();
            newThread.chargerSensor();
            newThread.toucherSensor();
            canvas.drawRect(0, 0, size.x, size.y, bgPaint);
            canvas.drawRect(initChargerX, 50, chargerX, 100, chargerPaint);
            canvas.drawBitmap(bitmap, xPosition, yPosition, ballPaint);
            invalidate();
        }

        public int calculateInSampleSize(
                BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) > reqHeight
                        && (halfWidth / inSampleSize) > reqWidth) {
                    inSampleSize *= 2;
                }
            }

            return inSampleSize;
        }

        public Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                             int reqWidth, int reqHeight) {

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(res, resId, options);

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeResource(res, resId, options);
        }
    }

    public class ThreadTest extends Thread {
        public void toucherSensor() {
            if (isTouching) {
                if (chargerX < initChargerX + 400 && !isCharged) {
                    chargerX++;
                } else {
                    isCharged = true;
                }
            }
        }

        public void chargerSensor() {
            if (isCharged) {
                ballPaint.setColorFilter(cFilter);
                if (chargerX < initChargerX + 10) {
                    isCharged = false;
                    ballPaint.setColorFilter(null);
                } else {
                    chargerX--;
                }
            }
        }
    }
}
