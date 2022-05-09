package com.ng.demo.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/*var 序号: String = "",
var vin: String = "",
var 状态: String = "",
var 电量: String = "",
var 里程: String = ""*/
@SuppressLint("ParcelCreator")
@Parcelize
data class CarBean(
    var 序号: String = "",
    var 状态: String = "",
    var 电量: String = "",
    var 里程: String = "",

    var areaCode: String = "",
    var commondType: String = "",
    var createBy: String = "",
    var createTime: String = "",
    var deptId: Int = 0,
    var deviceID: String = "",
    var iccid: String = "",
    var id: Int = 0,
    var isRemoved: Int = 0,
    var isRobot: Int = 0,
    var license: String = "",
    var params: @RawValue Any? = null,
    var platform: String = "",
    var powerType: String = "",
    var remark: String = "",
    var searchValue: String = "",
    var updateBy: String = "",
    var updateTime: String = "",
    var vehicleType: String = "",
    var vehicleVersion: String = "",
    var vin: String = ""
) : Parcelable