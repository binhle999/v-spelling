package com.binhle.vspelling.common.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.binhle.vspelling.common.constant.Constants;

/**
 * Created by BinhLe on 4/16/2017.
 */
public class ResourceUtil {
    /**
     * Get entry name of resource
     *
     * @param context
     * @param resId
     * @return
     */
    public static String getResourceEntryName(Context context, int resId) {
        String name = "";
        try {
            name = context.getResources().getResourceEntryName(resId);
            Log.i("Resource Name", name);
        } catch (Exception e) {
            Log.e("GetResourceName Error", String.format(Constants.RESOURCE_NAME_NOT_FOUND, resId));
        }
        return name;
    }

    /**
     * Get resource id
     *
     * @param context
     * @param type
     * @param name
     * @return
     */
    public static int getResourceId(Context context, String type, String name) {
        int result = -1;
        try {
            result = context.getResources().getIdentifier(name, type, context.getPackageName());

        } catch (Exception e) {
            Log.e("Get Resource ID Error:", "" + e.getMessage());
        }
        Log.i("---- Resource ID: ", "" + result);
        return result;
    }

    /**
     * Get sound resource id
     *
     * @param activity
     * @param soundName
     * @return
     */
    public static int getSoundResource(Activity activity, String soundName) {
        return getResourceId(activity.getBaseContext(), "raw", soundName);
    }

    /**
     * Get image resource id
     *
     * @param activity
     * @param imageName
     * @return
     */
    public static int getImageResource(Activity activity, String imageName) {
        return getResourceId(activity.getBaseContext(), "drawable", imageName);
    }
}

