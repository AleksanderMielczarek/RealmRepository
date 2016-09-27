package com.github.aleksandermielczarek.realmrepository;

import android.support.annotation.Nullable;

import java.util.List;

import io.realm.RealmObject;
import rx.Completable;
import rx.Observable;
import rx.Single;

/**
 * Created by Aleksander Mielczarek on 21.09.2016.
 */

public interface Repository<T extends RealmObject, ID> {

    long countSync();

    @Nullable
    T getOneSync(ID id);

    @Nullable
    T getFirstSync();

    List<T> findAllSync();

    boolean existsSync(ID id);

    T saveSync(T entity);

    List<T> saveSync(Iterable<T> entities);

    void deleteSync(T entity);

    void deleteSync(ID id);

    void deleteSync(Iterable<T> entities);

    void deleteAllSync();

    Single<Long> count();

    Single<T> getOne(ID id);

    Single<T> getFirst();

    Observable<T> findAll();

    Single<Boolean> exists(ID id);

    Single<T> save(T entity);

    Single<List<T>> save(Iterable<T> entities);

    Completable delete(T entity);

    Completable delete(ID id);

    Completable delete(Iterable<T> entities);

    Completable deleteAll();

}
