package com.kodeWorkTest.project;

import com.kodeWorkTest.project.data.DataManager;
import com.kodeWorkTest.project.data.local.DbManager;
import com.kodeWorkTest.project.data.remote.KodeWorkAPIService;
import com.kodeWorkTest.project.util.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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


}
