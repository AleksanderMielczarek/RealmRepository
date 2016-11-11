package com.github.aleksandermielczarek.realmrepositoryexample.component;

import com.github.aleksandermielczarek.napkin.module.ActivityModule;
import com.github.aleksandermielczarek.napkin.module.AppModule;
import com.github.aleksandermielczarek.napkin.scope.AppScope;
import com.github.aleksandermielczarek.realmrepositoryexample.RealmRepositoryApplication;
import com.github.aleksandermielczarek.realmrepositoryexample.module.MainModule;

import dagger.Component;

/**
 * Created by Aleksander Mielczarek on 22.09.2016.
 */
@Component(modules = {AppModule.class, MainModule.class})
@AppScope
public interface AppComponent {

    void inject(RealmRepositoryApplication application);

    ActivityComponent with(ActivityModule activityModule);
}
