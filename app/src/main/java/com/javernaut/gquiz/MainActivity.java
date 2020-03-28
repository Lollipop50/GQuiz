package com.javernaut.gquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainActivity extends LoggingActivity {

    private static final String KEY_CURRENT_QUESTION_INDEX = "key_current_question_index";
    private static final String KEY_ALL_ANSWERS = "key_all_answers";
    private static final int REQUEST_CODE_CHEAT = 42;

    private static final int ANSWER_CORRECT = 1;
    private static final int ANSWER_INCORRECT = -1;
    private static final int NOT_ANSWERED = 0;

    private TextView questionView;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button cheatButton;
    private Button statsButton;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int currentQuestionIndex = 0;

    private int[] allAnswers = new int[mQuestionBank.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt(KEY_CURRENT_QUESTION_INDEX);
            allAnswers = savedInstanceState.getIntArray(KEY_ALL_ANSWERS);
        }

        questionView = findViewById(R.id.question);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        cheatButton = findViewById(R.id.cheat_button);
        statsButton = findViewById(R.id.stats_button);

        applyCurrentQuestion();

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerSelected(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerSelected(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex == mQuestionBank.length - 1) {
                    currentQuestionIndex = 0;
                } else {
                    currentQuestionIndex++;
                }

                applyCurrentQuestion();
            }
        });

        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        CheatActivity.makeIntent(MainActivity.this, getCurrentQuestion().getCorrectAnswer()),
                        REQUEST_CODE_CHEAT
                );
            }
        });

        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStats();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_QUESTION_INDEX, currentQuestionIndex);
        outState.putIntArray(KEY_ALL_ANSWERS, allAnswers);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (resultCode == Activity.RESULT_OK) {
                showToast(R.string.judgment_toast);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void applyCurrentQuestion() {
        questionView.setText(getCurrentQuestion().getQuestionResId());
    }

    private Question getCurrentQuestion() {
        return mQuestionBank[currentQuestionIndex];
    }

    private void onAnswerSelected(boolean currentAnswer) {
        boolean wasTheAnswerCorrect = currentAnswer == getCurrentQuestion().getCorrectAnswer();

        allAnswers[currentQuestionIndex] = (wasTheAnswerCorrect ? ANSWER_CORRECT : ANSWER_INCORRECT);

        showToast(wasTheAnswerCorrect ? R.string.correct_toast : R.string.incorrect_toast);
    }

    private void showToast(int textId) {
        Toast.makeText(MainActivity.this, textId, Toast.LENGTH_SHORT).show();
    }

    private void showStats() {
        int answeredQuestions = 0;
        int correctAnswers = 0;

        for (int currentAnswer : allAnswers) {
            if (currentAnswer == ANSWER_CORRECT) {
                answeredQuestions++;
                correctAnswers++;
            } else if (currentAnswer == ANSWER_INCORRECT) {
                answeredQuestions++;
            }
        }

        startActivity(StatsActivity.makeIntent(
                MainActivity.this,
                answeredQuestions,
                mQuestionBank.length,
                correctAnswers));
    }

}
