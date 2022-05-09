package com.ng.demo.viewmodel.state

import androidx.lifecycle.MutableLiveData
import com.ng.demo.data.model.bean.TaskBean
import ng.crazy.jetpackmvvm.base.viewmodel.BaseViewModel

class TaskViewModel : BaseViewModel() {
    var taskBeans: MutableLiveData<MutableList<TaskBean>> = MutableLiveData()

    fun getTaskList() {
        val tasks = ArrayList<TaskBean>()
        for (i in 1..50) {
            tasks.add(
                TaskBean(
                    序号 = "No.$i",
                    id = i,
                    状态 = if (i % 2 == 0) "良" else "优",
                    vin = "vin$i",
                    进度 = "$i%",
                    差值 = "${100 - i}%"
                )
            )
        }
        taskBeans.value = tasks
    }
}