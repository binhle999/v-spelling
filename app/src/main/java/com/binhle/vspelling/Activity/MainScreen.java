package com.binhle.vspelling.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.binhle.vspelling.Common.Constants;
import com.binhle.vspelling.Common.MediaPlayerConstants;
import com.binhle.vspelling.Model.Word;
import com.binhle.vspelling.R;
import com.binhle.vspelling.dao.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainScreen extends AppCompatActivity {

    private ImageView imgAbout;
    private ImageView imgAlphaBetMenu;
    private ImageView imgSpellingMenu;
    private ImageView imgGameMenu;
    private ImageView imgSoundMenu;
    private MediaPlayer mediaPlayer;
    List<Word> wordList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_screen);
        getViews();
        initMediaPlayer();
        handleImgSoundEvent();
        handleImgAboutMenu();
        handleImgAlphaBetMenu();
        handleImgSpellingMenu();
        handleImgGameMenu();
    }
    // Get views
    private void getViews() {
        imgAbout = (ImageView) findViewById(R.id.img_about);
        imgAlphaBetMenu = (ImageView) findViewById(R.id.img_alphabet_menu);
        imgGameMenu = (ImageView) findViewById(R.id.img_game_menu);
        imgSpellingMenu = (ImageView) findViewById(R.id.img_spell_menu);
        imgSoundMenu = (ImageView) findViewById(R.id.img_sound);
    }
    // initialize media player object.
    private void initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(MainScreen.this, R.raw.bkgrsong);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(MediaPlayerConstants.MAX_LEFT_VOLUME,
                MediaPlayerConstants.MAX_RIGHT_VOLUME);
        mediaPlayer.start();
    }
    // handle click event of sound image
    private void handleImgSoundEvent() {
        imgSoundMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    imgSoundMenu.setImageResource(R.drawable.song_off);
                    mediaPlayer.pause();
                } else {
                    imgSoundMenu.setImageResource(R.drawable.song_on);
                    mediaPlayer.start();
                }
            }
        });
    }
    // handle click event of alphabet menu image
    private void handleImgAlphaBetMenu() {
        imgAlphaBetMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2AlphaBetMenu = new Intent(MainScreen.this, AlphaBetScreen.class);
                intent2AlphaBetMenu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2AlphaBetMenu);
                mediaPlayer.stop();
            }
        });

    }
    // handle click event of spelling menu image
    private void handleImgSpellingMenu() {
        imgSpellingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2SpellingMenu = new Intent(MainScreen.this, SpellingScreen.class);
                intent2SpellingMenu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2SpellingMenu);
                mediaPlayer.stop();
            }
        });

    }
    // handle click event of game menu image
    private void handleImgGameMenu() {
        imgGameMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2GameMenu = new Intent(MainScreen.this, GameDetailScreen.class);
                intent2GameMenu.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2GameMenu);
                mediaPlayer.stop();
            }
        });

    }
    // handle click event of about image
    private void handleImgAboutMenu() {
        imgAbout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                final Dialog aboutDialog = new Dialog(MainScreen.this);
                aboutDialog.setTitle(Constants.TITLE_DIALOG_ABOUT);
                aboutDialog.setContentView(R.layout.dialog_about);
                aboutDialog.show();
                Button backButton = (Button) aboutDialog.findViewById(R.id.btn_back);
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        aboutDialog.cancel();
                        mediaPlayer.start();
                    }
                });
            }
        });

    }
}