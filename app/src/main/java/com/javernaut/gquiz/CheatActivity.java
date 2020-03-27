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
    private static final String KEY_IS_CHEATER = "key_is_cheater";

    private boolean isCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        correctAnswerView = findViewById(R.id.correct_answer);

        if (savedInstanceState != null) {
            isCheater = savedInstanceState.getBoolean(KEY_IS_CHEATER);
            detectCheater();
        }

        findViewById(R.id.show_correct_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCheater = true;
                detectCheater();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_IS_CHEATER, isCheater);
    }

    private void detectCheater() {
        if (isCheater) {
            correctAnswerView.setText(String.valueOf(
                    getIntent().getBooleanExtra(KEY_CORRECT_ANSWER, false)));
            setResult(Activity.RESULT_OK);
        }
    }

    public static Intent makeIntent(Context context, boolean correctAnswer) {
        Intent intent = new Intent(context, CheatActivity.class);
        intent.putExtra(KEY_CORRECT_ANSWER, correctAnswer);
        return intent;
    }
}
