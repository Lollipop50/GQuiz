package com.javernaut.gquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class StatsActivity extends LoggingActivity {

    private static final String KEY_ANSWERED_QUESTIONS = "key_answered_questions";
    private static final String KEY_NUMBER_OF_QUESTIONS = "key_number_of_questions";
    private static final String KEY_CORRECT_ANSWERS = "key_correct_answers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        TextView statsView = findViewById(R.id.stats_view);

        statsView.setText(generateStatsText(
                getIntent().getIntExtra(KEY_ANSWERED_QUESTIONS, 0),
                getIntent().getIntExtra(KEY_NUMBER_OF_QUESTIONS, 0),
                getIntent().getIntExtra(KEY_CORRECT_ANSWERS, 0)));
    }

    public static Intent makeIntent(Context context, int answeredQuestions, int numberOfQuestions, int correctAnswers) {
        Intent intent = new Intent(context, StatsActivity.class);
        intent.putExtra(KEY_ANSWERED_QUESTIONS, answeredQuestions);
        intent.putExtra(KEY_NUMBER_OF_QUESTIONS, numberOfQuestions);
        intent.putExtra(KEY_CORRECT_ANSWERS, correctAnswers);
        return intent;
    }

    private String generateStatsText(int answeredQuestions, int numberOfQuestions, int correctAnswers) {
        return ("Answered: " + answeredQuestions + "/" + numberOfQuestions + "\n" +
                "Correct answers: " + correctAnswers);
    }

}
