package com.javernaut.gquiz;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionViewHolder extends RecyclerView.ViewHolder {

    private final TextView questionTextView;

    public QuestionViewHolder(@NonNull View itemView) {
        super(itemView);

        questionTextView = itemView.findViewById(R.id.question_text);
    }

    public void bindTo(Question question) {
        questionTextView.setText(question.getQuestionResId());
    }
}
