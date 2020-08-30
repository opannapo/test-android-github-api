package com.opannapo.testtiketcom.usecases.author;

import android.content.Context;

import com.opannapo.core.layer.application.domain.User;
import com.opannapo.core.layer.application.presenter.usecases.BaseUseCase;
import com.opannapo.core.layer.enterprise.business.rest.UserRulesImpl;
import com.opannapo.core.layer.interfaces.rest.UserRules;

/**
 * Created by napouser on 30,August,2020
 */
public class AuthorUseCaseImpl extends BaseUseCase<AuthorUseCase.View> implements AuthorUseCase.Action {
    UserRules<User> userRules = new UserRulesImpl();

    public AuthorUseCaseImpl(AuthorUseCase.View view) {
        super(view);
    }

    @Override
    public void doGetAuthorProfile(Context context) {

    }
}
