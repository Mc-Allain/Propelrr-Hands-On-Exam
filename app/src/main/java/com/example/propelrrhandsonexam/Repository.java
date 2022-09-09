package com.example.propelrrhandsonexam;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Repository {

    @GET("35bc2790-3220-4e82-b44d-2cd5cbc11dd3")
    Call<Responses> getResponses();

    @POST("35bc2790-3220-4e82-b44d-2cd5cbc11dd3")
    Call<Response> submitResponse(@Body Response response);

}
