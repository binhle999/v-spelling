package com.binhle.vspelling.activity;

import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.binhle.vspelling.R;
import com.binhle.vspelling.common.util.ActivityHelper;
import com.binhle.vspelling.model.Letter;
import com.binhle.vspelling.model.SpellingBase;
import com.binhle.vspelling.model.Word;
import com.binhle.vspelling.provider.DataProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlphaBetaDetailScreen extends AppCompatActivity {

    private boolean wasStarted;
    private String mainLetter;
    private ImageView homeView;
    private ImageView alphaBetaView;
    private ImageView previousView;
    private ImageView nextView;
    private ImageView mainView;
    private List<View> similarLetterViews;
    private List<View> relatedWordViews;
    private Map<Integer, SpellingBase> dataViews = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_beta_detail_screen);
        String letterName = getIntent().getStringExtra("letterName");
        execute(letterName);
    }

    /**
     * Execute
     *
     * @param letterName
     */
    private void execute(String letterName) {
        fetchViews();
        fillDataViews();
    }

    /**
     * Fetch all views
     */
    private void fetchViews() {
        if (!wasStarted) {

        }
    }

    /**
     * Fill data views
     */
    private void fillDataViews() {
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
        DataProvider dataProvider = DataProvider.getInstance();
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
            textView = (TextView) ActivityHelper.fetchAllChildren(view, AppCompatTextView.class);
            word = (Word) relatedWords.get(index);
            ActivityHelper.updateImageResource(imageView, word.getImage());
            ActivityHelper.updateText(textView, word.getContent());
            ActivityHelper.updateViewClickEvent(view, ViewGroupOnClickListener);
            updateDataViews(view, word);
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

        }
    };

    /**
     * The ViewGroup Click Listener
     */
    private final View.OnClickListener ViewGroupOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
