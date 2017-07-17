package com.binhle.vspelling.provider;

import com.binhle.vspelling.model.Letter;
import com.binhle.vspelling.model.SpellingBase;
import com.binhle.vspelling.model.SpellingWord;
import com.binhle.vspelling.model.Word;

import java.util.List;
import java.util.Map;

/**
 * The DataManager type
 * Created by BinhLe on 7/15/2017.
 */
public interface DataManager {
    Map<String, SpellingBase> fetchWordsByIndex(int pageIndex);

    Map<String, SpellingBase> getSpellingWordMap();

    List<String> getSimilarSpellings(String wordName, int numberOfSimilarWord);

    Map<String, SpellingBase> fetchLettersByNumber(int pageIndex, int numberOfLetters);

    Map<String, SpellingBase> getAlphaBetaLetters();

    List<String> getSimilarLetters(String letterName, int numberOfLetters);

    List<SpellingBase> fetchRelatedWords(String letterName, int pageIndex, int numberOfWords);

    void clear();
}
