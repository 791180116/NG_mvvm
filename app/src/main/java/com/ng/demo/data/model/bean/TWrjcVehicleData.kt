package com.ng.demo.data.model.bean

import java.io.Serializable
import java.util.*

class TWrjcVehicleData : Serializable {
    var id: Int? = null

    /**
     * 车辆唯一号
     */
    private var vin: String? = null

    /**
     * 车辆状态
     */
    var vehicleState: Int? = null

    /**
     * 充电状态
     */
    var chargeState: Int? = null

    /**
     * 一键报警状态
     */
    var touchAlarmStatus: Int? = null

    var runmode: Int? = null

    var speed: Double? = null

    var odometer: Double? = null

    var totalVoltage: Double? = null

    var totalCurrent: Double? = null

    var soc: Int? = null

    var dcStateTag: Int? = null

    var gear: Int? = null

    var hasSpeedUp: Int? = null

    var hasBrakeTravel: Int? = null

    var insResistance: Int? = null

    var speedUpValue: Int? = null

    var brakeTravelValue: Int? = null

    var leftFrontSpeed: Double? = null

    var rightFrontSpeed: Double? = null

    var leftBackSpeed: Double? = null

    var rightBackSpeed: Double? = null

    var parkStatus: Int? = null

    var locationState: Int? = null

    var longitude: Double? = null

    var latitude: Double? = null

    var heading: Float? = null

    var taskStatus: Int? = null

    var runningStatus: Int? = null

    var gpsStatus: Int? = null

    var radarStatus: Int? = null

    var communicateStatus: Int? = null

    var ultraRadarStatus: Int? = null

    var hardwareStatus: Int? = null

    var errorStop: Int? = null

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var uploadTime: Date? = null

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    var receiveTime: Date? = null

    private var remark: String? = null

    var directionAngle: Double? = null

    var enablingState: Int? = null

    var takeOverMark //0：自驾模式，1：远程接管 2：WIFI   3：充电   4：手柄遥控器
            : Int? = null

    var vehicleStopStatus: Int? = null

    var carLights: Int? = null

    var carTailLight: Int? = null

    var carLeftTurnLights: Int? = null

    var carRightTurnLights: Int? = null

    var alarmLamp: Int? = null

    var policeWhistle: Int? = null

    var facePlateCamera: Int? = null

    var wideAngleCamera: Int? = null

    var thermalImagingCameras: Int? = null

    var pickUp: Int? = null

    var personnelExpulsion: Int? = null

    var disinfectantSpray: Int? = null

    var announcementStatus: Int? = null

    var aiAudio: Int? = null

    var ledMonitor: Int? = null

    var overvoltageOrUndervoltage: Int? = null

    var abnormalCurrent: Int? = null

    var voltageAlarm: Int? = null

    var batteryTemperatureAlarm: Int? = null

    var socLow: Int? = null

    var driveMotorControllerAlarm: Int? = null

    var steeringMotorFailure: Int? = null

    var tempIncar: Int? = null

    var tempOutcar: Int? = null

    var humidityIncar: Int? = null

    var humidityOutcar: Int? = null

    var hazePhValue: Int? = null
    var gearEnable //挡位使能位
            : Int? = null
    var steeringEnable //转向使能位
            : Int? = null
    var driveEnable //驱动使能位
            : Int? = null
    var brakeEnable //制动使能位
            : Int? = null
    var parkEnable //驻车使能位
            : Int? = null

    var softwareVersion: String? = null
    var hardwareVersion: String? = null

    /**
     * 报警车辆id
     */
    var clbjId: Long = 0
    var containmentTatus: Int? = null
    var strongFlashlightStatus: Int? = null
    var leTwoFastClosingStatus: Int? = null
    var safeDistance //安全距离  前,后,左,右
            : String? = null
    var slam_x: Double? = null
    var slam_y: Double? = null
    var theta: Double? = null
    var workStatus // 工作状态 0：作业中  1：返航充电中 2：充电中 3：充电完成 4：返回作业中
            : Int? = null

    fun getVin(): String? {
        return vin
    }

    fun setVin(vin: String?) {
        this.vin = vin?.trim { it <= ' ' }
    }

    fun getRemark(): String? {
        return remark
    }

