package com.example.benjo.personalfinanceapp.utils

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/**
 * Created by benjo on 2018-01-27.
 */
class MemoryLeaks : Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }
}

