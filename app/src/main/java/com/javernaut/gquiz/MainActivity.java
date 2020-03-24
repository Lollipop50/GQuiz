package com.javernaut.gquiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainActivity extends LoggingActivity {

    private static final String KEY_CURRENT_QUESTION_INDEX = "key_current_question_index";
    private static final String KEY_ANSWERED_QUESTIONS = "key_answered_questions_map";
    private static final String KEY_CORRECT_ANSWERS = "key_correct_answers_map";

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

    private int currentQuestionIndex = 0;

    private boolean[] answeredQuestionsArray = new boolean[mQuestionBank.length];
    private boolean[] correctAnswersArray = new boolean[mQuestionBank.length];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt(KEY_CURRENT_QUESTION_INDEX);
            answeredQuestionsArray = savedInstanceState.getBooleanArray(KEY_ANSWERED_QUESTIONS);
            correctAnswersArray = savedInstanceState.getBooleanArray(KEY_CORRECT_ANSWERS);
        } else {
            for (int index = 0; index < mQuestionBank.length; index++) {
                answeredQuestionsArray[index] = false;
                correctAnswersArray[index] = false;
            }
        }

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        checkButton = findViewById(R.id.check_button);
        questionView = findViewById(R.id.question);

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

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndShowAnswers();
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_QUESTION_INDEX, currentQuestionIndex);
        outState.putBooleanArray(KEY_ANSWERED_QUESTIONS, answeredQuestionsArray);
        outState.putBooleanArray(KEY_CORRECT_ANSWERS, correctAnswersArray);
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
            correctAnswersArray[currentQuestionIndex] = true;
        }

        makeQuestionAnswered();

        showToast(wasTheAnswerCorrect ? R.string.correct_toast : R.string.incorrect_toast);
    }

    private void makeQuestionAnswered() {
        answeredQuestionsArray[currentQuestionIndex] = true;
    }

    private void checkAndShowAnswers() {
        int answeredQuestions = 0;
        int correctAnswers = 0;

        for (int index = 0; index < answeredQuestionsArray.length; index++) {
            if (answeredQuestionsArray[index]) {
                answeredQuestions++;
            }

            if (correctAnswersArray[index]) {
                correctAnswers++;
            }
        }

        Toast.makeText(MainActivity.this,
                ("Answered: " + answeredQuestions + "/" + mQuestionBank.length + "\n" +
                        "Correct answers: " + correctAnswers),
                Toast.LENGTH_SHORT).show();
    }

    private void showToast(int textId) {
        Toast.makeText(MainActivity.this, textId, Toast.LENGTH_SHORT).show();
    }

}
