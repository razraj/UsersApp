package com.kodeWorkTest.project.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import com.kodeWorkTest.project.common.injection.module.ApplicationTestModule;
import com.kodeWorkTest.project.injection.component.AppComponent;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends AppComponent {
}
