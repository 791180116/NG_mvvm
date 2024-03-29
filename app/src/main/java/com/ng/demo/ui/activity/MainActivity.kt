package com.ng.demo.ui.activity

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.blankj.utilcode.util.ToastUtils
import com.ng.demo.R
import com.ng.demo.app.appViewModel
import com.ng.demo.app.base.BaseActivity
import com.ng.demo.app.util.CacheUtil
import com.ng.demo.app.util.StatusBarUtil
import com.ng.demo.data.model.bean.UserInfo
import com.ng.demo.databinding.ActivityMainBinding
import com.ng.demo.viewmodel.state.MainViewModel
import ng.crazy.jetpackmvvm.network.manager.NetState
import kotlin.system.exitProcess

/**
 * 项目主页Activity
 */
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    var exitTime = 0L

    override fun initView(savedInstanceState: Bundle?) {
        //进入首页检查更新
        //Beta.checkUpgrade(false, true)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val nav = Navigation.findNavController(this@MainActivity, R.id.host_fragment)
                if (nav.currentDestination != null && nav.currentDestination!!.id != R.id.mainFragment
                    && nav.currentDestination!!.id != R.id.loginFragment
                ) {
                    //如果当前界面不是主页或者登录页，那么直接调用返回即可
                    nav.navigateUp()
                } else {
                    //是主页
                    if (System.currentTimeMillis() - exitTime > 2000) {
                        ToastUtils.showShort("再按一次退出程序")
                        exitTime = System.currentTimeMillis()
                    } else {
                        exitProcess(0)
                        //finish()
                    }
                }
            }
        })
        appViewModel.appColor.value?.let {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
            supportActionBar?.setBackgroundDrawable(ColorDrawable(it))
            StatusBarUtil.setColor(this, it, 0)
        }

        模拟用户数据()

        isLogin()
    }


    /**
     * 模拟登录成功缓存用户信息
     */
    private fun 模拟用户数据() {
        CacheUtil.setUser(UserInfo().apply {
            id = "123"
            name = "123"
            dept_id = "123"
            token = "123"
            permissions = listOf()
            lon = "123"
            lat = "123"
            level = "123"
        })
        appViewModel.userInfo.value = CacheUtil.getUser()
    }

    private fun isLogin() {
        try {
            if (appViewModel.userInfo.value != null && !TextUtils.isEmpty(appViewModel.userInfo.value?.dept_id)) {
                Navigation.findNavController(this@MainActivity, R.id.host_fragment)
                    .navigate(R.id.action_loginFragment_to_mainFragment)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun createObserver() {
        appViewModel.appColor.observeInActivity(this, Observer {
            supportActionBar?.setBackgroundDrawable(ColorDrawable(it))
            StatusBarUtil.setColor(this, it, 0)
        })
    }

    /**
     * 示例，在Activity/Fragment中如果想监听网络变化，可重写onNetworkStateChanged该方法
     */
    override fun onNetworkStateChanged(netState: NetState) {
        super.onNetworkStateChanged(netState)
        if (netState.isSuccess) {
            Toast.makeText(applicationContext, "我终于有网了啊!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "我怎么断网了!", Toast.LENGTH_SHORT).show()
        }
    }

}
