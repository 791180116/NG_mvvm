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
    var workStatus: Int  //???????????? 0????????????  1?????????????????? 2???????????? 3??????????????? 4?????????????????? 5.????????????
) {
    fun getWorkStatusString(status: Int) = if (!TextUtils.isEmpty(vin)) {
        when (status) {
            0 -> "?????????"
            1 -> "???????????????"
            2 -> "?????????"
            3 -> "????????????"
            4 -> "???????????????"
            5 -> "????????????"
            else -> "??????????????????????????????"
        }
    } else {
        "??????????????????????????????(?????????)"
    }
}