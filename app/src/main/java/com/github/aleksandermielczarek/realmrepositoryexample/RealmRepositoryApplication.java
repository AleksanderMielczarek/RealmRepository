package com.github.aleksandermielczarek.realmrepositoryexample;

import android.app.Application;

import com.github.aleksandermielczarek.napkin.ComponentProvider;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Aleksander Mielczarek on 21.09.2016.
 */

public class RealmRepositoryApplication extends Application implements ComponentProvider<AppComponent> {

    @Inject
    protected RealmConfiguration realmConfiguration;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);

        Realm.setDefaultConfiguration(realmConfiguration);
    }

    @Override
    public AppComponent provideComponent() {
        return appComponent;
    }
}
