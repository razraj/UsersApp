package com.kodeWorkTest.project.data;

import com.kodeWorkTest.project.data.model.response.UsersListResponse;

public interface DBHelper {
    void updateUsers(UsersListResponse usersListResponse);

    UsersListResponse getUsers(int page, int limit);
}
