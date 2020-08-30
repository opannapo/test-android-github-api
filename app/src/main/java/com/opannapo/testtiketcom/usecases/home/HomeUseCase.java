package com.opannapo.testtiketcom.usecases.home;

import android.content.Context;

import com.opannapo.core.layer.application.domain.User;
import com.opannapo.testtiketcom.etc.Constant.ErrorType;

import java.util.List;

/**
 * Created by napouser on 30,August,2020
 */
public interface HomeUseCase {
    interface Action {
        void doFindUser(Context context, String query, int page, int limit);
    }

    interface View {
        void findUsers(Context context, String query);

        void loadMore(Context context);

        void onProcessing(String msg);

        void onUsersResult(List<User> users);

        void onSearchError(@ErrorType int errorType);
    }
}