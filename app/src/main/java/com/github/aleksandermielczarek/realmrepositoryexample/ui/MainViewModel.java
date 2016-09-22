package com.github.aleksandermielczarek.realmrepositoryexample.ui;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.text.TextUtils;

import com.github.aleksandermielczarek.realmrepositoryexample.BR;
import com.github.aleksandermielczarek.realmrepositoryexample.R;
import com.github.aleksandermielczarek.realmrepositoryexample.entity.User;
import com.github.aleksandermielczarek.realmrepositoryexample.entity.UserFieldNames;

import javax.inject.Inject;

import me.tatarka.bindingcollectionadapter.ItemView;
import rx.Observable;
import rx.Subscription;

/**
 * Created by Aleksander Mielczarek on 21.09.2016.
 */

public class MainViewModel implements UserFieldNames {

    public final ObservableList<User> users = new ObservableArrayList<>();
    public final ItemView userView = ItemView.of(BR.viewModel, R.layout.item_user);
    public final ObservableField<User> newUser = new ObservableField<>();
    public final ObservableBoolean error = new ObservableBoolean(true);

    @Inject
    public MainViewModel() {
        newUser.set(new User());
    }

    public Subscription validate(Observable<CharSequence> name, Observable<CharSequence> surname) {
        return Observable.combineLatest(name.map(TextUtils::isEmpty),
                surname.map(TextUtils::isEmpty),
                (emptyName, emptySurname) -> emptyName || emptySurname)
                .subscribe(error::set);
    }

    public Subscription searchCity(Observable<CharSequence> query) {
        return query.subscribe();
    }

    public void addUser() {
        if (!error.get()) {
            users.add(newUser.get());
            newUser.set(new User());
        }
    }

    public void removeUser(int position) {
        users.remove(position);
    }
}
