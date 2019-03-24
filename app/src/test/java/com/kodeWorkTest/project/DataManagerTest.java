package com.kodeWorkTest.project;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import com.kodeWorkTest.project.common.TestDataFactory;
import com.kodeWorkTest.project.data.DataManager;
import com.kodeWorkTest.project.data.local.DbManager;
import com.kodeWorkTest.project.data.model.response.NamedResource;
import com.kodeWorkTest.project.data.model.response.Pokemon;
import com.kodeWorkTest.project.data.model.response.UsersListResponse;
import com.kodeWorkTest.project.data.remote.KodeWorkAPIService;
import com.kodeWorkTest.project.util.RxSchedulersOverrideRule;
import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by shivam on 29/5/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Rule
    public final RxSchedulersOverrideRule overrideSchedulersRule = new RxSchedulersOverrideRule();

    @Mock
    private KodeWorkAPIService mockKodeWorkAPIService;
    @Mock
    private DbManager dbManager;

    private DataManager dataManager;

    @Before
    public void setUp() {
        dataManager = new DataManager(mockKodeWorkAPIService, dbManager);
    }

//    @Test
//    public void getPokemonListCompletesAndEmitsPokemonList() {
//        List<NamedResource> namedResourceList = TestDataFactory.makeNamedResourceList(5);
//        UsersListResponse usersListResponse = new UsersListResponse();
//        usersListResponse.results = namedResourceList;
//
//        when(mockKodeWorkAPIService.getUsersList(1,anyInt()))
//                .thenReturn(Single.just(usersListResponse));
//
//        dataManager
//                .getPokemonList(10)
//                .test()
//                .assertComplete()
//                .assertValue(TestDataFactory.makePokemonNameList(namedResourceList));
//    }
}
