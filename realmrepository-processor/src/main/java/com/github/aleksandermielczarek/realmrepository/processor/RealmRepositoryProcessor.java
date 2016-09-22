package com.github.aleksandermielczarek.realmrepository.processor;

import com.google.auto.service.AutoService;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * Created by Aleksander Mielczarek on 21.09.2016.
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("com.github.aleksandermielczarek.realmrepository.RealmRepository")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class RealmRepositoryProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return false;
    }
}
