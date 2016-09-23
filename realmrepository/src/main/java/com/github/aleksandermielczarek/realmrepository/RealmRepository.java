package com.github.aleksandermielczarek.realmrepository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Aleksander Mielczarek on 21.09.2016.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RealmRepository {

    boolean autoGenerateId() default false;
}
