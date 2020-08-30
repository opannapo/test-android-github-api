package com.opannapo.core.layer.enterprise.business.rest.endpoints;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by napouser on 30,August,2020
 */
public interface UserEndpoint {
    @Headers({"Accept: application/json", "Content-Type: application/json",})
    @GET("search/users")
    Call<JsonObject> search(
            @Query("q") String query,
            @Query("page") int page,
            @Query("per_page") int perPage
    );

    @Headers({"Accept: application/json", "Content-Type: application/json",})
    @GET("user/{Id}")
    Call<JsonObject> getAuthor(@Path("Id") int id);
}