package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int[] gridElements = {0, 0, 0, 0};
    int correctAnswer;
    //scores
    int correct = 0;
    int overall = 1;
    CountDownTimer countDownTimer;
    androidx.gridlayout.widget.GridLayout gridLayout;
    TextView resultView;
    Button resetButton;
    Button answerView1;
    Button answerView2;
    Button answerView3;
    Button answerView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultView = (TextView) findViewById(R.id.resultView);
        gridLayout =  findViewById(R.id.gridLayout);
        resetButton = (Button) findViewById(R.id.resetButton);

        answerView1 = (Button) findViewById(R.id.ansButton1);
        answerView2 = (Button) findViewById(R.id.ansButton2);
        answerView3 = (Button) findViewById(R.id.ansButton3);
        answerView4 = (Button) findViewById(R.id.ansButton4);

        generateQuestion();
        displayScore();
        startTimer();

    }

    public void generateQuestion() {
        Random rand = new Random();

        // Obtain a number between [1 - 20].
        int n1 = rand.nextInt(20) + 1;
        int n2 = rand.nextInt(20) + 1;

        correctAnswer = n1 + n2;

        TextView questionView = (TextView) findViewById(R.id.questionView);
        questionView.setText(n1 + " + " + n2);

        // Obtain a number between [0 - 3].
        int randomTag = rand.nextInt(4);

        gridElements[randomTag] = 1;

        // Obtain a number between [1 - 50].
        int randomAns2 = rand.nextInt(40) + 1;
        int randomAns3 = rand.nextInt(40) + 1;
        int randomAns4 = rand.nextInt(40) + 1;

        //in case the randomly generated numbers are equal to correctAnswer, regenerated random numbers
        if (randomAns2 == correctAnswer) {
            randomAns2 = rand.nextInt(40) + 1;
        } else if (randomAns3 == correctAnswer) {
            randomAns3 = rand.nextInt(40) + 1;
        } else if (randomAns4 == correctAnswer) {
            randomAns4 = rand.nextInt(40) + 1;
        }

        if (gridElements[0] == 1) {
            answerView1.setText("" + correctAnswer);
            answerView2.setText("" + randomAns2);
            answerView3.setText("" + randomAns3);
            answerView4.setText("" + randomAns4);
            gridElements[randomTag] = 0;
        } else if (gridElements[1] == 1) {
            answerView1.setText("" + randomAns2);
            answerView2.setText("" + correctAnswer);
            answerView3.setText("" + randomAns3);
            answerView4.setText("" + randomAns4);
            gridElements[randomTag] = 0;
        } else if (gridElements[2] == 1) {
            answerView1.setText("" + randomAns2);
            answerView2.setText("" + randomAns3);
            answerView3.setText("" + correctAnswer);
            answerView4.setText("" + randomAns4);
            gridElements[randomTag] = 0;
        } else {
            answerView1.setText("" + randomAns2);
            answerView2.setText("" + randomAns3);
            answerView3.setText("" + randomAns4);
            answerView4.setText("" + correctAnswer);
            gridElements[randomTag] = 0;
        }
    }

    public void clickAnswer(View view) {
        Button clickedButton = (Button) view;
        String expectedAns = Integer.toString(correctAnswer);
        String actualAns = clickedButton.getText().toString();

        if (expectedAns.equals(actualAns)) {
            resultView.setText("Correct!");
            correct++;

        }
        else {
            resultView.setText("Wrong");
        }

        generateQuestion();
        overall++;
        displayScore();
    }

    public void displayScore() {
        TextView scoreView = (TextView) findViewById(R.id.scoreView);
        scoreView.setText(correct + "/" + overall);
    }

    public void startTimer() {
        countDownTimer = new CountDownTimer(20000, 1000) {

            public void onTick(long millisecondsUntilDone) {
                TextView countDownView = (TextView) findViewById(R.id.countDownView);
                countDownView.setText("" + millisecondsUntilDone/1000);
            }

            public void onFinish() {
                resetButton.setVisibility(View.VISIBLE);
                resultView.setText("Game Over!");
                alterButtons(false);
            }
        }.start();
    }

    public void resetGame(View view) {
        alterButtons(true);
        startTimer();
        correct = 0;
        overall = 1;
        displayScore();
        resultView.setText("");
        resetButton.setVisibility(View.GONE);
        generateQuestion();
    }

    private void alterButtons(boolean status) {

        // Get all touchable views
        ArrayList<View> layoutButtons = gridLayout.getTouchables();

        if (status == false) {
            answerView1.setEnabled(false);
            answerView2.setEnabled(false);
            answerView3.setEnabled(false);
            answerView4.setEnabled(false);
        } else {
            answerView1.setEnabled(true);
            answerView2.setEnabled(true);
            answerView3.setEnabled(true);
            answerView4.setEnabled(true);
        }
    }


}