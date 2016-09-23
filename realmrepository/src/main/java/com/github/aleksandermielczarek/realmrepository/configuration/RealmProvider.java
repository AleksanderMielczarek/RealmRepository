package com.github.aleksandermielczarek.realmrepository.configuration;

import io.realm.Realm;

/**
 * Created by Aleksander Mielczarek on 22.09.2016.
 */

public interface RealmProvider {

    Realm provideRealm();
}
