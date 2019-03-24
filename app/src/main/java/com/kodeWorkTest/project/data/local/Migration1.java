package com.kodeWorkTest.project.data.local;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class Migration1 implements RealmMigration {


    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 1) {

        }
    }

    /**
     * using ((31*x)+x)
     * @return
     */
    public int hashCode() {
        return ((31*13)+13);
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        return obj == this || obj instanceof RealmMigration;
    }
}
