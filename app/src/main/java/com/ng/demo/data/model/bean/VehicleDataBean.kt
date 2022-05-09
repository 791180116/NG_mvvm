package com.ng.demo.data.model.bean

import android.text.TextUtils

data class VehicleDataBean(
    var abnormalCurrent: Int,
    var aiAudio: Int,
    var alarmLamp: Int,
    var announcementStatus: Int,
    var batteryTemperatureAlarm: Int,
    var brakeEnable: Int,
    var brakeTravelValue: Int,
    var carLeftTurnLights: Int,
    var carLights: Int,
    var carRightTurnLights: Int,
    var carTailLight: Int,
    var chargeState: Int,
    var clbjId: Long,
    var communicateStatus: Int,
    var containmentTatus: Int,
    var dcStateTag: Int,
    var directionAngle: Double,
    var disinfectantSpray: Int,
    var driveEnable: Int,
    var driveMotorControllerAlarm: Int,
    var enablingState: Int,
    var errorStop: Int,
    var facePlateCamera: Int,
    var gear: Int,
    var gearEnable: Int,
    var gpsStatus: Int,
    var hardwareStatus: Int,
    var hardwareVersion: String,
    var hasBrakeTravel: Int,
    var hasSpeedUp: Int,
    var hazePhValue: Int,
    var heading: Float,
    var humidityIncar: Int,
    var humidityOutcar: Int,
    var id: Int,
    var insResistance: Int,
    var latitude: Double,
    var leTwoFastClosingStatus: Int,
    var ledMonitor: Int,
    var leftBackSpeed: Double,
    var leftFrontSpeed: Double,
    var limit: Int,
    var locationState: Int,
    var longitude: Double,
    var odometer: Double,
    var offset: Int,
    var overvoltageOrUndervoltage: Int,
    var page: Int,
    var parkEnable: Int,
    var parkStatus: Int,
    var personnelExpulsion: Int,
    var pickUp: Int,
    var policeWhistle: Int,
    var radarStatus: Int,
    var receiveTime: String,
    var remark: String,
    var rightBackSpeed: Double,
    var rightFrontSpeed: Double,
    var runmode: Int,
    var runningStatus: Int,
    var safeDistance: String,
    var slam_x: Double,
    var slam_y: Double,
    var soc: Int,
    var socLow: Int,
    var softwareVersion: String,
    var speed: Double,
    var speedUpValue: Int,
    var steeringEnable: Int,
    var steeringMotorFailure: Int,
    var strongFlashlightStatus: Int,
    var takeOverMark: Int,
    var taskStatus: Int,
    var tempIncar: Int,
    var tempOutcar: Int,
    var thermalImagingCameras: Int,
    var theta: Double,
    var totalCurrent: Double,
    var totalVoltage: Double,
    var touchAlarmStatus: Int,
    var ultraRadarStatus: Int,
    var uploadTime: String,
    var vehicleState: Int,
    var vehicleStopStatus: Int,
    var vehicleVersion: String,
    var vin: String,
    var voltageAlarm: Int,
    var wideAngleCamera: Int,
    var workStatus: Int  //工作状态 0：作业中  1：返航充电中 2：充电中 3：充电完成 4：返回作业中 5.充电异常
) {
    fun getWorkStatusString(status: Int) = if (!TextUtils.isEmpty(vin)) {
        when (status) {
            0 -> "作业中"
            1 -> "返航充电中"
            2 -> "充电中"
            3 -> "充电完成"
            4 -> "返回作业中"
            5 -> "充电异常"
            else -> "未选中车辆或状态异常"
        }
    } else {
        "未选中车辆或状态异常(不在线)"
    }
}