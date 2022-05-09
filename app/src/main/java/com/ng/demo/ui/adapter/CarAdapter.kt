package com.ng.demo.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ng.demo.R
import com.ng.demo.data.model.bean.CarBean

class CarAdapter(data: MutableList<CarBean>) : BaseQuickAdapter<CarBean, BaseViewHolder>(
    R.layout.item_cars, data
) {
    override fun convert(holder: BaseViewHolder, item: CarBean) {
        holder.setText(R.id.tv_num, (holder.layoutPosition + 1).toString())
        holder.setText(R.id.tv_vin, item.vin)
        holder.setText(R.id.tv_status, item.状态)
        holder.setText(R.id.tv_electric, item.电量)
        holder.setText(R.id.tv_mileage, item.里程)
    }
}