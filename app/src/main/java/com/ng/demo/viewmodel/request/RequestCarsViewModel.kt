package com.ng.demo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.ng.demo.app.appViewModel
import com.ng.demo.app.ext.toast
import com.ng.demo.app.network.apiService
import com.ng.demo.data.model.bean.CarBean
import ng.crazy.jetpackmvvm.base.viewmodel.BaseViewModel
import ng.crazy.jetpackmvvm.ext.request

/**
 * 描述　: 有两种回调方式：
 * 1.首页文章列表 将返回的数据放在ViewModel中过滤包装给activity/fragment去使用
 * 2.首页轮播图 将返回的数据直接给activity/fragment去处理使用
 * 可以根据个人理解与喜好使用(建议 简单的不需要做数据过滤包装的能直接用返回数据的可以直接用2   复杂的需要自己封装一下让使用变的更方便的可以使用1  )
 */
class RequestCarsViewModel : BaseViewModel() {
    var carBeans: MutableLiveData<MutableList<CarBean>> = MutableLiveData()

    fun getCarList() {
        appViewModel.userInfo.value?.let { userInfo ->
            request({ apiService.getVehicleByDeptId(userInfo.dept_id) }, {
                carBeans.value = it
            }, {
                toast(it.errorMsg)
                LogUtils.e(it.errorLog)
            })
        }

    }
}