package com.github.aleksandermielczarek.realmrepositoryexample;

import android.content.Context;

import com.github.aleksandermielczarek.napkin.scope.AppScope;
import com.github.aleksandermielczarek.realmrepository.configuration.RealmRepositoryConfiguration;
import com.github.aleksandermielczarek.realmrepositoryexample.repository.UserRepository;
import com.github.aleksandermielczarek.realmrepositoryexample.repository.UserRepositoryImpl;

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
