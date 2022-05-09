package com.ng.demo.data.model.bean

import java.io.Serializable

data class VehicleListBean(
    var alarmLamp: Int,
    var announcementStatus: Int,
    var carLights: Int,
    var carTailLight: Int,
    var dinfectantSpray: Int,
    var lat: Double,
    var lon: Double,
    var onLineStatus: String,
    var personnelExpulsion: Int,
    var policeWhtle: Int,
    var soc: Int,
    var speed: Double,
    var strongFlashlightStatus: Int,
    var takeOverStatus: Int,
    var vehicleStopStatus: Int,
    var vin: String,
    var workStatus: Int
) : Serializable