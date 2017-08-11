package com.binhle.vspelling.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binhle.vspelling.R;
import com.binhle.vspelling.common.util.ActivityHelper;
import com.binhle.vspelling.common.customize.AutoResizeTextView;
import com.binhle.vspelling.common.util.ResourceUtil;
import com.binhle.vspelling.common.util.Util;
import com.binhle.vspelling.model.SpellingBase;
import com.binhle.vspelling.model.SpellingWord;
import com.binhle.vspelling.provider.DataManager;
import com.binhle.vspelling.provider.DataProvider;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SpellingDetailScreen extends AppCompatActivity {

    private Map<String, LinearLayout> similarLayouts;
    private ImageView homeView;
    private ImageView previousView;
    private LinearLayout mainLayout;
    private Map<String, SpellingWord> currentSimilarWords;
    private DataManager dataManager = DataProvider.getInstance();
    private String currentWord;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_detail_screen);
        String wordName = getIntent().getStringExtra("wordName");
        fetchViews();
        registerEvents();
        execute(wordName);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * @param wordName
     */
    private void execute(String wordName) {
        this.currentWord = wordName;
        putViewsData();
        playSound(mainLayout);
    }

    /**
     * Fetch all view objects
     */
    private void fetchViews() {
        currentSimilarWords = new LinkedHashMap<>();
        homeView = (ImageView) findViewById(R.id.img_home);
        previousView = (ImageView) findViewById(R.id.img_back);
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
        Map<String, SpellingBase> spellingWordMap = dataManager.getSpellingWordMap();
        if (!Util.isNullOrEmpty(currentWord) && !Util.isNull(mainLayout) &&
                !Util.isNull(spellingWordMap)) {
            SpellingWord word = (SpellingWord) spellingWordMap.get(currentWord);
            ImageView imageView = (ImageView) ActivityHelper.
                    fetchAllChildren(mainLayout, AppCompatImageView.class).get(0);
            TextView textView = (TextView) ActivityHelper.
                    fetchAllChildren(mainLayout, AutoResizeTextView.class).get(0);
            ActivityHelper.updateImageResource(imageView, word.getImage());
            ActivityHelper.updateText(textView, word.getContent());
            String mainId = ResourceUtil.getResourceEntryName(this, mainLayout.getId());
            updateCurrentData(mainId, word);
        }
    }

    /**
     * Put data into similar words
     */
    private void putSimilarData() {
        Map<String, SpellingBase> spellingWordMap = dataManager.getSpellingWordMap();
        if (!Util.isNullOrEmpty(currentWord) && !Util.isNull(similarLayouts) &&
                !Util.isNull(spellingWordMap)) {
            List<String> similarWordName = dataManager.
                    getSimilarSpellings(currentWord, similarLayouts.size());
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
                        fetchAllChildren(linearLayout, AppCompatImageView.class).get(0);
                textView = (TextView) ActivityHelper.
                        fetchAllChildren(linearLayout, AutoResizeTextView.class).get(0);
                wordName = similarWordName.get(index);
                word = (SpellingWord) spellingWordMap.get(wordName);
                ActivityHelper.updateImageResource(imageView, word.getImage());
                ActivityHelper.updateText(textView, word.getContent());
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
        homeView.setOnClickListener(OtherOnClickListener);
        previousView.setOnClickListener(OtherOnClickListener);
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
     * The other on click listener
     */
    private final View.OnClickListener OtherOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            if (viewId == homeView.getId()) {
                ActivityHelper.backHome(SpellingDetailScreen.this);
            } else if (viewId == previousView.getId()) {
                onBackPressed();
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
        int soundId = ResourceUtil.getSoundResource(this, word.getSound());
        stopSound();
        mediaPlayer = MediaPlayer.create(this, soundId);
        mediaPlayer.start();
    }

    @Override
    public void onBackPressed() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        super.onBackPressed();
    }

    private void stopSound() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();;
        }
    }
}
