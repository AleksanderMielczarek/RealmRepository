package com.github.aleksandermielczarek.realmrepository.processor.method;

import com.github.aleksandermielczarek.realmrepository.processor.GlobalConfiguration;
import com.github.aleksandermielczarek.realmrepository.processor.LocalConfiguration;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;

/**
 * Created by Aleksander Mielczarek on 23.09.2016.
 */

public interface Method {

    MethodSpec createMethod(ProcessingEnvironment processingEnvironment, GlobalConfiguration globalConfiguration, LocalConfiguration localConfiguration, ExecutableElement method, TypeSpec.Builder repositoryBuilder);
}
