package com.binhle.vspelling.provider;

import android.app.Activity;

import com.binhle.vspelling.common.Service.SpellingService;
import com.binhle.vspelling.model.SpellingWord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BinhLe on 7/15/2017.
 */
public class DataProvider implements DataManager {
    private static DataProvider instance;
    private SpellingService spellingService;
    private Activity activity;

    /**
     * Get self instance
     *
     * @return
     */
    public static DataProvider getInstance() {
        if (instance == null) {
            instance = new DataProvider();
        }
        return instance;
    }

    // The spelling-word map
    private Map<String, SpellingWord> spellingWordMaps = new HashMap<String, SpellingWord>();

    /**
     * Gets spelling-words
     *
     * @return
     */
    @Override
    public Map<String, SpellingWord> getSpellingWordMap() {
        return spellingWordMaps;
    }

    @Override
    public List<String> getSimilarSpelling(String wordName, int numberOfSimilarWord) {
        List<String> spellingWords = new ArrayList<>();
        if (spellingWordMaps != null && !spellingWordMaps.isEmpty()) {
            // Get index of current word
            List<String> spellingWordList = new ArrayList<>(spellingWordMaps.keySet());
            int index = spellingWordList.indexOf(wordName);
            // Get the start offset and the end offset to get similar words.
            int lastIndex = spellingWordList.size() - 1;
            int realNumberOfSimilarWord = numberOfSimilarWord -1;
            int startOffset;
            int endOffset;
            if (spellingWordList.size() <= numberOfSimilarWord) {
                startOffset = 0;
                endOffset = lastIndex;
            } else {
                startOffset = index - realNumberOfSimilarWord / 2;
                startOffset = startOffset < 0 ? 0 : startOffset;

                endOffset = startOffset + realNumberOfSimilarWord;
                endOffset = endOffset >= lastIndex ? lastIndex : endOffset;
                startOffset =
                        endOffset == lastIndex ? lastIndex - realNumberOfSimilarWord : startOffset;
            }

            for (int i = startOffset; i <= endOffset; i++) {
                spellingWords.add(spellingWordList.get(i));
            }
        }
        return spellingWords;
    }

    /**
     * Setup spelling service
     *
     * @param activity
     */
    public void setupSpellingService(Activity activity) {
        if (activity != null && activity != this.activity) {
            this.spellingService = SpellingService.getInstance(activity);
            this.activity = activity;
        }
    }

    @Override
    public Map<String, SpellingWord> fetchWordsByIndex(int pageIndex) {
        clearSpellingWords();
        this.spellingWordMaps = this.spellingService.selectSpellingWordsByIndex(pageIndex);
        return spellingWordMaps;
    }

    @Override
    public void clear() {
        clearSpellingWords();
    }

    /**
     * Clear spelling-words
     */
    private void clearSpellingWords() {
        spellingWordMaps.clear();
    }
}
