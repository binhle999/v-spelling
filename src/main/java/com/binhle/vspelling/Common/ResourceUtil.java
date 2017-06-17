package com.binhle.vspelling.Common;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

/**
 * Created by BinhLe on 4/16/2017.
 */
public class ResourceUtil {
    public static String getResourceEntryName(Context context, int resId) {
        String name = "";
        try {
            name = context.getResources().getResourceEntryName(resId);
            Log.i("Resource Name", name);
        } catch (Exception e) {
            Log.e("GetResourceName Error", String.format(ErrorConstants.RESOURCE_NAME_NOT_FOUND, resId));
        }
        return name;
    }

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

    public static int getSoundResource(Activity activity, String soundName) {
        return getResourceId(activity.getBaseContext(),
                String.valueOf(CommonType.ResourceType.RAW).toLowerCase(),
                soundName);
    }
    // Get image resource 
    public static int getImageResource(Activity activity, String imageName) {
        return getResourceId(activity.getBaseContext(),
                String.valueOf(CommonType.ResourceType.DRAWABLE).toLowerCase(),
                imageName);
    }
}

