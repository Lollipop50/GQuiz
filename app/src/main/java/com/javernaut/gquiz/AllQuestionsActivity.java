package com.javernaut.gquiz;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AllQuestionsActivity extends LoggingActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO set layout with RecyclerView inside
        setContentView(R.layout.activity_allquestions);

        recyclerView = findViewById(R.id.recyclerView);

        // TODO set LayoutManager to the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO pass an Adapter to the RecyclerView. Use MainActivity.QUESTION_BANK as an argument
        recyclerView.setAdapter(new AllQuestionsAdapter(MainActivity.QUESTION_BANK));
    }
}
