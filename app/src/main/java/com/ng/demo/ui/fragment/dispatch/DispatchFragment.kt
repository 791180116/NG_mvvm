package com.ng.demo.ui.fragment.dispatch

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebViewClient
import com.ng.demo.R
import com.ng.demo.app.appViewModel
import com.ng.demo.app.base.BaseFragment
import com.ng.demo.app.ext.bindViewPager2
import com.ng.demo.app.ext.init
import com.ng.demo.app.ext.setUiTheme
import com.ng.demo.app.util.CacheUtil
import com.ng.demo.app.weight.MyPathView2
import com.ng.demo.databinding.FragmentDispatchBinding
import com.ng.demo.viewmodel.state.DispatchViewModel
import kotlinx.android.synthetic.main.fragment_dispatch.*
import kotlinx.android.synthetic.main.include_viewpager.*
import ng.crazy.jetpackmvvm.ext.nav
import ng.crazy.jetpackmvvm.ext.navigateAction
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class DispatchFragment : BaseFragment<DispatchViewModel, FragmentDispatchBinding>() {
    val tabs = arrayListOf("任务", "车辆")
    val tabs1 = arrayListOf("序号", "ID", "状态", "车辆键值", "进度", "差值")
    val tabs2 = arrayListOf("序号", "名称", "状态", "电量", "里程[km]")
    private var fragments: ArrayList<Fragment> = arrayListOf()

    init {
        fragments.add(TaskFragment())
        fragments.add(CarsFragment())
    }

    override fun layoutId(): Int = R.layout.fragment_dispatch
    lateinit var agentWeb: AgentWeb

    companion object {
        fun newInstance() = DispatchFragment()
    }

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    override fun initView(savedInstanceState: Bundle?) {

        appViewModel.appColor.value?.let { setUiTheme(it, viewpager_linear) }
        include_viewpager_toolbar.run {
            inflateMenu(R.menu.add_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.item_add -> {
                        if (CacheUtil.isLogin()) {
                            //toast("添加o(*￣︶￣*)o")
                            nav().navigate(R.id.action_mainFragment_to_createTaskFragment)
                        } else {
                            nav().navigateAction(R.id.action_to_loginFragment)
                        }
                    }
                }
                true
            }
        }

        ll_web_content.apply {
            layoutParams = layoutParams.apply {
                width = ScreenUtils.getScreenWidth()
                height = width * 14 / 16
            }
            /*addView(TextView(context).apply {
                gravity = Gravity.CENTER
                //setBackgroundColor(resources.getColor(R.color.white))
                //width = ViewGroup.LayoutParams.WRAP_CONTENT
                //height = ViewGroup.LayoutParams.WRAP_CONTENT
            })*/
            layoutParams.run {
                tv_info.text = "宽度：${width}\n高度：${height}"
                LogUtils.d("${tv_info.text};${SizeUtils.dp2px(340f)}")
            }

            setOnClickListener {
                agentWeb.urlLoader?.reload()
            }
        }

        agentWeb = AgentWeb.with(this).setAgentWebParent(
            ll_web_content,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            ).apply {
                //setMargins(2, 2, 2, 2)
            }).useDefaultIndicator()
            .setWebViewClient(object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return super.shouldOverrideUrlLoading(view, request)
                }
            })
            .createAgentWeb()
            .ready()
            //.go("172.16.3.194:8080/web/")
            //.go("http://172.16.3.188:8090/")
            //.go("file:///android_asset/web/index1.html")
            .go("file:///android_asset/dist/index.html?deptId=${appViewModel.userInfo?.value?.dept_id}")

        agentWeb.agentWebSettings.apply {
            webSettings?.let {
                webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
                webSettings.javaScriptEnabled = true

                try { //本地HTML里面有跨域的请求 原生webview需要设置之后才能实现跨域请求
                    if (Build.VERSION.SDK_INT >= 16) {
                        val clazz: Class<*> = webSettings.javaClass
                        val method: Method? = clazz.getMethod(
                            "setAllowUniversalAccessFromFileURLs", Boolean::class.javaPrimitiveType
                        )
                        if (method != null) {
                            method.invoke(webSettings, true)
                        }
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
                //agentWeb.urlLoader.loadUrl("file:///android_asset/web/index1.html")
                /*webView.loadUrl("file:///android_asset/HtmlFileTT/index.html")
                webView.setWebViewClient(object : WebViewClient() {
                    fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        return super.shouldOverrideUrlLoading(view, request)
                    }
                })*/
            }
        }

        mDatabind.click = DispatchClick()
    }

    override fun lazyLoadData() {
        //初始化viewpager2
        view_pager.init(this, fragments).offscreenPageLimit = fragments.size
        //初始化 magic_indicator
        magic_indicator.bindViewPager2(view_pager, mStringList = tabs) {
            if (it != 0) {
                include_viewpager_toolbar.menu.clear()
            } else {
                include_viewpager_toolbar.menu.hasVisibleItems().let { flag ->
                    if (!flag) include_viewpager_toolbar.inflateMenu(R.menu.add_menu)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        agentWeb?.let {
            it.urlLoader.loadUrl("file:///android_asset/dist/index.html?deptId=${appViewModel.userInfo?.value?.dept_id}")
        }
    }

    override fun onDestroyView() {
        try {
            agentWeb?.let {
                it.destroy()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onDestroyView()
    }

    inner class DispatchClick {
        fun goWeb() {
            nav().navigateAction(R.id.action_to_webFragment, Bundle().apply {
                putString("url", "172.16.3.194:8080/web/")
            })
        }

        fun start() {
            pathAnimView.start()
        }

        fun start(view: View) {
            view as MyPathView2
            view.start()
        }
    }
}