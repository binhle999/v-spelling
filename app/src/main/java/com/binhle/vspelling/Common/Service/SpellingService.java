package com.binhle.vspelling.Common.Service;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.binhle.vspelling.Model.Letter;
import com.binhle.vspelling.Model.Word;
import com.binhle.vspelling.dao.Database;

import java.util.ArrayList;
import java.util.HashMap;
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
     * Get information of specify letter by it's id.
     * @param letterId
     * @return
     */
    public Letter getLetterById(String letterId) {
        Letter letter = null;
        Cursor cursor = database.rawQuery("SELECT * FROM letter WHERE id='" + letterId.trim() + "'", null);
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            letter = new Letter(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        }
        return letter;
    }

    /**
     *  Get all letters.
     * @return
     */
    public List<Letter> getAllLetters() {
        List<Letter> resultMap = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT id, image_path FROM letter ORDER BY num", null);
        String id;
        String imagePath;
        Letter letter;
        // Get all information related to specify letter.
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            id = cursor.getString(0);
            imagePath = cursor.getString(1);
            letter = new Letter(id, imagePath);
            resultMap.add(letter);
        }
        return resultMap;
    }

    /**
     * Get all words by letter id.
     * @param letterId
     * @return
     */
    public List<Word> getWordsByLetterId(String letterId) {
        List<Word> resultMap = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM word WHERE letter_id='" + letterId + "'", null);
        String id;
        String name;
        String displayText;
        String description;
        String imagePath;
        String soundPath;
        Word word;
        // Get all information related to specify letter.
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            id = cursor.getString(0);
            name = cursor.getString(1);
            displayText = cursor.getString(2);
            description = cursor.getString(3);
            imagePath = cursor.getString(4);
            soundPath = cursor.getString(5);
            letterId = cursor.getString(6);
            word = new Word(id, name, displayText, description, imagePath, soundPath, letterId);
            //Add gotten word into result list.
            resultMap.add(word);
        }
        return resultMap;
    }
}
