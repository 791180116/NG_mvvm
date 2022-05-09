package com.ng.demo.app.weight.loadCallBack


import com.kingja.loadsir.callback.Callback
import com.ng.demo.R


class EmptyCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }

}