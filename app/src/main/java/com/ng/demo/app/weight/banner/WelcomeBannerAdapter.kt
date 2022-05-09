package com.ng.demo.app.weight.banner

/**
 * 描述　:
 */

import android.view.View
import com.ng.demo.R
import com.zhpan.bannerview.BaseBannerAdapter

class WelcomeBannerAdapter : BaseBannerAdapter<Int, WelcomeBannerViewHolder>() {

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.banner_itemwelcome
    }

    override fun createViewHolder(itemView: View, viewType: Int): WelcomeBannerViewHolder {
        return WelcomeBannerViewHolder(itemView);
    }

    override fun onBind(
        holder: WelcomeBannerViewHolder?,
        data: Int,
        position: Int,
        pageSize: Int
    ) {
        holder?.bindData(data, position, pageSize);
    }
}
