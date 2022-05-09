package com.ng.demo.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
var 序号: String = "",
var id: String = "",
var 状态: String = "",
var 车辆键值: String = "",
var 进度: String = "",
var 差值: String = ""*/
@SuppressLint("ParcelCreator")
@Parcelize
data class TaskBean(
    var 序号: String = "",
    var 状态: String = "",
    var 进度: String = "",
    var 差值: String = "",

    var createTime: String = "",
    var creator: String = "",
    var endTime: String = "",
    var id: Int = 0,
    var startTime: String = "",
    var status: Int = 0,
    var taskId: Int = 0,
    var taskType: Int = 0,
    var vin: String = ""
) : Parcelable {
    var statusCN: String = "未知"
        get() {
            return when (status) {//0未开始  1执行中  2暂停  3终止  4完成
                0 -> {
                    "未开始"
                }
                1 -> {
                    "执行中"
                }
                2 -> {
                    "暂停"
                }
                3 -> {
                    "终止"
                }
                4 -> {
                    "完成"
                }
                else -> {
                    "未知"
                }
            }
        }

    fun getStatusByCN(cn: String): Int {
        return when (cn) {
            "未开始" -> 0
            "暂停" -> 2
            "终止" -> 3
            "完成" -> 4
            else -> {
                if (cn.contains("执行")) 1 else 0
            }
        }
    }
}