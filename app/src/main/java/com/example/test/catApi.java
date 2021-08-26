package com.example.test;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface catApi {
    @GET("images/search")
    Call<List<catTempClass>> getData();
}
