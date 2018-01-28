package edwang.weatherappjava;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by student on 29/06/2017.
 */

public class MainApplication extends Application {
    private Realm realmCity;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }

    public void openRealm() {
        RealmConfiguration config = new RealmConfiguration.Builder().
                deleteRealmIfMigrationNeeded().
                build();

        realmCity = Realm.getInstance(config);
    }

    public void closeRealm() {
        realmCity.close();
    }

    public Realm getRealmCity() {
        return realmCity;
    }
}
