package com.opannapo.testtiketcom.usecases.main;

import android.content.Context;
import android.os.Handler;

import com.opannapo.core.layer.application.domain.User;
import com.opannapo.core.layer.application.presenter.usecases.BaseUseCase;
import com.opannapo.core.layer.enterprise.business.rest.UserRulesImpl;
import com.opannapo.core.layer.enterprise.business.rest.callbacks.EndpointGetOneCallback;
import com.opannapo.core.layer.interfaces.rest.UserRules;

/**
 * Created by napouser on 30,August,2020
 */
public class MainUseCaseImpl extends BaseUseCase<MainUseCase.View> implements MainUseCase.Action {
    UserRules<User> userRules = new UserRulesImpl();
    int AUTHOR_ID = 18698574;

    public MainUseCaseImpl(MainUseCase.View view) {
        super(view);
    }

    @Override
    public void doGetAuthorProfile(Context context) {
        view.onProcessing("Loading");

        new Handler().postDelayed(() -> {
            userRules.getOne(AUTHOR_ID, new EndpointGetOneCallback<User>() {
                @Override
                public void onProgress(String msg) {
                    view.onProcessing("loading");
                }

                @Override
                public void onComplete(Boolean isSuccess, User data, String error) {
                    view.onUserResult(data);
                }
            });
        }, 2000);

    }
}
