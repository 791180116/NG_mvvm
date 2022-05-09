package com.ng.demo.ui.fragment.dispatch

import android.os.Bundle
import android.view.View
import android.widget.Switch
import androidx.lifecycle.MutableLiveData
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.customListAdapter
import com.afollestad.materialdialogs.list.listItems
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ng.demo.R
import com.ng.demo.app.base.BaseFragment
import com.ng.demo.app.ext.toast
import com.ng.demo.data.model.bean.CreateTaskBean
import com.ng.demo.databinding.CreateTaskFragmentBinding
import com.ng.demo.viewmodel.request.RequestCreateTaskViewModel
import kotlinx.android.synthetic.main.create_task_fragment.*
import kotlinx.android.synthetic.main.include_toolbar.*
import ng.crazy.jetpackmvvm.ext.nav
import ng.crazy.jetpackmvvm.ext.parseState
import java.text.SimpleDateFormat
import java.util.*

class CreateTaskFragment : BaseFragment<RequestCreateTaskViewModel, CreateTaskFragmentBinding>() {
    private val sdf: SimpleDateFormat = SimpleDateFormat("yyyy年MM月dd日 HH:mm")
    private var time: Long = 0L
    private var vin: String = ""
    var createTaskBean: MutableLiveData<CreateTaskBean> = MutableLiveData();

    override fun layoutId(): Int = R.layout.create_task_fragment

    override fun initView(savedInstanceState: Bundle?) {
        toolbar.title = "添加任务"

        switch_delayed.run {
            switch_delayed.setOnCheckedChangeListener { _, isChecked ->
                //sb_select_time.visibility = if (isChecked) View.VISIBLE else View.GONE
            }
        }

        mDatabind.click = CreateTaskClick()
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        //mViewModel.getDispatchTask()
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.createTaskBean.observe(this, {
            context?.let { it1 ->
                MaterialDialog(it1).show {
                    title(null, "选择任务")

                    customListAdapter(object :
                        BaseQuickAdapter<CreateTaskBean, BaseViewHolder>(R.layout.md_listitem, it) {
                        override fun convert(holder: BaseViewHolder, item: CreateTaskBean) {
                            holder.setText(R.id.md_title, item.name)
                        }

                    }.also { it2 ->
                        it2.setOnItemClickListener() { adapter, view, position ->
                            createTaskBean.value = it[position]
                            mDatabind.sbSelectTask.leftText = it[position].name
                            dismiss()
                        }
                    })
                    /*listItems() { _, index, text ->
                        mDatabind.sbSelectTask.leftText = text
                    }*/

                    //debugMode(debugMode)
                    lifecycleOwner(this@CreateTaskFragment)
                }
            }
        })

        mViewModel.result.observe(this, {
            parseState(it, {
                try {
                    nav().navigateUp()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }, {
                toast(it.errorMsg)
            })
        })

        mViewModel.vinData.observe(this, {
            parseState(it, { data ->
                context?.let { it1 ->
                    MaterialDialog(it1).show {
                        title(null, "选择车辆")
                        listItems(items = data) { _, index, text ->
                            vin = text.toString()
                            mDatabind.sbSelectCar.leftText = text
                        }
                        //debugMode(debugMode)
                        lifecycleOwner(this@CreateTaskFragment)
                    }
                }
            })

        })
    }


    inner class CreateTaskClick {
        fun selectCar() {
            mViewModel.getVinList()
        }

        fun selectTask() {
            mViewModel.getDispatchTask()
        }

        fun delayedRun(view: View) {
            if (view is Switch) {
                sb_select_time.visibility = if (view.isChecked) {
                    View.VISIBLE
                } else {
                    time = 0L
                    mDatabind.sbSelectTime.leftText = ""
                    View.GONE
                }
            }
        }

        fun selectTime() {
            context?.let {
                /*val dialog = MaterialDialog(it, MaterialDialog.DEFAULT_BEHAVIOR)
                dialog.title(null, "选择任务执行时间")
                dialog.dateTimePicker(
                    Calendar.getInstance(), null, Calendar.getInstance(),
                    false, true
                ) { materialDialog, dateTime ->
                    time = dateTime.timeInMillis
                    mDatabind.sbSelectTime.leftText = sdf.format(time)
                    null
                }*/
                /*dialog.positiveButton(R.string.select, null) { materialDialog: MaterialDialog? ->
                    Toast.makeText(it, getText(R.string.select), Toast.LENGTH_SHORT).show()
                    null
                }
                dialog.negativeButton(R.string.cancel, null) { materialDialog: MaterialDialog? ->
                    Toast.makeText(it, getText(R.string.cancel), Toast.LENGTH_SHORT).show()
                    null
                }*/
                //.show()

                MaterialDialog(it, MaterialDialog.DEFAULT_BEHAVIOR).show {
                    title(null, "选择任务执行时间")
                    dateTimePicker(
                        Calendar.getInstance(),
                        null,
                        Calendar.getInstance(),
                        false,
                        true
                    ) { _, dateTime ->
                        time = dateTime.timeInMillis
                        mDatabind.sbSelectTime.leftText = sdf.format(time)
                    }
                }
            }
        }

        fun addVehicleTask() {
            if (vin.isEmpty()) {
                toast("请选择车辆")
                return
            } else if (createTaskBean.value == null) {
                toast("请选择任务")
                return
            }
            mViewModel.addVehicleTask(
                if (mDatabind.switchDelayed.isChecked) time else 0,
                createTaskBean.value?.id.toString(),
                createTaskBean.value?.type.toString(),
                vin
            )
        }
    }
}