package com.github.aleksandermielczarek.realmrepositoryexample.ui.bindingadapter;

import android.databinding.BindingAdapter;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

/**
 * Created by Aleksander Mielczarek on 22.09.2016.
 */

public class FloatingActionButtonBindingAdapter {

    private FloatingActionButtonBindingAdapter() {

    }

    @BindingAdapter({"showing", "showingAnchor"})
    public static void setError(FloatingActionButton actionButton, boolean showing, int showingAnchor) {
        if (actionButton != null) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) actionButton.getLayoutParams();
            if (showing) {
                layoutParams.setAnchorId(showingAnchor);
                actionButton.setLayoutParams(layoutParams);
                actionButton.show();
            } else {
                layoutParams.setAnchorId(View.NO_ID);
                actionButton.hide();
            }
        }
    }

}
