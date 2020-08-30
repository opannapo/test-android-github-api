package com.opannapo.testtiketcom.usecases.splash;

import android.content.Context;
import android.os.Handler;

import com.opannapo.core.layer.application.presenter.usecases.BaseUseCase;

/**
 * Created by napouser on 30,August,2020
 */
public class SplashUseCaseImpl extends BaseUseCase<SplashUseCase.View> implements SplashUseCase.Action {
    public SplashUseCaseImpl(SplashUseCase.View view) {
        super(view);
    }

    @Override
    public void doSync(Context context) {
        view.onProcessing("Prepare Sync");
        new Handler().postDelayed(() -> view.onSyncResult("Complete"), 2500);
    }
}
