package com.binhle.vspelling.common;

import android.app.Activity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.TintContextWrapper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binhle.vspelling.common.CustomizeViews.AutoResizeTextView;
import com.binhle.vspelling.model.SpellingBase;

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
     * Update view's click event
     * @param view
     * @param clickListener
     */
    public static void updateViewClickEvent(View view, View.OnClickListener clickListener) {
        view.setOnClickListener(clickListener);
    }

    /**
     * Put data into view
     *
     * @param view
     */
//    public static void putDataView(View view, SpellingBase spellingBase, boolean hasBackground,
//                                   boolean hasSound) {
//        Class<?> type = view.getClass();
//        if (AppCompatImageView.class.equals(type)) {
//            putImageViewData(view, spellingBase, hasBackground, hasSound);
//        }
////        else if (AppCompatTextView.class.equals(type) || AutoResizeTextView.class.equals(type)) {
////            putTextViewData(view, spellingBase, hasBackground, hasSound);
////        }
//    }

    /**
     * Put image view data
     *
     * @param view
     * @param spellingBase
     */
//    private static void putImageViewData(View view, SpellingBase spellingBase, boolean hasImage,
//                                         boolean hasSound) {
//        AppCompatImageView imageView = (AppCompatImageView) view;
//        Activity imageSelfActivity = null;
//        try {
//            if (hasImage) {
//                imageSelfActivity = getActivityByView(imageView);
//                imageView.setImageResource(spellingBase.getImageResourceId());
//            }
//            if (hasSound) {
//                imageSelfActivity = imageSelfActivity == null ? getActivityByView(imageView) :
//                        imageSelfActivity;
//                spellingBase.updateMediaPlayer(imageSelfActivity);
//            }
//        } catch (Exception e) {
//            Log.e("Get Activity", e.getMessage());
//        }
//    }

    /**
     * Put textview data
     *
     * @param view
     * @param spellingBase
     * @param hasBackground
     * @param hasSound
     */
//    private static void putTextViewData(View view, SpellingBase spellingBase, boolean
//            hasBackground, boolean hasSound) {
//        // Set text for the view
//        TextView textView = (TextView) view;
//        try {
//            spellingBase.setViewId(view.getId());
//            textView.setText(spellingBase.getContent());
//            Activity viewSelfActivity = null;
//
//            // Set background of the view if it has background
//            if (hasBackground) {
//                viewSelfActivity = viewSelfActivity != null ? viewSelfActivity :
//                        getActivityByView(textView);
//                spellingBase.updateImageResourceId(viewSelfActivity);
//                textView.setBackgroundResource(spellingBase.getImageResourceId());
//            }
//
//            // Set background of the view if it has sound
//            if (hasSound) {
//                viewSelfActivity = viewSelfActivity != null ? viewSelfActivity :
//                        getActivityByView(textView);
//                spellingBase.updateMediaPlayer(viewSelfActivity);
//            }
//        } catch (Exception e) {
//            Log.e("PutTextViewData", e.getMessage());
//        }
//    }

    /**
     * Get activity by view
     *
     * @return
     */
    private static Activity getActivityByView(View view) {
        return (Activity) ((TintContextWrapper) view.getContext()).getBaseContext();
    }
}
