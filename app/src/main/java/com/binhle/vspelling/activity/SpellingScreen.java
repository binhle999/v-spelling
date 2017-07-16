package com.binhle.vspelling.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binhle.vspelling.R;
import com.binhle.vspelling.common.ActivityHelper;
import com.binhle.vspelling.common.Constants;
import com.binhle.vspelling.common.CustomizeViews.AutoResizeTextView;
import com.binhle.vspelling.common.ResourceUtil;
import com.binhle.vspelling.model.SpellingWord;
import com.binhle.vspelling.provider.DataProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellingScreen extends AppCompatActivity {

    private Map<String, SpellingWord> wordsMap;
    private List<View> listOfViews;
    private Map<String, String> wordViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_screen);
        fetchWordViews();
        fillDataViews();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("State", "OnRestart");
        int a = 1;
    }

    @Override
    protected void onResume() {
        super.onResume();
        int b  = 1;
        Log.i("State", "OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("State", "OnPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("State", "OnStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("State", "OnStop");
    }

    /**
     * Fetch all views to show data
     */
    private void fetchWordViews() {
        LinearLayout itemList = (LinearLayout)findViewById(R.id.list_item);
        listOfViews = ActivityHelper.fetchAllChildrens(itemList, AutoResizeTextView.class);
    }

    /**
     * Fill data views
     */
    private void fillDataViews() {
        DataProvider dataProvider = DataProvider.getInstance();
        dataProvider.setupSpellingService(this);
        wordsMap = dataProvider.fetchWordsByIndex(Constants.DEFAULT_START_PAGE_INDEX);
        Object[] words = wordsMap.values().toArray();
        wordViews = new HashMap<>();
        for (int index = 0; index < listOfViews.size(); index++) {
            AutoResizeTextView view = (AutoResizeTextView)listOfViews.get(index);
            SpellingWord word = (SpellingWord)words[index];
            view.setText(word.getContent(), TextView.BufferType.SPANNABLE);
            String viewId = ResourceUtil.getResourceEntryName(getBaseContext(), view.getId());
            wordViews.put(viewId, word.getName());
            view.setOnClickListener(TextViewOnClickListener);
        }
    }

    /**
     * The click listener
     */
    private final View.OnClickListener TextViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String viewId = ResourceUtil.getResourceEntryName(getBaseContext(), v.getId());
            Log.i("View ID", viewId);
            String wordName = wordViews.get(viewId);
            Log.i("Word Name", wordName);
            Intent intent = new Intent(v.getContext(), SpellingDetailScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("wordName", wordName);
            startActivity(intent);
        }
    };
}