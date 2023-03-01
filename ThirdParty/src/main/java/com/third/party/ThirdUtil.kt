package com.third.party

import android.content.Context
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure

class ThirdUtil {
    companion object {
        /**
         * 预初始化友盟
         * appkey和channl必须保持和预初始化一致！！！
         * @param context 上下文
         * @param appkey "您的appkey"
         * @param channel "您的渠道"
         * @param pushSecret Push推送业务的secret
         */
        fun preInitUM(context: Context, appkey: String, channel: String) {
            //友盟统计SDK预初始化
            UMConfigure.preInit(context, appkey, channel)
        }

        /**
         * 初始化友盟相关
         * appkey和channl必须保持和预初始化一致！！！
         * @param context 上下文
         * @param appkey "您的appkey"
         * @param channel "您的渠道"
         * @param pushSecret Push推送业务的secret
         */
        fun initUM(context: Context, appkey: String, channel: String, pushSecret:String) {
            //SDK初始化
            UMConfigure.init( context, appkey, channel,UMConfigure.DEVICE_TYPE_PHONE, pushSecret);
            //设置LOG开关，默认为false
            UMConfigure.setLogEnabled(false)

            //修改session时长：默认为30秒 interval 单位为毫秒，如果想设定为40秒，interval应为 40*1000.
            //MobclickAgent.setSessionContinueMillis(40*1000)
        }
    }
}