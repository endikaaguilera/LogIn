package com.thisobeystudio.login.login.custom;

import android.content.Context;
import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.view.LayoutInflater;
import android.view.View;

import com.thisobeystudio.login.R;

import static android.support.constraint.ConstraintSet.BOTTOM;
import static android.support.constraint.ConstraintSet.RIGHT;
import static android.support.constraint.ConstraintSet.START;
import static android.support.constraint.ConstraintSet.TOP;

// Android ProgressDialog is deprecated, so we create our own ProgressDialog
// https://developer.android.com/reference/android/app/ProgressDialog
public class CustomProgressDialog {

    private ConstraintLayout container;
    private ConstraintLayout parent;

    public CustomProgressDialog(ConstraintLayout parent) {
        init(parent);
    }

    private void init(ConstraintLayout parent) {
        if (parent == null) return;
        this.parent = parent;
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        this.parent = parent;
        this.container = (ConstraintLayout)
                li.inflate(R.layout.custom_progress_dialog, parent, false);
        this.container.setOnClickListener(null); // disable user interaction
        this.container.setVisibility(View.GONE);
        parent.addView(container);
        connectConstraints();
    }

    private void connectConstraints() {

        if (parent == null) return;

        Context context = parent.getContext();
        if (context == null) return;

        Resources resources = context.getResources();
        if (resources == null) return;

        int elevation = (int) resources.getDimension(R.dimen.progressDialogElevation);

        ConstraintSet cs = new ConstraintSet();
        cs.clone(parent);
        cs.connect(this.container.getId(), START, parent.getId(), START);
        cs.connect(this.container.getId(), TOP, parent.getId(), TOP);
        cs.connect(this.container.getId(), RIGHT, parent.getId(), RIGHT);
        cs.connect(this.container.getId(), BOTTOM, parent.getId(), BOTTOM);
        cs.centerHorizontally(this.container.getId(), parent.getId());
        cs.centerVertically(this.container.getId(), parent.getId());
        cs.setElevation(this.container.getId(), elevation);
        cs.applyTo(this.parent);
    }

    public void show() {
        if (parent != null && container != null) container.setVisibility(View.VISIBLE);
    }

    public void hide() {
        if (parent != null && container != null) parent.removeView(container);
    }

//    public void cancelOnTouchOutside() {
//    }

}
