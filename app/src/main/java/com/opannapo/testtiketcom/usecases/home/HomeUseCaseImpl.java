package com.opannapo.testtiketcom.usecases.home;

import android.content.Context;

import com.opannapo.core.layer.application.domain.User;
import com.opannapo.core.layer.application.presenter.usecases.BaseUseCase;
import com.opannapo.core.layer.enterprise.business.rest.UserRulesImpl;
import com.opannapo.core.layer.enterprise.business.rest.callbacks.EndpointGetManyCallback;
import com.opannapo.core.layer.interfaces.rest.UserRules;
import com.opannapo.testtiketcom.etc.Constant;

import java.util.List;

/**
 * Created by napouser on 30,August,2020
 */
public class HomeUseCaseImpl extends BaseUseCase<HomeUseCase.View> implements HomeUseCase.Action {
    UserRules<User> userRules = new UserRulesImpl();

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
                    if (data.size() > 0) {
                        view.onUsersResult(data);
                    } else {
                        view.onSearchError(Constant.ErrorType.EMPTY_RESULT);
                    }
                } else {
                    view.onSearchError(Constant.ErrorType.ERROR_LOAD);
                }
            }

        });
    }
}