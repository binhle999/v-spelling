package com.binhle.vspelling.common.CustomizeViews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.binhle.vspelling.HandleEvent.CustomizeEventHandler;
import com.binhle.vspelling.HandleEvent.EventManager;

/**
 * Created by BinhLe on 4/16/2017.
 */
public class CustomizeImageView extends ImageView implements View.OnClickListener {
    private Context context;
    private AttributeSet attributeSet;

    public CustomizeImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        this.attributeSet = attributeSet;
        this.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //Log.i("---------- Message:", "Im here");
        CustomizeEventHandler customizeEventHandler = EventManager.getEventHandler(context.getClass().getSimpleName());
        Log.i("---------context: ", context.getClass().getSimpleName());
        customizeEventHandler.handleImageViewEvent(context, v);
    }
}
