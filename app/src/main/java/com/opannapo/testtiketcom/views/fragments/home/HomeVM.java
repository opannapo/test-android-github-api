package com.opannapo.testtiketcom.views.fragments.home;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.opannapo.core.layer.application.domain.User;
import com.opannapo.core.layer.application.presenter.view.BaseViewModel;
import com.opannapo.testtiketcom.usecases.home.HomeUseCase;
import com.opannapo.testtiketcom.usecases.home.HomeUseCaseImpl;

import java.util.List;

/**
 * Created by napouser on 30,August,2020
 */
public class HomeVM extends BaseViewModel<HomeUseCaseImpl> implements HomeUseCase.View {
    public MutableLiveData<Integer> liveLoadingState = new MutableLiveData<>(); //1 loading
    public MutableLiveData<List<User>> liveUsers = new MutableLiveData<>();
    private int currentPage;
    private String currentQuery;

    @Override
    protected HomeUseCaseImpl initUseCase() {
        return new HomeUseCaseImpl(this);
    }


    @Override
    public void findUsers(Context context, String query) {
        this.currentQuery = query;
        this.currentPage = 1;
        useCase.doFindUser(context, currentQuery, currentPage, 20);
    }

    @Override
    public void loadMore(Context context) {
        this.currentPage++;
        useCase.doFindUser(context, currentQuery, currentPage, 20);
    }

    @Override
    public void onProcessing(String msg) {
        liveLoadingState.postValue(1);
    }

    @Override
    public void onUsersResult(List<User> users) {
        liveLoadingState.postValue(0);
        liveUsers.postValue(users);
    }

    @Override
    public void onSearchError(int errorType) {

    }
}