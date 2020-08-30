package com.opannapo.testtiketcom.usecases.main;

import android.content.Context;

import com.opannapo.core.layer.application.domain.User;

/**
 * Created by napouser on 30,August,2020
 */
public interface MainUseCase {
    interface Action {
        void doGetAuthorProfile(Context context);
    }

    interface View {
        void getMyProfile(Context context);

        void onProcessing(String msg);

        void onUserResult(User owner);
    }
}
