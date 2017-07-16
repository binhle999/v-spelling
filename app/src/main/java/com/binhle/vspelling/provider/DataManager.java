package com.binhle.vspelling.provider;

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
    List<String> getSimilarSpelling(String wordName, int numberOfSimilarWord);
    void clear();
}
