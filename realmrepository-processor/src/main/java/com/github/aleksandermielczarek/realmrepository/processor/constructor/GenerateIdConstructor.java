package com.github.aleksandermielczarek.realmrepository.processor.constructor;

import com.github.aleksandermielczarek.realmrepository.processor.GlobalConfiguration;
import com.github.aleksandermielczarek.realmrepository.processor.LocalConfiguration;
import com.github.aleksandermielczarek.realmrepository.processor.factory.IdGeneratorFactory;
import com.github.aleksandermielczarek.realmrepository.processor.factory.IdSearchFactory;
import com.google.common.base.CaseFormat;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;

/**
 * Created by Aleksander Mielczarek on 23.09.2016.
 */
public class GenerateIdConstructor implements Constructor {

    @Override
    public MethodSpec createConstructor(ProcessingEnvironment processingEnvironment, GlobalConfiguration globalConfiguration, LocalConfiguration localConfiguration, TypeSpec.Builder repositoryBuilder) {
        TypeSpec idSetter = TypeSpec.classBuilder(localConfiguration.getEntityTypeElement().getSimpleName() + "IdSetter")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(GlobalConfiguration.ID_SETTER_INTERFACE_PACKAGE, GlobalConfiguration.ID_SETTER_INTERFACE_NAME), TypeName.get(localConfiguration.getEntityTypeMirror()), TypeName.get(localConfiguration.getIdTypeMirror())))
                .addMethod(MethodSpec.methodBuilder("setId")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(TypeName.VOID)
                        .addParameter(TypeName.get(localConfiguration.getEntityTypeMirror()), "entity")
                        .addParameter(TypeName.get(localConfiguration.getIdTypeMirror()), "id")
                        .addCode(CodeBlock.builder()
                                .addStatement("entity.set" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, localConfiguration.getIdFieldName()) + "(id)")
                                .build())
                        .build())
                .build();

        repositoryBuilder.addType(idSetter);

        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get(GlobalConfiguration.REALM_REPOSITORY_CONFIGURATION_CLASS_PACKAGE, GlobalConfiguration.REALM_REPOSITORY_CONFIGURATION_CLASS_NAME), "repositoryConfiguration")
                .addCode(CodeBlock.builder()
                        .addStatement("entityClass = " + localConfiguration.getEntityTypeElement().getSimpleName() + ".class")
                        .addStatement("this.repositoryConfiguration = repositoryConfiguration")
                        .addStatement("idGenerator = new $T()", IdGeneratorFactory.createIdGenerator(processingEnvironment, localConfiguration))
                        .addStatement("idSetter = new $N()", idSetter)
                        .addStatement("idFieldName = $S", localConfiguration.getIdFieldName())
                        .addStatement("idSearch = new $T()", IdSearchFactory.createIdSearch(processingEnvironment, localConfiguration))
                        .addStatement("repositoryDelegate = new $T(entityClass, repositoryConfiguration, idGenerator, idSetter, idFieldName, idSearch)", ParameterizedTypeName.get(ClassName.get(GlobalConfiguration.REPOSITORY_DELEGATE_CLASS_PACKAGE, GlobalConfiguration.REPOSITORY_DELEGATE_CLASS_NAME), TypeName.get(localConfiguration.getEntityTypeMirror()), TypeName.get(localConfiguration.getIdTypeMirror())))
                        .build())
                .build();
    }
}
