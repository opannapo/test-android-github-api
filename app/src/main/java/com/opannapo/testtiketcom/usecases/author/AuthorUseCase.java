package com.opannapo.testtiketcom.usecases.author;

import android.content.Context;

import com.opannapo.core.layer.application.domain.User;

/**
 * Created by napouser on 30,August,2020
 */
public interface AuthorUseCase {
    interface Action {
        void doGetAuthorProfile(Context context);
    }

    interface View {
        void getAuthorProfile(Context context);

        void onProcessing(String msg);

        void onAuthorProfileResult(User author);
    }
}

