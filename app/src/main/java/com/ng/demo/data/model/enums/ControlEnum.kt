package com.ng.demo.data.model.enums

/**
 * 接口文档：http://192.144.218.30:28080/swagger-ui.html#!/commond-controller/setCtrol_grpc2UsingGET
 * 控制车辆（TBox）(通过gRpc直接调用网关)
 * controlId参数（param参数）：
 * 8 全部设备（0关闭 1开启），
 * 9 警灯 （0关闭 1标准闪烁 1 频繁闪烁） 备注：以前享智接口有0关闭 1常亮 2闪烁 3 频闪。当前用的TBOX只有 0关闭 1闪烁，闪烁频率可以调整。
 * 10 警笛 （0关闭 1开启）
 * 11 人脸车牌摄像头 （0关闭 1开启）
 * 12 广角摄像头 （0关闭 1开启）
 * 13 热成像 （0关闭 1开启）
 * 14 拾音器 （0关闭 1开启）
 * 15 LED大屏控制(车辆版本 V1.0) 举例 49:=;48:=;1:=;防范诈骗
 * （yanse颜色（49红 50绿 51黄）
 * ziti字体（48 10号字 49 12号字 50 18号字）
 * xianshifangshi显示方式 （1静止 2闪烁 3左移 4右移 5上移 6下移）
 * neirong内容 （字符串））
 * 16 人员驱逐 (车辆版本 V1.0)（0关闭 1开启）
 * 17 语言播报控制(车辆版本 V1.0) （0停止 1开始 2暂停 3播放下一个 4播放上一个 5调高音量 6调低音量 7检查语音播报模块状态 8指定路径播放语音）
 * 18 消毒喷洒 （0关闭 1开启）
 * 19 急停控制 （0关闭 1开启）
 * 20 暂停控制 （0关闭 1开启）
 * 21 车辆照明灯/前灯 （0关闭 1两灯全开）
 * 22 后灯 （0关闭 1开启）
 * 23 一键报警 （0关闭）
 * 24 强光灯 （0关闭 1开启）
 * 25 二级开关控制 （0关闭 1开启）
 * 26 驻车控制 （0关闭 1开启）
 * 27 转向灯控制 （0全部关闭 1左转向灯开启 2右转向灯开启）
 * 28 接警命令下发，0：关闭，1：开启
 * 29 远程喊话控制指令下发，0：关闭，1：开启
 * 30 语音对讲控制指令下发，0：关闭，1：开启
 * 31 驾驶舱ip和端口发送给车端，数据类型：STRING
 * 32 工作模式，0：关闭远程控制 1：打开远程控制
 * 33 车辆控制 0,0,10,0
 * 方向控制(数值范围-100 到 100， 表示转动角度的百分比，用整数表示)
 * 档位（0：空挡 1：D档 2：R档（倒挡））
 * 加速踏板行程值（数值范围 0 到 100，表示行程百分比）
 * 制动踏板行程值（数值范围 0 到 100，表示行程百分比）
 * 34 FTP文件下载，(车辆版本 V2.0)文件路径及名称 1、图片 2、文本 3、视频 4、音频 255、其他
 * 1;/filedata/1235.jpg
 * 35 LED大屏控制(文字)(车辆版本 V2.0) 举例 1:=;1:=;2:=;16:=;5:=;防范诈骗
 * LED屏位置 (前屏：1，后屏：2，侧屏：3)
 * 字体（宋体: 1，黑体: 2，楷体: 3）
 * 颜色（红色：1，黄色：2，蓝色：3 ，绿色：4，白色：5）
 * 字号（16 16号字， 18 18号字，22 22号字，24 24号字，26 26号字，36 36号字，42 42号字，48 48号字）
 * 模式（静止：2，上进下出：4，左进右出：5，右进左出：6）
 * 内容（base64加密后的字符串）
 * 36 LED大屏控制(图片)(车辆版本 V2.0) 举例 1:=;2:=;10:=;新年快乐.png
 * LED屏位置 (前屏：1，后屏：2，侧屏：3)
 * 模式（静止：2，上进下出：4，左进右出：5，右进左出：6）
 * 时间间隔 (最小间隔：1秒，最大间隔：240秒)
 * 内容数据 (字符串)
 * 37 LED大屏控制(视频) (车辆版本 V2.0) 举例 1:=;智能巡逻.mp4
 * LED屏位置 (前屏：1，后屏：2，侧屏：3)
 * 内容数据 (字符串)
 * 38 语言播报控制 (车辆版本 V2.0) 举例 1:=;防范诈骗.mp3
 * 播放次数 (0表示停止，最小次数为：1次，最大次数为：254次，255表示无限次数)
 * 内容数据 (字符串)
 * 39 新LED大屏关闭，(车辆版本 V2.0)1：前屏关闭，2：后屏关闭，3：侧屏关闭，254：全部关闭
 * 40 音量大小，(车辆版本 V2.0) 举例：1:=;50
 * 音源类型 1 1:LCD屏，2:LED屏 ，3:语音播报,4:远程喊话，5:语音对讲，6:一键求助
 * 有效值 0~100 表示音量大小的百分比 （用于V2.0以上车辆）
 * 41 删除车载终端文件，(车辆版本 V2.0) 举例： /audio/123.mp3;/picture/456.png;/video/789.avi
 * 参数：文件的路径及名称，多个文件之间用半角分号分隔 （用于V2.0以上车辆）
 * 42 控制车辆充电及作业，0：去充电，1：去作业（老版本）； 业务下发：0：自驾复位，1：入库，2：出库，3：定桩并出库，4：主路巡逻，5：入库复位，6：出库复位（新版本）
 * 43 自驾模式：1：停障，2：绕障
 * 44 车辆状态查询命令，上报内容见A.1.1.8，将车辆当前状态上报一条
 * 45 平台比对预警下发车端语音播报警示，1：人脸黑名单
 * 46 AI语音启动、关闭通知车端， 0：关闭 1：开启
 */
