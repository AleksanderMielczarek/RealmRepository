package com.github.aleksandermielczarek.realmrepositoryexample;

import com.github.aleksandermielczarek.napkin.scope.AppScope;
import com.github.aleksandermielczarek.realmrepositoryexample.ui.MainActivity;

import dagger.Component;

/**
 * Created by Aleksander Mielczarek on 22.09.2016.
 */
@Component(modules = AppModule.class)
@AppScope
public interface AppComponent {

    void inject(RealmRepositoryApplication application);

    void inject(MainActivity mainActivity);
}
