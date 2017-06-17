package com.binhle.vspelling.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.binhle.vspelling.R;

public class AlphaBetScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_bet_screen);
        Log.i("---------onCreate", "Done");
    }
}
