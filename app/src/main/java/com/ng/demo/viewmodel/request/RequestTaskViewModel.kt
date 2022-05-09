package com.ng.demo.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.ng.demo.app.appViewModel
import com.ng.demo.app.ext.toast
import com.ng.demo.app.network.apiService
import com.ng.demo.data.model.bean.TaskBean
import ng.crazy.jetpackmvvm.base.viewmodel.BaseViewModel
import ng.crazy.jetpackmvvm.ext.request
import ng.crazy.jetpackmvvm.state.ResultState
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * 描述　: 有两种回调方式：
 * 1.首页文章列表 将返回的数据放在ViewModel中过滤包装给activity/fragment去使用
 * 2.首页轮播图 将返回的数据直接给activity/fragment去处理使用
 * 可以根据个人理解与喜好使用(建议 简单的不需要做数据过滤包装的能直接用返回数据的可以直接用2   复杂的需要自己封装一下让使用变的更方便的可以使用1  )
 */
class RequestTaskViewModel : BaseViewModel() {
    var taskBeans: MutableLiveData<MutableList<TaskBean>> = MutableLiveData()
    var result = MutableLiveData<ResultState<Any?>>()

    fun getTaskList() {
        appViewModel.userInfo.value?.let { userInfo ->
            request({ apiService.getVehicleTask(userInfo.dept_id) }, {
                taskBeans.value = it
            }, {
                toast(it.errorMsg)
                LogUtils.e(it.errorLog)
            })
        }
    }

    fun modifyVehicleTask(id: Int, status: Int) {
        request(
            {
                val body = HashMap<String, Int>()
                body["id"] = id
                body["status"] = status
                apiService.modifyVehicleTask(
                    RequestBody.create(
                        MediaType.parse("application/json;charset=utf-8"),
                        GsonUtils.toJson(body)
                    )
                )
            },//请求体
            result,//请求的返回结果，请求成功与否都会改变该值，在Activity或fragment中监听回调结果，具体可看loginActivity中的回调
            true,//是否显示等待框，，默认false不显示 可以默认不传
            "正在修改任务..."//等待框内容，可以默认不填请求网络中...
        )
    }
}