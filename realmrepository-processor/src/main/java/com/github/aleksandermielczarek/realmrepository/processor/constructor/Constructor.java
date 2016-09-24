package com.github.aleksandermielczarek.realmrepository.processor.constructor;

import com.github.aleksandermielczarek.realmrepository.processor.GlobalConfiguration;
import com.github.aleksandermielczarek.realmrepository.processor.LocalConfiguration;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;

/**
 * Created by Aleksander Mielczarek on 23.09.2016.
 */

public interface Constructor {

    MethodSpec createConstructor(ProcessingEnvironment processingEnvironment, GlobalConfiguration globalConfiguration, LocalConfiguration localConfiguration, TypeSpec.Builder repositoryBuilder);
}
