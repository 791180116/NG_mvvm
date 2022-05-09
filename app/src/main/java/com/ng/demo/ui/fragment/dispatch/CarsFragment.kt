package com.ng.demo.ui.fragment.dispatch

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ConvertUtils
import com.ng.demo.R
import com.ng.demo.app.base.BaseFragment
import com.ng.demo.app.ext.init
import com.ng.demo.app.weight.recyclerview.SpaceItemDecoration
import com.ng.demo.databinding.FragmentCarsBinding
import com.ng.demo.ui.adapter.CarAdapter
import com.ng.demo.viewmodel.request.RequestCarsViewModel
import com.ng.demo.viewmodel.state.CarsViewModel
import kotlinx.android.synthetic.main.include_recyclerview.*

class CarsFragment : BaseFragment<CarsViewModel, FragmentCarsBinding>() {
    private val carAdapter: CarAdapter by lazy { CarAdapter(arrayListOf()) }
    private val requestCarsViewModel: RequestCarsViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_cars

    override fun initView(savedInstanceState: Bundle?) {
        swipeRefresh.isEnabled = false

        //初始化recyclerView
        recyclerView.init(LinearLayoutManager(context), carAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            /*it.initFooter(SwipeRecyclerView.LoadMoreListener {
                mViewModel.getCollectAriticleData(false)
            })*/
            //初始化FloatingActionButton
            //it.initFloatBtn(floatbtn)
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        //mViewModel.getCarList()
        requestCarsViewModel.getCarList()
    }

    override fun createObserver() {
        super.createObserver()
        /*mViewModel.carBeans.observe(this, {
            //taskAdapter.data = it
            carAdapter.setList(it)
        })*/

        requestCarsViewModel.carBeans.observe(this, {
            //taskAdapter.data = it
            carAdapter.setList(it)
        })
    }

}