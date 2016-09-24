package com.github.aleksandermielczarek.realmrepository.processor.factory;

import com.github.aleksandermielczarek.realmrepository.processor.GlobalConfiguration;
import com.github.aleksandermielczarek.realmrepository.processor.LocalConfiguration;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;

/**
 * Created by Aleksander Mielczarek on 24.09.2016.
 */

public class IdSearchFactory {

    private IdSearchFactory() {

    }

    public static TypeName createIdSearch(ProcessingEnvironment processingEnvironment, LocalConfiguration localConfiguration) {
        TypeName idTypeName = TypeName.get(localConfiguration.getIdTypeMirror());
        if (idTypeName.equals(ClassName.get("java.lang", "Boolean")) || idTypeName.equals(TypeName.BOOLEAN)) {
            return ClassName.get(GlobalConfiguration.BOOLEAN_ID_SEARCH_CLASS_PACKAGE, GlobalConfiguration.BOOLEAN_ID_SEARCH_CLASS_NAME);
        } else if (idTypeName.equals(ArrayTypeName.of(TypeName.BYTE))) {
            return ClassName.get(GlobalConfiguration.BYTE_ARRAY_ID_SEARCH_CLASS_PACKAGE, GlobalConfiguration.BYTE_ARRAY_ID_SEARCH_CLASS_NAME);
        } else if (idTypeName.equals(ClassName.get("java.lang", "Byte")) || idTypeName.equals(TypeName.BYTE)) {
            return ClassName.get(GlobalConfiguration.BOOLEAN_ID_SEARCH_CLASS_PACKAGE, GlobalConfiguration.BOOLEAN_ID_SEARCH_CLASS_NAME);
        } else if (idTypeName.equals(ClassName.get("java.util", "Date"))) {
            return ClassName.get(GlobalConfiguration.DATE_ID_SEARCH_CLASS_PACKAGE, GlobalConfiguration.DATE_ID_SEARCH_CLASS_NAME);
        } else if (idTypeName.equals(ClassName.get("java.lang", "Double")) || idTypeName.equals(TypeName.DOUBLE)) {
            return ClassName.get(GlobalConfiguration.DOUBLE_ID_SEARCH_CLASS_PACKAGE, GlobalConfiguration.DOUBLE_ID_SEARCH_CLASS_NAME);
        } else if (idTypeName.equals(ClassName.get("java.lang", "Float")) || idTypeName.equals(TypeName.FLOAT)) {
            return ClassName.get(GlobalConfiguration.FLOAT_ID_SEARCH_CLASS_PACKAGE, GlobalConfiguration.FLOAT_ID_SEARCH_CLASS_NAME);
        } else if (idTypeName.equals(ClassName.get("java.lang", "Integer")) || idTypeName.equals(TypeName.INT)) {
            return ClassName.get(GlobalConfiguration.INTEGER_ID_SEARCH_CLASS_PACKAGE, GlobalConfiguration.INTEGER_ID_SEARCH_CLASS_NAME);
        } else if (idTypeName.equals(ClassName.get("java.lang", "Long")) || idTypeName.equals(TypeName.LONG)) {
            return ClassName.get(GlobalConfiguration.LONG_ID_SEARCH_CLASS_PACKAGE, GlobalConfiguration.LONG_ID_SEARCH_CLASS_NAME);
        } else if (idTypeName.equals(ClassName.get("java.lang", "Short")) || idTypeName.equals(TypeName.SHORT)) {
            return ClassName.get(GlobalConfiguration.SHORT_ID_SEARCH_CLASS_PACKAGE, GlobalConfiguration.SHORT_ID_SEARCH_CLASS_NAME);
        } else if (idTypeName.equals(ClassName.get("java.lang", "String"))) {
            return ClassName.get(GlobalConfiguration.STRING_ID_SEARCH_CLASS_PACKAGE, GlobalConfiguration.STRING_ID_SEARCH_CLASS_NAME);
        } else {
            processingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, "Not supported id type");
            return null;
        }
    }
}
