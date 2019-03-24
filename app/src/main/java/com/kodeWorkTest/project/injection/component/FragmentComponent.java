package com.kodeWorkTest.project.injection.component;

import dagger.Subcomponent;
import com.kodeWorkTest.project.injection.PerFragment;
import com.kodeWorkTest.project.injection.module.FragmentModule;

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {
}
