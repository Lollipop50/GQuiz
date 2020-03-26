package com.javernaut.gquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class CheatActivity extends LoggingActivity {

    TextView correctAnswerView;

    private static final String KEY_CORRECT_ANSWER = "key_correct_answer";
    private static final String KEY_TEXT = "key_text";
    private static final String KEY_RESULT = "key_result";

    private String text;
    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        correctAnswerView = findViewById(R.id.correct_answer);

        if (savedInstanceState != null) {
            text = savedInstanceState.getString(KEY_TEXT);
            correctAnswerView.setText(text);

            result = savedInstanceState.getInt(KEY_RESULT);
            setResult(result);
        }

        findViewById(R.id.show_correct_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean correctAnswer = getIntent().getBooleanExtra(KEY_CORRECT_ANSWER, false);

                text = String.valueOf(correctAnswer);
                correctAnswerView.setText(text);

                result = Activity.RESULT_OK;
                setResult(result);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_RESULT, result);
        outState.putString(KEY_TEXT, text);
    }

    public static Intent makeIntent(Context context, boolean correctAnswer) {
        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(KEY_CORRECT_ANSWER, correctAnswer);
        return intent;
    }
}
