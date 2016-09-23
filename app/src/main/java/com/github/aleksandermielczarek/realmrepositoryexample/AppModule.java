package com.github.aleksandermielczarek.realmrepositoryexample;

import android.content.Context;

import com.github.aleksandermielczarek.napkin.scope.AppScope;
import com.github.aleksandermielczarek.realmrepository.configuration.RealmRepositoryConfiguration;
import com.github.aleksandermielczarek.realmrepository.idgenerator.StringGenerator;
import com.github.aleksandermielczarek.realmrepository.RepositoryDelegate;
import com.github.aleksandermielczarek.realmrepositoryexample.entity.User;
import com.github.aleksandermielczarek.realmrepositoryexample.repository.UserRepository;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import io.realm.RealmConfiguration;
import rx.Completable;
import rx.Observable;

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
        RepositoryDelegate<User, String> repositoryDelegate = new RepositoryDelegate<>(User.class, repositoryConfiguration, new StringGenerator(), User::setId);
        return new UserRepository() {
            @Override
            public Observable<User> findAllAsync() {
                return repositoryDelegate.findAllAsync();
            }

            @Override
            public Observable<User> saveAsync(User entity) {
                return repositoryDelegate.saveAsync(entity);
            }

            @Override
            public Observable<List<User>> saveAsync(Iterable<User> entities) {
                return repositoryDelegate.saveAsync(entities);
            }

            @Override
            public Completable deleteAsync(User entity) {
                return repositoryDelegate.deleteAsync(entity);
            }

            @Override
            public Completable deleteAsync(Iterable<User> entities) {
                return repositoryDelegate.deleteAsync(entities);
            }

            @Override
            public Completable deleteAllAsync() {
                return repositoryDelegate.deleteAllAsync();
            }
        };
    }
}
