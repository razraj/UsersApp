package com.kodeWorkTest.project.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kodeWorkTest.project.data.local.DbManager;
import com.kodeWorkTest.project.data.model.response.UsersListResponse;
import com.kodeWorkTest.project.data.remote.KodeWorkAPIService;
import io.reactivex.Single;
import io.realm.Realm;

/**
 * Created by shivam on 29/5/17.
 */
@Singleton
public class DataManager implements APIHelper,DBHelper{

    private final KodeWorkAPIService kodeWorkAPIService;
    private final DbManager dbManager;

    @Inject
    public DataManager(KodeWorkAPIService kodeWorkAPIService, DbManager dbManager) {
        this.kodeWorkAPIService = kodeWorkAPIService;
        this.dbManager = dbManager;
    }

    private Realm getRealm(){
        return dbManager.getDataRealm();
    }

    @Override
    public Single<UsersListResponse> getUsersList(int page,int limit) {
        return kodeWorkAPIService
                .getUsersList(page,limit);
    }

    @Override
    public void updateUsers(UsersListResponse usersListResponse) {
        Realm realmdb = getRealm();
        realmdb.executeTransaction(realm -> {
            realm.insertOrUpdate(usersListResponse);
        });
    }

    @Override
    public UsersListResponse getUsers(int page, int limit) {
        Realm realmdb = getRealm();
        return realmdb.where(UsersListResponse.class).equalTo("page",page).findFirst();
    }
}
