package com.opannapo.testtiketcom.usecases.home;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opannapo.core.layer.application.domain.User;
import com.opannapo.core.layer.application.presenter.usecases.BaseUseCase;
import com.opannapo.core.layer.enterprise.business.rest.UserRulesImpl;
import com.opannapo.core.layer.enterprise.business.rest.callbacks.EndpointGetManyCallback;
import com.opannapo.core.layer.enterprise.utils.Log;
import com.opannapo.core.layer.interfaces.rest.UserRules;
import com.opannapo.testtiketcom.etc.Constant;

import java.util.List;

/**
 * Created by napouser on 30,August,2020
 */
public class HomeUseCaseImpl extends BaseUseCase<HomeUseCase.View> implements HomeUseCase.Action {
    UserRules<User> userRules = new UserRulesImpl();

    Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public HomeUseCaseImpl(HomeUseCase.View view) {
        super(view);
    }

    @Override
    public void doFindUser(Context context, String query, int page, int limit) {
        userRules.search(query, page, limit, new EndpointGetManyCallback<User>() {
            @Override
            public void onProgress(String msg) {
                view.onProcessing("Loading");
            }

            @Override
            public void onComplete(Boolean isSuccess, List<User> data, String error) {
                if (isSuccess) {
                    for (User user : data) {
                        Log.d("USER " + user.toString());
                    }

                    if (data.size() > 0) {
                        view.onUsersResult(data);
                        return;
                    }

                    view.onSearchError(Constant.ErrorType.ERROR_LOAD);
                } else {
                    view.onSearchError(Constant.ErrorType.ERROR_LOAD);
                }
            }

        });
    }
}