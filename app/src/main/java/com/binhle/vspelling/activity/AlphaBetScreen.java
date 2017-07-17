package com.binhle.vspelling.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.binhle.vspelling.R;
import com.binhle.vspelling.common.util.ActivityHelper;
import com.binhle.vspelling.common.constant.Constants;
import com.binhle.vspelling.model.Letter;
import com.binhle.vspelling.model.SpellingBase;
import com.binhle.vspelling.provider.DataProvider;

import java.util.List;
import java.util.Map;

public class AlphaBetScreen extends AppCompatActivity {

    private ImageView homeView;
    private List<View> viewList;
    private Map<String, SpellingBase> letterMap;
    private Map<Integer, String> letterViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_bet_screen);
        Log.i("---------onCreate", "Done");
        fetchLetterViews();
        fillDataViews();
    }

    /**
     * Fetch all letter views
     */
    private void fetchLetterViews() {
        homeView = (ImageView)findViewById(R.id.alphaBeta_home);
        View alphaBetaView = findViewById(R.id.alphaBeta_view);
        viewList = ActivityHelper.fetchAllChildren(alphaBetaView, AppCompatImageView.class);
        // Remove first object, it is home image.
        viewList.remove(0);
    }

    /**
     *  Fill data views
     */
    private void fillDataViews() {
        DataProvider dataProvider = DataProvider.getInstance();
        dataProvider.setupSpellingService(this);
        letterMap = dataProvider.fetchLettersByNumber(1, Constants.DEFAULT_NUMBER_OF_LETTERS);
        Object[] letters = letterMap.values().toArray();
        int numberOfViewToShow = Math.min(letters.length, letterViews.size());
        Letter letter;
        ImageView imageView;
        for (int index = 0; index < numberOfViewToShow; index++) {
            letter = (Letter)letters[index];
            imageView = (ImageView)viewList.get(index);
            letterViews.put(imageView.getId(), letter.getName());
            ActivityHelper.updateImageResource(imageView, letter.getImage());
            ActivityHelper.updateViewClickEvent(imageView, ImageOnClickListener);
        }
    }

    /**
     * Image OnClick Listener
     */
    private final View.OnClickListener ImageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == homeView) {

            } else {
                goToDetailScreen(v);
            }
        }
    };

    /**
     * Go to detail screen
     * @param view
     */
    private void goToDetailScreen(View view) {
        String letterName = letterViews.get(view.getId());
        Log.i("Letter Name", letterName);
        Intent intent = new Intent(view.getContext(), AlphaBetaDetailScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("letterName", letterName);
        startActivity(intent);
    }


}
