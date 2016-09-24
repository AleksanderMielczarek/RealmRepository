package com.github.aleksandermielczarek.realmrepository;

import java.util.List;

import io.realm.RealmObject;
import rx.Completable;
import rx.Observable;

/**
 * Created by Aleksander Mielczarek on 21.09.2016.
 */

public interface Repository<T extends RealmObject, ID> {

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
