package com.kodeWorkTest.project.injection.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import com.kodeWorkTest.project.data.remote.KodeWorkAPIService;

import retrofit2.Retrofit;

/**
 * Created by shivam on 29/5/17.
 */
@Module(includes = {NetworkModule.class})
public class ApiModule {

    @Provides
    @Singleton
    KodeWorkAPIService providePokemonApi(Retrofit retrofit) {
        return retrofit.create(KodeWorkAPIService.class);
    }
}
