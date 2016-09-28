package com.github.aleksandermielczarek.realmrepository.processor;

import com.github.aleksandermielczarek.realmrepository.RealmRepository;
import com.github.aleksandermielczarek.realmrepository.processor.constructor.Constructor;
import com.github.aleksandermielczarek.realmrepository.processor.constructor.GenerateIdConstructor;
import com.github.aleksandermielczarek.realmrepository.processor.constructor.NotGenerateIdConstructor;
import com.github.aleksandermielczarek.realmrepository.processor.exception.RealmRepositoryException;
import com.github.aleksandermielczarek.realmrepository.processor.method.CountMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.CountSyncMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.DeleteAllMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.DeleteAllSyncMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.DeleteIdMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.DeleteIterableTMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.DeleteSyncIdMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.DeleteSyncIterableTMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.DeleteSyncTMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.DeleteTMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.ExistsIdMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.ExistsSyncIdMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.FindAllMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.FindAllSyncMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.FindFirstMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.FindFirstSyncMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.FindOneIdMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.FindOneSyncIdMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.Method;
import com.github.aleksandermielczarek.realmrepository.processor.method.SaveIterableTMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.SaveSyncIterableTMethod;
import com.github.aleksandermielczarek.realmrepository.processor.method.SaveSyncTMethod;
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

    public static final String COUNT_SYNC_METHOD = "countSync()";
    public static final String FIND_ONE_SYNC_ID_METHOD = "findOneSync(ID)";
    public static final String FIND_FIRST_SYNC_METHOD = "findFirstSync()";
    public static final String FIND_ALL_SYNC_METHOD = "findAllSync()";
    public static final String EXISTS_SYNC_ID_METHOD = "existsSync(ID)";
    public static final String SAVE_SYNC_T_METHOD = "saveSync(T)";
    public static final String SAVE_SYNC_ITERABLE_T_METHOD = "saveSync(java.lang.Iterable<T>)";
    public static final String DELETE_SYNC_T_METHOD = "deleteSync(T)";
    public static final String DELETE_SYNC_ID_METHOD = "deleteSync(ID)";
    public static final String DELETE_SYNC_ITERABLE_T_METHOD = "deleteSync(java.lang.Iterable<T>)";
    public static final String DELETE_ALL_SYNC_METHOD = "deleteAllSync()";
    public static final String COUNT_METHOD = "count()";
    public static final String FIND_ONE_ID_METHOD = "findOne(ID)";
    public static final String FIND_FIRST_METHOD = "findFirst()";
    public static final String FIND_ALL_METHOD = "findAll()";
    public static final String EXISTS_ID_METHOD = "exists(ID)";
    public static final String SAVE_T_METHOD = "save(T)";
    public static final String SAVE_ITERABLE_T_METHOD = "save(java.lang.Iterable<T>)";
    public static final String DELETE_T_METHOD = "delete(T)";
    public static final String DELETE_ID_METHOD = "delete(ID)";
    public static final String DELETE_ITERABLE_T_METHOD = "delete(java.lang.Iterable<T>)";
    public static final String DELETE_ALL_METHOD = "deleteAll()";

    private final Method countSyncMethod = new CountSyncMethod();
    private final Method findOneSyncIdMethod = new FindOneSyncIdMethod();
    private final Method findFirstSyncMethod = new FindFirstSyncMethod();
    private final Method findAllSyncMethod = new FindAllSyncMethod();
    private final Method existsSyncIdMethod = new ExistsSyncIdMethod();
    private final Method saveSyncTMethod = new SaveSyncTMethod();
    private final Method saveSyncIterableTMethod = new SaveSyncIterableTMethod();
    private final Method deleteSyncTMethod = new DeleteSyncTMethod();
    private final Method deleteSyncIdMethod = new DeleteSyncIdMethod();
    private final Method deleteSyncIterableTMethod = new DeleteSyncIterableTMethod();
    private final Method deleteAllSyncMethod = new DeleteAllSyncMethod();
    private final Method countMethod = new CountMethod();
    private final Method findOneIdMethod = new FindOneIdMethod();
    private final Method findFirstMethod = new FindFirstMethod();
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
        try {
            roundEnv.getElementsAnnotatedWith(RealmRepository.class).stream()
                    .filter(element -> element.getKind().equals(ElementKind.INTERFACE))
                    .map(element -> (TypeElement) element)
                    .forEach(dataRepositoryInterfaceTypeElement -> {
                        LocalConfiguration localConfiguration = new LocalConfiguration(dataRepositoryInterfaceTypeElement, processingEnv, globalConfiguration);

                        TypeSpec.Builder repositoryBuilder = localConfiguration.getRealmRepositoryAnnotation().abstractRepository() ?
                                TypeSpec.classBuilder("Abstract" + dataRepositoryInterfaceTypeElement.getSimpleName()).addModifiers(Modifier.ABSTRACT) :
                                TypeSpec.classBuilder(dataRepositoryInterfaceTypeElement.getSimpleName() + "Impl");

                        repositoryBuilder.addModifiers(Modifier.PUBLIC)
                                .addSuperinterface(TypeName.get(dataRepositoryInterfaceTypeElement.asType()))
                                .addField(ParameterizedTypeName.get(ClassName.get("java.lang", "Class"), TypeName.get(localConfiguration.getEntityTypeMirror())), "entityClass", localConfiguration.getRealmRepositoryAnnotation().abstractRepository() ? Modifier.PROTECTED : Modifier.PRIVATE, Modifier.FINAL)
                                .addField(ClassName.get(GlobalConfiguration.REALM_REPOSITORY_CONFIGURATION_CLASS_PACKAGE, GlobalConfiguration.REALM_REPOSITORY_CONFIGURATION_CLASS_NAME), "repositoryConfiguration", localConfiguration.getRealmRepositoryAnnotation().abstractRepository() ? Modifier.PROTECTED : Modifier.PRIVATE, Modifier.FINAL)
                                .addField(ParameterizedTypeName.get(ClassName.get(GlobalConfiguration.ID_GENERATOR_INTERFACE_PACKAGE, GlobalConfiguration.ID_GENERATOR_INTERFACE_NAME), TypeName.get(localConfiguration.getIdTypeMirror())), "idGenerator", localConfiguration.getRealmRepositoryAnnotation().abstractRepository() ? Modifier.PROTECTED : Modifier.PRIVATE, Modifier.FINAL)
                                .addField(ParameterizedTypeName.get(ClassName.get(GlobalConfiguration.ID_SETTER_INTERFACE_PACKAGE, GlobalConfiguration.ID_SETTER_INTERFACE_NAME), TypeName.get(localConfiguration.getEntityTypeMirror()), TypeName.get(localConfiguration.getIdTypeMirror())), "idSetter", localConfiguration.getRealmRepositoryAnnotation().abstractRepository() ? Modifier.PROTECTED : Modifier.PRIVATE, Modifier.FINAL)
                                .addField(ClassName.get("java.lang", "String"), "idFieldName", localConfiguration.getRealmRepositoryAnnotation().abstractRepository() ? Modifier.PROTECTED : Modifier.PRIVATE, Modifier.FINAL)
                                .addField(ClassName.get(GlobalConfiguration.ID_SEARCH_INTERFACE_PACKAGE, GlobalConfiguration.ID_SEARCH_INTERFACE_NAME), "idSearch", localConfiguration.getRealmRepositoryAnnotation().abstractRepository() ? Modifier.PROTECTED : Modifier.PRIVATE, Modifier.FINAL)
                                .addField(ParameterizedTypeName.get(ClassName.get(GlobalConfiguration.REPOSITORY_DELEGATE_CLASS_PACKAGE, GlobalConfiguration.REPOSITORY_DELEGATE_CLASS_NAME), TypeName.get(localConfiguration.getEntityTypeMirror()), TypeName.get(localConfiguration.getIdTypeMirror())), "repositoryDelegate", localConfiguration.getRealmRepositoryAnnotation().abstractRepository() ? Modifier.PROTECTED : Modifier.PRIVATE, Modifier.FINAL);

                        Constructor constructor = localConfiguration.getRealmRepositoryAnnotation().autoGenerateId() ? new GenerateIdConstructor() : new NotGenerateIdConstructor();
                        repositoryBuilder.addMethod(constructor.createConstructor(processingEnv, globalConfiguration, localConfiguration, repositoryBuilder));

                        Map<String, ExecutableElement> repositoryMethods = Stream.concat(dataRepositoryInterfaceTypeElement.getEnclosedElements().stream(), globalConfiguration.getRepositoryInterfaceTypeElement().getEnclosedElements().stream())
                                .filter(element -> element.getKind().equals(ElementKind.METHOD))
                                .map(element -> (ExecutableElement) element)
                                .collect(Collectors.toMap(Object::toString, method -> method));

                        repositoryBuilder.addMethod(countSyncMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(COUNT_SYNC_METHOD), repositoryBuilder))
                                .addMethod(findOneSyncIdMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(FIND_ONE_SYNC_ID_METHOD), repositoryBuilder))
                                .addMethod(findFirstSyncMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(FIND_FIRST_SYNC_METHOD), repositoryBuilder))
                                .addMethod(findAllSyncMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(FIND_ALL_SYNC_METHOD), repositoryBuilder))
                                .addMethod(existsSyncIdMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(EXISTS_SYNC_ID_METHOD), repositoryBuilder))
                                .addMethod(saveSyncTMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(SAVE_SYNC_T_METHOD), repositoryBuilder))
                                .addMethod(saveSyncIterableTMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(SAVE_SYNC_ITERABLE_T_METHOD), repositoryBuilder))
                                .addMethod(deleteSyncTMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(DELETE_SYNC_T_METHOD), repositoryBuilder))
                                .addMethod(deleteSyncIdMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(DELETE_SYNC_ID_METHOD), repositoryBuilder))
                                .addMethod(deleteSyncIterableTMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(DELETE_SYNC_ITERABLE_T_METHOD), repositoryBuilder))
                                .addMethod(deleteAllSyncMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(DELETE_ALL_SYNC_METHOD), repositoryBuilder))
                                .addMethod(countMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(COUNT_METHOD), repositoryBuilder))
                                .addMethod(findOneIdMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(FIND_ONE_ID_METHOD), repositoryBuilder))
                                .addMethod(findFirstMethod.createMethod(processingEnv, globalConfiguration, localConfiguration, repositoryMethods.remove(FIND_FIRST_METHOD), repositoryBuilder))
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
                            throw new RealmRepositoryException("Cannot write class" + localConfiguration.getDataRepositoryInterfaceTypeElement().getSimpleName(), e);
                        }
                    });
        } catch (RealmRepositoryException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
        return true;
    }

}