    fun setRemark(remark: String?) {
        this.remark = remark?.trim { it <= ' ' }
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val data = o as TWrjcVehicleData
        return id!!.toInt() == data.id!!.toInt()
    }

    override fun toString(): String {
        return "TWrjcVehicleData{" +
                "id=" + id +
                ", vin='" + vin + '\'' +
                ", vehicleState=" + vehicleState +
                ", chargeState=" + chargeState +
                ", touchAlarmStatus=" + touchAlarmStatus +
                ", runmode=" + runmode +
                ", speed=" + speed +
                ", odometer=" + odometer +
                ", totalVoltage=" + totalVoltage +
                ", totalCurrent=" + totalCurrent +
                ", soc=" + soc +
                ", dcStateTag=" + dcStateTag +
                ", gear=" + gear +
                ", hasSpeedUp=" + hasSpeedUp +
                ", hasBrakeTravel=" + hasBrakeTravel +
                ", insResistance=" + insResistance +
                ", speedUpValue=" + speedUpValue +
                ", brakeTravelValue=" + brakeTravelValue +
                ", leftFrontSpeed=" + leftFrontSpeed +
                ", rightFrontSpeed=" + rightFrontSpeed +
                ", leftBackSpeed=" + leftBackSpeed +
                ", rightBackSpeed=" + rightBackSpeed +
                ", parkStatus=" + parkStatus +
                ", locationState=" + locationState +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", heading=" + heading +
                ", taskStatus=" + taskStatus +
                ", runningStatus=" + runningStatus +
                ", gpsStatus=" + gpsStatus +
                ", radarStatus=" + radarStatus +
                ", communicateStatus=" + communicateStatus +
                ", ultraRadarStatus=" + ultraRadarStatus +
                ", hardwareStatus=" + hardwareStatus +
                ", errorStop=" + errorStop +
                ", uploadTime=" + uploadTime +
                ", receiveTime=" + receiveTime +
                ", remark='" + remark + '\'' +
                ", directionAngle=" + directionAngle +
                ", enablingState=" + enablingState +
                ", takeOverMark=" + takeOverMark +
                ", vehicleStopStatus=" + vehicleStopStatus +
                ", carLights=" + carLights +
                ", carTailLight=" + carTailLight +
                ", carLeftTurnLights=" + carLeftTurnLights +
                ", carRightTurnLights=" + carRightTurnLights +
                ", alarmLamp=" + alarmLamp +
                ", policeWhistle=" + policeWhistle +
                ", facePlateCamera=" + facePlateCamera +
                ", wideAngleCamera=" + wideAngleCamera +
                ", thermalImagingCameras=" + thermalImagingCameras +
                ", pickUp=" + pickUp +
                ", personnelExpulsion=" + personnelExpulsion +
                ", disinfectantSpray=" + disinfectantSpray +
                ", announcementStatus=" + announcementStatus +
                ", aiAudio=" + aiAudio +
                ", ledMonitor=" + ledMonitor +
                ", overvoltageOrUndervoltage=" + overvoltageOrUndervoltage +
                ", abnormalCurrent=" + abnormalCurrent +
                ", voltageAlarm=" + voltageAlarm +
                ", batteryTemperatureAlarm=" + batteryTemperatureAlarm +
                ", socLow=" + socLow +
                ", driveMotorControllerAlarm=" + driveMotorControllerAlarm +
                ", steeringMotorFailure=" + steeringMotorFailure +
                ", tempIncar=" + tempIncar +
                ", tempOutcar=" + tempOutcar +
                ", humidityIncar=" + humidityIncar +
                ", humidityOutcar=" + humidityOutcar +
                ", hazePhValue=" + hazePhValue +
                ", gearEnable=" + gearEnable +
                ", steeringEnable=" + steeringEnable +
                ", driveEnable=" + driveEnable +
                ", brakeEnable=" + brakeEnable +
                ", parkEnable=" + parkEnable +
                ", softwareVersion='" + softwareVersion + '\'' +
                ", hardwareVersion='" + hardwareVersion + '\'' +
                ", clbjId=" + clbjId +
                ", containmentTatus=" + containmentTatus +
                ", strongFlashlightStatus=" + strongFlashlightStatus +
                ", leTwoFastClosingStatus=" + leTwoFastClosingStatus +
                ", safeDistance=" + safeDistance +
                '}'
    }

    companion object {
        const val serialVersionUID = 1L
    }
}