package com.opannapo.core.layer.enterprise.business.rest.callbacks;

import java.util.List;

/**
 * Created by napouser on 30,August,2020
 */
public abstract class EndpointGetManyCallback<T> {
    public abstract void onProgress(String msg);

    public abstract void onComplete(Boolean isSuccess, List<T> data, String error);
}
