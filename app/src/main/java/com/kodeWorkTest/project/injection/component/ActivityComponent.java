package com.kodeWorkTest.project.injection.component;

import dagger.Subcomponent;
import com.kodeWorkTest.project.features.main.MainActivity;
import com.kodeWorkTest.project.injection.PerActivity;
import com.kodeWorkTest.project.injection.module.ActivityModule;

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
