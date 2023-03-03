package com.example.vfndemo

import android.app.Application

class MainApplication : Application() {

    companion object {
        /** Returns singleton instance of MyApplication  */
        @JvmStatic
        @Volatile
        lateinit var instance: MainApplication
        lateinit var mContext: MainApplication


    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        mContext = this

    }

    fun getContext(): MainApplication? {
        return mContext
    }

}