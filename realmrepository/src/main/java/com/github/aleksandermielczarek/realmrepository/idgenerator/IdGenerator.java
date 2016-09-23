package com.github.aleksandermielczarek.realmrepository.idgenerator;

/**
 * Created by Aleksander Mielczarek on 23.09.2016.
 */

public interface IdGenerator<ID> {

    ID generateNextId();

}
