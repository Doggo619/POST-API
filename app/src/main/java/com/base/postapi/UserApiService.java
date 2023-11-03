package com.base.postapi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {
    @POST("users")
    Call<Void> postUserData(@Body UserData userData);
}
