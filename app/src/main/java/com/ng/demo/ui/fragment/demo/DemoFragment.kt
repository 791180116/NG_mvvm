package com.ng.demo.ui.fragment.demo

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.LogUtils
import com.ng.demo.R
import com.ng.demo.app.base.BaseFragment
import com.ng.demo.databinding.FragmentDemoBinding
import kotlinx.android.synthetic.main.fragment_demo.*
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.util.*

class DemoFragment : BaseFragment<DemoViewModel, FragmentDemoBinding>() {
    var json =
        "{\"data\":[{\"actionCode\":0,\"automaticDrivingMode\":1,\"dGear\":1,\"directionAngle\":100,\"heading\":180,\"locationX\":-1460,\"locationY\":0,\"nGear\":0,\"overTime\":0,\"pGear\":0,\"rGear\":0,\"routeId\":1,\"routeProportion\":938,\"shutdownState\":0,\"soc\":96,\"speed\":50,\"uploadTime\":1645000741739,\"vin\":\"test001\"},{\"actionCode\":0,\"automaticDrivingMode\":1,\"dGear\":1,\"directionAngle\":100,\"heading\":180,\"locationX\":-1460,\"locationY\":0,\"nGear\":0,\"overTime\":0,\"pGear\":0,\"rGear\":0,\"routeId\":1,\"routeProportion\":938,\"shutdownState\":0,\"soc\":96,\"speed\":50,\"uploadTime\":1645000741739,\"vin\":\"test002\"},{\"actionCode\":0,\"automaticDrivingMode\":1,\"dGear\":1,\"directionAngle\":100,\"heading\":180,\"locationX\":-1460,\"locationY\":0,\"nGear\":0,\"overTime\":0,\"pGear\":0,\"rGear\":0,\"routeId\":1,\"routeProportion\":938,\"shutdownState\":0,\"soc\":96,\"speed\":50,\"uploadTime\":1645000741739,\"vin\":\"test003\"},{\"actionCode\":0,\"automaticDrivingMode\":1,\"dGear\":1,\"directionAngle\":100,\"heading\":180,\"locationX\":-1460,\"locationY\":0,\"nGear\":0,\"overTime\":0,\"pGear\":0,\"rGear\":0,\"routeId\":1,\"routeProportion\":938,\"shutdownState\":0,\"soc\":96,\"speed\":50,\"uploadTime\":1645000741739,\"vin\":\"test004\"},{\"actionCode\":0,\"automaticDrivingMode\":1,\"dGear\":1,\"directionAngle\":100,\"heading\":90,\"locationX\":400,\"locationY\":-2786,\"nGear\":0,\"overTime\":0,\"pGear\":0,\"rGear\":0,\"routeId\":1,\"routeProportion\":8072,\"shutdownState\":0,\"soc\":96,\"speed\":50,\"uploadTime\":1644999368548,\"vin\":\"test005\"},{\"actionCode\":0,\"automaticDrivingMode\":1,\"dGear\":1,\"directionAngle\":100,\"heading\":90,\"locationX\":400,\"locationY\":-2786,\"nGear\":0,\"overTime\":0,\"pGear\":0,\"rGear\":0,\"routeId\":1,\"routeProportion\":8072,\"shutdownState\":0,\"soc\":96,\"speed\":50,\"uploadTime\":1644999368549,\"vin\":\"test006\"}],\"type\":\"currentData\"}"
    var timer = Timer();
    var timerTask = object : TimerTask() {
        override fun run() {
            my_path?.addData(
                JSON.parseArray(
                    JSON.toJSONString(JSON.parseObject(json)["data"]),
                    CurrentData::class.java
                )
            )
        }
    }

    companion object {
        fun newInstance() = DemoFragment()
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView(savedInstanceState: Bundle?) {

        /*try { //本地HTML里面有跨域的请求 原生webView需要设置之后才能实现跨域请求
            if (Build.VERSION.SDK_INT >= 16) {
                val clazz: Class<*> = webView.settings.javaClass
                val method: Method? = clazz.getMethod(
                    "setAllowUniversalAccessFromFileURLs", Boolean::class.javaPrimitiveType
                )
                method?.invoke(webView.settings, true)
            }
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        webView.settings.defaultTextEncodingName = "utf-8"
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.settings.javaScriptEnabled = true
        //webView.loadUrl("file:///android_asset/web/index1.html")
        //webView.loadUrl("http://172.16.3.188:8090/")
        webView.loadUrl("file:///android_asset/dist/index.html?deptId=724")
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }
        }*/
        getData()
        //timer.schedule(timerTask,0,10000)
    }

    var url = "ws://192.144.218.30:28080/realTimePoint"

    //var url = "ws://172.16.2.251:18443/realTimePoint"
    var webSocket: WebSocketClient? = null
    fun getData() {
        //var webSocket:WebSocket = WebSocket.Factory()
        webSocket =
            object : WebSocketClient(
                URI.create(url),
                Draft_6455()
            ) {
                override fun onOpen(handshakedata: ServerHandshake?) {
                    LogUtils.d("onOpen:$handshakedata")
                    webSocket?.send("{\"cmd\":\"init\",\"deptId\":10}")
                }

                override fun onMessage(message: String?) {
                    LogUtils.d("onMessage:$message")
                    var msgData = JSON.parseObject(message)
                    /*var msgData: MsgData? = try {
                        JSON.parseObject(message, MsgData::class.java)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        null
                    }*/
                    when (msgData["type"]) {
                        "vehicle" -> {//车辆

                        }
                        "dispatchTask" -> {//默认任务（命令）列表

                        }
                        "vehicleTask" -> {//正在执行的任务列表

                        }
                        "currentData" -> {//实时数据
                            try {
                                my_path.addData(
                                    JSON.parseArray(
                                        JSON.toJSONString(msgData["data"]),
                                        CurrentData::class.java
                                    )
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        else -> {
                            Log.d("unknow msg", msgData["data"].toString());
                        }
                    }
                }

                override fun onClose(code: Int, reason: String?, remote: Boolean) {
                    LogUtils.d("onClose:${reason}")
                }

                override fun onError(ex: java.lang.Exception?) {
                    LogUtils.d("onError:${ex.toString()}")
                }

            }
        webSocket?.connectBlocking()

    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
    }

    override fun onDestroyView() {
        try {
            webView?.let {
                it.stopLoading()
                it.clearHistory()
                it.removeAllViews()
                it.settings.javaScriptEnabled = false
                it.destroy()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onDestroyView()
    }

    override fun onDestroy() {
        webSocket?.close()
        timer.cancel()
        super.onDestroy()
    }
}