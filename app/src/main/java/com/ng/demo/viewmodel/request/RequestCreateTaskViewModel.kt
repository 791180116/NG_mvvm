package com.ng.demo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.ng.demo.app.appViewModel
import com.ng.demo.app.ext.toast
import com.ng.demo.app.network.apiService
import com.ng.demo.data.model.bean.CreateTaskBean
import ng.crazy.jetpackmvvm.base.viewmodel.BaseViewModel
import ng.crazy.jetpackmvvm.ext.request
import ng.crazy.jetpackmvvm.state.ResultState
import okhttp3.MediaType
import okhttp3.RequestBody

class RequestCreateTaskViewModel : BaseViewModel() {
    //car列表数据
    var vinData: MutableLiveData<ResultState<ArrayList<String>>> = MutableLiveData()

    var createTaskBean: MutableLiveData<MutableList<CreateTaskBean>> = MutableLiveData()
    var result = MutableLiveData<ResultState<Any?>>()
    fun getDispatchTask() {
        appViewModel.userInfo.value?.let { userInfo ->
            request({ apiService.getDispatchTask(userInfo.dept_id) }, {
                createTaskBean.value = it
            }, {
                toast(it.errorMsg)
                LogUtils.e(it.errorLog)
            })
        }

    }

    /**
     * 获取车辆列表数据
     */
    fun getVinList() {
        appViewModel.userInfo.value?.let {
            request({ apiService.getVinsByDeptId(it.dept_id) }, vinData)
        }
    }

    /**
    {
    "startTime": "2022-01-18T03:14:43.086Z",
    "taskId": 0,
    "taskType": 0,
    "vin": "string"
    }
     */
    fun addVehicleTask(startTime: Long, taskId: String, taskType: String, vin: String) {
        request(
            {
                val body = HashMap<String, String>()
                if (startTime > 0) body["startTime"] = startTime.toString()
                body["taskId"] = taskId
                body["taskType"] = taskType
                body["vin"] = vin
                apiService.addVehicleTask(
                    RequestBody.create(
                        MediaType.parse("application/json;charset=utf-8"),
                        GsonUtils.toJson(body)
                    )
                )
            },//请求体
            result,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
            true,//是否显示等待框，，默认false不显示 可以默认不传
            "正在创建任务..."//等待框内容，可以默认不填请求网络中...
        )
    }
}