package com.ng.demo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.ng.demo.app.appViewModel
import com.ng.demo.app.network.apiService
import ng.crazy.jetpackmvvm.base.viewmodel.BaseViewModel
import ng.crazy.jetpackmvvm.ext.request
import ng.crazy.jetpackmvvm.state.ResultState

/**
 * 描述　:
 */
class RequestMainViewModel : BaseViewModel() {
    //car列表数据
    var vinData: MutableLiveData<ResultState<ArrayList<String>>> = MutableLiveData()

    /**
     * 获取车辆列表数据
     */
    fun getVinList() {
        appViewModel.userInfo.value?.let {
            request({ apiService.getVinsByDeptId(it.dept_id) }, vinData)
        }
    }
}