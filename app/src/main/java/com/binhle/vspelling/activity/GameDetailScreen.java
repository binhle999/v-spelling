package com.binhle.vspelling.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.binhle.vspelling.R;
import com.binhle.vspelling.common.constant.Constants;
import com.binhle.vspelling.common.customize.AutoResizeTextView;
import com.binhle.vspelling.common.util.ActivityHelper;
import com.binhle.vspelling.common.util.ResourceUtil;
import com.binhle.vspelling.model.SpellingBase;
import com.binhle.vspelling.provider.DataProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameDetailScreen extends AppCompatActivity {

    private ImageView firstLetter;
    private ImageView secondLetter;
    private ImageView thirdLetter;
    private ImageView fourthLetter;
    private List<Integer> letterViewsIndex = new ArrayList<>();
    private ImageView result, home, next, repeat;
    private MediaPlayer truePlayer, falsePlayer, selectionPlayer;
    private AutoResizeTextView scoreView;
    private int score;
    private DataProvider dataProvider = DataProvider.getInstance();
    private List<SpellingBase> letters;
    private int resultIndex;
    private int selectionCount = 0;
    private int repeatCount = 0;
    private static final int MAX_REPEAT = 2;
    private static final int MAX_SELECTION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail_screen);
        dataProvider.setupSpellingService(this);
        letters = new ArrayList<>(dataProvider.
                fetchLettersByNumber(Constants.DEFAULT_START_PAGE_INDEX, Constants
                        .DEFAULT_NUMBER_OF_LETTERS).values());
        fetchAllViews();
        initialize();
        registerEvent();
        updateViewData();
    }

    /**
     * Fetch all view elements
     */
    private void fetchAllViews() {
        firstLetter = (ImageView) findViewById(R.id.img_first_letter);
        secondLetter = (ImageView) findViewById(R.id.img_second_letter);
        thirdLetter = (ImageView) findViewById(R.id.img_third_letter);
        fourthLetter = (ImageView) findViewById(R.id.img_fourth_letter);
        letterViewsIndex.add(firstLetter.getId());
        letterViewsIndex.add(secondLetter.getId());
        letterViewsIndex.add(thirdLetter.getId());
        letterViewsIndex.add(fourthLetter.getId());
        home = (ImageView) findViewById(R.id.img_home);
        next = (ImageView) findViewById(R.id.img_next);
        result = (ImageView) findViewById(R.id.img_result_msg);
        scoreView = (AutoResizeTextView) findViewById(R.id.score);
        repeat = (ImageView) findViewById(R.id.img_speaker);
        next.setVisibility(View.VISIBLE);
    }

    private void initialize() {
        truePlayer = MediaPlayer.create(this, R.raw.dung_roi_gioi_qua);
        falsePlayer = MediaPlayer.create(this, R.raw.oi_sai_roi);
//        selectionPlayer = MediaPlayer.create(this, R.raw.oi_sai_roi);
        truePlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                updateViewData();
                result.setImageBitmap(null);
                next.setVisibility(View.VISIBLE);
                repeat.setVisibility(View.VISIBLE);
                setClickable(true);
            }
        });
        falsePlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                result.setImageBitmap(null);
                setClickable(true);
                toGameOver();
            }
        });
    }

    /**
     * Register event for view
     */
    private void registerEvent() {
        ActivityHelper.updateViewClickEvent(firstLetter, SelectionOnClickListener);
        ActivityHelper.updateViewClickEvent(secondLetter, SelectionOnClickListener);
        ActivityHelper.updateViewClickEvent(thirdLetter, SelectionOnClickListener);
        ActivityHelper.updateViewClickEvent(fourthLetter, SelectionOnClickListener);
        ActivityHelper.updateViewClickEvent(home, OtherOnClickListener);
        ActivityHelper.updateViewClickEvent(next, OtherOnClickListener);
        ActivityHelper.updateViewClickEvent(repeat, OtherOnClickListener);
    }

    /**
     * Update data for views
     */
    private void updateViewData() {
        selectionCount = 0;
        repeatCount = 0;
        List<Integer> randomLetterIndex = generateLetterIndex(letters.size(), letterViewsIndex
                .size());
        ImageView view;
        SpellingBase letter;
        // Put data into views
        for (int i = 0; i < letterViewsIndex.size(); i++) {
            view = (ImageView) findViewById(letterViewsIndex.get(i));
            letter = letters.get(randomLetterIndex.get(i));
            ActivityHelper.updateImageResource(view, letter.getImage());
        }
        // Generate result index
        resultIndex = generateLetterIndex(4, 1).get(0);
        SpellingBase resultLetter = letters.get(randomLetterIndex.get(resultIndex));
        int soundId = ResourceUtil.getSoundResource(this, resultLetter.getSound());
        selectionPlayer = MediaPlayer.create(this, soundId);
        selectionPlayer.start();
    }

    /**
     * Generate letter index
     *
     * @param range
     * @param expectQuantity
     * @return
     */
    private List<Integer> generateLetterIndex(int range, int expectQuantity) {
        List<Integer> intList = new ArrayList<Integer>();
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i < range; i++) {
            intList.add(i);
        }
        Collections.shuffle(intList);
        for (int i = 0; i < expectQuantity; i++) {
            result.add(intList.get(i));
        }
        return result;
    }

    /**
     * Stop sound
     */
    public void stopSound() {
        if (truePlayer.isPlaying()) {
            truePlayer.pause();
        }
        if (falsePlayer.isPlaying()) {
            falsePlayer.pause();
        }
        if (selectionPlayer != null) {
            if (selectionPlayer.isPlaying()) {
                selectionPlayer.pause();
            }
        }
    }

    @Override
    public void onBackPressed() {
        stopSound();
        super.onBackPressed();
    }

    /**
     * Execute if the user's selection is correct
     */
    public void correct() {
        result.setImageResource(R.drawable.dungroi);
        score++;
        scoreView.setText(score + "");
        truePlayer.start();
    }

    /**
     * Execute if the user's selection is incorrect
     */
    public void incorrect() {
        result.setImageResource(R.drawable.sairoi);
        falsePlayer.start();
    }

    /**
     * The selection click listener
     */
    private final View.OnClickListener SelectionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageView imageView = (ImageView) v;
            stopSound();
            setClickable(false);
            if (letterViewsIndex.indexOf(imageView.getId()) == resultIndex) {
                correct();
            } else {
                selectionCount++;
                incorrect();
            }
        }
    };
    /**
     * The other click listener
     */
    private final View.OnClickListener OtherOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            if (viewId == next.getId()) {
                updateViewData();
            } else if (viewId == home.getId()) {
                ActivityHelper.backHome(getBaseContext());
            } else if (viewId == repeat.getId()) {
                stopSound();
                repeatCount++;
                if (repeatCount >= MAX_REPEAT) {
                    repeat.setVisibility(View.INVISIBLE);
                    next.setVisibility(View.INVISIBLE);
                }
                selectionPlayer.start();
            }
        }
    };

    /**
     * Go to Game over
     */
    private void toGameOver() {
        if (selectionCount >= MAX_SELECTION) {
            Intent intent = new Intent(this, GameOverScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
        }
    }

    private void setClickable(boolean state) {
        for (int index = 0; index < letterViewsIndex.size();index++) {
            ImageView imageView = (ImageView) findViewById(letterViewsIndex.get(index));
            imageView.setClickable(state);
        }
    }

}
