package com.binhle.vspelling.provider;

import android.app.Activity;

import com.binhle.vspelling.dao.SpellingService;
import com.binhle.vspelling.model.Letter;
import com.binhle.vspelling.model.SpellingBase;
import com.binhle.vspelling.model.SpellingWord;
import com.binhle.vspelling.model.Word;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    private Map<String, SpellingBase> spellingWordMaps = new LinkedHashMap<>();
    private Map<String, SpellingBase> letters = new LinkedHashMap<>();

    /**
     * Gets spelling-words
     *
     * @return
     */
    @Override
    public Map<String, SpellingBase> getSpellingWordMap() {
        return spellingWordMaps;
    }

    @Override
    public Map<String, SpellingBase> getAlphaBetaLetters() {
        return letters;
    }

    @Override
    public List<String> getSimilarLetters(String letterName, int numberOfLetters) {
        return getSimilarSpellings(letters, letterName, numberOfLetters);
    }

    @Override
    public List<SpellingBase> fetchRelatedWords(String letterName, int pageIndex, int
            numberOfWords) {
        List<SpellingBase> relatedWords = this.spellingService.
                selectRelatedWords(letterName, pageIndex, numberOfWords);
        return relatedWords;
    }

    @Override
    public Map<String, SpellingBase> fetchLettersByNumber(int pageIndex, int numberOfLetters) {
        clearLetters();
        this.letters = this.spellingService.selectLetters(pageIndex, numberOfLetters);
        return letters;
    }

    @Override
    public Map<String, SpellingBase> fetchWordsByIndex(int pageIndex) {
        clearSpellingWords();
        this.spellingWordMaps = this.spellingService.selectSpellingWordsByIndex(pageIndex);
        return spellingWordMaps;
    }

    @Override
    public List<String> getSimilarSpellings(String wordName, int numberOfSimilarWord) {
        return getSimilarSpellings(spellingWordMaps, wordName, numberOfSimilarWord);
    }

    private List<String> getSimilarSpellings(Map<String, SpellingBase> spellingWordMaps, String
            key, int numberOfSimilar) {
        List<String> spellingWords = new ArrayList<>();
        if (spellingWordMaps != null && !spellingWordMaps.isEmpty()) {
            // Get index of current word
            List<String> spellingWordList = new ArrayList<>(spellingWordMaps.keySet());
            int index = spellingWordList.indexOf(key);
            // Get the start offset and the end offset to get similar words.
            int lastIndex = spellingWordList.size() - 1;
            int realNumberOfSimilarWord = numberOfSimilar - 1;
            int startOffset;
            int endOffset;
            if (spellingWordList.size() <= numberOfSimilar) {
                startOffset = 0;
                endOffset = lastIndex;
            } else {
                startOffset = index - realNumberOfSimilarWord / 2;
                startOffset = startOffset < 0 ? 0 : startOffset;

                endOffset = startOffset + realNumberOfSimilarWord;
                endOffset = endOffset >= lastIndex ? lastIndex : endOffset;
                startOffset = endOffset == lastIndex ? lastIndex - realNumberOfSimilarWord :
                        startOffset;
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
    public void clear() {
        clearSpellingWords();
        clearLetters();
    }

    /**
     * Clear spelling-words
     */
    private void clearSpellingWords() {
        spellingWordMaps.clear();
    }

    /**
     * Clear letters
     */
    private void clearLetters() {
        letters.clear();
    }
}
