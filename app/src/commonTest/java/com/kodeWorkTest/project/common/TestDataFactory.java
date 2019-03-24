package com.kodeWorkTest.project.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import com.kodeWorkTest.project.data.model.response.UserData;

/**
 * Factory class that makes instances of data models with random field values. The aim of this class
 * is to help setting up test fixtures.
 */
public class TestDataFactory {

    private static final Random random = new Random();

    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    public static UserData makeUser(int id) {
        UserData userData = new UserData();
        userData.setId(id);
        userData.setFirst_name(randomUuid() + id);
        userData.setLast_name(randomUuid() + id);
        userData.setAvatar(randomUuid() + id);
        return userData;
    }

    public static void makeUserListResponse(){

    }

    public static List<UserData> makeUsersResourceList(int count) {
        List<UserData> namedResourceList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            namedResourceList.add(makeUser(i));
        }
        return namedResourceList;
    }
}
