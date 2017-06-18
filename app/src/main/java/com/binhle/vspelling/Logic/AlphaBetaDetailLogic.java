package com.binhle.vspelling.Logic;

import android.app.Activity;
import com.binhle.vspelling.Common.Service.SpellingService;
import com.binhle.vspelling.Logic.Parameter.AlphaBetaDetailParam;
import com.binhle.vspelling.Logic.Parameter.AlphaBetaDetailResult;
import com.binhle.vspelling.Model.Letter;
import com.binhle.vspelling.Model.Word;
import java.util.List;
import java.util.Map;

/**
 * Created by BinhLe on 5/1/2017.
 */
public class AlphaBetaDetailLogic {
    private static AlphaBetaDetailLogic instance;
    private static SpellingService spService;
    private static List<Letter> allLetters;

    private AlphaBetaDetailLogic(Activity activity){
        if (spService == null) {
            spService = SpellingService.getInstance(activity);
        }
    }
    public static synchronized AlphaBetaDetailLogic getInstance(Activity activity) {
        if (instance == null) {
            instance = new AlphaBetaDetailLogic(activity);
        }
        if (spService != null) {
            allLetters = spService.getAllLetters();
        }
        return instance;
    }
    /**
     * Get letter data
     * @param param
     * @return
     */
    public AlphaBetaDetailResult getDataForViews(AlphaBetaDetailParam param) {
        AlphaBetaDetailResult result = new AlphaBetaDetailResult();
        Letter letter = spService.getLetterById(param.getCurrentLetterId());
        List<Word> wordList = spService.getWordsByLetterId(param.getCurrentLetterId());
        result.setCurrentLetter(letter);
        result.setHintWords(wordList);
        return result;
    }

    /**
     * Get all letters
     * @return
     */
    public List<Letter> getAllLetters() {
        return allLetters;
    }
}
