package com.binhle.vspelling.HandleEvent.Implementation;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.binhle.vspelling.Activity.SpellingDetailScreen;
import com.binhle.vspelling.Common.CommonType;
import com.binhle.vspelling.HandleEvent.CustomizeEventHandler;
import com.binhle.vspelling.Common.ResourceUtil;
import com.binhle.vspelling.R;

/**
 * Created by BinhLe on 5/1/2017.
 */
public class SpellingListEventHandler implements CustomizeEventHandler {

    private static SpellingListEventHandler instance;

    private SpellingListEventHandler(){}
    public static synchronized SpellingListEventHandler getInstance() {
        if (instance == null) {
            instance = new SpellingListEventHandler();
        }
        return instance;
    }

    @Override
    public void handleImageViewEvent(Context context, View v) {
        if (v.getId() == R.id.img_home) {
            CommonEventHandler.handleBackHomeEvent(context);
        }
    }

    @Override
    public void handleTextViewEvent(Context context, View v) {
        handleDefaultEvent(context, v);
    }

    private void handleDefaultEvent(Context context, View v) {
        String resourceID = ResourceUtil.getResourceEntryName(context, v.getId());
        if (!resourceID.isEmpty()) {
            Intent alphaBetIntent = new Intent(context, SpellingDetailScreen.class);
            alphaBetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            alphaBetIntent.putExtra(String.valueOf(CommonType.IntentKeyType.INDEX), resourceID);
            context.startActivity(alphaBetIntent);
        }
    }
}
