package com.binhle.vspelling.provider;

import android.app.Activity;

import com.binhle.vspelling.dao.SpellingService;
import com.binhle.vspelling.model.Letter;
import com.binhle.vspelling.model.SpellingBase;
import com.binhle.vspelling.model.SpellingWord;
import com.binhle.vspelling.model.Word;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    // The letters
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

    /**
     * get index of alpha beta
     *
     * @param name
     * @return
     */
    @Override
    public int getIndexOfAlphaBeta(String name) {
        List<SpellingBase> letterList = new ArrayList<>(letters.values());
        SpellingBase spellingBase = letters.get(name);
        return letterList.indexOf(spellingBase);
    }

    /**
     * Get alpha beta by index
     *
     * @param index
     * @return
     */
    @Override
    public SpellingBase getAlphaBetaByIndex(int index) {
        List<SpellingBase> letterList = new ArrayList<>(letters.values());
        index = index >= letterList.size() ? index - letterList.size() : index;
        index = index < 0 ? index + letterList.size() : index;
        return letterList.get(index);
    }

    /**
     * Get similar letters
     * @param letterName
     * @param numberOfLetters
     * @return
     */
    @Override
    public List<String> getSimilarLetters(String letterName, int numberOfLetters) {
        return getSimilarSpellings(letters, letterName, numberOfLetters);
    }

    /**
     * Fetch related words
     * @param letterName
     * @param pageIndex
     * @param numberOfWords
     * @return
     */
    @Override
    public List<SpellingBase> fetchRelatedWords(String letterName, int pageIndex, int
            numberOfWords) {
        List<SpellingBase> relatedWords = this.spellingService.
                selectRelatedWords(letterName, pageIndex, numberOfWords);
        return relatedWords;
    }

    /**
     * Fetch Letters By Number
     * @param pageIndex
     * @param numberOfLetters
     * @return
     */
    @Override
    public Map<String, SpellingBase> fetchLettersByNumber(int pageIndex, int numberOfLetters) {
        clearLetters();
        this.letters = this.spellingService.selectLetters(pageIndex, numberOfLetters);
        return letters;
    }

    /**
     * Fetch word by index
     * @param pageIndex
     * @return
     */
    @Override
    public Map<String, SpellingBase> fetchWordsByIndex(int pageIndex) {
        clearSpellingWords();
        this.spellingWordMaps = this.spellingService.selectSpellingWordsByIndex(pageIndex);
        return spellingWordMaps;
    }

    /**
     * Get similar spelling
     * @param wordName
     * @param numberOfSimilarWord
     * @return
     */
    @Override
    public List<String> getSimilarSpellings(String wordName, int numberOfSimilarWord) {
        return getSimilarSpellings(spellingWordMaps, wordName, numberOfSimilarWord);
    }

    /**
     * Get similar spelling
     * @param spellingWordMaps
     * @param key
     * @param numberOfSimilar
     * @return
     */
    private List<String> getSimilarSpellings(Map<String, SpellingBase> spellingWordMaps, String
            key, int numberOfSimilar) {
        List<String> spellingWords = new ArrayList<>();
        if (spellingWordMaps != null && !spellingWordMaps.isEmpty()) {
            // Get currentIndex of current word
            List<String> spellingWordList = new ArrayList<>(spellingWordMaps.keySet());
            int currentIndex = spellingWordList.indexOf(key);
            // Get the start offset and the end offset to get similar words.
            int startOffset;
            int sizeOfExpectedList;
            int sizeOfDataList = spellingWordList.size();
            List<Integer> listOfIndex = new ArrayList<>();
            if (spellingWordList.size() <= numberOfSimilar) {
                startOffset = 0;
                sizeOfExpectedList = sizeOfDataList - 1;
            } else {
                startOffset = currentIndex - numberOfSimilar / 2;
                sizeOfExpectedList = numberOfSimilar;
            }
            // Get expected data
            int index;
            for (int i = 0; i < sizeOfExpectedList; i++) {
                index = startOffset + i;
                index = index < 0 ? index + sizeOfDataList : index;
                index = index >= sizeOfDataList ? index - sizeOfDataList : index;
                spellingWords.add(spellingWordList.get(index));
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
