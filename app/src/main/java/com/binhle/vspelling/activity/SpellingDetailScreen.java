package com.binhle.vspelling.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binhle.vspelling.R;
import com.binhle.vspelling.common.ActivityHelper;
import com.binhle.vspelling.common.CustomizeViews.AutoResizeTextView;
import com.binhle.vspelling.common.MediaPlayerUtil;
import com.binhle.vspelling.common.ResourceUtil;
import com.binhle.vspelling.common.Util;
import com.binhle.vspelling.common.extension.StringExtension;
import com.binhle.vspelling.model.SpellingWord;
import com.binhle.vspelling.provider.DataManager;
import com.binhle.vspelling.provider.DataProvider;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SpellingDetailScreen extends AppCompatActivity {
    private Map<String, LinearLayout> similarLayouts;
    private LinearLayout mainLayout;
    private Map<String, SpellingWord> currentSimilarWords;
    private DataManager dataManager = DataProvider.getInstance();
    private String currentWord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_detail_screen);
        String wordName = getIntent().getStringExtra("wordName");
        execute(wordName);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataManager.clear();
    }

    /**
     * @param wordName
     */
    private void execute(String wordName) {
        this.currentWord = wordName;
        fetchViews();
        registerEvents();
        putViewsData();
        playSound(mainLayout);
    }

    /**
     * Fetch all view objects
     */
    private void fetchViews() {
        currentSimilarWords = new LinkedHashMap<>();
        // fetch main view
        mainLayout = (LinearLayout) findViewById(R.id.main);
        String layoutId = ResourceUtil.getResourceEntryName(this, mainLayout.getId());
        currentSimilarWords.put(layoutId, null);
        // fetch all views of similar word
        similarLayouts = new LinkedHashMap<>();
        LinearLayout similarLayout = (LinearLayout) findViewById(R.id.similar);
        for (int index = 0; index < similarLayout.getChildCount(); index++) {
            View view = similarLayout.getChildAt(index);
            if (view instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) view;
                layoutId = ResourceUtil.getResourceEntryName(this, linearLayout.getId());
                similarLayouts.put(layoutId, linearLayout);
                currentSimilarWords.put(layoutId, null);
            }
        }
    }

    /**
     * Put data for all views
     */
    private void putViewsData() {
        putMainViewData();
        putSimilarData();
    }

    /**
     * Put data into main view
     */
    private void putMainViewData() {
        Map<String, SpellingWord> spellingWordMap = dataManager.getSpellingWordMap();
        if (!StringExtension.isNullOrEmpty(currentWord) && !Util.isNull(mainLayout) &&
                !Util.isNull(spellingWordMap)) {
            SpellingWord spellingWord = spellingWordMap.get(currentWord);
            ImageView imageViewMain = (ImageView) ActivityHelper.
                    fetchAllChildrens(mainLayout, AppCompatImageView.class).get(0);
            TextView textViewMain = (TextView) ActivityHelper.
                    fetchAllChildrens(mainLayout, AutoResizeTextView.class).get(0);
            ActivityHelper.putDataView(imageViewMain, spellingWord, true, false);
            ActivityHelper.putDataView(textViewMain, spellingWord, false, false);
            String mainId = ResourceUtil.getResourceEntryName(this, mainLayout.getId());
            updateCurrentData(mainId, spellingWord);
        }
    }

    /**
     * Put data into similar words
     */
    private void putSimilarData() {
        Map<String, SpellingWord> spellingWordMap = dataManager.getSpellingWordMap();
        if (!StringExtension.isNullOrEmpty(currentWord) && !Util.isNull(similarLayouts) &&
                !Util.isNull(spellingWordMap)) {
            List<String> similarWordName = dataManager.
                    getSimilarSpelling(currentWord, similarLayouts.size());
            List<String> similarLayoutIds = new ArrayList<>(similarLayouts.keySet());
            String wordName;
            SpellingWord word;
            ImageView imageView;
            TextView textView;
            LinearLayout linearLayout;
            String similarId;
            for (int index = 0; index < similarWordName.size(); index++) {
                similarId = similarLayoutIds.get(index);
                linearLayout = similarLayouts.get(similarId);

                imageView = (ImageView) ActivityHelper.
                        fetchAllChildrens(linearLayout, AppCompatImageView.class).get(0);
                textView = (TextView) ActivityHelper.
                        fetchAllChildrens(linearLayout, AutoResizeTextView.class).get(0);
                wordName = similarWordName.get(index);
                word = spellingWordMap.get(wordName);
                ActivityHelper.putDataView(imageView, word, true, true);
                ActivityHelper.putDataView(textView, word, false, false);
                updateCurrentData(similarId, word);
            }
        }
    }

    /**
     * Update current data
     *
     * @param similarLayoutId
     * @param word
     */
    private void updateCurrentData(String similarLayoutId, SpellingWord word) {
        currentSimilarWords.put(similarLayoutId, word);
    }

    /**
     * Register events
     */
    private void registerEvents() {
        mainLayout.setOnClickListener(LinearLayoutOnClickListener);
        for (LinearLayout linearLayout : similarLayouts.values()) {
            linearLayout.setOnClickListener(LinearLayoutOnClickListener);
        }
    }

    /**
     * The Linear layout on click listener
     */
    private final View.OnClickListener LinearLayoutOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == mainLayout.getId()) {
                playSound(v);
            } else {
                String layoutId = ResourceUtil.getResourceEntryName(v.getContext(), v.getId());
                String wordName = currentSimilarWords.get(layoutId).getName();
                execute(wordName);
            }
        }
    };

    /**
     * Play sound
     *
     * @param v
     */
    private void playSound(View v) {
        String layoutId = ResourceUtil.getResourceEntryName(v.getContext(), v.getId());
        SpellingWord word = currentSimilarWords.get(layoutId);
        MediaPlayerUtil.playSound(word.getMediaPlayer());
    }
}
