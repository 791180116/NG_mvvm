package com.ng.demo.app.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.google.gson.reflect.TypeToken;
import com.ng.demo.R;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPathView extends View {
    List<PointBean> points = new ArrayList<>();
    List<LineBean> lines = new ArrayList<>();
    Map<String, PointBean> pointBeanMap = new HashMap<>();
    float scaling = 0.560f;//缩放系数
    //float scaling =(1f-0.39130434f);//缩放系数
    float[] drawPoints;
    Path path;
    Paint paint;
    Bitmap bitmap;

    public MyPathView(Context context) {
        super(context);
    }

    public MyPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        paint = new Paint();

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
    }

    public MyPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        drawPoints = new float[points.size() * 2];
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
        for (LineBean lineBean : lines) {
            try {
                if (lineBean.lineType == 0) {
                    path.moveTo(pointBeanMap.get(lineBean.sId).x * scaling,
                            pointBeanMap.get(lineBean.sId).y * scaling);
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

                    path.addArc(new RectF((lineBean.o.pointBean.x - lineBean.o.r) * scaling,
                                    (lineBean.o.pointBean.y - lineBean.o.r) * scaling,
                                    (lineBean.o.pointBean.x + lineBean.o.r) * scaling,
                                    (lineBean.o.pointBean.y + lineBean.o.r) * scaling),
                            //pointBeanMap.get(lineBean.eId).angle,
                            /*endAngle,
                            startAngle);*/
                            startAngle,
                            endAngle);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.translate(100, 100);
        try {

            try {
                canvas.drawBitmap(bitmap, 0, 0, paint);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            }
            //canvas.drawPoints(drawPoints, paint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    float maxX = 0;
    float maxY = 0;
    float minX = 0;
    float minY = 0;

    private void 转换点位() {
        if (points.size() > 0) {
            for (PointBean point : points) {
                maxX = Math.max(maxX, point.x);
                maxY = Math.max(maxY, point.y);
                minX = Math.min(minX, point.x);
                minY = Math.min(minY, point.y);
                /*if (point.x > maxX) {
                    maxX = point.x;
                }
                if (point.y > maxY) {
                    maxY = point.y;
                }
                if (point.x < minX) {
                    minX = point.x;
                }
                if (point.x < minY) {
                    minY = point.y;
                }*/
            }
            float moveW = maxX - minX + 400;
            float moveH = maxY - minY + 400;
            //float move = Math.min(minX, minY);

            //scaling = ScreenUtils.getScreenWidth() * 1f / moveW;
            scaling = getMeasuredWidth() * 1f / moveW;

            for (PointBean point : points) {
                point.x -= (minX - 200);
                point.y -= (minY - 200);

                point.y = moveH - point.y;
                /*if (point.angle<0){
                    point.angle = Math.abs(point.angle)+180;
                }*/
                //point.angle = -point.angle + 90;
            }

            for (LineBean line : lines) {
                if (line.lineType == 1) {
                    line.o.pointBean.x -= (minX - 200);
                    line.o.pointBean.y -= (minY - 200);
                    line.o.pointBean.y = moveH - line.o.pointBean.y;
                }
            }
        }
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
}
