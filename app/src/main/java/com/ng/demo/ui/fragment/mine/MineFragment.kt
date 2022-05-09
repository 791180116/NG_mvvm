package com.ng.demo.ui.fragment.mine

import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.LogUtils
import com.ng.demo.R
import com.ng.demo.app.base.BaseFragment
import com.ng.demo.databinding.FragmentMineBinding
import ng.crazy.jetpackmvvm.ext.nav
import ng.crazy.jetpackmvvm.ext.navigateAction

class MineFragment : BaseFragment<MineViewModel, FragmentMineBinding>() {
    var dialog: MaterialDialog? = null

    companion object {
        fun newInstance() = MineFragment()
    }

    override fun layoutId(): Int = R.layout.fragment_mine

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewModel = mViewModel
        mDatabind.click = MineClick()
        /*dialog = context?.let {
            MaterialDialog(it, MaterialDialog.DEFAULT_BEHAVIOR)
                .title(null, "是否退出登录")
                //.cancelOnTouchOutside(false)
                .cancelable(false)
                .positiveButton {
                    mViewModel.loginOut(this)
                    //nav().navigateAction(R.id.action_to_loginFragment)
                }
                .negativeButton(null, "取消") {
                    //ToastUtils.showShort("123")
                }
        }*/
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.userInfo.observe(this, {
            LogUtils.d(it)
        })
    }


    inner class MineClick {
        fun demo() {
            nav().navigateAction(R.id.action_to_demoFragment)
        }

        fun loginOut() {
            //dialog?.show()
            MaterialDialog(context!!).show {
                title(null, "是否退出登录")
                cancelable(false)
                positiveButton(null, "退出") {
                    mViewModel.loginOut(this@MineFragment)
                }
                negativeButton(null, "取消") { }
            }
        }
    }
}