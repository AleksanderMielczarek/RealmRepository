package com.github.aleksandermielczarek.realmrepositoryexample.ui;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.text.TextUtils;

import com.github.aleksandermielczarek.realmrepositoryexample.BR;
import com.github.aleksandermielczarek.realmrepositoryexample.R;
import com.github.aleksandermielczarek.realmrepositoryexample.entity.User;
import com.github.aleksandermielczarek.realmrepositoryexample.repository.UserRepository;

import javax.inject.Inject;

import me.tatarka.bindingcollectionadapter.ItemView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Aleksander Mielczarek on 21.09.2016.
 */

public class MainViewModel {

    public final ObservableList<User> users = new ObservableArrayList<>();
    public final ItemView userView = ItemView.of(BR.viewModel, R.layout.item_user);
    public final ObservableField<User> newUser = new ObservableField<>();
    public final ObservableBoolean error = new ObservableBoolean(true);

    private final UserRepository userRepository;

    @Inject
    public MainViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        newUser.set(new User());
        userRepository.findAll()
                .subscribeOn(Schedulers.newThread())
                .subscribe(users::add);
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
        userRepository.save(newUser.get())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(users::add)
                .subscribe(added -> newUser.set(new User()));
    }

    public void removeUser(int position) {
        Observable.just(users.get(position))
                .toSingle()
                .flatMapCompletable(userRepository::delete)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> users.remove(position));
    }
}
