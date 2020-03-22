package com.javernaut.gquiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainActivity extends LoggingActivity {

    private static final String KEY_CURRENT_QUESTION_INDEX = "key_current_question_index";

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button checkButton;
    private TextView questionView;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int numberOfQuestions = mQuestionBank.length;
    private int answeredQuestions = 0;
    private int correctAnswers = 0;
    private int currentQuestionIndex = 0;

    private HashMap<Integer, Boolean> answeredQuestionsMap = new HashMap<>();
    private HashMap<Integer, Boolean> correctAnswersMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt(KEY_CURRENT_QUESTION_INDEX);
        }

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        checkButton = findViewById(R.id.check_button);
        questionView = findViewById(R.id.question);

        applyCurrentQuestion();

        for (int index = 0; index < mQuestionBank.length; index++) {
            answeredQuestionsMap.put(index, false);
            correctAnswersMap.put(index, false);
        }

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerSelected(true);
                makeQuestionAnswered();
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnswerSelected(false);
                makeQuestionAnswered();
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

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnsweredAndCorrect();
                showCorrectAnswers(answeredQuestions, numberOfQuestions, correctAnswers);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_QUESTION_INDEX, currentQuestionIndex);
    }

    private void applyCurrentQuestion() {
        questionView.setText(getCurrentQuestion().getQuestionResId());
    }

    private Question getCurrentQuestion() {
        return mQuestionBank[currentQuestionIndex];
    }

    private void onAnswerSelected(boolean currentAnswer) {
        boolean wasTheAnswerCorrect = currentAnswer == getCurrentQuestion().getCorrectAnswer();
        if (wasTheAnswerCorrect) {
            correctAnswersMap.put(currentQuestionIndex, true);
        }

        showToast(wasTheAnswerCorrect ? R.string.correct_toast : R.string.incorrect_toast);
    }

    private void makeQuestionAnswered() {
        answeredQuestionsMap.put(currentQuestionIndex, true);
    }

    private void checkAnsweredAndCorrect() {
        answeredQuestions = 0;
        correctAnswers = 0;

        for (int index = 0; index < answeredQuestionsMap.size(); index++) {
            if (answeredQuestionsMap.get(index)) {
                answeredQuestions++;
            }

            if (correctAnswersMap.get(index)) {
                correctAnswers++;
            }
        }
    }

    private void showToast(int textId) {
        Toast.makeText(MainActivity.this, textId, Toast.LENGTH_SHORT).show();
    }

    private void showCorrectAnswers(int answeredQuestions, int numberOfQuestions, int correctAnswers) {
        Toast.makeText(MainActivity.this,
                ("Answered: " + answeredQuestions + "/" + numberOfQuestions + "\n" +
                        "Correct answers: " + correctAnswers),
                Toast.LENGTH_LONG).show();
    }
}
