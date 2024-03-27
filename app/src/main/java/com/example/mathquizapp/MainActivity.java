package com.example.mathquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Button btnLevel0, btnLevel1, btnLevel2;
    MediaPlayer introSound;
    ImageView ivSoundIcon;
    Boolean soundIsPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLevel0 = findViewById(R.id.lev0);
        btnLevel1 = findViewById(R.id.lev1);
        btnLevel2 = findViewById(R.id.lev2);
        ivSoundIcon = findViewById(R.id.ivSoundIcon);
        soundIsPlaying = true;  // for checking sound is on or off to make switch

        ivSoundIcon.setImageResource(R.drawable.icon_soundon);
        introSound = MediaPlayer.create(this,R.raw.game_intro_sound);
        introSound.setLooping(true);
        introSound.start();

        btnLevel0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lev0Intent = new Intent(MainActivity.this, Level0Activity.class);
                startActivity(lev0Intent);
                btnLevel0.setBackgroundColor(Color.BLUE);
                btnLevel0.setTextColor(Color.WHITE);
                introSound.stop();
            }
        });

        btnLevel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lev1Intent = new Intent(MainActivity.this, Level1Activity.class);
                startActivity(lev1Intent);
                btnLevel1.setBackgroundColor(Color.BLUE);
                btnLevel1.setTextColor(Color.WHITE);
                introSound.stop();
            }
        });

        btnLevel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lev2Intent = new Intent(MainActivity.this, Level2Activity.class);
                startActivity(lev2Intent);
                btnLevel2.setBackgroundColor(Color.BLUE);
                btnLevel2.setTextColor(Color.WHITE);
                introSound.stop();
            }
        });

        ivSoundIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soundIsPlaying){         // if sound is playing on click -> turn off
                    ivSoundIcon.setImageResource(R.drawable.icon_soundoff);
                    introSound.pause();
                    soundIsPlaying = false;
                }else{               // if sound is not playing on click -> turn on
                    ivSoundIcon.setImageResource(R.drawable.icon_soundon);
                    introSound.start();
                    soundIsPlaying = true;
                }
            }
        });

    }

}