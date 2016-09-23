package com.github.aleksandermielczarek.realmrepository.idgenerator;

/**
 * Created by Aleksander Mielczarek on 23.09.2016.
 */

public class StringGenerator implements IdGenerator<String> {

    private final UuidIdGenerator uuidIdGenerator = new UuidIdGenerator();

    @Override
    public String generateNextId() {
        return uuidIdGenerator.generateNextId().toString();
    }
}
