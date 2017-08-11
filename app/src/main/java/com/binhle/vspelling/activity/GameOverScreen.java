package com.binhle.vspelling.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.binhle.vspelling.R;
import com.binhle.vspelling.common.util.ActivityHelper;

public class GameOverScreen extends AppCompatActivity {

    private Animation blink;
    private Animation clockwise;
    private Animation fade;
    private Animation move;
    private Animation retryAnim;
    private Animation slide;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        final ImageView imageView = (ImageView) findViewById(R.id.img_retry);
        retryAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bob);
        imageView.startAnimation(retryAnim);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2GameMenu = new Intent(GameOverScreen.this, GameDetailScreen.class);
                intent2GameMenu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent
                        .FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2GameMenu);
            }
        });
        ImageView home = (ImageView) findViewById(R.id.img_cancel);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHelper.backHome(GameOverScreen.this);
            }
        });
    }


}
