package com.example.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button startButton;
    private ArrayList<Integer> answers;
    private int correctAnswer;

    private CountDownTimer cntDownTimer;

    private int totalAnswers = 0;
    private int totalCorrect = 0;

    private void updateAnswers() {
        TextView sumTextView = findViewById(R.id.pointsTextView);
        sumTextView.setText(String.valueOf(this.totalCorrect) + "/" + String.valueOf(this.totalAnswers));
    }

    private void updateQuestion(int a, int b) {
        TextView sumTextView = findViewById(R.id.sumTextView);
        sumTextView.setText(String.valueOf(a) + " + " + String.valueOf(b));
    }

    private void updateResultTextView(boolean isCorrect) {
        ((TextView) findViewById(R.id.resultTextView)).setText(isCorrect ? "Correct" : "Incorrect");
        this.updateAnswers();
    }

    private void updateTime(int seconds) {
        TextView sumTextView = findViewById(R.id.timerTextView);
        sumTextView.setText(String.valueOf(seconds) + "s");
    }

    private void generateAnswers() {
        this.totalAnswers ++;
        this.updateAnswers();

        this.answers = new ArrayList<Integer>();

        Random rand = new Random();

        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        this.correctAnswer = a + b;

        this.updateQuestion(a, b);

        int correctLocation = rand.nextInt(4);

        for(int i = 0; i < 4; i++) {
            if(i == correctLocation){
                this.answers.add(correctLocation, this.correctAnswer);
            }else {
                this.answers.add(rand.nextInt(41));
            }
        }

        for(int i= 0; i < 4; i++) {
            int id = getResources().getIdentifier("button" + i, "id", getPackageName());
            Button btn = findViewById(id);
            btn.setText(String.valueOf(this.answers.get(i)));
            btn.setTag(String.valueOf(this.answers.get(i)));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.startButton = (Button) findViewById(R.id.startButton);

        (findViewById(R.id.tableLayout)).setVisibility(View.INVISIBLE);
        (findViewById(R.id.sumTextView)).setVisibility(View.INVISIBLE);

        this.generateAnswers();
    }

    public void start(View v) {
        Log.i("start", "clicked");
        this.startButton.setVisibility(View.INVISIBLE);
        (findViewById(R.id.tableLayout)).setVisibility(View.VISIBLE);
        (findViewById(R.id.sumTextView)).setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.resultTextView)).setText("");

        this.totalAnswers = 1;
        this.totalCorrect = 0;
        this.updateAnswers();

        this.cntDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTime((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                (findViewById(R.id.tableLayout)).setVisibility(View.INVISIBLE);
                (findViewById(R.id.sumTextView)).setVisibility(View.INVISIBLE);

                startButton.setVisibility(View.VISIBLE);
            }
        }.start();

    }

    public void chooseAnswer(View v) {
        Button btn = (Button) v;

        int answer = Integer.parseInt(btn.getTag().toString());

        if(answer == this.correctAnswer){
            this.updateResultTextView(true);
            this.totalCorrect ++;
        }else{
            this.updateResultTextView(false);
        }
        this.generateAnswers();
    }
}
