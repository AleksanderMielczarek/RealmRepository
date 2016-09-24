package com.github.aleksandermielczarek.realmrepository.processor;

import com.github.aleksandermielczarek.realmrepository.processor.constructor.Constructor;
import com.github.aleksandermielczarek.realmrepository.processor.constructor.GenerateIdConstructor;
import com.github.aleksandermielczarek.realmrepository.processor.constructor.NotGenerateIdConstructor;
import com.github.aleksandermielczarek.realmrepository.processor.method.CountMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.DeleteAllMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.DeleteIdMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.DeleteIterableTMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.DeleteTMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.ExistsIdMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.FindAllMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.GetFirstMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.GetOneIdMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.Method;
import com.github.aleksandermielczarek.realmrepository.processor.method.SaveIterableTMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.SaveTMethod;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by Aleksander Mielczarek on 21.09.2016.
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes(GlobalConfiguration.REALM_REPOSITORY_ANNOTATION_FULL_NAME)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class RealmRepositoryProcessor extends AbstractProcessor {

    public static final String COUNT_METHOD = "count()";
    public static final String GET_ONE_ID_METHOD = "getOne(ID)";
    public static final String GET_FIRST_METHOD = "getFirst()";
    public static final String FIND_ALL_METHOD = "findAll()";
    public static final String EXISTS_ID_METHOD = "exists(ID)";
    public static final String SAVE_T_METHOD = "save(T)";
    public static final String SAVE_ITERABLE_T_METHOD = "save(java.lang.Iterable<T>)";
    public static final String DELETE_T_METHOD = "delete(T)";
    public static final String DELETE_ID_METHOD = "delete(ID)";
    public static final String DELETE_ITERABLE_T_METHOD = "delete(java.lang.Iterable<T>)";
    public static final String DELETE_ALL_METHOD = "deleteAll()";

    private final Method countMethod = new CountMethod();
    private final Method getOneIdMethod = new GetOneIdMethod();
    private final Method getFirstMethod = new GetFirstMethod();
    private final Method findAllMethod = new FindAllMethod();
    private final Method existsIdMethod = new ExistsIdMethod();
    private final Method saveTMethod = new SaveTMethod();
    private final Method saveIterableTMethod = new SaveIterableTMethod();
    private final Method deleteTMethod = new DeleteTMethod();
    private final Method deleteIdMethod = new DeleteIdMethod();
    private final Method deleteIterableTMethod = new DeleteIterableTMethod();
    private final Method deleteAllMethod = new DeleteAllMethod();

    private GlobalConfiguration globalConfiguration;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        globalConfiguration = new GlobalConfiguration(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(globalConfiguration.getRealmRepositoryAnnotationClass()).stream()
                .filter(element -> element.getKind().equals(ElementKind.INTERFACE))
                .map(element -> (TypeElement) element)
                .forEach(dataRepositoryInterfaceTypeElement -> {
                    LocalConfiguration localConfiguration = new LocalConfiguration(dataRepositoryInterfaceTypeElement, processingEnv, globalConfiguration);

                    TypeSpec.Builder repositoryBuilder = TypeSpec.classBuilder(dataRepositoryInterfaceTypeElement.getSimpleName() + "Impl")
                            .addModifiers(Modifier.PUBLIC)
                            .addSuperinterface(TypeName.get(dataRepositoryInterfaceTypeElement.asType()))
                            .addField(ParameterizedTypeName.get(ClassName.get("java.lang", "Class"), TypeName.get(localConfiguration.getEntityTypeMirror())), "entityClass", Modifier.PRIVATE, Modifier.FINAL)
                            .addField(ClassName.get(GlobalConfiguration.REALM_REPOSITORY_CONFIGURATION_CLASS_PACKAGE, GlobalConfiguration.REALM_REPOSITORY_CONFIGURATION_CLASS_NAME), "repositoryConfiguration", Modifier.PRIVATE, Modifier.FINAL)
                            .addField(ParameterizedTypeName.get(ClassName.get(GlobalConfiguration.ID_GENERATOR_INTERFACE_PACKAGE, GlobalConfiguration.ID_GENERATOR_INTERFACE_NAME), TypeName.get(localConfiguration.getIdTypeMirror())), "idGenerator", Modifier.PRIVATE, Modifier.FINAL)
                            .addField(ParameterizedTypeName.get(ClassName.get(GlobalConfiguration.ID_SETTER_INTERFACE_PACKAGE, GlobalConfiguration.ID_SETTER_INTERFACE_NAME), TypeName.get(localConfiguration.getEntityTypeMirror()), TypeName.get(localConfiguration.getIdTypeMirror())), "idSetter", Modifier.PRIVATE, Modifier.FINAL)
                            .addField(ClassName.get("java.lang", "String"), "idFieldName", Modifier.PRIVATE, Modifier.FINAL)
                            .addField(ClassName.get(GlobalConfiguration.ID_SEARCH_INTERFACE_PACKAGE, GlobalConfiguration.ID_SEARCH_INTERFACE_NAME), "idSearch", Modifier.PRIVATE, Modifier.FINAL)
                            .addField(ParameterizedTypeName.get(ClassName.get(GlobalConfiguration.REPOSITORY_DELEGATE_CLASS_PACKAGE, GlobalConfiguration.REPOSITORY_DELEGATE_CLASS_NAME), TypeName.get(localConfiguration.getEntityTypeMirror()), TypeName.get(localConfiguration.getIdTypeMirror())), "repositoryDelegate", Modifier.PRIVATE, Modifier.FINAL);

                    Constructor constructor = localConfiguration.isAutogenerateId() ? new GenerateIdConstructor() : new NotGenerateIdConstructor();
                    repositoryBuilder.addMethod(constructor.createConstructor(processingEnv, globalConfiguration, localConfiguration, repositoryBuilder));

                    Map<String, ExecutableElement> repositoryMethods = Stream.concat(dataRepositoryInterfaceTypeElement.getEnclosedElements().stream(), globalConfiguration.getRepositoryInterfaceTypeElement().getEnclosedElements().stream())
                            .filter(element -> element.getKind().equals(ElementKind.METHOD))
                            .map(element -> (ExecutableElement) element)
                            .collect(Collectors.toMap(Object::toString, method -> method));

                    repositoryBuilder.addMethod(countMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(COUNT_METHOD), repositoryBuilder))
                            .addMethod(getOneIdMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(GET_ONE_ID_METHOD), repositoryBuilder))
                            .addMethod(getFirstMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(GET_FIRST_METHOD), repositoryBuilder))
                            .addMethod(findAllMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(FIND_ALL_METHOD), repositoryBuilder))
                            .addMethod(existsIdMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(EXISTS_ID_METHOD), repositoryBuilder))
                            .addMethod(saveTMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(SAVE_T_METHOD), repositoryBuilder))
                            .addMethod(saveIterableTMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(SAVE_ITERABLE_T_METHOD), repositoryBuilder))
                            .addMethod(deleteTMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(DELETE_T_METHOD), repositoryBuilder))
                            .addMethod(deleteIdMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(DELETE_ID_METHOD), repositoryBuilder))
                            .addMethod(deleteIterableTMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(DELETE_ITERABLE_T_METHOD), repositoryBuilder))
                            .addMethod(deleteAllMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(DELETE_ALL_METHOD), repositoryBuilder));

                    JavaFile javaFile = JavaFile.builder(localConfiguration.getDataRepositoryPackageElement().getQualifiedName().toString(), repositoryBuilder.build())
                            .build();
                    try {
                        javaFile.writeTo(processingEnv.getFiler());
                    } catch (IOException e) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
                    }
                });

        return true;
    }

}
