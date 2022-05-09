package com.ng.demo.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ng.demo.R
import com.ng.demo.data.model.bean.TaskBean

class TaskAdapter(data: MutableList<TaskBean>) : BaseQuickAdapter<TaskBean, BaseViewHolder>(
    R.layout.item_task, data
) {
    override fun convert(holder: BaseViewHolder, item: TaskBean) {
        holder.setText(R.id.tv_num, item.taskId.toString())
        holder.setText(R.id.tv_id, item.id.toString())
        holder.setText(R.id.tv_status, item.statusCN)
        holder.setText(R.id.tv_car_vin, item.vin)
        holder.setText(R.id.tv_process, item.进度)
        holder.setText(R.id.tv_chazhi, item.差值)
    }
}