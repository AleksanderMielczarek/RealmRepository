package com.github.aleksandermielczarek.realmrepository;

import android.support.annotation.Nullable;

import com.github.aleksandermielczarek.realmrepository.configuration.RealmRepositoryConfiguration;
import com.github.aleksandermielczarek.realmrepository.idgenerator.IdGenerator;
import com.github.aleksandermielczarek.realmrepository.idsearch.IdSearch;
import com.github.aleksandermielczarek.realmrepository.idsetter.IdSetter;

import java.util.List;
import java.util.concurrent.Callable;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Completable;
import rx.Observable;
import rx.Single;
import rx.functions.Func1;

/**
 * Created by Aleksander Mielczarek on 23.09.2016.
 */

public class RepositoryDelegate<T extends RealmObject, ID> implements Repository<T, ID> {

    private final Class<T> entityClass;
    private final RealmRepositoryConfiguration repositoryConfiguration;
    private final IdGenerator<ID> idGenerator;
    private final IdSetter<T, ID> idSetter;
    private final String idFieldName;
    private final IdSearch idSearch;

    public RepositoryDelegate(Class<T> entityClass, RealmRepositoryConfiguration repositoryConfiguration, @Nullable IdGenerator<ID> idGenerator, @Nullable IdSetter<T, ID> idSetter, String idFieldName, IdSearch idSearch) {
        this.entityClass = entityClass;
        this.repositoryConfiguration = repositoryConfiguration;
        this.idGenerator = idGenerator;
        this.idSetter = idSetter;
        this.idFieldName = idFieldName;
        this.idSearch = idSearch;
    }

    @Override
    public long countSync() {
        Realm realm = null;
        try {
            realm = repositoryConfiguration.getRealmProvider().provideRealm();
            return realm.where(entityClass).count();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Nullable
    @Override
    public T getOneSync(ID id) {
        Realm realm = null;
        try {
            realm = repositoryConfiguration.getRealmProvider().provideRealm();
            RealmQuery<T> query = realm.where(entityClass);
            T entity = idSearch.searchId(query, idFieldName, id)
                    .findFirst();
            return entity == null ? null : realm.copyFromRealm(entity);
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Nullable
    @Override
    public T getFirstSync() {
        Realm realm = null;
        try {
            realm = repositoryConfiguration.getRealmProvider().provideRealm();
            T entity = realm.where(entityClass)
                    .findFirst();
            return entity == null ? null : realm.copyFromRealm(entity);
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public List<T> findAllSync() {
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

    @Override
    public boolean existsSync(ID id) {
        Realm realm = null;
        try {
            realm = repositoryConfiguration.getRealmProvider().provideRealm();
            RealmQuery<T> query = realm.where(entityClass);
            return idSearch.searchId(query, idFieldName, id)
                    .count() > 0;
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public T saveSync(T entity) {
        Realm realm = null;
        try {
            realm = repositoryConfiguration.getRealmProvider().provideRealm();
            realm.beginTransaction();
            if (idGenerator != null && idSetter != null) {
                ID id = idGenerator.generateNextId();
                idSetter.setId(entity, id);
            }
            T newEntity = realm.copyToRealmOrUpdate(entity);
            realm.commitTransaction();
            return realm.copyFromRealm(newEntity);
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public List<T> saveSync(Iterable<T> entities) {
        Realm realm = null;
        try {
            realm = repositoryConfiguration.getRealmProvider().provideRealm();
            realm.beginTransaction();
            if (idGenerator != null && idSetter != null) {
                for (T entity : entities) {
                    ID id = idGenerator.generateNextId();
                    idSetter.setId(entity, id);
                }
            }
            List<T> newEntities = realm.copyToRealmOrUpdate(entities);
            realm.commitTransaction();
            return realm.copyFromRealm(newEntities);
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public void deleteSync(T entity) {
        Realm realm = null;
        try {
            realm = repositoryConfiguration.getRealmProvider().provideRealm();
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(entity).deleteFromRealm();
            realm.commitTransaction();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public void deleteSync(ID id) {
        Realm realm = null;
        try {
            realm = repositoryConfiguration.getRealmProvider().provideRealm();
            realm.beginTransaction();
            RealmQuery<T> query = realm.where(entityClass);
            T entity = idSearch.searchId(query, idFieldName, id)
                    .findFirst();
            if (entity != null) {
                entity.deleteFromRealm();
            }
            realm.commitTransaction();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public void deleteSync(Iterable<T> entities) {
        Realm realm = null;
        try {
            realm = repositoryConfiguration.getRealmProvider().provideRealm();
            realm.beginTransaction();
            for (T entity : entities) {
                realm.copyToRealmOrUpdate(entity).deleteFromRealm();
            }
            realm.commitTransaction();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public void deleteAllSync() {
        Realm realm = null;
        try {
            realm = repositoryConfiguration.getRealmProvider().provideRealm();
            realm.beginTransaction();
            realm.delete(entityClass);
            realm.commitTransaction();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public Single<Long> count() {
        return Single.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return countSync();
            }
        });
    }

    @Override
    public Single<T> getOne(final ID id) {
        return Single.fromCallable(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return getOneSync(id);
            }
        });
    }

    @Override
    public Single<T> getFirst() {
        return Single.fromCallable(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return getFirstSync();
            }
        });
    }

    @Override
    public Observable<T> findAll() {
        return Observable.fromCallable(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return findAllSync();
            }
        }).flatMap(new Func1<List<T>, Observable<T>>() {
            @Override
            public Observable<T> call(List<T> ts) {
                return Observable.from(ts);
            }
        });
    }

    @Override
    public Single<Boolean> exists(final ID id) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return existsSync(id);
            }
        });
    }

    @Override
    public Single<T> save(final T entity) {
        return Single.fromCallable(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return saveSync(entity);
            }
        });
    }

    @Override
    public Single<List<T>> save(final Iterable<T> entities) {
        return Single.fromCallable(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return saveSync(entities);
            }
        });
    }

    @Override
    public Completable delete(final T entity) {
        return Completable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                deleteSync(entity);
                return null;
            }
        });
    }

    @Override
    public Completable delete(final ID id) {
        return Completable.fromCallable(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                deleteSync(id);
                return null;
            }
        });
    }

    @Override
    public Completable delete(final Iterable<T> entities) {
        return Completable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                deleteSync(entities);
                return null;
            }
        });
    }

    @Override
    public Completable deleteAll() {
        return Completable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                deleteAllSync();
                return null;
            }
        });
    }
}
