package com.javernaut.gquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QuestionsFragment extends Fragment {

    private static final String KEY_CURRENT_QUESTION_INDEX = "key_current_question_index";

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int currentQuestionIndex = 0;

    // Is it better to use constructor or inflater (line 53) to set layout file?
    public QuestionsFragment() {
        super(R.layout.fragment_questions);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            currentQuestionIndex = savedInstanceState.getInt(KEY_CURRENT_QUESTION_INDEX);
        }
    }

    /*@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_questions, container, false); // Check question at line 35
    }*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        trueButton = view.findViewById(R.id.true_button);
        falseButton = view.findViewById(R.id.false_button);
        nextButton = view.findViewById(R.id.next_button);
        questionView = view.findViewById(R.id.question);

        applyCurrentQuestion();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
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

        showToast(wasTheAnswerCorrect ? R.string.correct_toast : R.string.incorrect_toast);
    }

    private void showToast(int textId) {
        Toast.makeText(getActivity(), textId, Toast.LENGTH_SHORT).show();   //MainActivity.this -> getActivity()
    }
}
