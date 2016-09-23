package com.github.aleksandermielczarek.realmrepository.idsetter;

import io.realm.RealmObject;

/**
 * Created by Aleksander Mielczarek on 23.09.2016.
 */

public class NoIdSetter<T extends RealmObject, ID> implements IdSetter<T, ID> {

    @Override
    public void setId(T entity, ID id) {

    }
}
