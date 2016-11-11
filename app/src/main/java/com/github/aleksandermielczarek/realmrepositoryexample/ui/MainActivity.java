package com.github.aleksandermielczarek.realmrepositoryexample.ui;

import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.View;

import com.github.aleksandermielczarek.napkin.Napkin;
import com.github.aleksandermielczarek.napkin.module.ActivityModule;
import com.github.aleksandermielczarek.realmrepositoryexample.R;
import com.github.aleksandermielczarek.realmrepositoryexample.component.AppComponent;
import com.github.aleksandermielczarek.realmrepositoryexample.databinding.ActivityMainBinding;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding.widget.RxTextView;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Aleksander Mielczarek on 21.09.2016.
 */

public class MainActivity extends AppCompatActivity {

    @Inject
    protected MainViewModel mainViewModel;

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Napkin.<AppComponent>provideComponent(this)
                .with(new ActivityModule(this))
                .inject(this);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(mainViewModel);
        compositeSubscription.add(mainViewModel.validate(RxTextView.textChanges(binding.name), RxTextView.textChanges(binding.surname)));
        ItemTouchHelper deleteUserTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                mainViewModel.removeUser(position);
            }
        });
        deleteUserTouchHelper.attachToRecyclerView(binding.users);

        binding.users.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int positionInAdapter = parent.getChildAdapterPosition(view);

                if (positionInAdapter == 0) {
                    outRect.top = getResources().getDimensionPixelSize(R.dimen.standardMargin);
                    outRect.bottom = getResources().getDimensionPixelSize(R.dimen.standardMarginHalf);
                } else if (positionInAdapter == parent.getAdapter().getItemCount() - 1) {
                    outRect.top = getResources().getDimensionPixelSize(R.dimen.standardMarginHalf);
                    outRect.bottom = getResources().getDimensionPixelSize(R.dimen.standardMargin);
                } else {
                    outRect.top = getResources().getDimensionPixelSize(R.dimen.standardMarginHalf);
                    outRect.bottom = getResources().getDimensionPixelSize(R.dimen.standardMarginHalf);
                }

                outRect.left = getResources().getDimensionPixelSize(R.dimen.standardMargin);
                outRect.right = getResources().getDimensionPixelSize(R.dimen.standardMargin);
            }
        });
        setSupportActionBar(binding.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_action_search));
        compositeSubscription.add(mainViewModel.searchCity(RxSearchView.queryTextChanges(searchView)));
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }
}
