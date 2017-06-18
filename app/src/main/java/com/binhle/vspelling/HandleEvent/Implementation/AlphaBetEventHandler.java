package com.binhle.vspelling.HandleEvent.Implementation;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.binhle.vspelling.Activity.AlphaBetaDetailScreen;
import com.binhle.vspelling.Common.CommonType;
import com.binhle.vspelling.Common.ResourceUtil;
import com.binhle.vspelling.R;

import com.binhle.vspelling.HandleEvent.CustomizeEventHandler;

/**
 * Created by BinhLe on 4/16/2017.
 */
public class AlphaBetEventHandler implements CustomizeEventHandler {
    private static AlphaBetEventHandler instance;

    private AlphaBetEventHandler(){}
    public static synchronized AlphaBetEventHandler getInstance() {
        if (instance == null) {
            instance = new AlphaBetEventHandler();
        }
        return instance;
    }

    private void handleCustomizeImageViewEvent(Context context, View v) {
        if (v.getId() == R.id.img_home) {
            CommonEventHandler.handleBackHomeEvent(context);
        } else {
            handleDefaultEvent(context, v);
        }
    }

    private void handleDefaultEvent(Context context, View v) {
        String resourceID = ResourceUtil.getResourceEntryName(context, v.getId());
        if (!resourceID.isEmpty()) {
            Intent alphaBetIntent = new Intent(context, AlphaBetaDetailScreen.class);
            alphaBetIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            alphaBetIntent.putExtra(String.valueOf(CommonType.IntentKeyType.INDEX), resourceID.replace("img_",""));
            context.startActivity(alphaBetIntent);
        }
    }

    @Override
    public void handleImageViewEvent(Context context, View v) {
        handleCustomizeImageViewEvent(context, v);
    }

    @Override
    public void handleTextViewEvent(Context context, View v) {

    }
}
