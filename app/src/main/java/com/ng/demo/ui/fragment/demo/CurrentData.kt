package com.ng.demo.ui.fragment.demo

import java.io.Serializable

class CurrentData : Serializable {
    var actionCode: Int = 0
    var automaticDrivingMode: Int = 0
    var dGear: Int = 0
    var directionAngle: Int = 0
    var heading: Int = 0
    var locationX: Int = 0
    var locationY: Int = 0
    var nGear: Int = 0
    var overTime: Int = 0
    var pGear: Int = 0
    var rGear: Int = 0
    var routeId: Int = 0
    var routeProportion: Int = 0
    var shutdownState: Int = 0
    var soc: Int = 0
    var speed: Int = 0
    var uploadTime: Long = 0L
    var vin: String = ""
}