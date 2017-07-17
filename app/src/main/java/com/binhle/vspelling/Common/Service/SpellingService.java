package com.binhle.vspelling.common.Service;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.binhle.vspelling.common.Constants;
import com.binhle.vspelling.model.Letter;
import com.binhle.vspelling.model.SpellingWord;
import com.binhle.vspelling.model.Word;
import com.binhle.vspelling.dao.Database;

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
    private SpellingService() {}

    /**
     * Set database for service instance.
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
     * @param pageIndex
     * @return
     */
    public Map<String, SpellingWord> selectSpellingWordsByIndex(int pageIndex) {
        Map<String, SpellingWord> spellingWords = new LinkedHashMap<>();
        // Calculate offset to query data
        int offset = pageIndex - 1;
        // Build query statement
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("SELECT id,name,content,image,sound FROM 'spelling-word'");
        mainQuery.append(" ORDER BY id");
        mainQuery.append(" LIMIT ").append(pageIndex * Constants.NUMBER_OF_WORD_PER_PAGE);
        mainQuery.append(" OFFSET ").append(offset);
        // Fill data
        String name, content, image, sound;
        int id;
        SpellingWord spellingWord;
        Cursor cursor = database.rawQuery(mainQuery.toString(), null);
        // Loop to fill data into map
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            id = cursor.getInt(0);
            name= cursor.getString(1);
            content = cursor.getString(2);
            image = cursor.getString(3);
            sound = cursor.getString(4);
            spellingWord = new SpellingWord(id, name, content, image, sound);
            spellingWords.put(name, spellingWord);
        }
        return spellingWords;
    }

    /**
     * Select letters
     * @param pageIndex
     * @param numberOfLetters
     * @return
     */
    public Map<String, Letter> selectLetters(int pageIndex, int numberOfLetters) {
        Map<String, Letter> letters = new LinkedHashMap<>();
        // Calculate offset to query data
        int offset = pageIndex - 1;
        // Build query statement
        StringBuilder mainQuery = new StringBuilder();
        mainQuery.append("SELECT id,name,content,image,sound FROM letter");
        mainQuery.append(" ORDER BY alphabeta");
        mainQuery.append(" LIMIT ").append(numberOfLetters);
        mainQuery.append(" OFFSET ").append(offset);

        // Fill data
        String name, content, image, sound;
        int id;
        Letter letter;
        Cursor cursor = database.rawQuery(mainQuery.toString(), null);
        // Loop to fill data into map
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            id = cursor.getInt(0);
            name= cursor.getString(1);
            content = cursor.getString(2);
            image = cursor.getString(3);
            sound = cursor.getString(4);
            letter = new Letter(id, name, content, image, sound);
            letters.put(name, letter);
        }
        return letters;
    }

}
