package com.ng.demo.ui.fragment.mine

import androidx.lifecycle.MutableLiveData
import com.ng.demo.app.util.CacheUtil
import com.ng.demo.data.model.bean.UserInfo
import ng.crazy.jetpackmvvm.base.viewmodel.BaseViewModel
import ng.crazy.jetpackmvvm.ext.nav

class MineViewModel : BaseViewModel() {
    var userInfo: MutableLiveData<UserInfo> = MutableLiveData<UserInfo>(CacheUtil.getUser())

    fun loginOut(fragment: MineFragment) {
        CacheUtil.setUser(null)
        userInfo.value = CacheUtil.getUser()
        fragment.nav().navigateUp()
        //nav(view).navigateAction(R.id.action_to_loginFragment)
    }
}