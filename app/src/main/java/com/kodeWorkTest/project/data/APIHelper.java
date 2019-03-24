package com.kodeWorkTest.project.data;

import com.kodeWorkTest.project.data.model.response.UsersListResponse;

import java.util.List;

import io.reactivex.Single;

public interface APIHelper {
    public Single<UsersListResponse> getUsersList(int page, int limit);
}
