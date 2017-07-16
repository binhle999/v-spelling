package com.binhle.vspelling.Logic.Parameter;

import com.binhle.vspelling.model.Letter;
import com.binhle.vspelling.model.Word;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BinhLe on 5/27/2017.
 */
public class AlphaBetaDetailCurrentData {
    private Letter letter;
    private Map<Integer, Word> hintMap = new HashMap<>();
    private Map<Integer, Letter> currentSimilarLetters = new HashMap<>();

    public AlphaBetaDetailCurrentData() {
        super();
    }

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    public Map<Integer, Word> getHintMap() {
        return hintMap;
    }

    public Map<Integer, Letter> getCurrentSimilarLetters() {
        return currentSimilarLetters;
    }
}
