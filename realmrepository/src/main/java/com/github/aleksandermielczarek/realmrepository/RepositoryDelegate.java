package com.github.aleksandermielczarek.realmrepository;

import com.github.aleksandermielczarek.realmrepository.configuration.RealmRepositoryConfiguration;
import com.github.aleksandermielczarek.realmrepository.idgenerator.IdGenerator;
import com.github.aleksandermielczarek.realmrepository.idsetter.IdSetter;

import java.util.List;
import java.util.concurrent.Callable;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Completable;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Aleksander Mielczarek on 23.09.2016.
 */

public class RepositoryDelegate<T extends RealmObject, ID> implements Repository<T, ID> {

    private final Class<T> entityClass;
    private final RealmRepositoryConfiguration repositoryConfiguration;
    private final IdGenerator<ID> idGenerator;
    private final IdSetter<T, ID> idSetter;

    public RepositoryDelegate(Class<T> entityClass, RealmRepositoryConfiguration repositoryConfiguration, IdGenerator<ID> idGenerator, IdSetter<T, ID> idSetter) {
        this.entityClass = entityClass;
        this.repositoryConfiguration = repositoryConfiguration;
        this.idGenerator = idGenerator;
        this.idSetter = idSetter;
    }

    @Override
    public Observable<T> findAllAsync() {
        return Observable.fromCallable(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                Realm realm = null;
                try {
                    realm = repositoryConfiguration.getRealmProvider().provideRealm();
                    RealmResults<T> users = realm.where(entityClass)
                            .findAll();
                    return realm.copyFromRealm(users);
                } finally {
                    if (realm != null) {
                        realm.close();
                    }
                }
            }
        }).flatMap(new Func1<List<T>, Observable<T>>() {
            @Override
            public Observable<T> call(List<T> ts) {
                return Observable.from(ts);
            }
        });
    }

    @Override
    public Observable<T> saveAsync(final T entity) {
        return Observable.fromCallable(new Callable<T>() {
            @Override
            public T call() throws Exception {
                Realm realm = null;
                try {
                    realm = repositoryConfiguration.getRealmProvider().provideRealm();
                    realm.beginTransaction();
                    ID id = idGenerator.generateNextId();
                    idSetter.setId(entity, id);
                    T newEntity = realm.copyToRealm(entity);
                    realm.commitTransaction();
                    return realm.copyFromRealm(newEntity);
                } finally {
                    if (realm != null) {
                        realm.close();
                    }
                }
            }
        });
    }

    @Override
    public Observable<List<T>> saveAsync(final Iterable<T> entities) {
        return Observable.fromCallable(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                Realm realm = null;
                try {
                    realm = repositoryConfiguration.getRealmProvider().provideRealm();
                    realm.beginTransaction();
                    for (T entity : entities) {
                        ID id = idGenerator.generateNextId();
                        idSetter.setId(entity, id);
                    }
                    List<T> newEntities = realm.copyToRealm(entities);
                    realm.commitTransaction();
                    return realm.copyFromRealm(newEntities);
                } finally {
                    if (realm != null) {
                        realm.close();
                    }
                }
            }
        });
    }

    @Override
    public Completable deleteAsync(final T entity) {
        return Completable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Realm realm = null;
                try {
                    realm = repositoryConfiguration.getRealmProvider().provideRealm();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(entity).deleteFromRealm();
                    realm.commitTransaction();
                    return null;
                } finally {
                    if (realm != null) {
                        realm.close();
                    }
                }
            }
        });
    }

    @Override
    public Completable deleteAsync(final Iterable<T> entities) {
        return Completable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Realm realm = null;
                try {
                    realm = repositoryConfiguration.getRealmProvider().provideRealm();
                    realm.beginTransaction();
                    for (T entity : entities) {
                        realm.copyToRealmOrUpdate(entity).deleteFromRealm();
                    }
                    realm.commitTransaction();
                    return null;
                } finally {
                    if (realm != null) {
                        realm.close();
                    }
                }
            }
        });
    }

    @Override
    public Completable deleteAllAsync() {
        return Completable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Realm realm = null;
                try {
                    realm = repositoryConfiguration.getRealmProvider().provideRealm();
                    realm.beginTransaction();
                    realm.delete(entityClass);
                    realm.commitTransaction();
                    return null;
                } finally {
                    if (realm != null) {
                        realm.close();
                    }
                }
            }
        });
    }
}
