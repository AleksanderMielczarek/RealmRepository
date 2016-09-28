package com.github.aleksandermielczarek.realmrepository.processor;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;

/**
 * Created by Aleksander Mielczarek on 23.09.2016.
 */

public class GlobalConfiguration {

    public static final String PRIMARY_KEY_ANNOTATION_PACKAGE = "io.realm.annotations";
    public static final String PRIMARY_KEY_ANNOTATION_NAME = "PrimaryKey";
    public static final String PRIMARY_KEY_ANNOTATION_FULL_NAME = "io.realm.annotations.PrimaryKey";

    public static final String REALM_REPOSITORY_ANNOTATION_PACKAGE = "com.github.aleksandermielczarek.realmrepository";
    public static final String REALM_REPOSITORY_ANNOTATION_NAME = "RealmRepository";
    public static final String REALM_REPOSITORY_ANNOTATION_FULL_NAME = "com.github.aleksandermielczarek.realmrepository.RealmRepository";

    public static final String REPOSITORY_INTERFACE_PACKAGE = "com.github.aleksandermielczarek.realmrepository";
    public static final String REPOSITORY_INTERFACE_NAME = "Repository";
    public static final String REPOSITORY_INTERFACE_FULL_NAME = "com.github.aleksandermielczarek.realmrepository.Repository";

    public static final String REPOSITORY_DELEGATE_CLASS_PACKAGE = "com.github.aleksandermielczarek.realmrepository";
    public static final String REPOSITORY_DELEGATE_CLASS_NAME = "RepositoryDelegate";
    public static final String REPOSITORY_DELEGATE_CLASS_FULL_NAME = "com.github.aleksandermielczarek.realmrepository.RepositoryDelegate";

    public static final String REALM_REPOSITORY_CONFIGURATION_CLASS_PACKAGE = "com.github.aleksandermielczarek.realmrepository.configuration";
    public static final String REALM_REPOSITORY_CONFIGURATION_CLASS_NAME = "RealmRepositoryConfiguration";
    public static final String REALM_REPOSITORY_CONFIGURATION_CLASS_FULL_NAME = "com.github.aleksandermielczarek.realmrepository.configuration.RealmRepositoryConfiguration";

    public static final String ID_GENERATOR_INTERFACE_PACKAGE = "com.github.aleksandermielczarek.realmrepository.idgenerator";
    public static final String ID_GENERATOR_INTERFACE_NAME = "IdGenerator";
    public static final String ID_GENERATOR_INTERFACE_FULL_NAME = "com.github.aleksandermielczarek.realmrepository.idgenerator.IdGenerator";

    public static final String STRING_ID_GENERATOR_CLASS_PACKAGE = "com.github.aleksandermielczarek.realmrepository.idgenerator";
    public static final String STRING_ID_GENERATOR_CLASS_NAME = "StringIdGenerator";
    public static final String STRING_ID_GENERATOR_CLASS_FULL_NAME = "com.github.aleksandermielczarek.realmrepository.idgenerator.StringIdGenerator";

    public static final String ID_SEARCH_INTERFACE_PACKAGE = "com.github.aleksandermielczarek.realmrepository.idsearch";
    public static final String ID_SEARCH_INTERFACE_NAME = "IdSearch";
    public static final String ID_SEARCH_INTERFACE_FULL_NAME = "com.github.aleksandermielczarek.realmrepository.idsearch.IdSearch";

    public static final String BOOLEAN_ID_SEARCH_CLASS_PACKAGE = "com.github.aleksandermielczarek.realmrepository.idsearch";
    public static final String BOOLEAN_ID_SEARCH_CLASS_NAME = "BooleanIdSearch";
    public static final String BOOLEAN_ID_SEARCH_CLASS_FULL_NAME = "com.github.aleksandermielczarek.realmrepository.idsearch.BooleanIdSearch";

    public static final String BYTE_ARRAY_ID_SEARCH_CLASS_PACKAGE = "com.github.aleksandermielczarek.realmrepository.idsearch";
    public static final String BYTE_ARRAY_ID_SEARCH_CLASS_NAME = "ByteArrayIdSearch";
    public static final String BYTE_ARRAY_ID_SEARCH_CLASS_FULL_NAME = "com.github.aleksandermielczarek.realmrepository.idsearch.ByteArrayIdSearch";

