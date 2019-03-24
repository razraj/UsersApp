package com.kodeWorkTest.project.features.main;

import javax.inject.Inject;

import com.kodeWorkTest.project.data.DataManager;
import com.kodeWorkTest.project.features.base.BasePresenter;
import com.kodeWorkTest.project.injection.ConfigPersistent;
import com.kodeWorkTest.project.util.rx.scheduler.SchedulerUtils;

import io.reactivex.disposables.Disposable;
import timber.log.Timber;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager dataManager;
    private Disposable usersDisposable;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    public void getUsers(int page, int limit) {
        checkViewAttached();
        getView().showProgress(true);
        usersDisposable = dataManager
                .getUsersList(page, limit)
                .compose(SchedulerUtils.ioToMain())
                .doOnSuccess(dataManager::updateUsers)
                .onErrorReturn(throwable -> {
                    Timber.e(throwable);
                    return dataManager.getUsers(page, limit);
                })
                .subscribe(usersListResponse -> {
                            getView().showProgress(false);
                            getView().setPagesDetail(usersListResponse.getTotal_pages(), usersListResponse.getPage());
                            getView().showUsers(usersListResponse.getData());
                        },
                        throwable -> {
                            getView().showProgress(false);
                            getView().showError(throwable);
                        });
        addDisposable(usersDisposable);
    }
}
