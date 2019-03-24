package com.kodeWorkTest.project.data.remote;

import com.kodeWorkTest.project.data.model.response.Pokemon;
import com.kodeWorkTest.project.data.model.response.UsersListResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface KodeWorkAPIService {

    @GET("users")
    Single<UsersListResponse> getUsersList(@Query("page") int page, @Query("per_page") int perPage);

}
