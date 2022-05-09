package com.ng.demo.app.weight.banner

/**
 * 描述　:
 */

import android.view.View
import android.widget.ImageView
import com.ng.demo.R
import com.zhpan.bannerview.BaseViewHolder

class WelcomeBannerViewHolder(view: View) : BaseViewHolder<Int>(view) {
    override fun bindData(data: Int, position: Int, pageSize: Int) {
        /*val textView = findView<TextView>(R.id.banner_text)
        textView.text = data*/
        val imageView = findView<ImageView>(R.id.iv_splash)
        imageView.setImageResource(data)
    }
}
