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
import com.binhle.vspelling.common.customize.AutoResizeTextView;
import com.binhle.vspelling.common.util.ActivityHelper;
import com.binhle.vspelling.common.util.ResourceUtil;
import com.binhle.vspelling.common.util.Util;
import com.binhle.vspelling.model.Spelling;
import com.binhle.vspelling.model.SpellingBase;
import com.binhle.vspelling.model.Word;
import com.binhle.vspelling.provider.DataManager;
import com.binhle.vspelling.provider.DataProvider;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SpellingDetailScreen extends AppCompatActivity {

    private List<Integer> wordLayouts;
    private ImageView homeView;
    private ImageView backView;
    private LinearLayout mainLayout;

    private DataManager dataManager = DataProvider.getInstance();
    private String currentSpelling;
    private MediaPlayer mediaPlayer;

    private List<MediaPlayer> players = new LinkedList();
    private MediaPlayer mediaPlayer1, mediaPlayer2, mediaPlayer3, mediaPlayer4, mediaPlayer5;
    private Map<Integer, SpellingBase> dataViews = new LinkedHashMap<>();

    private Animation shake;
    private Animation flip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_detail_screen);
        String wordName = getIntent().getStringExtra("spellingName");
        fetchViews();
        execute(wordName);
    }

    /**
     * @param spellingName
     */
    private void execute(String spellingName) {
        this.currentSpelling = spellingName;
        putViewsData();
        playSoundFirstTime();
    }

    /**
     * Fetch all view objects
     */
    private void fetchViews() {
        shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        flip = AnimationUtils.loadAnimation(this, R.anim.flip);

        homeView = (ImageView) findViewById(R.id.img_home);
        backView = (ImageView) findViewById(R.id.img_back);
        ActivityHelper.updateViewClickEvent(homeView, OtherOnClickListener);
        ActivityHelper.updateViewClickEvent(backView, OtherOnClickListener);
        // fetch main view
        mainLayout = (LinearLayout) findViewById(R.id.main);

        // fetch all views of words
        wordLayouts = new ArrayList<>();
        LinearLayout similarLayout = (LinearLayout) findViewById(R.id.words);
        for (int index = 0; index < similarLayout.getChildCount(); index++) {
            View view = similarLayout.getChildAt(index);
            if (view instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) view;
                wordLayouts.add(linearLayout.getId());
            }
        }
    }

    /**
     * Put data for all views
     */
    private void putViewsData() {
        putMainViewData();
        putWordsData();
    }

    /**
     * Put data into main view
     */
    private void putMainViewData() {
        Map<String, SpellingBase> spellingMap = dataManager.getSpellingWordMap();
        if (!Util.isNullOrEmpty(currentSpelling) && !Util.isNull(mainLayout) &&
                !Util.isNull(spellingMap)) {
            Spelling spelling = (Spelling) spellingMap.get(currentSpelling);
            TextView textView = (TextView) ActivityHelper.
                    fetchAllChildren(mainLayout, AutoResizeTextView.class).get(0);
            ActivityHelper.updateText(textView, spelling.getContent());
            ActivityHelper.updateViewClickEvent(mainLayout, ViewGroupOnClickListener);
            updateDataViews(mainLayout, spelling);
            players.add(getMediaPlayer(spelling));
        }
    }

    /**
     * Put data into similar words
     */
    private void putWordsData() {
        Map<String, SpellingBase> spellingMap = dataManager.getSpellingWordMap();
        if (!Util.isNullOrEmpty(currentSpelling) && !Util.isNull(wordLayouts) &&
                !Util.isNull(spellingMap)) {
            List<SpellingBase> words = dataManager.fetchWordsBySpelling(currentSpelling, 1,
                    wordLayouts.size());
            int numberOfViewToShow = Math.min(words.size(), wordLayouts.size());
            View view;
            ImageView imageView;
            TextView textView;
            Word word;
            for (int index = 0; index < numberOfViewToShow; index++) {
                view = findViewById(wordLayouts.get(index));
                imageView = (ImageView) ActivityHelper.
                        fetchAllChildren(view, AppCompatImageView.class).get(0);
                textView = (TextView) ActivityHelper.
                        fetchAllChildren(view, AutoResizeTextView.class).get(0);
                word = (Word) words.get(index);
                ActivityHelper.updateImageResource(imageView, word.getImage());
                ActivityHelper.updateText(textView, word.getContent());

                ActivityHelper.updateViewClickEvent(view, ViewGroupOnClickListener);
                updateDataViews(view, word);
                players.add(getMediaPlayer(word));
            }
        }
    }

    /**
     * Play sound
     *
     * @param v
     */
    private void playSound(View v) {
        int viewId = v.getId();
        SpellingBase spellingBase = dataViews.get(viewId);
        ImageView imageView;
        if (viewId == mainLayout.getId()) {
            TextView mainTextView = (TextView) ActivityHelper.
                    fetchAllChildren(mainLayout, AutoResizeTextView.class).get(0);
            mainTextView.startAnimation(flip);
        } else {
            imageView = (ImageView) ((LinearLayout) findViewById(viewId)).getChildAt(0);
            imageView.startAnimation(flip);
        }
        int soundId = ResourceUtil.getSoundResource(this, spellingBase.getSound());
        if (soundId > 0) {
            mediaPlayer = MediaPlayer.create(this, soundId);
            mediaPlayer.start();
        }
    }

    /**
     * OnBackPressed
     */
    @Override
    public void onBackPressed() {
        stopSound();
        super.onBackPressed();
    }

    /**
     * Update data views
     *
     * @param view
     * @param spellingBase
     */
    private void updateDataViews(View view, SpellingBase spellingBase) {
        dataViews.put(view.getId(), spellingBase);
    }

    /**
     * Get media player
     *
     * @param spellingBase
     */
    private MediaPlayer getMediaPlayer(SpellingBase spellingBase) {
        MediaPlayer mediaPlayer = null;
        int soundId = ResourceUtil.getSoundResource(this, spellingBase.getSound());
        if (soundId > 0) {
            mediaPlayer = MediaPlayer.create(this, soundId);
        }
        return mediaPlayer;
    }

    /**
     * Stop all sounds
     */
    private void stopSound() {
        if (mediaPlayer1 != null && mediaPlayer1.isPlaying()) {
            mediaPlayer1.pause();
        }
        if (mediaPlayer2 != null && mediaPlayer2.isPlaying()) {
            mediaPlayer2.pause();
        }
        if (mediaPlayer3 != null && mediaPlayer3.isPlaying()) {
            mediaPlayer3.pause();
        }
        if (mediaPlayer4 != null && mediaPlayer4.isPlaying()) {
            mediaPlayer4.pause();
        }
        if (mediaPlayer5 != null && mediaPlayer5.isPlaying()) {
            mediaPlayer5.pause();
        }
    }

    /**
     * Play sound at the first time
     */
    private void playSoundFirstTime() {
        mediaPlayer1 = players.get(0);
        mediaPlayer2 = players.get(1);
        mediaPlayer3 = players.get(2);
        mediaPlayer4 = players.get(3);
        mediaPlayer5 = players.get(4);
        if (mediaPlayer1 != null) {
            mediaPlayer1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (mediaPlayer2 != null) {
                        mediaPlayer2.start();
                        stopAnimation();
                        findViewById(wordLayouts.get(0)).startAnimation(flip);
                    }
                }
            });
        }
        if (mediaPlayer2 != null) {
            mediaPlayer2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (mediaPlayer3 != null) {
                        mediaPlayer3.start();
                        stopAnimation();
                        findViewById(wordLayouts.get(1)).startAnimation(flip);
                    }
                }
            });
        }
        if (mediaPlayer3 != null) {
            mediaPlayer3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (mediaPlayer4 != null) {
                        mediaPlayer4.start();
                        stopAnimation();
                        findViewById(wordLayouts.get(2)).startAnimation(flip);
                    }
                }
            });
        }
        if (mediaPlayer4 != null) {
            mediaPlayer4.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (mediaPlayer5 != null) {
                        mediaPlayer5.start();
                        stopAnimation();
                        findViewById(wordLayouts.get(3)).startAnimation(flip);
                    }
                }
            });
        }
        stopSound();
        if (mediaPlayer1 != null) {
            mediaPlayer1.start();
            TextView mainTextView = (TextView) ActivityHelper.
                    fetchAllChildren(mainLayout, AutoResizeTextView.class).get(0);
            mainTextView.startAnimation(shake);
        }
    }

    /**
     * Stop animation
     */
    private void stopAnimation() {
        TextView mainTextView = (TextView) ActivityHelper.
                fetchAllChildren(mainLayout, AutoResizeTextView.class).get(0);
        mainTextView.clearAnimation();
        for (int viewId : wordLayouts) {
            findViewById(viewId).clearAnimation();
        }
    }


    /**
     * The ViewGroup Click Listener
     */
    private final View.OnClickListener ViewGroupOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            playSound(v);
        }
    };

    /**
     * The other on click listener
     */
    private final View.OnClickListener OtherOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            stopSound();
            if (viewId == homeView.getId()) {
                ActivityHelper.backHome(getBaseContext());
            } else if (viewId == backView.getId()) {
                onBackPressed();
            }
        }
    };


}
