package com.github.aleksandermielczarek.realmrepository.processor.factory;

import com.github.aleksandermielczarek.realmrepository.processor.GlobalConfiguration;
import com.github.aleksandermielczarek.realmrepository.processor.LocalConfiguration;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;

/**
 * Created by Aleksander Mielczarek on 24.09.2016.
 */

public class IdGeneratorFactory {

    private IdGeneratorFactory() {

    }

    public static TypeName createIdGenerator(ProcessingEnvironment processingEnvironment, LocalConfiguration localConfiguration) {
        TypeName idTypeName = TypeName.get(localConfiguration.getIdTypeMirror());
        if (idTypeName.equals(ClassName.get("java.lang", "String"))) {
            return ClassName.get(GlobalConfiguration.STRING_ID_GENERATOR_CLASS_PACKAGE, GlobalConfiguration.STRING_ID_GENERATOR_CLASS_NAME);
        } else {
            processingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, "Only String Id generation is supported");
            return null;
        }
    }
}
