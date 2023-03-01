package com.ng.demo.ui.fragment.dispatch

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.blankj.utilcode.util.ConvertUtils
import com.ng.demo.R
import com.ng.demo.app.base.BaseFragment
import com.ng.demo.app.ext.init
import com.ng.demo.app.ext.toast
import com.ng.demo.app.weight.recyclerview.SpaceItemDecoration
import com.ng.demo.databinding.FragmentTaskBinding
import com.ng.demo.ui.adapter.TaskAdapter
import com.ng.demo.viewmodel.request.RequestTaskViewModel
import com.ng.demo.viewmodel.state.TaskViewModel
import kotlinx.android.synthetic.main.include_recyclerview.*
import ng.crazy.jetpackmvvm.ext.parseState

class TaskFragment : BaseFragment<TaskViewModel, FragmentTaskBinding>() {
    val status = arrayListOf("未开始", "执行", "暂停", "终止", "完成")
    private val taskAdapter: TaskAdapter by lazy { TaskAdapter(arrayListOf()) }
    private val requestTaskViewModel: RequestTaskViewModel by viewModels()

    @SuppressLint("CheckResult")
    override fun initView(savedInstanceState: Bundle?) {
        swipeRefresh.isEnabled = false

        //初始化recyclerView
        recyclerView.init(LinearLayoutManager(context), taskAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, ConvertUtils.dp2px(8f)))
            /*it.initFooter(SwipeRecyclerView.LoadMoreListener {
                mViewModel.getCollectAriticleData(false)
            })*/
            //初始化FloatingActionButton
            //it.initFloatBtn(floatbtn)
        }

        taskAdapter.run {
            setOnItemClickListener { adapter, view, position ->
                context?.let {
                    MaterialDialog(it).show() {
                        title(null, "任务编辑")
                        //setContentView(R.layout.dialog_edit_task)
                        message(
                            null,
                            "任务ID：${data[position].id}\n车辆键值：${data[position].vin}\n状态：${data[position].statusCN}"
                        )
                        listItemsSingleChoice(
                            null,
                            status,
                            intArrayOf(0, data[position].status, 4),
                            -1,
                            true
                        ) { materialDialog, index, text ->
                            requestTaskViewModel.modifyVehicleTask(data[position].id, index)
                            dismiss()
                        }

                        positiveButton(null, "编辑") { }
                        negativeButton(null, "取消") { dismiss() }
                    }
                }
            }
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        //requestTaskViewModel.getTaskList()
        //mViewModel.getTaskList()
    }

    override fun onResume() {
        super.onResume()
        requestTaskViewModel.getTaskList()
    }

    override fun createObserver() {
        super.createObserver()
        /*mViewModel.taskBeans.observe(this, {
            //taskAdapter.data = it
            taskAdapter.setList(it)
        })*/
        requestTaskViewModel.taskBeans.observe(this, {
            //taskAdapter.data = it
            taskAdapter.setList(it)
        })
        requestTaskViewModel.result.observe(this, {
            parseState(it, {
                try {
                    requestTaskViewModel.getTaskList()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, { it1 ->
                toast(it1.errorMsg)
            })
        })
    }
}