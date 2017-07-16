package com.binhle.vspelling.activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.binhle.vspelling.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlphaBetScreen extends AppCompatActivity {
    private Map<String, String> wordViewMap = new HashMap<>();
    private List<TextView> wordTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_bet_screen);
        Log.i("---------onCreate", "Done");

    }
}
