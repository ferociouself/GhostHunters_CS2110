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
import android.graphics.Rect;
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
import java.util.*;


public class MainActivity extends ActionBarActivity implements SensorEventListener {

    CustomDrawableView customDrawView = null;
    ShapeDrawable drawnShape = new ShapeDrawable();
    public int xMax, yMax;
    private Bitmap mainBitmap;
    private Bitmap mainMetal;
    private SensorManager sensorManager = null;
    public static float frameTime = 0.666f;

    private Sensor accelerometer;

    public Point size;

    public boolean isTouching;
    public boolean isCharged;
    public int initChargerX = 300;
    public int chargerX = initChargerX + 10;

    public Paint bgPaint;
    public Paint ballPaint;
    public Paint chargerPaint;
    public Paint chargerBGPaint;
    public Paint genericPaint;

    public ColorFilter cFilter;

    public Ball ball;

    public ArrayList<Ghost> ghostArray = new ArrayList<>();
    public ArrayList<Item> itemArray = new ArrayList<>();

    public ArrayList<Entity> entityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);

        // Finding the boundaries
        Display display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        xMax = size.x - 50;
        yMax = size.y - 100;

        genericPaint = new Paint();
        bgPaint = new Paint();
        chargerPaint = new Paint();
        chargerBGPaint = new Paint();
        ballPaint = new Paint();

        cFilter = new LightingColorFilter(Color.YELLOW, 1);
        chargerPaint.setARGB(255, 255, 0, 0);
        chargerPaint.setARGB(200, 200, 50, 50);
        bgPaint.setARGB(255, 100, 100, 100);

        createEntities();

        customDrawView = new CustomDrawableView(this);
        setContentView(customDrawView);
    }

    public boolean createEntities(){
        ball = new Ball(R.drawable.ball, xMax/2, yMax/2, 1, xMax, yMax, 100, 100);
        entityList.add(ball);

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = MotionEventCompat.getActionMasked(event);

        if (action == MotionEvent.ACTION_DOWN) {
            ball.toggleTouching();
        } else if (action == MotionEvent.ACTION_UP) {
            ball.toggleTouching();
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
            ball.updateAcceleration(event.values[0], event.values[1]);
        }
    }

    private void update() {
        for (Entity e : entityList) {
            e.update();
        }
    }

    public Ball getBall() {
        return ball;
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

/*    @Override
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
    }*/
    public class CustomDrawableView extends View {
        public CustomDrawableView(Context context) {
            super(context);
            Bitmap ballBMP = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
            final int dstWidth = 100;
            final int dstHeight = 100;
            mainBitmap = Bitmap.createScaledBitmap(ballBMP, dstWidth, dstHeight, true);

        }

        protected void onDraw(Canvas canvas) {
            final Bitmap bitmap = mainBitmap;
            ThreadTest newThread = new ThreadTest();
            update();
            newThread.chargerSensor();
            newThread.toucherSensor();
            canvas.drawRect(0, 0, size.x, size.y, bgPaint);
            canvas.drawRect(initChargerX - 10, 40, initChargerX + 410, 110, chargerBGPaint);
            canvas.drawRect(initChargerX, 50, chargerX, 100, chargerPaint);
            canvas.drawBitmap(bitmap, ball.getxPosition(), ball.getyPosition(), ballPaint);
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
            if (ball.isTouching()) {
                if (chargerX < initChargerX + 400 && !ball.isCharged()) {
                    chargerX++;
                } else {
                    ball.setCharged(true);
                }
            }
        }

        public void chargerSensor() {
            if (ball.isCharged()) {
                if (ballPaint.getColorFilter() != null) {
                    ballPaint.setColorFilter(cFilter);
                }
                if (chargerX < initChargerX + 10) {
                    ball.setCharged(false);
                    ballPaint.setColorFilter(null);
                } else {
                    chargerX--;
                }
            }
        }
    }
}
