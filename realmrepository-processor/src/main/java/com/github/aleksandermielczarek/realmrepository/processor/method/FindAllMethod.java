package com.github.aleksandermielczarek.realmrepository.processor.method;

import com.github.aleksandermielczarek.realmrepository.processor.GlobalConfiguration;
import com.github.aleksandermielczarek.realmrepository.processor.LocalConfiguration;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;

/**
 * Created by Aleksander Mielczarek on 23.09.2016.
 */
public class FindAllMethod implements Method {

    @Override
    public MethodSpec createMethod(ProcessingEnvironment processingEnvironment, GlobalConfiguration globalConfiguration, LocalConfiguration localConfiguration, ExecutableElement method, TypeSpec.Builder repositoryBuilder) {
        return MethodSpec.overriding(method, localConfiguration.getRepositoryInterfaceDeclaredType(), processingEnvironment.getTypeUtils())
                .addCode(CodeBlock.builder()
                        .addStatement("return repositoryDelegate.findAll()")
                        .build())
                .build();
    }
}
