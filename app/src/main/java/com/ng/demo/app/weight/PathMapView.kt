package com.ng.demo.app.weight

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import java.io.Serializable
import kotlin.math.*

class PathMapView : View {
    private var points: List<PointBean> = ArrayList()
    private var lines: List<LineBean> = ArrayList()
    private var pointBeanMap: HashMap<String, PointBean> = hashMapOf()
    private var scaling = 0.560f //缩放系数

    //float scaling =(1f-0.39130434f);//缩放系数
    private lateinit var drawPoints: FloatArray
    private var path: Path? = null
    private var paint: Paint? = null
    private var bitmap: Bitmap? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        path = Path()
        paint = Paint()

        /*bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.map);
        bitmap = ImageUtils.scale(bitmap, ScreenUtils.getScreenWidth(),
                bitmap.getHeight() * ScreenUtils.getScreenWidth() / bitmap.getWidth(),
                false);*/

        paint!!.color = Color.parseColor("#ff0000")
        paint!!.style = Paint.Style.STROKE
        paint!!.strokeWidth = 20f
        paint!!.strokeCap = Paint.Cap.ROUND
        paint!!.textSize = 36f
        paint!!.isAntiAlias = true

        try {
            val reader1: Reader = InputStreamReader(resources.assets.open("B3Point.json"))
            points = GsonUtils.fromJson(
                reader1,
                object : TypeToken<List<PointBean?>?>() {}.type
            )
            val reader2: Reader = InputStreamReader(resources.assets.open("B3Line.json"))
            lines = GsonUtils.fromJson(
                reader2,
                object : TypeToken<List<LineBean?>?>() {}.type
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }
        drawPoints = FloatArray(points.size * 2)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        dataConversion()
        var index = 0
        for (point in points) {
            pointBeanMap[point.id!!] = point
            drawPoints[index++] = point.x * scaling
            drawPoints[index++] = point.y * scaling
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
        /*for (int i = 0; i < points.size(); i++) {
            pointBeanMap.put(points.get(i).id, points.get(i));
            try {
                drawPoints[index++] = points.get(i).x * scaling;
                drawPoints[index++] = points.get(i).y * scaling;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        for (lineBean in lines) {
            try {
                if (lineBean.lineType == 0) {
                    path!!.moveTo(
                        pointBeanMap[lineBean.sId]!!.x * scaling,
                        pointBeanMap[lineBean.sId]!!.y * scaling
                    )
                    path!!.lineTo(
                        pointBeanMap[lineBean.eId]!!.x * scaling,
                        pointBeanMap[lineBean.eId]!!.y * scaling
                    )
                } else {
                    val x1 = pointBeanMap[lineBean.sId]!!.x - lineBean.o?.pointBean!!.x
                    val x2 = pointBeanMap[lineBean.eId]!!.x - lineBean.o?.pointBean!!.x
                    val y1 = pointBeanMap[lineBean.sId]!!.y - lineBean.o?.pointBean!!.y
                    val y2 = pointBeanMap[lineBean.eId]!!.y - lineBean.o?.pointBean!!.y
                    val value = (x1 * x2 + y1 * y2) /
                            (sqrt((x1 * x1 + y1 * y1).toDouble())
                                    * sqrt((x2 * x2 + y2 * y2).toDouble())) // 余弦值

                    //int startAngle = (int) (180 / Math.PI * Math.atan2(y1, x1));//翻转前起始角
                    val startAngle =
                        (180 / Math.PI * atan2(y2.toDouble(), x2.toDouble())).toInt() //结束角
                    //int endAngle = (int) (180 / Math.PI * Math.atan2(y2, x2));//结束角
                    val endAngle =
                        Math.toDegrees(acos(value)).toFloat() // 角度
                    LogUtils.e("startAngle:$startAngle---endAngle:$endAngle")
                    path!!.addArc(
                        (lineBean.o?.pointBean?.x!! - lineBean.o?.r!!) * scaling,
                        (lineBean.o?.pointBean?.y!! - lineBean.o?.r!!) * scaling,
                        (lineBean.o?.pointBean?.x!! + lineBean.o?.r!!) * scaling,
                        (lineBean.o?.pointBean?.y!! + lineBean.o?.r!!) * scaling,
                        //pointBeanMap.get(lineBean.eId).angle,
                        /*endAngle,
                            startAngle);*/
                        startAngle.toFloat(),
                        endAngle
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //canvas.translate(100, 100);
        try {
            try {
                //canvas.drawBitmap(bitmap, 0, 0, paint);
            } catch (e: Exception) {
                e.printStackTrace()
            }
            paint!!.color = Color.GRAY
            paint!!.strokeWidth = 400 * scaling
            canvas!!.drawPath(path!!, paint!!)
            paint!!.color = Color.GREEN
            paint!!.strokeWidth = 4f
            canvas.drawPath(path!!, paint!!)
            paint!!.color = Color.RED
            var i = 0
            while (i + 1 < drawPoints.size) {
                paint!!.strokeWidth = 20f
                canvas.drawPoint(drawPoints[i], drawPoints[i + 1], paint!!)
                paint!!.strokeWidth = 2f
                canvas.drawText("p_" + i / 2, drawPoints[i], drawPoints[i + 1], paint!!)
                i += 2
            }
            //canvas.drawPoints(drawPoints, paint);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private var maxX = 0f
    private var maxY = 0f
    private var minX = 0f
    private var minY = 0f

    /**
     * 数据转换
     */
    private fun dataConversion() {
        if (points.isNotEmpty()) {
            for (point in points) {
                maxX = max(maxX, point.x)
                maxY = max(maxY, point.y)
                minX = min(minX, point.x)
                minY = min(minY, point.y)
            }
            val moveW = maxX - minX + 400
            val moveH = maxY - minY + 400
            //float move = Math.min(minX, minY);

            //scaling = ScreenUtils.getScreenWidth() * 1f / moveW;
            scaling = measuredWidth * 1f / moveW
            for (point in points) {
                point.x -= minX - 200
                point.y -= minY - 200
                point.y = moveH - point.y
                /*if (point.angle<0){
                    point.angle = Math.abs(point.angle)+180;
                }*/
                //point.angle = -point.angle + 90;
            }
            for (line in lines) {
                if (line.lineType == 1) {
                    line.o!!.pointBean!!.x -= minX - 200
                    line.o!!.pointBean!!.y -= minY - 200
                    line.o!!.pointBean!!.y = moveH - line.o!!.pointBean!!.y
                }
            }
        }
    }

    class LineBean : Serializable {
        var l = 0
        var o: O? = null
        var w = 0
        var eId: String? = null
        var id: String? = null
        var lineType = 0
        var angle = 0
        var sId: String? = null

        class O : Serializable {
            var pointBean: PointBean? = null
            var r = 0
        }
    }

    class PointBean : Serializable {
        var id: String? = null
        var angle = 0
        var x = 0f
        var y = 0f
    }
}