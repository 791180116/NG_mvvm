package com.ng.demo.data.model.bean

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 描述　: 账户信息
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class UserInfo(
    var id: String = "",
    var name: String = "",
    var dept_id: String = "",
    var token: String = "",
    var permissions: List<String> = listOf(),
    var lon: String = "",
    var lat: String = "",
    var level: String = ""
) : Parcelable
