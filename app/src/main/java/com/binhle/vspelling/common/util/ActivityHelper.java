package com.binhle.vspelling.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.TintContextWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.binhle.vspelling.activity.MainScreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BinhLe on 7/15/2017.
 */
public final class ActivityHelper {
    /**
     * Fetch all children
     *
     * @param view
     * @return
     */
    public static List<View> fetchAllChildren(View view, Class<?> cls) {
        if (!(view instanceof ViewGroup)) {
            List<View> viewList1 = new ArrayList<>();
            if (cls == view.getClass()) {
                viewList1.add(view);
            }
            return viewList1;
        }
        List<View> viewList2 = new ArrayList<>();
        ViewGroup viewGroup = (ViewGroup) view;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childView = viewGroup.getChildAt(i);
            viewList2.addAll(fetchAllChildren(childView, cls));
        }
        return viewList2;
    }

    /**
     * Update image resource
     * @param view
     * @param imageId
     */
    public static void updateImageResource(ImageView view, String imageId) {
        Activity activity = getActivityByView(view);
        int imageResId = ResourceUtil.getImageResource(activity, imageId);
        view.setImageResource(imageResId);
    }

    /**
     * Update text for textview
     * @param view
     * @param text
     */
    public static void updateText(TextView view, String text) {
        view.setText(text);
    }

    /**
     * Update view's click event
     * @param view
     * @param clickListener
     */
    public static void updateViewClickEvent(View view, View.OnClickListener clickListener) {
        view.setOnClickListener(clickListener);
    }

    /**
     * Back to home
     * @param context
     */
    public static void backHome(Context context) {
        Intent intent = new Intent(context, MainScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
    /**
     * Get activity by view
     *
     * @return
     */
    private static Activity getActivityByView(View view) {
        return (Activity) ((TintContextWrapper) view.getContext()).getBaseContext();
    }
}
