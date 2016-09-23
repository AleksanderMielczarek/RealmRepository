package com.github.aleksandermielczarek.realmrepository.idgenerator;

import java.util.UUID;

/**
 * Created by Aleksander Mielczarek on 23.09.2016.
 */
class UuidIdGenerator implements IdGenerator<UUID> {

    @Override
    public UUID generateNextId() {
        return UUID.randomUUID();
    }

}
