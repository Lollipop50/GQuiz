package com.javernaut.gquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class StatsActivity extends LoggingActivity {

    private static final String KEY_ALL_ANSWERS = "key_all_answers";

    private int answeredQuestions = 0;
    private int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        int[] allAnswers = getIntent().getIntArrayExtra(KEY_ALL_ANSWERS);
        countStats(allAnswers);

        TextView statsView = findViewById(R.id.stats_view);
        statsView.setText("Answered: " + answeredQuestions + "/" + allAnswers.length + "\n" +
                "Correct answers: " + correctAnswers);
    }

    public static Intent makeIntent(Context context, int[] allAnswers) {
        Intent intent = new Intent(context, StatsActivity.class);
        intent.putExtra(KEY_ALL_ANSWERS, allAnswers);
        return intent;
    }

    private void countStats(int[] allAnswers) {
        for (int index = 0; index < allAnswers.length; index++) {
            int currentAnswer = allAnswers[index];

            if (currentAnswer == MainActivity.CORRECT_ANSWER) {
                answeredQuestions++;
                correctAnswers++;
            } else if (currentAnswer == MainActivity.INCORRECT_ANSWER) {
                answeredQuestions++;
            }
        }
    }
}
