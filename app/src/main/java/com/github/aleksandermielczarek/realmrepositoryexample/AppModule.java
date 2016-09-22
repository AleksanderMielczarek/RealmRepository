package com.github.aleksandermielczarek.realmrepositoryexample;

import android.content.Context;

import com.github.aleksandermielczarek.napkin.scope.AppScope;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmConfiguration;

/**
 * Created by Aleksander Mielczarek on 22.09.2016.
 */
@Module
@AppScope
public class AppModule {

    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @AppScope
    RealmConfiguration provideRealmConfiguration() {
        return new RealmConfiguration.Builder(context)
                .build();
    }
}
