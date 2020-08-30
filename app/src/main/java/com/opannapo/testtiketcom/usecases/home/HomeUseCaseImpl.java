package com.opannapo.testtiketcom.usecases.home;

import android.content.Context;

import com.opannapo.core.layer.application.domain.User;
import com.opannapo.core.layer.application.presenter.usecases.BaseUseCase;
import com.opannapo.core.layer.enterprise.business.rest.UserRulesImpl;
import com.opannapo.core.layer.interfaces.rest.UserRules;

import java.util.List;

/**
 * Created by napouser on 30,August,2020
 */
public class HomeUseCaseImpl extends BaseUseCase<HomeUseCase.View> implements HomeUseCase.Action {
    UserRules<User> userLocal = new UserRulesImpl();

    public HomeUseCaseImpl(HomeUseCase.View view) {
        super(view);
    }

    @Override
    public void doGetAllUsers(Context context) {
        /*userLocal.getAll(context, new RoomGetManyCallback<User>() {
            @Override
            public void onProgress(String msg) {
                view.onProcessing(msg);
            }

            @Override
            public void onComplete(Boolean isSuccess, List<User> data, String error) {
                view.onUsersResult(data);
            }
        });*/
    }
}