package com.github.aleksandermielczarek.realmrepository;

import java.util.List;

import io.realm.RealmObject;
import rx.Completable;
import rx.Observable;

/**
 * Created by Aleksander Mielczarek on 21.09.2016.
 */

public interface Repository<T extends RealmObject, ID> {

    long countSync();

    T getOneSync(ID id);

    T getFirstSync();

    List<T> findAllSync();

    boolean existsSync(ID id);

    T saveSync(T entity);

    List<T> saveSync(Iterable<T> entities);

    void deleteSync(T entity);

    void deleteSync(ID id);

    void deleteSync(Iterable<T> entities);

    void deleteAllSync();

    Observable<Long> count();

    Observable<T> getOne(ID id);

    Observable<T> getFirst();

    Observable<T> findAll();

    Observable<Boolean> exists(ID id);

    Observable<T> save(T entity);

    Observable<List<T>> save(Iterable<T> entities);

    Completable delete(T entity);

    Completable delete(ID id);

    Completable delete(Iterable<T> entities);

    Completable deleteAll();

}
