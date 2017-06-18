package com.binhle.vspelling.HandleEvent.Implementation;

import android.content.Context;
import android.content.Intent;

import com.binhle.vspelling.Activity.MainScreen;

/**
 * Created by BinhLe on 5/1/2017.
 */
public class CommonEventHandler {

    public static void handleBackHomeEvent(Context context) {
        Intent backHomeIntent = new Intent(context, MainScreen.class);
        backHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(backHomeIntent);
    }
}
