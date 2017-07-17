package com.binhle.vspelling.provider;

import com.binhle.vspelling.model.Letter;
import com.binhle.vspelling.model.SpellingWord;

import java.util.List;
import java.util.Map;

/**
 * The DataManager type
 * Created by BinhLe on 7/15/2017.
 */
public interface DataManager {
    Map<String, SpellingWord> fetchWordsByIndex(int pageIndex);
    Map<String, SpellingWord> getSpellingWordMap();
    List<String> getSimilarSpellings(String wordName, int numberOfSimilarWord);
    Map<String, Letter> fetchLettersByNumber(int pageIndex, int numberOfLetters);
    Map<String, Letter> getAlphaBetaLetters();
    void clear();
}
