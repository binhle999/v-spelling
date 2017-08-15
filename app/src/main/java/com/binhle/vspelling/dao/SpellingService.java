package com.binhle.vspelling.dao;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.binhle.vspelling.common.constant.Constants;
import com.binhle.vspelling.model.Letter;
import com.binhle.vspelling.model.Spelling;
import com.binhle.vspelling.model.SpellingBase;
import com.binhle.vspelling.model.Word;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BinhLe on 5/1/2017.
 */
public class SpellingService {
    private static SpellingService instance;
    private static final String DB_NAME = "vn-spelling.sqlite";
    private static SQLiteDatabase database;
    private static Activity activity;

    /**
     * Get it's instance
     *
     * @param activity
     * @return
     */
    public static SpellingService getInstance(Activity activity) {
        if (instance == null) {
            instance = new SpellingService();
        }
        setDatabase(activity);
        return instance;
    }

    /**
     * Constructor
     */
    private SpellingService() {
    }

    /**
     * Set database for service instance.
     *
     * @param act
     */
    private static void setDatabase(Activity act) {
        if (act != null && activity != act) {
            activity = act;
            database = Database.initDatabase(activity, DB_NAME);
        }
    }

    /**
     * Select spelling-words by page index
     *
     * @param pageIndex
     * @return
     */
    public Map<String, SpellingBase> selectSpellingByIndex(int pageIndex) {
        Map<String, SpellingBase> spellings = new LinkedHashMap<>();
        // Calculate offset to query data
        int offset = pageIndex - 1;
        // Build query statement
        StringBuilder mainQuery = new StringBuilder();
//        mainQuery.append("SELECT id,name,content,image,sound FROM 'spelling-word'");
//        mainQuery.append(" ORDER BY id");
        mainQuery.append(" SELECT id,name,text,sound               ");
        mainQuery.append(" FROM tblSpelling                        ");
        mainQuery.append(" WHERE tblSpelling.spOrder IS NOT NULL   ");
        mainQuery.append(" ORDER BY tblSpelling.spOrder            ");
        mainQuery.append(" LIMIT ").append(Constants.NUMBER_OF_WORD_PER_PAGE);
        mainQuery.append(" OFFSET ").append(offset * Constants.NUMBER_OF_WORD_PER_PAGE);
        // Fill data
        String name, content, image = null, sound;
        int id;
        Spelling spelling;
        Cursor cursor = database.rawQuery(mainQuery.toString(), null);
        // Loop to fill data into map
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            id = cursor.getInt(0);
            name = cursor.getString(1);
            content = cursor.getString(2);
            sound = cursor.getString(3);
            spelling = new Spelling(id, name, content, image, sound);
            spellings.put(name, spelling);
        }
        return spellings;
    }

    public List<SpellingBase> selectWordsBySpelling(String spellingName, int pageIndex, int
            numberOfWords) {
        List<SpellingBase> words = new ArrayList<>();
        // Calculate offset to query data
        int offset = pageIndex - 1;
        // Build query statement
        StringBuilder mainQuery = new StringBuilder();

        mainQuery.append(" SELECT tblWord.id,tblWord.name, tblWord.text");
        mainQuery.append("  ,tblWord.image, tblWord.sound ");
        mainQuery.append(" FROM tblSpelling, tblSpellingWord, tblWord                      ");
        mainQuery.append(" WHERE tblSpelling.name = tblSpellingWord.spelling               ");
        mainQuery.append(" AND tblSpellingWord.word = tblWord.name                         ");
        mainQuery.append(" AND tblSpelling.name = '").append(spellingName).append("' ");
        mainQuery.append(" LIMIT ").append(numberOfWords);
        mainQuery.append(" OFFSET ").append(offset * numberOfWords);

        // Fill data
        try {
            String name, content, image, sound;
            int id;
            Word word;
            Cursor cursor = database.rawQuery(mainQuery.toString(), null);
            // Loop to fill data into map
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                id = cursor.getInt(0);
                name = cursor.getString(1);
                content = cursor.getString(2);
                image = cursor.getString(3);
                sound = cursor.getString(4);
                word = new Word(id, name, content, image, sound);
                words.add(word);
            }
        } catch (Exception ex) {
            Log.e("selectWordsBySpelling", ex.getMessage());
        }
        return words;
    }

    /**
     * Select letters
     *
     * @param pageIndex
     * @param numberOfLetters
     * @return
     */
    public Map<String, SpellingBase> selectLetters(int pageIndex, int numberOfLetters) {
        Map<String, SpellingBase> letters = new LinkedHashMap<>();
        // Calculate offset to query data
        int offset = pageIndex - 1;
        // Build query statement
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" SELECT id,name,text,image,sound ");
        mainQuery.append(" FROM tblLetter               ");
        mainQuery.append(" LIMIT ").append(numberOfLetters);
        mainQuery.append(" OFFSET ").append(offset);

        // Fill data
        String name, text, image, sound;
        int id;
        Letter letter;
        Cursor cursor = database.rawQuery(mainQuery.toString(), null);
        // Loop to fill data into map
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            id = cursor.getInt(0);
            name = cursor.getString(1);
            text = cursor.getString(2);
            image = cursor.getString(3);
            sound = cursor.getString(4);
            letter = new Letter(id, name, text, image, sound);
            letters.put(name, letter);
        }
        return letters;
    }

    /**
     * Select related words
     *
     * @param letterName
     * @param pageIndex
     * @param numberOfWords
     * @return
     */
    public List<SpellingBase> selectRelatedWords(String letterName, int pageIndex, int numberOfWords) {
        List<SpellingBase> words = new ArrayList<>();
        // Calculate offset to query data
        int offset = pageIndex - 1;
        // Build query statement
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append(" SELECT tblWord.id, tblWord.name, tblWord.text");
        mainQuery.append("  ,tblWord.image, tblWord.sound ");
        mainQuery.append(" FROM tblLetter, tblLetterWord, tblWord                          ");
        mainQuery.append(" WHERE tblLetter.name = tblLetterWord.letter                     ");
        mainQuery.append(" AND tblLetterWord.word = tblWord.name                           ");
        mainQuery.append(" AND tblLetter.name ='").append(letterName).append("'");
        mainQuery.append(" LIMIT ").append(numberOfWords);
        mainQuery.append(" OFFSET ").append(offset);

        // Fill data
        String name, content, image, sound;
        int id;
        Word word;
        Cursor cursor = database.rawQuery(mainQuery.toString(), null);
        // Loop to fill data into map
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            id = cursor.getInt(0);
            name = cursor.getString(1);
            content = cursor.getString(2);
            image = cursor.getString(3);
            sound = cursor.getString(4);
            word = new Word(id, name, content, image, sound);
            words.add(word);
        }
        return words;
    }
}