enum class ControlEnum(val controlId: Int, var param: Int = 0, val desc: String) {
    All(8, 0, "全部设备"),
    AlarmLight(9, 0, "警灯"),
    AlarmWhistle(10, 0, "警笛"),
    CarStop(19, 0, "急停"),
    HeadLight(21, 0, "前灯"),
    BackLight(22, 0, "后灯"),
    ChargeOrWork(42, 0, "充电或作业"),
    Floodlight(24, 0, "强光灯");

    fun setParam(param: Int): ControlEnum {
        this.param = param
        return this
    }

    /**
     * 适用0或1，开关类
     */
    fun setParamSwitch(bool: Boolean): ControlEnum {
        this.param = if (bool) 1 else 0
        return this
    }

    companion object {
        /**
         * 根据传入的controlId得到相关的枚举类型 没有的话默认
         */
        fun byControlId(controlId: Int, param: Int): ControlEnum? {
            values().forEach {
                if (controlId == it.controlId) {
                    it.param = param
                    return it
                }
            }
            return null
        }

        /**
         * 用于开关类控制 开启1，关闭0
         * bool 开关状态
         */
        fun byControlId(controlId: Int, bool: Boolean): ControlEnum? {
            values().forEach {
                if (controlId == it.controlId) {
                    it.param = if (bool) 1 else 0
                    return it
                }
            }
            return null
        }


        /**
         * 根据传入的desc得到相关的枚举类型 没有的话null
         */
        fun byDesc(desc: String, param: Int): ControlEnum? {
            values().forEach {
                if (desc == it.desc) {
                    it.param = param
                    return it
                }
            }
            return null
        }

        /**
         * 用于开关类控制 开启1，关闭0
         * bool 开关状态
         */
        fun byDesc(desc: String, bool: Boolean): ControlEnum? {
            values().forEach {
                if (desc == it.desc) {
                    it.param = if (bool) 1 else 0
                    return it
                }
            }
            return null
        }
    }

}