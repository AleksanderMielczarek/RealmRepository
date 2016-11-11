package com.github.aleksandermielczarek.realmrepositoryexample.module;

import android.content.Context;

import com.github.aleksandermielczarek.napkin.qualifier.AppContext;
import com.github.aleksandermielczarek.napkin.scope.AppScope;
import com.github.aleksandermielczarek.realmrepository.configuration.RealmRepositoryConfiguration;
import com.github.aleksandermielczarek.realmrepositoryexample.repository.UserRepository;
import com.github.aleksandermielczarek.realmrepositoryexample.repository.UserRepositoryImpl;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Aleksander Mielczarek on 22.09.2016.
 */
@Module
@AppScope
public class MainModule {

    @Provides
    @AppScope
    RealmConfiguration provideRealmConfiguration(@AppContext Context context) {
        Realm.init(context);
        return new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
    }

    @Provides
    @AppScope
    RealmRepositoryConfiguration provideRepositoryConfiguration() {
        return RealmRepositoryConfiguration.getDefault();
    }

    @Provides
    @AppScope
    UserRepository provideUserRepository(RealmRepositoryConfiguration repositoryConfiguration) {
        return new UserRepositoryImpl(repositoryConfiguration);
    }
}
