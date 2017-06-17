package com.binhle.vspelling.HandleEvent.Implementation;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.binhle.vspelling.HandleEvent.CustomizeEventHandler;
import com.binhle.vspelling.Logic.AlphaBetaDetailLogic;

/**
 * Created by BinhLe on 5/1/2017.
 */
public class AlphaBetDetailEventHandler implements CustomizeEventHandler {
    private static AlphaBetDetailEventHandler instance;

    private AlphaBetDetailEventHandler(){}
    public static synchronized AlphaBetDetailEventHandler getInstance() {
        if (instance == null) {
            instance = new AlphaBetDetailEventHandler();
        }
        return instance;
    }

//    private AlphaBetaDetailLogic logic = AlphaBetaDetailLogic.getInstance();

    @Override
    public void handleImageViewEvent(Context context, View v) {

    }

    @Override
    public void handleTextViewEvent(Context context, View v) {

    }
}
