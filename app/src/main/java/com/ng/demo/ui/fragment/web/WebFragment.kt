package com.ng.demo.ui.fragment.web

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.webkit.JsResult
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import com.just.agentweb.AgentWeb
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.ng.demo.R
import com.ng.demo.app.base.BaseFragment
import com.ng.demo.app.ext.hideSoftKeyboard
import com.ng.demo.app.ext.initClose
import com.ng.demo.databinding.FragmentWebBinding
import com.ng.demo.viewmodel.state.WebViewModel
import kotlinx.android.synthetic.main.fragment_web.*
import kotlinx.android.synthetic.main.include_toolbar.*
import ng.crazy.jetpackmvvm.ext.nav
import java.io.IOException


/**
 * 描述　:
 */
@Suppress("DEPRECATED_IDENTITY_EQUALS")
class WebFragment : BaseFragment<WebViewModel, FragmentWebBinding>() {

    private var mAgentWeb: AgentWeb? = null

    private var preWeb: AgentWeb.PreAgentWeb? = null

    override fun layoutId() = R.layout.fragment_web

    override fun initView(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        arguments?.run {
            //点击文章进来的
            mViewModel.url = getString("url", "")
            /*//点击首页轮播图进来的
            getParcelable<BannerResponse>("bannerdata")?.let {
                mViewModel.ariticleId = it.id
                mViewModel.showTitle = it.title
                //从首页轮播图 没法判断是否已经收藏过，所以直接默认没有收藏
                mViewModel.collect = false
                mViewModel.url = it.url
                mViewModel.collectType = CollectType.Url.type
            }
            //从收藏文章列表点进来的
            getParcelable<CollectResponse>("collect")?.let {
                mViewModel.ariticleId = it.originId
                mViewModel.showTitle = it.title
                //从收藏列表过来的，肯定 是 true 了
                mViewModel.collect = true
                mViewModel.url = it.link
                mViewModel.collectType = CollectType.Ariticle.type
            }
            //点击收藏网址列表进来的
            getParcelable<CollectUrlResponse>("collectUrl")?.let {
                mViewModel.ariticleId = it.id
                mViewModel.showTitle = it.name
                //从收藏列表过来的，肯定 是 true 了
                mViewModel.collect = true
                mViewModel.url = it.link
                mViewModel.collectType = CollectType.Url.type
            }*/
        }
        toolbar.run {
            //设置menu 关键代码
            mActivity.setSupportActionBar(this)
            initClose(mViewModel.showTitle) {
                hideSoftKeyboard(activity)
                mAgentWeb?.let { web ->
                    if (web.webCreator.webView.canGoBack()) {
                        web.webCreator.webView.goBack()
                    } else {
                        nav().navigateUp()
                    }
                }
            }
        }
        preWeb = AgentWeb.with(this)
            .setAgentWebParent(
                webcontent,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            .useDefaultIndicator()
            .setWebViewClient(object : WebViewClient() {
                override fun shouldInterceptRequest(
                    view: WebView,
                    url: String
                ): WebResourceResponse? {
                    Log.e("url", "url=$url") //

                    if (url.contains("new_file.js")) { //加载指定.js时 引导服务端加载本地Assets/www文件夹下的cordova.js
                        try {
                            return WebResourceResponse(
                                "application/x-javascript",
                                "utf-8",
                                context?.getAssets()?.open("js/new_file.js")
                            )
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    } else if (ng.crazy.jetpackmvvm.network.NetworkUtil.url.contains("style.css")) {
                        /*try {
                            return WebResourceResponse(
                                "text/css",
                                "utf-8",
                                context?.getAssets()?.open("css/style.css")
                            )
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }*/
                    }
                    return super.shouldInterceptRequest(view, url)
                }
            })
            .setWebChromeClient(object : WebChromeClient() {
                override fun onJsBeforeUnload(
                    view: WebView?,
                    url: String?,
                    message: String?,
                    result: JsResult?
                ): Boolean {
                    return super.onJsBeforeUnload(view, url, message, result)
                }
            })
            .createAgentWeb()
            .ready()
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun lazyLoadData() {
        //加载网页
        mAgentWeb = preWeb?.go(mViewModel.url)
        //mAgentWeb = preWeb?.go("www.baidu.com")
        /*mAgentWeb?.agentWebSettings?.webSettings?.javaScriptEnabled = true
        mAgentWeb?.agentWebSettings?.webSettings?.allowContentAccess = true
        mAgentWeb?.agentWebSettings?.webSettings?.allowFileAccess = true*/
        mAgentWeb?.agentWebSettings?.webSettings?.builtInZoomControls = true
        mAgentWeb?.agentWebSettings?.webSettings?.displayZoomControls = true
        mAgentWeb?.agentWebSettings?.webSettings?.useWideViewPort = true
        mAgentWeb?.agentWebSettings?.webSettings?.loadWithOverviewMode = true
        mAgentWeb?.agentWebSettings?.webSettings?.cacheMode = WebSettings.LOAD_NO_CACHE

        /*val screenDensity = resources.displayMetrics.densityDpi
        var zoomDensity: WebSettings.ZoomDensity = WebSettings.ZoomDensity.FAR
        when (screenDensity) {
            DisplayMetrics.DENSITY_LOW -> zoomDensity = WebSettings.ZoomDensity.CLOSE
            DisplayMetrics.DENSITY_MEDIUM -> zoomDensity = WebSettings.ZoomDensity.MEDIUM
            DisplayMetrics.DENSITY_HIGH -> zoomDensity = WebSettings.ZoomDensity.FAR
        }
        mAgentWeb?.agentWebSettings?.webSettings?.setDefaultZoom(zoomDensity)*/

        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    mAgentWeb?.let { web ->
                        if (web.webCreator.webView.canGoBack()) {
                            web.webCreator.webView.goBack()
                        } else {
                            nav().navigateUp()
                        }
                    }
                }
            })
    }

    override fun createObserver() {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.web_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        //如果收藏了，右上角的图标相对应改变
        context?.let {
            /*if (mViewModel.collect) {
                menu.findItem(R.id.web_collect).icon =
                    ContextCompat.getDrawable(it, R.drawable.ic_collected)
            } else {
                menu.findItem(R.id.web_collect).icon =
                    ContextCompat.getDrawable(it, R.drawable.ic_collect)
            }*/
        }

        return super.onPrepareOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.web_share -> {
                //分享
                startActivity(Intent.createChooser(Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "{${mViewModel.showTitle}}:${mViewModel.url}")
                    type = "text/plain"
                }, "分享到"))
            }
            R.id.web_refresh -> {
                //刷新网页
                mAgentWeb?.urlLoader?.reload()
            }

            R.id.web_liulanqi -> {
                //用浏览器打开
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://" + mViewModel.url)))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        mAgentWeb?.webLifeCycle?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb?.webLifeCycle?.onDestroy()
        mActivity.setSupportActionBar(null)
        super.onDestroy()
    }

}