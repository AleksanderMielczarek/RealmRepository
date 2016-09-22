package com.github.aleksandermielczarek.realmrepositoryexample.entity;

import com.github.aleksandermielczarek.fieldnames.FieldNames;

import io.realm.RealmObject;

/**
 * Created by Aleksander Mielczarek on 21.09.2016.
 */
@FieldNames
public class User extends RealmObject {

    private String name;
    private String surname;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
