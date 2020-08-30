package com.opannapo.testtiketcom;

import android.app.Application;

import com.opannapo.core.layer.enterprise.utils.Log;


/**
 * Created by napouser on 30,August,2020
 */
public class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupLogConfig();
    }

    void setupLogConfig() {
        if (BuildConfig.DEBUG) {
            Log.buildConfig()
                    .setLogEnable(true)
                    .setWithDetailLine(true)
                    .setTAG("TEST-TIKET")
                    .setWithDetailCaller(true);
        } else {
            // TODO: 8/5/20 what ?
        }
    }
}
