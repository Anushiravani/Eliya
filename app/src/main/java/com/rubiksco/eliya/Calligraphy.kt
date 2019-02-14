package com.rubiksco.eliya

import android.app.Application;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig.initDefault


class Calligraphy : Application() {

    override fun onCreate() {
        super.onCreate()
        initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/vazir.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )

    }

}