package com.github.aleksandermielczarek.realmrepository.idsetter;

import io.realm.RealmObject;

/**
 * Created by Aleksander Mielczarek on 23.09.2016.
 */

public interface IdSetter<T extends RealmObject, ID> {

    void setId(T entity, ID id);
}
