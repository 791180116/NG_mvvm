package com.ng.demo.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItems
import com.ng.demo.R
import com.ng.demo.app.appViewModel
import com.ng.demo.app.base.BaseFragment
import com.ng.demo.app.ext.init
import com.ng.demo.app.ext.initMain
import com.ng.demo.app.ext.interceptLongClick
import com.ng.demo.app.ext.setUiTheme
import com.ng.demo.databinding.FragmentMainBinding
import com.ng.demo.viewmodel.request.RequestMainViewModel
import com.ng.demo.viewmodel.state.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.include_toolbar.*
import ng.crazy.jetpackmvvm.ext.parseState


/**
 * 描述　:项目主页Fragment
 */
class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>() {
    //请求数据ViewModel
    private val requestMainViewModel: RequestMainViewModel by viewModels()

    override fun layoutId() = R.layout.fragment_main

    override fun initView(savedInstanceState: Bundle?) {
        //初始化 toolbar
        toolbar.run {
            init("请选择车辆")
            inflateMenu(R.menu.home_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.home_switch_car -> {
                        //切换车
                        mDialog?.show()
                    }
                }
                true
            }
            toolbar.setOnClickListener {
                mDialog?.show()
            }
        }
        //初始化viewpager2
        mainViewpager.initMain(this)
        //初始化 bottomBar
        mainBottom.init {
            when (it) {
                R.id.menu_robot -> {
                    mainViewpager.setCurrentItem(0, false)
                    toolbar.visibility = View.VISIBLE
                }
                R.id.menu_task -> {
                    toolbar.visibility = View.GONE
                    mainViewpager.setCurrentItem(1, false)
                }
                R.id.menu_mine -> {
                    toolbar.visibility = View.GONE
                    mainViewpager.setCurrentItem(2, false)
                }
            }
        }
        mainBottom.interceptLongClick(R.id.menu_robot, R.id.menu_task, R.id.menu_mine)

        initVinDialog()
    }

    var mDialog: MaterialDialog? = null
    private fun initVinDialog() {
        mDialog = context?.let {
            MaterialDialog(
                it,
                MaterialDialog.DEFAULT_BEHAVIOR
            )//BottomSheet(LayoutMode.WRAP_CONTENT)
                .title(null, "请选择车辆")
                .lifecycleOwner(this)
        }
        /*mBuilder.titleGravity(GravityEnum.CENTER)
        mBuilder.items(stocks)
        mBuilder.autoDismiss(true)
        mBuilder.itemsCallback(object : ListCallback() {
            fun onSelection(
                dialog: MaterialDialog?,
                itemView: View?,
                position: Int,
                text: CharSequence?
            ) {
                Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
            }
        })
        mMaterialDialog = mBuilder.build()
        mMaterialDialog.show()*/
    }

    @SuppressLint("CheckResult")
    override fun createObserver() {
        requestMainViewModel.run {
            //监听car列表请求的数据变化
            vinData.observe(viewLifecycleOwner, Observer { resultState ->
                parseState(resultState, { data ->
                    mDialog?.let {
                        /*it.listItems(null, data, null, false, object : ItemListener {
                            override fun invoke(
                                dialog: MaterialDialog,
                                index: Int,
                                text: CharSequence
                            ) {
                                vin.value = text.toString()
                                toolbar.title = vin.value
                            }

                        })*/
                        //it.positiveButton(null, "确定")
                        //it.negativeButton(null, "取消")
                        it.listItems(items = data) { _, _, text ->
                            vin.value = text.toString()
                            toolbar.title = vin.value
                        }
                    }
                })
            })
        }

        appViewModel.run {
            //监听账户信息是否改变 有值时(登录)将相关的数据设置为已收藏，为空时(退出登录)，将已收藏的数据变为未收藏
            userInfo.observeInFragment(this@MainFragment, Observer {
                if (it != null) {
                    requestMainViewModel.getVinList()
                }
            })
            //监听全局的主题颜色改变
            appColor.observeInFragment(this@MainFragment) {
                //setUiTheme(it, toolbar, floatbtn, swipeRefresh, loadsir, footView)
                setUiTheme(it, mainBottom)
            }
            //监听全局的列表动画改编
            appAnimation.observeInFragment(this@MainFragment) {
                //articleAdapter.setAdapterAnimation(it)
            }
        }
    }

    override fun lazyLoadData() {
        //请求car列表数据
        requestMainViewModel.getVinList()
    }

    companion object {
        var vin: MutableLiveData<String> = MutableLiveData("")
    }

    override fun onDestroy() {
        vin.value = ""
        super.onDestroy()
    }
}

fun getVin(): MutableLiveData<String> {
    return MainFragment.vin
}