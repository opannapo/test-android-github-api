package com.opannapo.testtiketcom.usecases.author;

import com.opannapo.core.layer.application.presenter.usecases.BaseUseCase;

/**
 * Created by napouser on 30,August,2020
 */
public class AuthorUseCaseImpl extends BaseUseCase<AuthorUseCase.View> implements AuthorUseCase.Action {
    public AuthorUseCaseImpl(AuthorUseCase.View view) {
        super(view);
    }
}
