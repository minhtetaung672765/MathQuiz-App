package com.example.mathquizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class Level0Activity extends AppCompatActivity {
    TextView tvLevel, tvTotalQuestion, tvQuestion;
    ImageView ivImage;
    Button btnChoice1, btnChoice2, btnChoice3, btnChoice4, btnSubmit;
    Button selectedButton;
    int totalQuestion;
    int questionRemainingCount;
    int questionCount = 0;
    public int score;
    boolean isAnswered;
    private QuestionModel currentQuestion;
    private ArrayList<QuestionModel> questionList;
    ColorStateList defaultColor;
    int color_lightYellow, color_navy;  // for creating custom colors/
    MediaPlayer correctSound, wrongSound;

    // Building variables for random-question-generating functions
    Boolean generateForAddition, generateForSubtraction, generateForMultiplication, generateForDivision;
    private int firstRandomNumber, secondRandomNumber, calculatedResult;
    private String option1,option2,option3,correctOption;
    int questionIndex;
    private ArrayList<Integer> finishedQuestionIndexList;   // to store indexes of finish questions
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level0);

        tvLevel = findViewById(R.id.tvLevel);
        tvTotalQuestion = findViewById(R.id.tvTotalQuestion);
        tvQuestion = findViewById(R.id.tvQuestion);
        ivImage = findViewById(R.id.ivQuestion);
        btnChoice1 = findViewById(R.id.btnchoice1);
        btnChoice2 = findViewById(R.id.btnchoice2);
        btnChoice3 = findViewById(R.id.btnchoice3);
        btnChoice4 = findViewById(R.id.btnchoice4);
        btnSubmit = findViewById(R.id.btnSubmit);
        defaultColor = btnChoice1.getTextColors();

        color_lightYellow = ContextCompat.getColor(this, R.color.lightYellow);
        color_navy = ContextCompat.getColor(this, R.color.navy);

        correctSound = MediaPlayer.create(this,R.raw.correct_sound);
        wrongSound = MediaPlayer.create(this,R.raw.wrong_sound2);

        questionList = new ArrayList<>();  // list for storing questions
        addQuestion();  // add questions to the list
        totalQuestion = questionList.size();

        finishedQuestionIndexList = new ArrayList<>();
        questionRemainingCount = questionList.size() - 1;  // for tracking number of remaining questions

        // Start the game by showing a new question
        showNewQuestion();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isAnswered == false){   // isAnswered value is set to false since the first question run
                    if(selectedButton == btnChoice1 || selectedButton == btnChoice2
                            || selectedButton == btnChoice3 || selectedButton == btnChoice4){
                        checkAnswer();      // if one of the four options is selected, check it
                    }else{                 // if none option is selected, prompt to select
                        Toast.makeText(Level0Activity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                        ivImage.setImageResource(R.drawable.prof_comment);
                    }
                }
                else{   // isAnswered value is set to true since checkAnswer method is run
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
        btnChoice1.setEnabled(true);  // makes choice buttons clickable
        btnChoice2.setEnabled(true);
        btnChoice3.setEnabled(true);
        btnChoice4.setEnabled(true);

        tvLevel.setText("Level 0");
        ivImage.setImageResource(R.drawable.prof_asking);   // use an respective image for question process
        selectedButton = null;     // removes previous selected data
        btnChoice1.setBackgroundColor(Color.WHITE);   // set default color at the question start
        btnChoice2.setBackgroundColor(Color.WHITE);
        btnChoice3.setBackgroundColor(Color.WHITE);
        btnChoice4.setBackgroundColor(Color.WHITE);

        if(questionCount < totalQuestion){   // do this condition until the all questions are displayed

            questionIndex = random.nextInt(totalQuestion); // get the random index value between 0 and 10

            // checks condition to avoid repeated questions
            if (finishedQuestionIndexList.contains(questionIndex)) {  // if gathered index value already exists
                do {
                    questionIndex = random.nextInt(totalQuestion);   // generate a new index till it repeats
                }while(finishedQuestionIndexList.contains(questionIndex));
            }
            currentQuestion = questionList.get(questionIndex);  // current displaying question can be any (nth) within all questions
            finishedQuestionIndexList.add(questionIndex);  // after setting question, it goes into finished list

            tvTotalQuestion.setText("Question remaining: "+questionRemainingCount);
            tvQuestion.setText(currentQuestion.getQuestion());   // setting question
            btnChoice1.setText(currentQuestion.getChoice1());    // setting choice options
            btnChoice2.setText(currentQuestion.getChoice2());
            btnChoice3.setText(currentQuestion.getChoice3());
            btnChoice4.setText(currentQuestion.getChoice4());
            btnSubmit.setText("Submit");
            btnSubmit.setBackgroundColor(color_navy);
            btnSubmit.setTextColor(Color.WHITE);

            questionCount++;          // displayed question count increases by 1
            questionRemainingCount--;  //  1 question is finished
            isAnswered = false;       //  reset for condition check of future question

            btnChoice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedButton = btnChoice1;      // if user clicks option 1, button1 becomes selected answer
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
                    selectedButton = btnChoice2;     // if user clicks option 2, button2 becomes selected answer
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
                    selectedButton = btnChoice3;     // if user clicks option 3, button3 becomes selected answer
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
                    selectedButton = btnChoice4;     // if user clicks option 4, button4 becomes selected answer
                    btnChoice4.setBackgroundColor(color_lightYellow);
                    btnChoice2.setBackgroundColor(Color.WHITE);
                    btnChoice3.setBackgroundColor(Color.WHITE);
                    btnChoice1.setBackgroundColor(Color.WHITE);
                    ivImage.setImageResource(R.drawable.prof_asking);
                }
            });
        }
        else{          // if all questions run out, finish ( if necessary)
            finish();
        }
    }
    private void checkAnswer(){
        isAnswered = true;    // set for condition checking onclick of submit button

        // when user selected option is correct
        if(Integer.parseInt(selectedButton.getText().toString()) == currentQuestion.getCorrectAns()){
            correctSound.start();
            score++;
            ivImage.setImageResource(R.drawable.prof_happy);  // use an respective image for answer correct process
            selectedButton.setBackgroundColor(Color.GREEN);
            selectedButton.setText(selectedButton.getText().toString()+"  ✔️");
        }
        else {     // when user selected option is wrong
            wrongSound.start();
            // reveals correct answer
            if(Integer.parseInt(btnChoice1.getText().toString()) == currentQuestion.getCorrectAns()){
                btnChoice1.setText(btnChoice1.getText().toString()+"  ✔️");
            }else if(Integer.parseInt(btnChoice2.getText().toString()) == currentQuestion.getCorrectAns()){
                btnChoice2.setText(btnChoice2.getText().toString()+"  ✔️");
            }else if(Integer.parseInt(btnChoice3.getText().toString()) == currentQuestion.getCorrectAns()){
                btnChoice3.setText(btnChoice3.getText().toString()+"  ✔️");
            }else{
                btnChoice4.setText(btnChoice4.getText().toString()+"  ✔️");
            }
            ivImage.setImageResource(R.drawable.prof_angry);  // use an respective image for answer wrong process
            selectedButton.setBackgroundColor(Color.YELLOW);
            selectedButton.setText(selectedButton.getText().toString()+"   ❌️");
        }
        tvLevel.setText("Score: ⭐"+score);  // set score text view with updated score

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
                public void onClick(View v) {    // if all questions are done, move to result activity
                    tvLevel.setText("Level 0");   // to pass the string for level checking on result activity
                    Intent intent = new Intent(Level0Activity.this, End_Activity.class);
                    intent.putExtra("level", tvLevel.getText().toString()); // Pass the level text
                    intent.putExtra("score", score); // Pass the score value
                    intent.putExtra("totalQuestion", totalQuestion); // Pass the totalQuestion value
                    startActivity(intent);
                }
            });
        }
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