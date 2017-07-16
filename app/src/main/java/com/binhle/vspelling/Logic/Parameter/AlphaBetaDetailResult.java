package com.binhle.vspelling.Logic.Parameter;

import com.binhle.vspelling.model.Letter;
import com.binhle.vspelling.model.Word;

import java.util.List;

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
