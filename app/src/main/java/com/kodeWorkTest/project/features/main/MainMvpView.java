package com.kodeWorkTest.project.features.main;

import java.util.List;

import com.kodeWorkTest.project.data.model.response.UserData;
import com.kodeWorkTest.project.features.base.MvpView;

public interface MainMvpView extends MvpView {

    void showUsers(List<UserData> users);

    void showProgress(boolean show);

    void showError(Throwable error);

    void setPagesDetail(int maxPages, int currentPage);

}
