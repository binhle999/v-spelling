package com.binhle.vspelling.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binhle.vspelling.R;
import com.binhle.vspelling.common.customize.AutoResizeTextView;
import com.binhle.vspelling.common.util.ActivityHelper;
import com.binhle.vspelling.common.util.ResourceUtil;
import com.binhle.vspelling.model.Letter;
import com.binhle.vspelling.model.SpellingBase;
import com.binhle.vspelling.model.Word;
import com.binhle.vspelling.provider.DataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class AlphaBetaDetailScreen extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private boolean wasStarted;
    private String mainLetter;
    private ImageView homeView;
    private ImageView alphaBetaView;
    private ImageView previousView;
    private ImageView nextView;
    private ImageView mainView;
    private List<View> similarLetterViews;
    private List<View> relatedWordViews = new ArrayList<>();
    private Map<Integer, SpellingBase> dataViews = new HashMap<>();
    private DataProvider dataProvider = DataProvider.getInstance();
    private List<MediaPlayer> players = new LinkedList();
    private MediaPlayer mediaPlayer1, mediaPlayer2, mediaPlayer3, mediaPlayer4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_beta_detail_screen);
        String letterName = getIntent().getStringExtra("letterName");
        execute(letterName);
        wasStarted = true;
    }

    /**
     * Execute
     *
     * @param letterName
     */
    private void execute(String letterName) {
        mainLetter = letterName;
        fetchViews();
        fillDataViews();
        playSoundFirstTime();
    }

    /**
     * Fetch all views
     */
    private void fetchViews() {
        if (!wasStarted) {
            View similarView = findViewById(R.id.layout_similar_letters);
            previousView = (ImageView) findViewById(R.id.img_pre_letter);
            nextView = (ImageView) findViewById(R.id.img_next_letter);
            homeView = (ImageView) findViewById(R.id.img_home);
            alphaBetaView = (ImageView) findViewById(R.id.img_back);

            ActivityHelper.updateViewClickEvent(previousView, OtherLetterOnClickListener);
            ActivityHelper.updateViewClickEvent(nextView, OtherLetterOnClickListener);
            ActivityHelper.updateViewClickEvent(homeView, OtherLetterOnClickListener);
            ActivityHelper.updateViewClickEvent(alphaBetaView, OtherLetterOnClickListener);

            similarLetterViews = ActivityHelper.
                    fetchAllChildren(similarView, AppCompatImageView.class);
            similarLetterViews.remove(0);
            similarLetterViews.remove(similarLetterViews.size() - 1);

            mainView = (ImageView) findViewById(R.id.img_main_letter);

            ViewGroup relatedWordView = (ViewGroup) findViewById(R.id.layout_related_words);
            for (int index = 0; index < relatedWordView.getChildCount(); index++) {
                View view = relatedWordView.getChildAt(index);
                if (view instanceof LinearLayout) {
                    relatedWordViews.add(view);
                }
            }
        }
    }

    /**
     * Fill data views
     */
    private void fillDataViews() {
        players.clear();
        fillDataSimilarLetters();
        fillDataMainLetter();
        fillDataRelatedWords();
    }

    /**
     * Fill data for main letter
     */
    private void fillDataMainLetter() {
        DataProvider dataProvider = DataProvider.getInstance();
        Letter letter = (Letter) dataProvider.getAlphaBetaLetters().get(mainLetter);
        ActivityHelper.updateImageResource(mainView, letter.getImage());
        ActivityHelper.updateViewClickEvent(mainView, ImageOnClickListener);
        updateDataViews(mainView, letter);
        players.add(getMediaPlayer(letter));
    }

    /**
     * Fill data for similar letters
     */
    private void fillDataSimilarLetters() {
        DataProvider dataProvider = DataProvider.getInstance();
        List<String> similarLetterNames = dataProvider.
                getSimilarLetters(mainLetter, similarLetterViews.size());
        Map<String, SpellingBase> letters = dataProvider.getAlphaBetaLetters();
        int numberOfViewToShow = Math.min(similarLetterViews.size(), similarLetterNames.size());
        ImageView imageView;
        Letter similarLetter;
        String letterName;
        for (int index = 0; index < numberOfViewToShow; index++) {
            imageView = (ImageView) similarLetterViews.get(index);
            letterName = similarLetterNames.get(index);
            similarLetter = (Letter) letters.get(letterName);
            ActivityHelper.updateImageResource(imageView, similarLetter.getImage());
            ActivityHelper.updateViewClickEvent(imageView, ImageOnClickListener);
            updateDataViews(imageView, similarLetter);
        }
    }

    /**
     * Fill data for related words
     */
    private void fillDataRelatedWords() {

        List<SpellingBase> relatedWords = dataProvider.
                fetchRelatedWords(mainLetter, 1, relatedWordViews.size());
        int numberOfViewToShow = Math.min(relatedWords.size(), relatedWordViews.size());
        View view;
        ImageView imageView;
        TextView textView;
        Word word;
        for (int index = 0; index < numberOfViewToShow; index++) {
            view = relatedWordViews.get(index);
            imageView = (ImageView) ActivityHelper.
                    fetchAllChildren(view, AppCompatImageView.class).get(0);
            textView = (TextView) ActivityHelper.
                    fetchAllChildren(view, AutoResizeTextView.class).get(0);
            word = (Word) relatedWords.get(index);
            ActivityHelper.updateImageResource(imageView, word.getImage());
            ActivityHelper.updateText(textView, word.getContent());
            ActivityHelper.updateViewClickEvent(view, ViewGroupOnClickListener);
            updateDataViews(view, word);
            players.add(getMediaPlayer(word));
        }
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
     * Image OnClick Listener
     */
    private final View.OnClickListener ImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof ImageView) {
                ImageView imageView = (ImageView) v;
                if (imageView.getId() == mainView.getId()) {
                    playSound(v);
                } else {
                    String letterName = dataViews.get(v.getId()).getName();
                    execute(letterName);
                }
            }
        }
    };

    /**
     * The ViewGroup Click Listener
     */
    private final View.OnClickListener ViewGroupOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            playSound(v);
        }
    };

    private final View.OnClickListener OtherLetterOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            if (viewId == homeView.getId()) {
                stopSound();
                ActivityHelper.backHome(AlphaBetaDetailScreen.this);
            } else if (viewId == alphaBetaView.getId()) {
                onBackPressed();
            } else if (viewId == previousView.getId()) {
                executeOtherLetter(-1);
            } else if (viewId == nextView.getId()) {
                executeOtherLetter(1);
            }

        }
    };

    /**
     * Play sound
     *
     * @param v
     */
    private void playSound(View v) {
        int viewId = v.getId();
        SpellingBase spellingBase = dataViews.get(viewId);
        int soundId = ResourceUtil.getSoundResource(this, spellingBase.
                getSound().replace("sound_", ""));
        if (soundId > 0) {
            mediaPlayer = MediaPlayer.create(this, soundId);
            mediaPlayer.start();
        }
    }

    /**
     * Execute other letter
     *
     * @param offset
     */
    private void executeOtherLetter(int offset) {
        int currentIndex = dataProvider.getIndexOfAlphaBeta(mainLetter);
        SpellingBase spellingBase = dataProvider.getAlphaBetaByIndex(currentIndex + offset);
        execute(spellingBase.getName());
    }

    /**
     * Play all sound on the first time
     */
    private void playSoundFirstTime() {
        mediaPlayer1 = players.get(0);
        mediaPlayer2 = players.get(1);
        mediaPlayer3 = players.get(2);
        mediaPlayer4 = players.get(3);
        mediaPlayer1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer2.start();
            }
        });
        mediaPlayer2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer3.start();
            }
        });
        mediaPlayer3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer4.start();
            }
        });
        stopSound();
        mediaPlayer1.start();
    }

    public void stopSound() {
//        loop = false;
        if (mediaPlayer1.isPlaying()){
            mediaPlayer1.pause();
        }
        if (mediaPlayer2.isPlaying()){
            mediaPlayer2.pause();
        }
        if (mediaPlayer3.isPlaying()){
            mediaPlayer3.pause();
        }
        if (mediaPlayer4.isPlaying()){
            mediaPlayer4.pause();
        }
    }

    /**
     * Get media player
     *
     * @param spellingBase
     */
    private MediaPlayer getMediaPlayer(SpellingBase spellingBase) {
        MediaPlayer mediaPlayer = null;
        int soundId = ResourceUtil.getSoundResource(this, spellingBase.
                getSound().replace("sound_", ""));
        if (soundId > 0) {
            mediaPlayer = MediaPlayer.create(this, soundId);
            mediaPlayer.start();
        }
        return mediaPlayer;
    }

    @Override
    public void onBackPressed() {
        stopSound();
        super.onBackPressed();
    }
}
