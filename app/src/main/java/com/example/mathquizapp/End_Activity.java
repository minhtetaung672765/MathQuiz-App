package com.example.mathquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class End_Activity extends AppCompatActivity {

    TextView tvGrade, tvScoreStatement, tvLevel, tvScore;
    ImageView ivImage;
    Button btnReturn;
    MediaPlayer endSound;
    private String level;
    private int scoreResult;
    private int totalQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lv0_end);

        ivImage = findViewById(R.id.ivImage);
        tvGrade = findViewById(R.id.tvGrade);
        tvScoreStatement = findViewById(R.id.tvScoreStatement);
        tvLevel = findViewById(R.id.tvLevel);
        tvScore = findViewById(R.id.tvScore);
        btnReturn = findViewById(R.id.btnReturn);

        endSound = MediaPlayer.create(this,R.raw.game_result_sound);
        endSound.start();

        // accepts the values from previous activity
        Intent intent = getIntent();
        level = intent.getStringExtra("level");
        scoreResult = intent.getIntExtra("score", 0);
        totalQuestion = intent.getIntExtra("totalQuestion", 0);

        switch (level){      // show level that user comes from
            case "Level 0": tvLevel.setText("Level 0"); break;
            case "Level 1": tvLevel.setText("Level 1"); break;
            case "Level 2": tvLevel.setText("Level 2"); break;
        }
        tvScoreStatement.setText("Your score is "+scoreResult+" out of "+totalQuestion);
        tvScore.setText("Score - "+scoreResult+"/"+totalQuestion);

        // all correct
        if(scoreResult == totalQuestion) {
            tvGrade.setText("Amazing!");
        }
        // score is 0
        else if(scoreResult == 0) {
            tvGrade.setText("Better Luck Next Time!");
            ivImage.setImageResource(R.drawable.prof_comment);
        }
        // score is below 40%
        else if(scoreResult <= (totalQuestion * 0.4)) {
            tvGrade.setText("Well Tried!");
        }
        // score is below 70%
        else if(scoreResult > (totalQuestion * 0.4) && scoreResult <= (totalQuestion * 0.7)){
            tvGrade.setText("Good Job!");
        }
        // score is above 70%
        else{
            tvGrade.setText("Excellent!");
        }

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(End_Activity.this, MainActivity.class);
                startActivity(intent);
                btnReturn.setBackgroundColor(Color.BLACK);
            }
        });
        //
    }
}