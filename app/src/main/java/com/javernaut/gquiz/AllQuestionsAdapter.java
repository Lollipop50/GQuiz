package com.javernaut.gquiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllQuestionsAdapter extends RecyclerView.Adapter<QuestionViewHolder> {

    private final Question[] questionBank;

    public AllQuestionsAdapter(Question[] questionBank) {
        this.questionBank = questionBank;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        holder.bindTo(questionBank[position]);
    }

    @Override
    public int getItemCount() {
        return questionBank.length;
    }
}
