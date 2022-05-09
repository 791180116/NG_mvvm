package com.ng.demo.viewmodel.request

import android.text.TextUtils
import android.widget.CompoundButton
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.ng.demo.app.ext.toast
import com.ng.demo.app.network.apiService
import com.ng.demo.data.model.bean.VehicleDataBean
import com.ng.demo.data.model.enums.ControlEnum
import ng.crazy.jetpackmvvm.base.viewmodel.BaseViewModel
import ng.crazy.jetpackmvvm.ext.request

/**
 * 描述　: 有两种回调方式：
 * 1.首页文章列表 将返回的数据放在ViewModel中过滤包装给activity/fragment去使用
 * 2.首页轮播图 将返回的数据直接给activity/fragment去处理使用
 * 可以根据个人理解与喜好使用(建议 简单的不需要做数据过滤包装的能直接用返回数据的可以直接用2   复杂的需要自己封装一下让使用变的更方便的可以使用1  )
 */
class RequestHomeViewModel : BaseViewModel() {
    val url =
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fuploads.chinatimes.net.cn%2Farticle%2F202105%2F20210506224905Hxxr5X4GSJ.jpg&refer=http%3A%2F%2Fuploads.chinatimes.net.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1643873873&t=70ee6a19168ac3e2a40027a4d72273ba"
    var vehicleDataBean: MutableLiveData<VehicleDataBean> = MutableLiveData()

    /**
     * 获取首页文章列表数据
     * @param isRefresh 是否是刷新，即第一页
     */
    fun getHomeData(isRefresh: Boolean) {
        /*if (isRefresh) {
            pageNo = 0
        }
        request({ HttpRequestCoroutine.getHomeData(pageNo) }, {
            //请求成功
            pageNo++
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it.isEmpty(),
                    hasMore = it.hasMore(),
                    isFirstEmpty = isRefresh && it.isEmpty(),
                    listData = it.datas
                )
            homeDataState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<AriticleResponse>()
                )
            homeDataState.value = listDataUiState
        })*/
    }

    fun getCarDataByVin(vin: String) {
        request({ apiService.getLatestBehicleDataByVin(vin) }, {
            vehicleDataBean.value = it
        }, {
            toast(it.errorMsg)
            LogUtils.e(it.errorLog)
        })
    }

    fun controlV2(
        carId: String,
        controlId: String,
        param: String
    ) {
        request({ apiService.setControlV2(carId, controlId, param) }, {
            ToastUtils.showShort("发送成功")
        }, {
            ToastUtils.showShort(it.errorMsg)
        })
    }

    fun controlV2(
        carId: String,
        controlEnum: ControlEnum,
        compoundButton: CompoundButton
    ) {
        if (TextUtils.isEmpty(carId)) {
            toast("请先选择车辆！")
            compoundButton.isChecked = controlEnum.param != 1
            return
        }
        request({
            apiService.setControlV2(
                carId,
                controlEnum.controlId.toString(),
                controlEnum.param.toString()
            )
        }, {
            ToastUtils.showShort("发送成功")
            compoundButton.isChecked = controlEnum.param == 1
        }, {
            ToastUtils.showShort(it.errorMsg)
            compoundButton.isChecked = controlEnum.param != 1
        }, true, "正在发送中...")
    }
}