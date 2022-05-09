package com.ng.demo.app.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.google.gson.reflect.TypeToken;
import com.ng.demo.R;
import com.ng.demo.ui.fragment.demo.CurrentData;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPathView2 extends View {
    private List<PointBean> points = new ArrayList<>();
    private List<LineBean> lines = new ArrayList<>();
    private Map<String, PointBean> pointBeanMap = new HashMap<>();
    private float scaling = 0.560f;//缩放系数
    //float scaling =(1f-0.39130434f);//缩放系数
    private float[] drawPoints;
    private Path path;
    private Paint paint;
    private Bitmap bitmap;
    private Bitmap mBitmap;
    private int mOffsetX, mOffsetY; // 图片的中间位置
    private PathMeasure mPathMeasure;
    private float mPathLength;
    private static final int INVALIDATE_TIMES = 1000 * 2; //总共执行100次
    private double mStep;            //distance each step
    private float mDistance;        //distance moved

    private float[] mPos;
    private float[] mTan;

    private Matrix mMatrix;

    public MyPathView2(Context context) {
        super(context);
    }

    public MyPathView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        paint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.car);
        mBitmap = rotateBitmap(mBitmap, 90);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.map);
        bitmap = ImageUtils.scale(bitmap, ScreenUtils.getScreenWidth(),
                bitmap.getHeight() * ScreenUtils.getScreenWidth() / bitmap.getWidth(),
                false);

        paint.setColor(Color.parseColor("#ff0000"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setTextSize(36);
        paint.setAntiAlias(true);

        try {
            Reader reader1 = new InputStreamReader(getResources().getAssets().open("B3Point.json"));
            points = GsonUtils.fromJson(reader1, new TypeToken<List<PointBean>>() {
            }.getType());
            Reader reader2 = new InputStreamReader(getResources().getAssets().open("B3Line.json"));
            lines = GsonUtils.fromJson(reader2, new TypeToken<List<LineBean>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        drawPoints = new float[points.size() * 2];

        mPathMeasure = new PathMeasure();
        mPos = new float[2];
        mTan = new float[2];
        mMatrix = new Matrix();

    }

    public MyPathView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyPathView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        转换点位();
        int index = 0;
        for (PointBean point : points) {
            pointBeanMap.put(point.id, point);
            drawPoints[index++] = point.x * scaling;
            drawPoints[index++] = point.y * scaling;
        }
        /*for (int i = 0; i < points.size(); i++) {
            pointBeanMap.put(points.get(i).id, points.get(i));
            try {
                drawPoints[index++] = points.get(i).x * scaling;
                drawPoints[index++] = points.get(i).y * scaling;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        path.moveTo(pointBeanMap.get(lines.get(0).sId).x * scaling,
                pointBeanMap.get(lines.get(0).sId).y * scaling);
        for (LineBean lineBean : lines) {
            try {
                if (lineBean.lineType == 0) {
                    /*path.moveTo(pointBeanMap.get(lineBean.sId).x * scaling,
                            pointBeanMap.get(lineBean.sId).y * scaling);*/
                    path.lineTo(pointBeanMap.get(lineBean.eId).x * scaling,
                            pointBeanMap.get(lineBean.eId).y * scaling);
                } else {
                    float x1 = pointBeanMap.get(lineBean.sId).x - lineBean.o.pointBean.x;
                    float x2 = pointBeanMap.get(lineBean.eId).x - lineBean.o.pointBean.x;
                    float y1 = pointBeanMap.get(lineBean.sId).y - lineBean.o.pointBean.y;
                    float y2 = pointBeanMap.get(lineBean.eId).y - lineBean.o.pointBean.y;
                    double value = (x1 * x2 + y1 * y2) /
                            (Math.sqrt(x1 * x1 + y1 * y1)
                                    * Math.sqrt(x2 * x2 + y2 * y2)); // 余弦值

                    //int startAngle = (int) (180 / Math.PI * Math.atan2(y1, x1));//翻转前起始角
                    int startAngle = (int) (180 / Math.PI * Math.atan2(y2, x2));//结束角
                    //int endAngle = (int) (180 / Math.PI * Math.atan2(y2, x2));//结束角
                    float endAngle = (float) Math.toDegrees(Math.acos(value));   // 角度

                    LogUtils.e("startAngle:" + startAngle + "---endAngle:" + endAngle);
                    if (startAngle + 90 > 180) {
                        startAngle -= 270;
                    } else {
                        startAngle += 90;
                    }
                    path.arcTo((lineBean.o.pointBean.x - lineBean.o.r) * scaling,
                            (lineBean.o.pointBean.y - lineBean.o.r) * scaling,
                            (lineBean.o.pointBean.x + lineBean.o.r) * scaling,
                            (lineBean.o.pointBean.y + lineBean.o.r) * scaling,
                            //pointBeanMap.get(lineBean.eId).angle,
                            /*endAngle,
                            startAngle);*/
                            startAngle,
                            -endAngle, false);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        path.close();
        mOffsetX = mBitmap.getWidth() / 2;
        mOffsetY = mBitmap.getHeight() / 2;
        mPathMeasure.setPath(path, true);
        mPathLength = mPathMeasure.getLength();

        mStep = mPathLength / INVALIDATE_TIMES;
        mDistance = mPathLength;
    }

    public void start() {
        if (mDistance < mPathLength) {
            mDistance = mPathLength;
        } else {
            mDistance = 0;
        }
        invalidate();
    }

    String json =
            "{\"data\":[{\"actionCode\":0,\"automaticDrivingMode\":1,\"dGear\":1,\"directionAngle\":100,\"heading\":180,\"locationX\":-1460,\"locationY\":0,\"nGear\":0,\"overTime\":0,\"pGear\":0,\"rGear\":0,\"routeId\":1,\"routeProportion\":938,\"shutdownState\":0,\"soc\":96,\"speed\":50,\"uploadTime\":1645000741739,\"vin\":\"test001\"},{\"actionCode\":0,\"automaticDrivingMode\":1,\"dGear\":1,\"directionAngle\":100,\"heading\":180,\"locationX\":-1460,\"locationY\":0,\"nGear\":0,\"overTime\":0,\"pGear\":0,\"rGear\":0,\"routeId\":1,\"routeProportion\":938,\"shutdownState\":0,\"soc\":96,\"speed\":50,\"uploadTime\":1645000741739,\"vin\":\"test002\"},{\"actionCode\":0,\"automaticDrivingMode\":1,\"dGear\":1,\"directionAngle\":100,\"heading\":180,\"locationX\":-1460,\"locationY\":0,\"nGear\":0,\"overTime\":0,\"pGear\":0,\"rGear\":0,\"routeId\":1,\"routeProportion\":938,\"shutdownState\":0,\"soc\":96,\"speed\":50,\"uploadTime\":1645000741739,\"vin\":\"test003\"},{\"actionCode\":0,\"automaticDrivingMode\":1,\"dGear\":1,\"directionAngle\":100,\"heading\":180,\"locationX\":-1460,\"locationY\":0,\"nGear\":0,\"overTime\":0,\"pGear\":0,\"rGear\":0,\"routeId\":1,\"routeProportion\":938,\"shutdownState\":0,\"soc\":96,\"speed\":50,\"uploadTime\":1645000741739,\"vin\":\"test004\"},{\"actionCode\":0,\"automaticDrivingMode\":1,\"dGear\":1,\"directionAngle\":100,\"heading\":90,\"locationX\":400,\"locationY\":-2786,\"nGear\":0,\"overTime\":0,\"pGear\":0,\"rGear\":0,\"routeId\":1,\"routeProportion\":8072,\"shutdownState\":0,\"soc\":96,\"speed\":50,\"uploadTime\":1644999368548,\"vin\":\"test005\"},{\"actionCode\":0,\"automaticDrivingMode\":1,\"dGear\":1,\"directionAngle\":100,\"heading\":90,\"locationX\":400,\"locationY\":-2786,\"nGear\":0,\"overTime\":0,\"pGear\":0,\"rGear\":0,\"routeId\":1,\"routeProportion\":8072,\"shutdownState\":0,\"soc\":96,\"speed\":50,\"uploadTime\":1644999368549,\"vin\":\"test006\"}],\"type\":\"currentData\"}";
    List<CurrentData> currentDataList = new ArrayList<>();
    //List<CurrentData> currentDataList = JSON.parseArray(JSON.toJSONString(JSON.parseObject(json).get("data")), CurrentData.class);

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.translate((Math.abs(minX) + 200) * scaling, (Math.abs(minY) + 200) * scaling);
        canvas.translate((Math.abs(minX) + 200) * scaling, (Math.abs(minY) - 2 * (maxY - minY) - 200) * scaling);
        //canvas.translate(100, 100);
        try {

            try {
                //canvas.drawBitmap(bitmap, 0, 0, paint);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (mDistance < 0 || true) {
                paint.setColor(Color.GRAY);
                paint.setStrokeWidth(400 * scaling);
                canvas.drawPath(path, paint);

                paint.setColor(Color.GREEN);
                paint.setStrokeWidth(4);
                canvas.drawPath(path, paint);

                paint.setColor(Color.RED);
                for (int i = 0; i + 1 < drawPoints.length; i += 2) {
                    paint.setStrokeWidth(20);
                    canvas.drawPoint(drawPoints[i], drawPoints[i + 1], paint);
                    paint.setStrokeWidth(2);
                    canvas.drawText("p_" + i / 2, drawPoints[i], drawPoints[i + 1], paint);
                    //LogUtils.d(i / 2 + "p");
                }
                //canvas.drawPoints(drawPoints, paint);
                //canvas.save();
            }

            /*if (mDistance < mPathLength) {
                mPathMeasure.getPosTan(mDistance, mPos, mTan);
                mMatrix.reset();
                mMatrix.postTranslate(-mOffsetX, -mOffsetY);
                float degrees = (float) (Math.atan2(mTan[1], mTan[0]) * 180.0 / Math.PI);
                mMatrix.postRotate(degrees);
                mMatrix.postTranslate(mPos[0], mPos[1]);
                canvas.drawBitmap(mBitmap, mMatrix, null);
                mDistance += mStep;
                invalidate();
            } else {
                canvas.drawBitmap(mBitmap, mMatrix, null);
                mDistance = 0;
                invalidate();
            }*/

            for (CurrentData currentData : currentDataList) {
                LogUtils.d("X:" + (moveW - currentData.getLocationX()) * scaling + "\nY:" + (moveH - currentData.getLocationY()) * scaling);
                mBitmap = rotateBitmap(mBitmap, currentData.getHeading());
                canvas.drawBitmap(mBitmap,
                        (moveW - currentData.getLocationX()) * scaling - 2 * ((Math.abs(minX) + 200) * scaling) - mBitmap.getWidth() / 2,
                        (moveH - currentData.getLocationY()) * scaling - mBitmap.getHeight() / 2,
                        paint);
                canvas.drawText(currentData.getVin(), (moveW - currentData.getLocationX()) * scaling - 2 * ((Math.abs(minX) + 200) * scaling) - mBitmap.getWidth() / 2,
                        (moveH - currentData.getLocationY()) * scaling - mBitmap.getHeight() / 2,
                        paint);
                mBitmap = rotateBitmap(mBitmap, -currentData.getHeading());
                //invalidate();
            }
            //canvas.drawBitmap(bitmap,-(Math.abs(minX) + 200) * scaling, -(Math.abs(minY) - 2 * (maxY - minY) - 200) * scaling,paint);
            //invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    float maxX = 0;
    float maxY = 0;
    float minX = 0;
    float minY = 0;
    float moveW;
    float moveH;

    private void 转换点位() {
        if (points.size() > 0) {
            for (PointBean point : points) {
                maxX = Math.max(maxX, point.x);
                maxY = Math.max(maxY, point.y);
                minX = Math.min(minX, point.x);
                minY = Math.min(minY, point.y);
            }
            moveW = maxX - minX + 400;
            moveH = maxY - minY + 400;
            //float move = Math.min(minX, minY);

            scaling = getMeasuredWidth() * 1f / moveW;

            for (PointBean point : points) {
                //point.x -= (minX - 200);
                //point.y -= (minY - 200);

                point.y = moveH - point.y;
                /*if (point.angle<0){
                    point.angle = Math.abs(point.angle)+180;
                }*/
                //point.angle = -point.angle + 90;
            }

            for (LineBean line : lines) {
                if (line.lineType == 1) {
                    //line.o.pointBean.x -= (minX - 200);
                    //line.o.pointBean.y -= (minY - 200);
                    line.o.pointBean.y = moveH - line.o.pointBean.y;
                }
            }
        }
    }

    public void addData(@NotNull List<CurrentData> parseArray) {
        currentDataList = parseArray;
        invalidate();
    }

    public static class LineBean implements Serializable {
        public int l;
        public O o;
        public int w;
        public String eId;
        public String id;
        public int lineType;
        public int angle;
        public String sId;

        public static class O implements Serializable {
            public PointBean pointBean;
            public int r;
        }
    }

    public static class PointBean implements Serializable {
        public String id;
        public int angle;
        public float x;
        public float y;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {

        if (bitmap != null) {

            Matrix m = new Matrix();

            m.postRotate(degress);

            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m,

                    true);

            return bitmap;

        }

        return bitmap;
    }
}
