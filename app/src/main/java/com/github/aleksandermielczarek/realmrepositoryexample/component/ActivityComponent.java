package com.github.aleksandermielczarek.realmrepositoryexample.component;

import com.github.aleksandermielczarek.napkin.module.ActivityModule;
import com.github.aleksandermielczarek.napkin.scope.ActivityScope;
import com.github.aleksandermielczarek.realmrepositoryexample.ui.MainActivity;

import dagger.Subcomponent;

/**
 * Created by Aleksander Mielczarek on 11.11.2016.
 */
@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
}
