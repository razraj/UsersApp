package com.kodeWorkTest.project.features.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;

import com.kodeWorkTest.project.R;
import com.kodeWorkTest.project.data.model.response.UserData;
import com.kodeWorkTest.project.features.base.BaseActivity;
import com.kodeWorkTest.project.features.common.ErrorView;
import com.kodeWorkTest.project.injection.component.ActivityComponent;
import com.kodeWorkTest.project.util.rx.scheduler.SchedulerUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainMvpView, ErrorView.ErrorListener {

    private static final int USERS_PER_PAGE_COUNT = 6;
    private int pageNumber = 1;
    private int maxPageNumber = 1;

    private LinearLayoutManager layoutManager;

    @Inject
    UsersAdapter usersAdapter;
    @Inject
    MainPresenter mainPresenter;

    @BindView(R.id.view_error)
    ErrorView errorView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.recycler_pokemon)
    RecyclerView pokemonRecycler;

    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);

        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.primary);
        swipeRefreshLayout.setColorSchemeResources(R.color.white);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            usersAdapter.removeAll();
            mainPresenter.getUsers(1, USERS_PER_PAGE_COUNT);
        });

        layoutManager = new LinearLayoutManager(this);
        pokemonRecycler.setLayoutManager(layoutManager);
        pokemonRecycler.setAdapter(usersAdapter);
        userClicked();
        errorView.setErrorListener(this);

        mainPresenter.getUsers(pageNumber, USERS_PER_PAGE_COUNT);

        Observable<Integer> pageDetector = Observable.create((ObservableOnSubscribe<Integer>) subscriber ->
                pokemonRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    int pastVisibleItems, visibleItemCount, totalItemCount;

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        visibleItemCount = layoutManager.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount - 1) {
                            if (pageNumber < maxPageNumber)
                                subscriber.onNext((++pageNumber));
                        }
                    }
                })).debounce(400, TimeUnit.MILLISECONDS);

        mainPresenter.addDisposable(pageDetector
                .compose(SchedulerUtils.ioToMain())
                .subscribe(page -> {
                    Timber.d("Loading more");
                    mainPresenter.getUsers(page, USERS_PER_PAGE_COUNT);
                }));
    }

    private void userClicked() {
        Disposable disposable =
                usersAdapter
                        .getPokemonClick()
                        .subscribe(
                                userData -> {
                                    //start activity
                                },
                                throwable -> {
                                    Timber.e(throwable, "Pokemon click failed");
                                    Toast.makeText(
                                            this,
                                            R.string.error_something_bad_happened,
                                            Toast.LENGTH_LONG)
                                            .show();
                                });
        mainPresenter.addDisposable(disposable);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    @Override
    protected void attachView() {
        mainPresenter.attachView(this);
    }

    @Override
    protected void detachPresenter() {
        mainPresenter.detachView();
    }

    @Override
    public void showUsers(List<UserData> users) {
        usersAdapter.setUsers(users);
        if (pageNumber == maxPageNumber)
            usersAdapter.hideLoader();
        pokemonRecycler.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {
            if (pokemonRecycler.getVisibility() == View.VISIBLE
                    && usersAdapter.getItemCount() > 0) {
                swipeRefreshLayout.setRefreshing(true);
            } else {
                progressBar.setVisibility(View.VISIBLE);

                pokemonRecycler.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.GONE);
            }

            errorView.setVisibility(View.GONE);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(Throwable error) {
        pokemonRecycler.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        Timber.e(error, "There was an error retrieving the pokemon");
    }

    @Override
    public void setPagesDetail(int maxPages, int currentPage) {
        this.maxPageNumber = maxPages;
        this.pageNumber = currentPage;

    }

    @Override
    public void onReloadData() {
        mainPresenter.getUsers(pageNumber, USERS_PER_PAGE_COUNT);
    }
}
