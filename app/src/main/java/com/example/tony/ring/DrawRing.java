package com.example.tony.ring;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Tony on 2016/7/3.
 */
public class DrawRing extends View {
    private int[] ballX = {300, 300, 300};
    private int[] ballY = {300, 300, 300};

    private ArrayList<CircleRing> circleRings;
    private int radius = 100;
    private int width = 30;
    private int startAngle = 360;
    private int angleSpeed = 1;

    public DrawRing(Context context) {
        super(context);
        circleRings = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*
        ProgressRing pRing = new ProgressRing(ballX , ballY + 300, radius, width, Color.RED);
        pRing.drawProgressRing(canvas, startAngle);
        */

        /*
        if (startAngle <= 0) {
            angleSpeed = 1;
        } else if (startAngle >= 90) {
            angleSpeed = -1;
        }
        */

        startAngle += angleSpeed;
        if (startAngle == 360)
            startAngle = 0;
//        startAngle %= 360;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < circleRings.size(); i++) {
            circleRings.get(i).setRingXY(ballX[i], ballY[i]);
            circleRings.get(i).drawCircleRing(canvas, startAngle);
        }
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointCount = event.getPointerCount();
        switch (pointCount) {
            case 1:
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        ballX[0] = (int) event.getX();
                        ballY[0] = (int) event.getY();
                        CircleRing c = new CircleRing(ballX[0], ballY[0], radius, width, Color.RED);
                        circleRings.add(c);
                        Log.e("touch", "1 down-------");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        ballX[0] = (int) event.getX();
                        ballY[0] = (int) event.getY();
                        Log.e("touch", "1 move-------");
                        break;
                    case MotionEvent.ACTION_UP:
                        circleRings.remove(0);
                        Log.e("touch", "1 up-------");
                        break;
                }
            break;

            case 2:
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                //删除单点触摸的圆，绘制双电触摸的2个圆
                circleRings.remove(0);
                Log.e("touch", "2 down-------");
                ballX[0] = (int) event.getX(0);
                ballY[0] = (int) event.getY(0);
                CircleRing c = new CircleRing(ballX[0], ballY[0], radius, width, Color.RED);
                circleRings.add(c);
                ballX[1] = (int) event.getX(1);
                ballY[1] = (int) event.getY(1);
                CircleRing c1 = new CircleRing(ballX[1], ballY[1], radius, width, Color.RED);
                circleRings.add(c1);
                break;
            case MotionEvent.ACTION_MOVE:
                ballX[0] = (int) event.getX(0);
                ballY[0] = (int) event.getY(0);
                ballX[1] = (int) event.getX(1);
                ballY[1] = (int) event.getY(1);
                Log.e("touch", "2 move-------");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                //删除双电触摸的其中一个圆
                circleRings.remove(1);
                Log.e("touch", "2 up-------");
                break;
            }
                break;
            case 3:
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_POINTER_DOWN:
                        Log.e("touch", "3 down-------");
                        //删除2点触摸时生成的2个圆，从新绘制3个圆
                        circleRings.remove(1);
                        circleRings.remove(0);
                        ballX[0] = (int) event.getX(0);
                        ballY[0] = (int) event.getY(0);
                        CircleRing c = new CircleRing(ballX[0], ballY[0], radius, width, Color.RED);
                        circleRings.add(c);

                        ballX[1] = (int) event.getX(1);
                        ballY[1] = (int) event.getY(1);
                        CircleRing c1 = new CircleRing(ballX[1], ballY[1], radius, width, Color.RED);
                        circleRings.add(c1);

                        ballX[2] = (int) event.getX(2);
                        ballY[2] = (int) event.getY(2);
                        CircleRing c2 = new CircleRing(ballX[2], ballY[2], radius, width, Color.RED);
                        circleRings.add(c2);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        ballX[0] = (int) event.getX(0);
                        ballY[0] = (int) event.getY(0);

                        ballX[1] = (int) event.getX(1);
                        ballY[1] = (int) event.getY(1);

                        ballX[2] = (int) event.getX(2);
                        ballY[2] = (int) event.getY(2);
                        Log.e("touch", "3 move-------");
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        //3点触摸释放一个手指，会进入2点触摸
                        circleRings.remove(2);
                        Log.e("touch", "3 up-------");
                        break;
                }
                break;
        }
        return true;

    }
}

class CircleRing {
    int ringX;
    int ringY;
    int ringRadius;
    int ringWidth;
    Paint paint;

