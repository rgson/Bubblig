package se.rgson.da401a.bubblig.model.readability;

import android.content.Context;
import android.graphics.Canvas;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by johan on 1/5/2015.
 */
public class ZoomView extends View {

    //These two constants specify the minimum and maximum zoom
    private static float MIN_ZOOM = 1f;
    private static float MAX_ZOOM = 5f;

    private float scaleFactor = 1.f;
    private ScaleGestureDetector detector;

    //These constants specify the mode that we’re in
    private static int NONE = 0;
    private static int DRAG = 1;
    private static int ZOOM = 2;

    private int mode;

    //These two variables keep track of the X and Y coordinate of the finger when it first
//touches the screen
    private float startX = 0f;
    private float startY = 0f;

    //These two variables keep track of the amount we need to translate the canvas along the X
//and the Y coordinate
    private float translateX = 0f;
    private float translateY = 0f;

    //These two variables keep track of the amount we translated the X and Y coordinates, the last time we
//panned.
    private float previousTranslateX = 0f;
    private float previousTranslateY = 0f;

    private boolean dragged = false;
    private float displayWidth;
    private float displayHeight;

    public ZoomView(Context context)
    {
        super(context);
        detector = new ScaleGestureDetector(getContext(), new ScaleListener());

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        displayWidth = this.getWidth();
        displayHeight = this.getHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        System.out.println("user touch the screen!");
        switch (event.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:
                mode = DRAG;

                startX = event.getX() - previousTranslateX;
                startY = event.getY() - previousTranslateY;
                System.out.println("Action_Down!");
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                translateX = event.getX() - startX;
                translateY = event.getY() - startY;

                double distance = Math.sqrt(Math.pow(event.getX() - (startX + previousTranslateX), 2) +
                    Math.pow(event.getY() - (startY + previousTranslateY), 2)
                );

                if(distance > 0)
                {
                    dragged = true;
                }
                System.out.println("Action_Move!");
                invalidate();
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                mode = ZOOM;
                System.out.println("Action_Pointer_Down!");
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                mode = NONE;
                dragged = false;
                previousTranslateX = translateX;
                previousTranslateY = translateY;
                System.out.println("Action_Up!");
                invalidate();
                break;

            case MotionEvent.ACTION_POINTER_UP:
                mode = DRAG;
                previousTranslateX = translateX;
                previousTranslateY = translateY;
                System.out.println("Action_Pointer_Up!");
                invalidate();
                break;
        }

        detector.onTouchEvent(event);

        if ((mode == DRAG && scaleFactor != 1f && dragged) || mode == ZOOM)
        {
            invalidate();
        }

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("Draws the new canvas on the screen!");
        canvas.save();

        if((translateX * -1) == (scaleFactor - 1) * displayWidth)
        {
            translateX = (1 - scaleFactor) * displayWidth;
        }

        if(translateY * -1 == (scaleFactor - 1) * displayHeight)
        {
            translateY = (1 - scaleFactor) * displayHeight;
        }

        canvas.translate(translateX, translateY);

        canvas.scale(scaleFactor, scaleFactor);

        canvas.restore();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
    {
        @Override
        public boolean onScale(ScaleGestureDetector detector)
        {
            System.out.println("Entering ScaleListener");
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM));
            return true;
        }
    }
}
