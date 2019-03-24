package com.kodeWorkTest.project.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import com.kodeWorkTest.project.data.DataManager;
import com.kodeWorkTest.project.injection.ApplicationContext;
import com.kodeWorkTest.project.injection.module.AppModule;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ApplicationContext
    Context context();

    Application application();

    DataManager apiManager();
}
