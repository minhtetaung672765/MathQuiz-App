
//  *********** refer to Level0Activity.java for full comments for the same code, most are the same as this class **************

package com.example.mathquizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Level2Activity extends AppCompatActivity {

    TextView tvLevel, tvTotalQuestion, tvQuestion, tvTimer;
    ImageView ivImage;
    Button btnChoice1, btnChoice2, btnChoice3, btnChoice4, btnSubmit;
    Button selectedButton;
    int totalQuestion;
    int questionRemainingCount;
    int questionCount = 0;
    public int score;
    boolean isAnswered;
    CountDownTimer countDownTimer;
    private QuestionModel currentQuestion;
    private ArrayList<QuestionModel> questionList;
    ColorStateList defaultColor;
    int color_lightYellow, color_navy;
    MediaPlayer wrongSound, correctSound, clockTickSound;
    Boolean generateForAddition, generateForSubtraction, generateForMultiplication, generateForDivision;
    private int firstRandomNumber, secondRandomNumber, calculatedResult;
    private String option1,option2,option3,correctOption;
    int questionIndex;
    private ArrayList<Integer> finishedQuestionIndexList;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level2);

        tvLevel = findViewById(R.id.tvLevel);
        tvTotalQuestion = findViewById(R.id.tvTotalQuestion);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvTimer = findViewById(R.id.tvTimer);
        ivImage = findViewById(R.id.ivQuestion);
        btnChoice1 = findViewById(R.id.btnchoice1);
        btnChoice2 = findViewById(R.id.btnchoice2);
        btnChoice3 = findViewById(R.id.btnchoice3);
        btnChoice4 = findViewById(R.id.btnchoice4);
        btnSubmit = findViewById(R.id.btnSubmit);
        defaultColor = btnChoice1.getTextColors();

        color_lightYellow = ContextCompat.getColor(this, R.color.lightYellow);
        color_navy = ContextCompat.getColor(this, R.color.navy);

        clockTickSound = MediaPlayer.create(this,R.raw.clocktick_sound);
        correctSound = MediaPlayer.create(this,R.raw.correct_sound);
        wrongSound = MediaPlayer.create(this,R.raw.wrong_sound2);

        questionList = new ArrayList<>();
        addQuestion();
        totalQuestion = questionList.size();

        finishedQuestionIndexList = new ArrayList<>();

        questionRemainingCount = questionList.size() - 1;

        // Start the game by showing a new question
        showNewQuestion();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isAnswered == false){
                    if(selectedButton == btnChoice1 || selectedButton == btnChoice2
                            || selectedButton == btnChoice3 || selectedButton == btnChoice4){
                        checkAnswer();
                        countDownTimer.cancel();
                    }else{
                        Toast.makeText(Level2Activity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                        ivImage.setImageResource(R.drawable.prof_comment);
                    }
                }
                else{
                    showNewQuestion();
                }
            }
        });
    }
    private void addQuestion(){
        //1
        generateForAddition = true;
        generateRandomQuestions();
        questionList.add(new QuestionModel(""+firstRandomNumber+" + "+secondRandomNumber+" = ?", option1, correctOption, option2, option3, calculatedResult));
        //2
        generateForMultiplication = true;
        generateRandomQuestions();
        questionList.add(new QuestionModel(""+firstRandomNumber+" × "+secondRandomNumber+" = ?", option1, option2, correctOption, option3, calculatedResult));
        //3
        generateForMultiplication = true;
        generateRandomQuestions();
        questionList.add(new QuestionModel(""+firstRandomNumber+" × "+secondRandomNumber+" = ?", option1, option2, correctOption, option3, calculatedResult));
        //4
        generateForMultiplication = true;
        generateRandomQuestions();
        questionList.add(new QuestionModel(""+firstRandomNumber+" × "+secondRandomNumber+" = ?", option1, option2, correctOption, option3, calculatedResult));
        //5
        generateForSubtraction = true;
        generateRandomQuestions();
        questionList.add(new QuestionModel(""+firstRandomNumber+" - "+secondRandomNumber+" = ?", correctOption, option1, option2, option3, calculatedResult));
        //6
        generateForAddition = true;
        generateRandomQuestions();
        questionList.add(new QuestionModel(""+firstRandomNumber+" + "+secondRandomNumber+" = ?", option1, correctOption, option2, option3, calculatedResult));
        //7
        generateForDivision = true;
        generateRandomQuestions();
        questionList.add(new QuestionModel(""+firstRandomNumber+" ÷ "+secondRandomNumber+" = ?", option1,option2,option3,correctOption,calculatedResult));
        //8
        generateForDivision = true;
        generateRandomQuestions();
        questionList.add(new QuestionModel(""+firstRandomNumber+" ÷ "+secondRandomNumber+" = ?", option1,option2,option3,correctOption,calculatedResult));
        //9
        generateForDivision = true;
        generateRandomQuestions();
        questionList.add(new QuestionModel(""+firstRandomNumber+" ÷ "+secondRandomNumber+" = ?", option1,option2,option3,correctOption,calculatedResult));
        //10
        generateForSubtraction = true;
        generateRandomQuestions();
        questionList.add(new QuestionModel(""+firstRandomNumber+" - "+secondRandomNumber+" = ?", correctOption, option1, option2, option3, calculatedResult));
    }

    private void showNewQuestion(){
        btnChoice1.setEnabled(true);
        btnChoice2.setEnabled(true);
        btnChoice3.setEnabled(true);
        btnChoice4.setEnabled(true);

        clockTickSound.setLooping(true);
        clockTickSound.start();

        tvLevel.setText("Level 2");
        ivImage.setImageResource(R.drawable.prof_asking);
        tvTimer.setTextColor(defaultColor);
        selectedButton = null;     // removes previous selected data
        btnChoice1.setBackgroundColor(Color.WHITE);
        btnChoice2.setBackgroundColor(Color.WHITE);
        btnChoice3.setBackgroundColor(Color.WHITE);
        btnChoice4.setBackgroundColor(Color.WHITE);

        if(questionCount < totalQuestion){
            Timer();
            questionIndex = random.nextInt(totalQuestion);

            if (finishedQuestionIndexList.contains(questionIndex)) {
                do {
                    questionIndex = random.nextInt(totalQuestion);
                }while(finishedQuestionIndexList.contains(questionIndex));
            }
            currentQuestion = questionList.get(questionIndex);
            finishedQuestionIndexList.add(questionIndex);

            tvTotalQuestion.setText("Question remaining: "+questionRemainingCount);
            tvQuestion.setText(currentQuestion.getQuestion());
            btnChoice1.setText(currentQuestion.getChoice1());
            btnChoice2.setText(currentQuestion.getChoice2());
            btnChoice3.setText(currentQuestion.getChoice3());
            btnChoice4.setText(currentQuestion.getChoice4());
            btnSubmit.setText("Submit");
            btnSubmit.setBackgroundColor(color_navy);
            btnSubmit.setTextColor(Color.WHITE);

            btnChoice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedButton = btnChoice1;
                    btnChoice1.setBackgroundColor(color_lightYellow);
                    btnChoice2.setBackgroundColor(Color.WHITE);
                    btnChoice3.setBackgroundColor(Color.WHITE);
                    btnChoice4.setBackgroundColor(Color.WHITE);
                    ivImage.setImageResource(R.drawable.prof_asking);
                }
            });
            btnChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedButton = btnChoice2;
                    btnChoice2.setBackgroundColor(color_lightYellow);
                    btnChoice1.setBackgroundColor(Color.WHITE);
                    btnChoice3.setBackgroundColor(Color.WHITE);
                    btnChoice4.setBackgroundColor(Color.WHITE);
                    ivImage.setImageResource(R.drawable.prof_asking);
                }
            });
            btnChoice3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedButton = btnChoice3;
                    btnChoice3.setBackgroundColor(color_lightYellow);
                    btnChoice2.setBackgroundColor(Color.WHITE);
                    btnChoice1.setBackgroundColor(Color.WHITE);
                    btnChoice4.setBackgroundColor(Color.WHITE);
                    ivImage.setImageResource(R.drawable.prof_asking);
                }
            });
            btnChoice4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedButton = btnChoice4;
                    btnChoice4.setBackgroundColor(color_lightYellow);
                    btnChoice2.setBackgroundColor(Color.WHITE);
                    btnChoice3.setBackgroundColor(Color.WHITE);
                    btnChoice1.setBackgroundColor(Color.WHITE);
                    ivImage.setImageResource(R.drawable.prof_asking);
                }
            });

            questionCount++;
            questionRemainingCount--;
            isAnswered = false;
        }
        else{
            clockTickSound.stop();
            if(selectedButton==null ){
                tvLevel.setText("Level 2");   // to pass the string for level checking on result activity
                Intent intent = new Intent(Level2Activity.this, End_Activity.class);
                intent.putExtra("level", tvLevel.getText().toString()); // Pass the level text
                intent.putExtra("score", score); // Pass the score value
                intent.putExtra("totalQuestion", totalQuestion); // Pass the totalQuestion value
                startActivity(intent);
            }
        }
    }

    private void checkAnswer(){
        isAnswered = true;

        tvTimer.setText("");
        clockTickSound.pause();

        if(Integer.parseInt(selectedButton.getText().toString()) == currentQuestion.getCorrectAns()){

            correctSound.start();

            score++;
            ivImage.setImageResource(R.drawable.prof_happy);
            selectedButton.setBackgroundColor(Color.GREEN);
            selectedButton.setText(selectedButton.getText().toString()+"  ✔️");

        }else {
            wrongSound.start();

            if(Integer.parseInt(btnChoice1.getText().toString()) == currentQuestion.getCorrectAns()){
                btnChoice1.setText(btnChoice1.getText().toString()+"  ✔️");
            }else if(Integer.parseInt(btnChoice2.getText().toString()) == currentQuestion.getCorrectAns()){
                btnChoice2.setText(btnChoice2.getText().toString()+"  ✔️");
            }else if(Integer.parseInt(btnChoice3.getText().toString()) == currentQuestion.getCorrectAns()){
                btnChoice3.setText(btnChoice3.getText().toString()+"  ✔️");
            }else{
                btnChoice4.setText(btnChoice4.getText().toString()+"  ✔️");
            }

            ivImage.setImageResource(R.drawable.prof_angry);
            selectedButton.setBackgroundColor(Color.YELLOW);
            selectedButton.setText(selectedButton.getText().toString()+"   ❌️");

        }
        tvLevel.setText("Score: ⭐"+score);

        // prevents buttons from being clickable during the checking process
        btnChoice1.setEnabled(false); // Disable the button
        btnChoice2.setEnabled(false);
        btnChoice3.setEnabled(false);
        btnChoice4.setEnabled(false);

        if(questionCount < totalQuestion){
            btnSubmit.setText("Next");
            btnSubmit.setBackgroundColor(Color.YELLOW);
            btnSubmit.setTextColor(Color.BLACK);
        }else{
            btnSubmit.setText("  Finish  ");
            btnSubmit.setBackgroundColor(Color.RED);

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvLevel.setText("Level 2");   // to pass the string for level checking on result activity
                    Intent intent = new Intent(Level2Activity.this, End_Activity.class);
                    intent.putExtra("level", tvLevel.getText().toString()); // Pass the level text
                    intent.putExtra("score", score); // Pass the score value
                    intent.putExtra("totalQuestion", totalQuestion); // Pass the totalQuestion value
                    startActivity(intent);
                }
            });
        }

    }
    private void Timer(){
        countDownTimer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long l) {
                tvTimer.setText("Time left: "+l/1000+"s");
                if (l <= 6000) {
                    tvTimer.setTextColor(Color.RED); // Change the color to red when the timer is at 5 seconds or less
                }
                if (l <= 1000 && selectedButton == null) {
                    ivImage.setImageResource(R.drawable.prof_angry);
                    wrongSound.start();
                }
            }
            @Override
            public void onFinish() {
                showNewQuestion();
            }
        }.start();
    }

    private void generateRandomQuestions(){

        // **** for addition and multiplication *****
        if(generateForAddition || generateForMultiplication) {

            do{      // numbers should be greater than zero for appropriate addition and multiplication
                firstRandomNumber = random.nextInt(13);
                secondRandomNumber = random.nextInt(13);
            }while(secondRandomNumber == 0 || firstRandomNumber == 0);

            if (generateForAddition) {
                calculatedResult = firstRandomNumber + secondRandomNumber;
            } else {
                calculatedResult = firstRandomNumber * secondRandomNumber;
            }

            int resultTracker = calculatedResult;
            correctOption = String.valueOf(calculatedResult);  // convert to String, as options are String

            if(resultTracker > 5){
                resultTracker += 10;     // make more options that are greater than correct answer
                option1 = String.valueOf(random.nextInt(resultTracker));
                option2 = String.valueOf(random.nextInt(resultTracker));
                option3 = String.valueOf(random.nextInt(resultTracker));
            }else{
                resultTracker += 5;  // make more options that are greater and close to correct answer
                option1 = String.valueOf(random.nextInt(resultTracker));
                option2 = String.valueOf(random.nextInt(resultTracker));
                option3 = String.valueOf(random.nextInt(resultTracker));
            }
            // makes each option is unique and do not repeat to each other
            while (option1.equals(0) || option1.equals(correctOption)){     // addition cannot result 0
                option1 = String.valueOf(random.nextInt(resultTracker));
            }
            while(option2.equals(0) ||option2.equals(option1)  ||
                    option2.equals(option3) || option2.equals(correctOption)){
                option2 = String.valueOf(random.nextInt(resultTracker));
            }
            while(option3.equals(0) ||option3.equals(option1) ||
                    option3.equals(option2)|| option3.equals(correctOption)){
                option3 = String.valueOf(random.nextInt(resultTracker));
            }
        }

        // ********** for subtraction and division *******
        else{

            int resultTracker = 0;
            if(generateForSubtraction){
                do{      // numbers should be greater than zero for appropriate subtraction
                    firstRandomNumber = random.nextInt(13);
                    secondRandomNumber = random.nextInt(13);
                }while(secondRandomNumber == 0 || firstRandomNumber == 0);

                if(firstRandomNumber < secondRandomNumber){  // first number must be greater than second in subtraction
                    int temp = firstRandomNumber;
                    firstRandomNumber = secondRandomNumber; // swap greater number as first
                    secondRandomNumber = temp;
                }
                calculatedResult = firstRandomNumber - secondRandomNumber;
                resultTracker = calculatedResult;

                if(resultTracker > 5){
                    resultTracker += 3;  // makes close values for guessing since 0 to 12
                    option1 = String.valueOf(random.nextInt(resultTracker));
                    option2 = String.valueOf(random.nextInt(resultTracker));
                    option3 = String.valueOf(random.nextInt(resultTracker));
                }else{
                    resultTracker += 5;  // creates more options
                    option1 = String.valueOf(random.nextInt(resultTracker));
                    option2 = String.valueOf(random.nextInt(resultTracker));
                    option3 = String.valueOf(random.nextInt(resultTracker));
                }

            }else {
                do{     // numbers should be greater than 1 and 0 for efficient division, divisible for whole number result
                    firstRandomNumber = random.nextInt(13);
                    secondRandomNumber = random.nextInt(13);
                }while(firstRandomNumber <=1 || secondRandomNumber <=1 || firstRandomNumber == secondRandomNumber ||
                        firstRandomNumber % secondRandomNumber != 0);

                calculatedResult = firstRandomNumber / secondRandomNumber;
                resultTracker = calculatedResult;

                resultTracker += 5;    // create more greater options than correct answer
                option1 = String.valueOf(random.nextInt(resultTracker));
                option2 = String.valueOf(random.nextInt(resultTracker));
                option3 = String.valueOf(random.nextInt(resultTracker));
            }

            correctOption = String.valueOf(calculatedResult);

            // makes each option is unique and do not repeat to each other
            while (option1.equals(correctOption)){
                option1 = String.valueOf(random.nextInt(resultTracker));
            }
            while(option2.equals(option1)  || option2.equals(option3) ||
                    option2.equals(correctOption)){
                option2 = String.valueOf(random.nextInt(resultTracker));
            }
            while(option3.equals(option1) || option3.equals(option2)||
                    option3.equals(correctOption)){
                option3 = String.valueOf(random.nextInt(resultTracker));
            }
        }

        // reset the condition check
        generateForAddition = false;
        generateForDivision = false;
        generateForMultiplication = false;
        generateForSubtraction = false;
    }


}