package com.haifeng.kotlinwordstext

import android.app.Application

/**
 *创建人：Liuhaifeng
 *时间：2020/9/4
 *描述：
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        CrashHandler.getInstance()

    }
}