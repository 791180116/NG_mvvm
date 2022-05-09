package com.ng.demo.viewmodel.state

import ng.crazy.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * 描述　:
 */
class WebViewModel : BaseViewModel() {
    //标题
    var showTitle: String = ""

    //文章的网络访问路径
    var url: String = ""
}