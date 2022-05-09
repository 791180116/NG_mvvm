package com.ng.demo.ui.fragment.home

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import com.kingja.loadsir.core.LoadService
import com.ng.demo.R
import com.ng.demo.app.base.BaseFragment
import com.ng.demo.app.weight.recyclerview.DefineLoadMoreView
import com.ng.demo.data.model.enums.ControlEnum
import com.ng.demo.databinding.FragmentHomeBinding
import com.ng.demo.ui.fragment.getVin
import com.ng.demo.viewmodel.request.RequestHomeViewModel
import com.ng.demo.viewmodel.state.HomeViewModel
import java.util.*
import kotlin.concurrent.timerTask

/**
 * 首页Fragment
 */
class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    private val freshTime = 5000L  //状态刷新间隔

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    //recyclerview的底部加载view 因为在首页要动态改变他的颜色，所以加了他这个字段
    private lateinit var footView: DefineLoadMoreView

    //请求数据ViewModel
    private val requestHomeViewModel: RequestHomeViewModel by viewModels()

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun layoutId() = R.layout.fragment_home

    override fun initView(savedInstanceState: Bundle?) {
        //状态页配置
        /*loadsir = loadServiceInit(swipeRefresh) {
            //点击重试时触发的操作
            //loadsir.showLoading()
            if (!TextUtils.isEmpty(getVin().value!!)) {
                requestHomeViewModel.getCarDataByVin(getVin().value!!)
            }
        }*/

        //初始化 SwipeRefreshLayout
        /*swipeRefresh.init {
            //触发刷新监听时请求数据
            if (!TextUtils.isEmpty(getVin().value!!)) {
                requestHomeViewModel.getCarDataByVin(getVin().value!!)
            }
        }*/

        mDatabind.click = HomeClick()
        mDatabind.vehicle = requestHomeViewModel

        addLoadingObserve(requestHomeViewModel)
    }

    /**
     * 懒加载
     */
    override fun lazyLoadData() {
        //设置界面 加载中
        //loadsir.showLoading()
        //请求car列表数据
        //请求文章列表数据
        //requestHomeViewModel.getCarDataByVin(getVin())
    }

    override fun createObserver() {
        requestHomeViewModel.run {
            //监听car列表请求的数据变化
            vehicleDataBean.observe(viewLifecycleOwner, { data ->
                //swipeRefresh.isRefreshing = false
                data?.let {
                    getVehicle()
                }
            })
        }

        getVin().observe(viewLifecycleOwner, {
            if (!TextUtils.isEmpty(it)) {
                mDatabind.tvVin.text = it
                //刷新接口信息
                requestHomeViewModel.getCarDataByVin(it)
            }
        })
    }

    var timer: Timer = Timer()
    var isRunning = false
    var isTimerStart = false
    private fun getVehicle() {
        if (!isTimerStart) {
            isTimerStart = true
            timer.schedule(timerTask {
                getVin().value?.let {
                    if (isRunning) {
                        requestHomeViewModel.getCarDataByVin(it)
                    }
                }
            }, freshTime, freshTime)
        }
    }

    override fun onPause() {
        super.onPause()
        isRunning = false
    }

    override fun onResume() {
        super.onResume()
        isRunning = true
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    inner class HomeClick {
        fun stopCar() {//19 急停控制 （0关闭 1开启）
            //requestHomeViewModel.controlV2(getVin().value!!, "19", "0")
            context.run { }
        }

        fun stopCar(view: View) {
            if (view is CompoundButton) {
                requestHomeViewModel.controlV2(
                    getVin().value!!,
                    ControlEnum.CarStop.setParamSwitch(view.isChecked),
                    view
                )
            }
        }

        fun cAlarmLight(view: View) {
            if (view is CompoundButton) {
                requestHomeViewModel.controlV2(
                    getVin().value!!,
                    ControlEnum.AlarmLight.setParamSwitch(view.isChecked),
                    view
                )
            }
        }

        fun cAlarmWhistle(view: View) {
            if (view is CompoundButton) {
                requestHomeViewModel.controlV2(
                    getVin().value!!,
                    ControlEnum.AlarmWhistle.setParamSwitch(view.isChecked),
                    view
                )
            }
        }

        fun cCharge(view: View) {
            if (view is CompoundButton) {
                requestHomeViewModel.controlV2(
                    getVin().value!!,
                    ControlEnum.ChargeOrWork.setParamSwitch(view.isChecked),
                    view
                )
            }
        }

        fun cWork(view: View) {
            if (view is CompoundButton) {
                requestHomeViewModel.controlV2(
                    getVin().value!!,
                    ControlEnum.ChargeOrWork.setParamSwitch(view.isChecked),
                    view
                )
            }
        }

        fun cHeadLight(view: View) {
            if (view is CompoundButton) {
                requestHomeViewModel.controlV2(
                    getVin().value!!,
                    ControlEnum.HeadLight.setParamSwitch(view.isChecked),
                    view
                )
            }
        }

        fun cBackLight(view: View) {
            if (view is CompoundButton) {
                requestHomeViewModel.controlV2(
                    getVin().value!!,
                    ControlEnum.BackLight.setParamSwitch(view.isChecked),
                    view
                )
            }
        }

        fun cFloodlight(view: View) {
            if (view is CompoundButton) {
                requestHomeViewModel.controlV2(
                    getVin().value!!,
                    ControlEnum.Floodlight.setParamSwitch(view.isChecked),
                    view
                )
            }
        }
    }
}