      public CircleRing(int ringX, int ringY, int ringRadius, int ringWidth, int ringColor) {
        this.ringX = ringX;
        this.ringY =  ringY;
        this.ringRadius =  ringRadius;
        this.ringWidth=  ringWidth;
        paint = new Paint();
        paint.reset();
        paint.setColor(ringColor);
        paint.setAntiAlias(true); //消除锯齿
        paint.setStrokeWidth(ringWidth);
        paint.setStyle(Paint.Style.STROKE);  //绘制空心圆或 空心矩形,只显示边缘的线，不显示内部
    }

    public void setRingXY (int ringX, int ringY) {
        this.ringX = ringX;
        this.ringY = ringY;
    }

    public void drawCircleRing(Canvas canvas, int startAngle) {
        RectF rect = new RectF(ringX - ringRadius, ringY - ringRadius, ringX + ringRadius, ringY + ringRadius);
        //false 不画圆心
        paint.setAlpha(255);
        canvas.drawArc(rect, startAngle + 0, 90, false, paint);
        canvas.drawArc(rect, startAngle + 180, 90, false, paint);
        paint.setAlpha(100);
        canvas.drawArc(rect, startAngle + 90, 90, false, paint);
        canvas.drawArc(rect, startAngle + 270, 90, false, paint);
    }
}

class ProgressRing {
    int ringX;
    int ringY;
    int ringRadius;
    int ringWidth;
    int headBallX;
    int headBallY;
    Paint paint;
    Shader  sweepGradient;
    //private static final int RED = 230, GREEN = 85, BLUE = 35; //基础颜色，这里是橙红色
    private static final int RED = 230, GREEN = 0, BLUE = 0; //基础颜色，这里是橙红色
    private static final int MIN_ALPHA = 30; //最小不透明度
    private static final int MAX_ALPHA = 255; //最大不透明度
    /*
    //圆环颜色
    //渐变顺序不同，显示的方向不同
    private static int[] changeColors = new int[]{
            Color.argb(MAX_ALPHA, RED, GREEN, BLUE),
            Color.argb(MIN_ALPHA, RED, GREEN, BLUE),
            Color.argb(MIN_ALPHA, RED, GREEN, BLUE),
//            Color.TRANSPARENT,
    };
    */
    private static int[] changeColors = new int[]{
            Color.argb(MIN_ALPHA, RED, GREEN, BLUE),
            Color.argb(MIN_ALPHA, RED, GREEN, BLUE),
            Color.argb(MAX_ALPHA, RED, GREEN, BLUE),
//            Color.TRANSPARENT,
    };

    public ProgressRing(int ringX, int ringY, int ringRadius, int ringWidth, int ringColor) {
        this.ringX = ringX;
        this.ringY =  ringY;
        this.ringRadius =  ringRadius;
        this.ringWidth=  ringWidth;
//        this.ringColor = ringColor;
//        this.startAngle = startAngle;
        paint = new Paint();
        paint.reset();
        paint.setColor(ringColor);
        paint.setAntiAlias(true); //消除锯齿
        paint.setStrokeWidth(ringWidth);
        paint.setStyle(Paint.Style.STROKE);  //绘制空心圆或 空心矩形,只显示边缘的线，不显示内部
    }

    public void drawProgressRing(Canvas canvas, int rotateDegree) {
        paint.reset();
        RectF rect = new RectF(ringX - ringRadius, ringY - ringRadius, ringX + ringRadius, ringY + ringRadius);
//        paint.setColor(Color.RED);
//        this.paint.setARGB(255, 138, 43, 226);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(ringWidth);
        paint.setStyle(Paint.Style.STROKE);
//        paint.setShader(new SweepGradient(ringX, ringY, changeColors, null));
        //设置渐变
        sweepGradient = new SweepGradient(ringX, ringY, changeColors, null);
        //按照圆心旋转
        Matrix matrix = new Matrix();
        matrix.setRotate(rotateDegree, ringX, ringY);
        sweepGradient.setLocalMatrix(matrix);
//        sweepGradient = new SweepGradient(ringX, ringY, changeColors, );
        paint.setShader(sweepGradient);
        canvas.drawArc(rect, 0, 360, false, paint);

        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.argb(MAX_ALPHA, RED, GREEN, BLUE));
        //使用三角函数时，需要把角度转为弧度
        headBallX = ringX + (int)(ringRadius * Math.cos((double)rotateDegree/180 * Math.PI));
        Log.e("degree", "degree: " + rotateDegree + "cos: " + Math.cos((double)rotateDegree/180 * Math.PI));
        headBallY = ringY + (int)(ringRadius * Math.sin((double)rotateDegree/180 * Math.PI));
        canvas.drawCircle(headBallX, headBallY, ringWidth/ 2, paint);
/*
        paint.reset();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.argb(MAX_ALPHA, RED, GREEN, BLUE));
        canvas.drawCircle(radius, ballY + 450, width/ 2, paint);
        */
    }
}
