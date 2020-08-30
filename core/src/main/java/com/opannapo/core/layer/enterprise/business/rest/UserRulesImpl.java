package com.opannapo.core.layer.enterprise.business.rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.opannapo.core.layer.application.domain.User;
import com.opannapo.core.layer.enterprise.business.rest.callbacks.EndpointGetManyCallback;
import com.opannapo.core.layer.enterprise.business.rest.callbacks.EndpointGetOneCallback;
import com.opannapo.core.layer.enterprise.business.rest.endpoints.UserEndpoint;
import com.opannapo.core.layer.enterprise.utils.Log;
import com.opannapo.core.layer.interfaces.rest.UserRules;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by napouser on 30,August,2020
 */
public class UserRulesImpl implements UserRules<User> {
    UserEndpoint endpoint;

    Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public UserRulesImpl() {
        endpoint = ApiClient.getClient().create(UserEndpoint.class);
    }

    @Override
    public void getAll(final EndpointGetManyCallback<User> callback) {
        callback.onProgress("Loading");

    }

    @Override
    public void getAll(int page, EndpointGetManyCallback<User> callback) {

    }

    @Override
    public void getOne(int id, EndpointGetOneCallback<User> callback) {
        endpoint.getAuthor(18698574).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                User author = gson.fromJson(response.body(), User.class);
                Log.d("AUTHOR : " + author.toString());
                callback.onComplete(true, author, null);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onComplete(false, null, t.getMessage());
            }
        });
    }

    @Override
    public void search(String query, int page, int limit, EndpointGetManyCallback<User> callback) {
        endpoint.search(query, page, limit).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() == null) {
                    callback.onComplete(false, new ArrayList<>(), "No Response Body");
                    return;
                }
                if (!response.body().has("items")) {
                    callback.onComplete(false, new ArrayList<>(), "No Items");
                    return;
                }
                if (!response.body().get("items").isJsonArray()) {
                    callback.onComplete(false, new ArrayList<>(), "No Items");
                    return;
                }

                List<User> users = gson.fromJson(response.body().getAsJsonArray("items"), new TypeToken<List<User>>() {
                }.getType());

                for (User user : users) {
                    Log.d("USER " + user.toString());
                }

                callback.onComplete(true, users, null);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onComplete(false, null, t.getMessage());
            }
        });
    }

}