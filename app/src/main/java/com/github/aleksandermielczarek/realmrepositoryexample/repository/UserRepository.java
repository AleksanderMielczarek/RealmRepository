package com.github.aleksandermielczarek.realmrepositoryexample.repository;

import com.github.aleksandermielczarek.realmrepository.RealmRepository;
import com.github.aleksandermielczarek.realmrepository.Repository;
import com.github.aleksandermielczarek.realmrepositoryexample.entity.User;

/**
 * Created by Aleksander Mielczarek on 21.09.2016.
 */
@RealmRepository(autoGenerateId = true)
public interface UserRepository extends Repository<User, String> {
}
