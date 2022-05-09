package com.ng.demo.ui.fragment.login

import android.os.Bundle
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ng.demo.R
import com.ng.demo.app.appViewModel
import com.ng.demo.app.base.BaseFragment
import com.ng.demo.app.ext.showMessage
import com.ng.demo.app.util.CacheUtil
import com.ng.demo.databinding.FragmentLoginBinding
import com.ng.demo.viewmodel.request.RequestLoginRegisterViewModel
import com.ng.demo.viewmodel.state.LoginRegisterViewModel
import ng.crazy.jetpackmvvm.ext.nav
import ng.crazy.jetpackmvvm.ext.parseState

/**
 * 描述　: 登录页面
 */
class LoginFragment : BaseFragment<LoginRegisterViewModel, FragmentLoginBinding>() {

    private val requestLoginRegisterViewModel: RequestLoginRegisterViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_login

    override fun initView(savedInstanceState: Bundle?) {
        addLoadingObserve(requestLoginRegisterViewModel)
        mDatabind.viewmodel = mViewModel

        mDatabind.click = ProxyClick()
    }

    override fun createObserver() {
        requestLoginRegisterViewModel.loginResult.observe(
            viewLifecycleOwner,
            Observer { resultState ->
                parseState(resultState, {
                    //登录成功 通知账户数据发生改变鸟
                    CacheUtil.setUser(it)
                    CacheUtil.setIsLogin(true)
                    appViewModel.userInfo.value = it
                    nav().navigate(R.id.action_loginFragment_to_mainFragment)
                }, {
                    //登录失败
                    showMessage(it.errorMsg)
                })
            })
    }

    inner class ProxyClick {

        fun clear() {
            mViewModel.username.set("")
        }

        fun login() {
            when {
                mViewModel.username.get().isEmpty() -> showMessage("请填写账号")
                mViewModel.password.get().isEmpty() -> showMessage("请填写密码")
                else -> requestLoginRegisterViewModel.loginReq(
                    mViewModel.username.get(),
                    mViewModel.password.get()
                )
            }
        }

        var onCheckedChangeListener =
            CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                mViewModel.isShowPwd.set(isChecked)
            }
    }

    /**
     *$ git push origin main
    Logon failed, use ctrl+c to cancel basic credential prompt.
    Username for 'https://github.com': 791180016@qq.com
    error: unable to read askpass response from 'C:/Program Files/Git/mingw64/libexec/git-core/git-gui--askpass'
    Password for 'https://791180016@qq.com@github.com':
    remote: Support for password authentication was removed on August 13, 2021. Please use a personal access token instead.
    remote: Please see https://github.blog/2020-12-15-token-authentication-requirements-for-git-operations/ for more information.
    fatal: Authentication failed for 'https://github.com/791180116/NG_mvvm.git/'
     *  */
}