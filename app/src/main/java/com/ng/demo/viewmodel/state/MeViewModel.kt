package com.ng.demo.viewmodel.state

import com.ng.demo.app.util.ColorUtil
import ng.crazy.jetpackmvvm.base.viewmodel.BaseViewModel
import ng.crazy.jetpackmvvm.callback.databind.IntObservableField
import ng.crazy.jetpackmvvm.callback.databind.StringObservableField

/**
 * 描述　: 专门存放 MeFragment 界面数据的ViewModel
 */
class MeViewModel : BaseViewModel() {

    var name = StringObservableField("请先登录~")

    var integral = IntObservableField(0)

    var info = StringObservableField("id：--　排名：-")

    var imageUrl = StringObservableField(ColorUtil.randomImage())
}