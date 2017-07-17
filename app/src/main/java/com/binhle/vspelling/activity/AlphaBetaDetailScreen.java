package com.binhle.vspelling.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binhle.vspelling.Logic.AlphaBetaDetailLogic;
import com.binhle.vspelling.Logic.Parameter.AlphaBetaDetailCurrentData;
import com.binhle.vspelling.Logic.Parameter.AlphaBetaDetailParam;
import com.binhle.vspelling.Logic.Parameter.AlphaBetaDetailResult;
import com.binhle.vspelling.R;
import com.binhle.vspelling.common.CommonType;
import com.binhle.vspelling.common.MediaPlayerUtil;
import com.binhle.vspelling.common.ResourceUtil;
import com.binhle.vspelling.model.Letter;
import com.binhle.vspelling.model.Word;

import java.util.ArrayList;
import java.util.List;

public class AlphaBetaDetailScreen extends AppCompatActivity {

//    private static String currentLetterId;
//    private AlphaBetaDetailLogic logic;
//    private AlphaBetaDetailParam param = new AlphaBetaDetailParam();
//    private AlphaBetaDetailResult result;
//    private List<Letter> allLetters;
//    private static AlphaBetaDetailCurrentData currentData = new AlphaBetaDetailCurrentData();
//    private List<ImageView> similarLetterImageViews = new ArrayList<>();
//    private ImageView backImageView;
//    private ImageView preLetterImageView;
//    private ImageView nextLetterImageView;
//    private ImageView mainLetterImageView;
//    private List<ImageView> hintImageViews = new ArrayList<>();
//    private List<TextView> hinTextViews = new ArrayList<>();
//    private int currentLetterIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_beta_detail_screen);

//        String letterId = this.getIntent()
//                .getExtras()
//                .getString(String.valueOf(CommonType.IntentKeyType.INDEX));
//        findLayouts();
//        logic = AlphaBetaDetailLogic.getInstance(this);
//        allLetters = logic.getAllLetters();
//        changeLetter(letterId);
//        handleClickEvent();
    }