    public static final String BYTE_ID_SEARCH_CLASS_PACKAGE = "com.github.aleksandermielczarek.realmrepository.idsearch";
    public static final String BYTE_ID_SEARCH_CLASS_NAME = "ByteIdSearch";
    public static final String BYTE_ID_SEARCH_CLASS_FULL_NAME = "com.github.aleksandermielczarek.realmrepository.idsearch.ByteIdSearch";

    public static final String DATE_ID_SEARCH_CLASS_PACKAGE = "com.github.aleksandermielczarek.realmrepository.idsearch";
    public static final String DATE_ID_SEARCH_CLASS_NAME = "DateIdSearch";
    public static final String DATE_ID_SEARCH_CLASS_FULL_NAME = "com.github.aleksandermielczarek.realmrepository.idsearch.DateIdSearch";

    public static final String DOUBLE_ID_SEARCH_CLASS_PACKAGE = "com.github.aleksandermielczarek.realmrepository.idsearch";
    public static final String DOUBLE_ID_SEARCH_CLASS_NAME = "DoubleIdSearch";
    public static final String DOUBLE_ID_SEARCH_CLASS_FULL_NAME = "com.github.aleksandermielczarek.realmrepository.idsearch.DoubleIdSearch";

    public static final String FLOAT_ID_SEARCH_CLASS_PACKAGE = "com.github.aleksandermielczarek.realmrepository.idsearch";
    public static final String FLOAT_ID_SEARCH_CLASS_NAME = "FloatIdSearch";
    public static final String FLOAT_ID_SEARCH_CLASS_FULL_NAME = "com.github.aleksandermielczarek.realmrepository.idsearch.FloatIdSearch";

    public static final String INTEGER_ID_SEARCH_CLASS_PACKAGE = "com.github.aleksandermielczarek.realmrepository.idsearch";
    public static final String INTEGER_ID_SEARCH_CLASS_NAME = "IntegerIdSearch";
    public static final String INTEGER_ID_SEARCH_CLASS_FULL_NAME = "com.github.aleksandermielczarek.realmrepository.idsearch.IntegerIdSearch";

    public static final String LONG_ID_SEARCH_CLASS_PACKAGE = "com.github.aleksandermielczarek.realmrepository.idsearch";
    public static final String LONG_ID_SEARCH_CLASS_NAME = "LongIdSearch";
    public static final String LONG_ID_SEARCH_CLASS_FULL_NAME = "com.github.aleksandermielczarek.realmrepository.idsearch.LongIdSearch";

    public static final String SHORT_ID_SEARCH_CLASS_PACKAGE = "com.github.aleksandermielczarek.realmrepository.idsearch";
    public static final String SHORT_ID_SEARCH_CLASS_NAME = "ShortIdSearch";
    public static final String SHORT_ID_SEARCH_CLASS_FULL_NAME = "com.github.aleksandermielczarek.realmrepository.idsearch.ShortIdSearch";

    public static final String STRING_ID_SEARCH_CLASS_PACKAGE = "com.github.aleksandermielczarek.realmrepository.idsearch";
    public static final String STRING_ID_SEARCH_CLASS_NAME = "StringIdSearch";
    public static final String STRING_ID_SEARCH_CLASS_FULL_NAME = "com.github.aleksandermielczarek.realmrepository.idsearch.StringIdSearch";

    public static final String ID_SETTER_INTERFACE_PACKAGE = "com.github.aleksandermielczarek.realmrepository.idsetter";
    public static final String ID_SETTER_INTERFACE_NAME = "IdSetter";
    public static final String ID_SETTER_INTERFACE_FULL_NAME = "com.github.aleksandermielczarek.realmrepository.idsetter.IdSetter";

    private final TypeElement repositoryInterfaceTypeElement;

    @SuppressWarnings("unchecked")
    public GlobalConfiguration(ProcessingEnvironment processingEnvironment) {
        repositoryInterfaceTypeElement = processingEnvironment.getElementUtils().getTypeElement(REPOSITORY_INTERFACE_FULL_NAME);
    }

    public TypeElement getRepositoryInterfaceTypeElement() {
        return repositoryInterfaceTypeElement;
    }

}
