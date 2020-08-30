package com.opannapo.testtiketcom.views.fragments.author;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.opannapo.core.layer.application.domain.User;
import com.opannapo.core.layer.application.presenter.view.BaseViewModel;
import com.opannapo.testtiketcom.usecases.author.AuthorUseCase;
import com.opannapo.testtiketcom.usecases.author.AuthorUseCaseImpl;

/**
 * Created by napouser on 30,August,2020
 */
public class AuthorVM extends BaseViewModel<AuthorUseCaseImpl> implements AuthorUseCase.View {
    public MutableLiveData<Integer> liveLoadingState = new MutableLiveData<>(); //1 loading
    public MutableLiveData<User> liveAuthor = new MutableLiveData<>();

    @Override
    protected AuthorUseCaseImpl initUseCase() {
        return new AuthorUseCaseImpl(this);
    }

    @Override
    public void getAuthorProfile(Context context) {
        useCase.doGetAuthorProfile(context);
    }

    @Override
    public void onProcessing(String msg) {
        liveLoadingState.postValue(1);
    }

    @Override
    public void onAuthorProfileResult(User author) {
        liveLoadingState.postValue(0);
        liveAuthor.postValue(author);
    }
}