//    /**
//     * Set similar letters
//     */
//    private void setSimilarLetters() {
//        if (result != null) {
//            List<Letter> letters = getSomeLettersToDisplay();
//            currentData.getCurrentSimilarLetters().clear();
//            if (letters.size() <= similarLetterImageViews.size()) {
//                ImageView imageView;
//                Letter letter;
//                for (int index = 0; index < letters.size(); index++) {
//                    imageView = similarLetterImageViews.get(index);
//                    letter = letters.get(index);
//                    letter.setViewId(imageView.getId());
//                    letter.setImageResourceId(ResourceUtil.getImageResource(this, letter.getImagePath()));
//                    setImageViewData(letter);
//                    currentData.getCurrentSimilarLetters().put(imageView.getId(), letter);
//                }
//            }
//        }
//
//    }
//
//    /**
//     * Get 5 similar letters to display on screen
//     *
//     * @return
//     */
//    private List<Letter> getSomeLettersToDisplay() {
//        List<Letter> letters = new ArrayList<>();
//        if (allLetters != null) {
//            for (int index = 0; index < allLetters.size(); index++) {
//                if (currentLetterId.equalsIgnoreCase(allLetters.get(index).getId())) {
//                    int pointer = 0;
//                    if (index > 1 && index < allLetters.size() - 2) {
//                        pointer = index - 2;
//                    } else if (index == allLetters.size() - 2) {
//                        pointer = index - 3;
//                    } else if (index == allLetters.size() - 1) {
//                        pointer = index - 4;
//                    } else {
//                        pointer = index;
//                    }
//                    letters.add(allLetters.get(pointer));
//                    letters.add(allLetters.get(pointer + 1));
//                    letters.add(allLetters.get(pointer + 2));
//                    letters.add(allLetters.get(pointer + 3));
//                    letters.add(allLetters.get(pointer + 4));
//                    break;
//                }
//            }
//        }
//        return letters;
//    }
//
//    /**
//     * Set data for current letter
//     */
//    private void setCurrentLetterData() {
//        if (result != null && result.getCurrentLetter() != null) {
//            Letter letter = result.getCurrentLetter();
//            letter.setViewId(mainLetterImageView.getId());
//            letter.setMediaPlayer(this, ResourceUtil.getSoundResource(this, letter.getSoundPath()));
//            letter.setImageResourceId(ResourceUtil.getImageResource(this, letter.getImagePath()));
//            setImageViewData(letter);
//            currentData.setLetter(letter);
//        }
//    }
//
//    /**
//     * Set hint words of current letter
//     */
//    private void setWordHint() {
//        if (result != null) {
//            List<Word> wordList = result.getHintWords();
//            ImageView imgView;
//            TextView textView;
//            if (hintImageViews.size() <= wordList.size()) {
//                currentData.getHintMap().clear();
//                for (int i = 0; i < hintImageViews.size(); i++) {
//                    imgView = hintImageViews.get(i);
//                    textView = hinTextViews.get(i);
//                    Word word = wordList.get(i);
//
//                    word.setViewId(imgView.getId());
//                    word.setImageResourceId(ResourceUtil.getImageResource(this, word.getImagePath()));
//                    word.setMediaPlayer(this, ResourceUtil.getSoundResource(this, word.getSoundPath()));
//
//                    setImageViewData(word);
//                    textView.setText(word.getDisplayText());
//                    currentData.getHintMap().put(imgView.getId(), word);
//                }
//            } else {
//                cleanWordHint();
//            }
//        }
//    }
//
//    /**
//     * Set content for image view
//     *
//     * @param mediaBase
//     */
//    private void setImageViewData(MediaBase mediaBase) {
//        ImageView imageView = (ImageView) this.findViewById(mediaBase.getViewId());
//        if (mediaBase.getImageResourceId() > 0) {
//            imageView.setImageResource(mediaBase.getImageResourceId());
//        } else {
//            imageView.setImageResource(0);
//        }
//    }
//
//    /**
//     * Find all layout in this screen.
//     */
//    private void findLayouts() {
//        LinearLayout similarLettersLayout = (LinearLayout) findViewById(R.id.layout_similar_letters);
//        LinearLayout currentLayout = (LinearLayout) findViewById(R.id.layout_current);
//        LinearLayout hintLayout = (LinearLayout) findViewById(R.id.layout_word_hint);
//        // Get similar letters image views
//        for (int index = 1; index < similarLettersLayout.getChildCount() - 1; index++) {
//            View v = similarLettersLayout.getChildAt(index);
//            if (v instanceof ImageView) {
//                similarLetterImageViews.add((ImageView) v);
//            }
//        }
//        /**
//         * Get main letter image views
//         */
//        mainLetterImageView = (ImageView) currentLayout.findViewById(R.id.img_main_letter);
//        /**
//         * Get previous letter of current letter
//         */
//        preLetterImageView = (ImageView) currentLayout.findViewById(R.id.img_pre_letter);
//        /**
//         * Get next letter of current letter
//         */
//        nextLetterImageView = (ImageView) currentLayout.findViewById(R.id.img_next_letter);
//
//        /**
//         * Get hint word image views
//         */
//        for (int i = 0; i < hintLayout.getChildCount(); i++) {
//            View view = hintLayout.getChildAt(i);
//            if (view instanceof LinearLayout) {
//                View v;
//                ImageView imgView;
//                TextView textView;
//                for (int j = 0; j < ((LinearLayout) view).getChildCount(); j++) {
//                    v = ((LinearLayout) view).getChildAt(j);
//                    if (v instanceof ImageView) {
//                        imgView = (ImageView) v;
//                        hintImageViews.add(imgView);
//                    } else if (v instanceof TextView) {
//                        textView = (TextView) v;
//                        hinTextViews.add(textView);
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * Handle image view click events
//     */
//    private void handleClickEvent() {
//        handleSimilarClickEvent();
//        handleCurrentLetterClickEvent();
//        handleHintWordClickEvent();
//        handleNextLetterClickEvent();
//        handlePreLetterClickEvent();
//    }
//
//    /**
//     * Handle click event for main letter
//     */
//    private void handleCurrentLetterClickEvent() {
//        mainLetterImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MediaPlayerUtil.playSound(currentData.getLetter().getMediaPlayer());
//            }
//        });
//    }
//
//    /**
//     * Handle click event for similar letters
//     */
//    private void handleSimilarClickEvent() {
//        for (int index = 0; index < similarLetterImageViews.size(); index++) {
//            View view = similarLetterImageViews.get(index);
//            ((ImageView) view).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (currentData.getCurrentSimilarLetters().containsKey(v.getId())) {
//                        Letter letter = currentData.getCurrentSimilarLetters().get(v.getId());
//                        changeLetter(letter.getId());
//                    }
//                }
//            });
//        }
//    }
//
//    /**
//     * Handle click event for hint words of main letter
//     */
//    private void handleHintWordClickEvent() {
//        ImageView imgView;
//        for (int index = 0; index < hintImageViews.size(); index++) {
//            imgView = hintImageViews.get(index);
//            imgView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (currentData.getHintMap().containsKey(v.getId())) {
//                        Word word = currentData.getHintMap().get(v.getId());
//                        MediaPlayerUtil.playSound(word.getMediaPlayer());
//                    }
//                }
//            });
//        }
//    }
//
//    /**
//     * Handle previous letter
//     */
//    private void handlePreLetterClickEvent() {
//        preLetterImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleRelatedLetter(true);
//            }
//        });
//    }
//
//    private void handleNextLetterClickEvent() {
//        nextLetterImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleRelatedLetter(false);
//            }
//        });
//    }
//
//    /**
//     * Handle related letter
//     *
//     * @param isPrevious
//     */
//    private void handleRelatedLetter(boolean isPrevious) {
//        int step = 1;
//        if (isPrevious) {
//            step = -1;
//        }
//        int nextLetterIndex = currentLetterIndex + step;
//        if (nextLetterIndex >= 0 && nextLetterIndex < allLetters.size()) {
//            Letter letter = allLetters.get(nextLetterIndex);
//            changeLetter(letter.getId());
//        }
//    }
//
//    /**
//     * Handle when gamer change letter
//     *
//     * @param letterId
//     */
//    private void changeLetter(String letterId) {
//        currentLetterId = letterId;
//        param.setCurrentLetterId(currentLetterId);
//        result = logic.getDataForViews(param);
//        setSimilarLetters();
//        setCurrentLetterData();
//        setWordHint();
//        getCurrentLetterIndex();
//        enablePreNextView();
//    }
//
//    /**
//     * Get current letter index
//     */
//    private void getCurrentLetterIndex() {
//        Letter letter;
//        for (int index = 0; index < allLetters.size(); index++) {
//            letter = allLetters.get(index);
//            if (currentLetterId.equalsIgnoreCase(letter.getId())) {
//                currentLetterIndex = index;
//                break;
//            }
//        }
//    }
//
//    /**
//     * Enable or disable pre and next image view
//     */
//    private void enablePreNextView() {
//        if (currentLetterIndex == 0) {
//            preLetterImageView.setEnabled(false);
//            nextLetterImageView.setEnabled(true);
//        } else if (currentLetterIndex == allLetters.size() - 1) {
//            nextLetterImageView.setEnabled(false);
//            preLetterImageView.setEnabled(true);
//        } else {
//            nextLetterImageView.setEnabled(true);
//            preLetterImageView.setEnabled(true);
//        }
//    }
//
//    /**
//     * Clean image
//     */
//    private void cleanWordHint() {
//        ImageView imageView;
//        TextView textView;
//        for (int index = 0; index < hintImageViews.size(); index++) {
//            imageView = hintImageViews.get(index);
//            imageView.setImageResource(android.R.color.transparent);
//            textView = hinTextViews.get(index);
//            textView.setText("");
//        }
//    }
}
