package com.github.aleksandermielczarek.realmrepository.idgenerator;

/**
 * Created by Aleksander Mielczarek on 23.09.2016.
 */

public class VoidIdGenerator implements IdGenerator<Void> {

    @Override
    public Void generateNextId() {
        return null;
    }

}
