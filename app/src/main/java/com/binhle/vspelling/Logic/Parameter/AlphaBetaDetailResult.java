package com.binhle.vspelling.Logic.Parameter;

import com.binhle.vspelling.Model.Letter;
import com.binhle.vspelling.Model.Word;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BinhLe on 5/27/2017.
 */
public class AlphaBetaDetailResult {
    private Letter currentLetter;
    private List<Word> hintWords;

    public Letter getCurrentLetter() {
        return currentLetter;
    }

    public void setCurrentLetter(Letter currentLetter) {
        this.currentLetter = currentLetter;
    }

    public List<Word> getHintWords() {
        return hintWords;
    }

    public void setHintWords(List<Word> hintWords) {
        this.hintWords = hintWords;
    }
}
