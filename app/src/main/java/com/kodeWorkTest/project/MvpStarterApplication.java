package com.kodeWorkTest.project;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.singhajit.sherlock.core.Sherlock;
import com.squareup.leakcanary.LeakCanary;
import com.tspoon.traceur.Traceur;

import com.kodeWorkTest.project.injection.component.AppComponent;
import com.kodeWorkTest.project.injection.component.DaggerAppComponent;
import com.kodeWorkTest.project.injection.module.AppModule;
import com.kodeWorkTest.project.injection.module.NetworkModule;

import io.realm.Realm;
import timber.log.Timber;

public class MvpStarterApplication extends Application {

    private AppComponent appComponent;

    public static MvpStarterApplication get(Context context) {
        return (MvpStarterApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            Stetho.initializeWithDefaults(this);
            LeakCanary.install(this);
            Sherlock.init(this);
            Traceur.enableLogging();
        }
    }

    public AppComponent getComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .networkModule(new NetworkModule(this, BuildConfig.KODE_WORK_API_URL))
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }
}
