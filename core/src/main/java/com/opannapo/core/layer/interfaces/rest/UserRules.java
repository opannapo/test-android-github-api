package com.opannapo.core.layer.interfaces.rest;

import com.opannapo.core.layer.enterprise.business.rest.callbacks.EndpointGetManyCallback;
import com.opannapo.core.layer.enterprise.business.rest.callbacks.EndpointGetOneCallback;

/**
 * Created by napouser on 30,August,2020
 */
public interface UserRules<T> {
    void getAll(EndpointGetManyCallback<T> callback);

    void getAll(int page, EndpointGetManyCallback<T> callback);

    void getOne(int id, EndpointGetOneCallback<T> callback);

    void search(String query, int page, int limit, EndpointGetManyCallback<T> callback);
}