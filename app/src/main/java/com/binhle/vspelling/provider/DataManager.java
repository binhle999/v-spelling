package com.binhle.vspelling.provider;

import com.binhle.vspelling.model.SpellingBase;

import java.util.List;
import java.util.Map;

/**
 * The DataManager type
 * Created by BinhLe on 7/15/2017.
 */
public interface DataManager {
    Map<String, SpellingBase> fetchSpellingByIndex(int pageIndex);

    Map<String, SpellingBase> getSpellingWordMap();

    List<String> getSimilarSpellings(String wordName, int numberOfSimilarWord);

    Map<String, SpellingBase> fetchLettersByNumber(int pageIndex, int numberOfLetters);

    Map<String, SpellingBase> getAlphaBetaLetters();

    int getIndexOfAlphaBeta(String name);

    SpellingBase getAlphaBetaByIndex(int index);

    List<String> getSimilarLetters(String letterName, int numberOfLetters);

    List<SpellingBase> fetchRelatedWords(String letterName, int pageIndex, int numberOfWords);

    List<SpellingBase> fetchWordsBySpelling(String spellingName, int pageIndex, int numberOfWords);

    void clear();
}
