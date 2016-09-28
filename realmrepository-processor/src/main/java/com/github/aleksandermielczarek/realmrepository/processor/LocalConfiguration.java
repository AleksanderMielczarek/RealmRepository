package com.github.aleksandermielczarek.realmrepository.processor;

import com.github.aleksandermielczarek.realmrepository.RealmRepository;

import java.util.List;
import java.util.Optional;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

/**
 * Created by Aleksander Mielczarek on 23.09.2016.
 */

public class LocalConfiguration {

    private final TypeElement dataRepositoryInterfaceTypeElement;
    private final PackageElement dataRepositoryPackageElement;
    private final DeclaredType repositoryInterfaceDeclaredType;
    private final RealmRepository realmRepositoryAnnotation;
    private final TypeMirror entityTypeMirror;
    private final TypeElement entityTypeElement;
    private final TypeMirror idTypeMirror;
    private final TypeElement idTypeElement;
    private String idFieldName;

    public LocalConfiguration(TypeElement dataRepositoryInterfaceTypeElement, ProcessingEnvironment processingEnvironment, GlobalConfiguration globalConfiguration) {
        this.dataRepositoryInterfaceTypeElement = dataRepositoryInterfaceTypeElement;
        dataRepositoryPackageElement = processingEnvironment.getElementUtils().getPackageOf(dataRepositoryInterfaceTypeElement);
        if (dataRepositoryInterfaceTypeElement.getInterfaces().size() != 1 || !dataRepositoryInterfaceTypeElement.getInterfaces().get(0).toString().contains(GlobalConfiguration.REPOSITORY_INTERFACE_FULL_NAME)) {
            processingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, "Repository interface must extend " + GlobalConfiguration.REPOSITORY_INTERFACE_FULL_NAME);
        }
        repositoryInterfaceDeclaredType = (DeclaredType) dataRepositoryInterfaceTypeElement.getInterfaces().get(0);
        realmRepositoryAnnotation = dataRepositoryInterfaceTypeElement.getAnnotation(RealmRepository.class);
        List<? extends TypeMirror> genericTypes = repositoryInterfaceDeclaredType.getTypeArguments();
        entityTypeMirror = genericTypes.get(0);
        idTypeMirror = genericTypes.get(1);
        entityTypeElement = processingEnvironment.getElementUtils().getTypeElement(entityTypeMirror.toString());
        idTypeElement = processingEnvironment.getElementUtils().getTypeElement(idTypeMirror.toString());
        Optional<VariableElement> primaryKeyField = entityTypeElement.getEnclosedElements().stream()
                .filter(element -> element.getKind().equals(ElementKind.FIELD))
                .filter(element -> !element.getModifiers().contains(Modifier.STATIC))
                .map(element -> (VariableElement) element)
                .filter(variableElement -> variableElement.getAnnotationMirrors().stream()
                        .map(Object::toString)
                        .anyMatch(annotation -> annotation.contains(GlobalConfiguration.PRIMARY_KEY_ANNOTATION_FULL_NAME)))
                .findAny();
        if (primaryKeyField.isPresent()) {
            idFieldName = primaryKeyField.get().getSimpleName().toString();
        } else {
            processingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, "Entity class must have @PrimaryKey");
        }
    }

    public TypeElement getDataRepositoryInterfaceTypeElement() {
        return dataRepositoryInterfaceTypeElement;
    }

    public PackageElement getDataRepositoryPackageElement() {
        return dataRepositoryPackageElement;
    }

    public DeclaredType getRepositoryInterfaceDeclaredType() {
        return repositoryInterfaceDeclaredType;
    }

    public TypeMirror getEntityTypeMirror() {
        return entityTypeMirror;
    }

    public TypeElement getEntityTypeElement() {
        return entityTypeElement;
    }

    public TypeMirror getIdTypeMirror() {
        return idTypeMirror;
    }

    public TypeElement getIdTypeElement() {
        return idTypeElement;
    }

    public String getIdFieldName() {
        return idFieldName;
    }

    public RealmRepository getRealmRepositoryAnnotation() {
        return realmRepositoryAnnotation;
    }
}
