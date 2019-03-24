package com.kodeWorkTest.project.data.local;

import com.kodeWorkTest.project.BuildConfig;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by rraikar on 29/5/17.
 */



@Singleton
public class DbManager {
    private static final String DATA_DB = "data.realm";
    public static final int DATA_SCHEMA_VERSION = 21;
    private final RealmConfiguration config;

    /**
     * Instantiates a new Db manager.
     */
    @Inject
    public DbManager() {
        config =
                new RealmConfiguration.Builder()
                        .name(DATA_DB)
                        .schemaVersion(DATA_SCHEMA_VERSION)
                        .migration(new Migration1())
                        .build();

        Realm.setDefaultConfiguration(config);

    }

    /**
     * Gets data realm.
     *
     * @return the data realm
     */
    public Realm getDataRealm() {
        Realm realm;
//        realm = Realm.getInstance(config);

        try {
            realm = Realm.getInstance(config);
        } catch (Exception e) {
            Timber.e(e);
            if (BuildConfig.DEBUG)
                realm = Realm.getInstance(new RealmConfiguration.Builder()
                        .name(DATA_DB)
                        .schemaVersion(DATA_SCHEMA_VERSION)
                        .deleteRealmIfMigrationNeeded()
                        .build());
            else
                realm = Realm.getDefaultInstance();
        }
        return realm;
    }

    public void closeInstance(Realm realm) {
        realm.close();
    }
}
