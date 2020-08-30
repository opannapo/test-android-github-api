package com.opannapo.core.layer.enterprise.business.rest.callbacks;

import com.google.gson.JsonObject;
import com.opannapo.core.layer.application.domain.User;

/**
 * Created by napouser on 30,August,2020
 */
public abstract class EndpointGetOneCallback<T> {
    public abstract void onProgress(String msg);

    public abstract void onComplete(Boolean isSuccess, User data, String error);
}
