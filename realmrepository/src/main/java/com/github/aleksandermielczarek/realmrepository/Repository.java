package com.github.aleksandermielczarek.realmrepository;

import java.util.List;

import io.realm.RealmObject;
import rx.Completable;
import rx.Observable;

/**
 * Created by Aleksander Mielczarek on 21.09.2016.
 */

public interface Repository<T extends RealmObject, ID> {

    Observable<T> findAllAsync();

    Observable<T> saveAsync(T entity);

    Observable<List<T>> saveAsync(Iterable<T> entities);

    Completable deleteAsync(T entity);

    Completable deleteAsync(Iterable<T> entities);

    Completable deleteAllAsync();

}
