package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<TaskModel> tasks;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerList);

        DBHandler dbHandler = new DBHandler(this, "todoListDB", null, 1);
        tasks = dbHandler.showAllTAsks();
        if (tasks == null) tasks = new ArrayList<>();

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);


        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            TaskFragment taskFragment = new TaskFragment();
            taskFragment.show(getSupportFragmentManager(),"TaskFragment");

            }
        });
    }
    public void onTasksUpdated() {
        DBHandler dbHandler = new DBHandler(this, "todoListDB", null, 1);
        List<TaskModel> updatedTasks = dbHandler.showAllTAsks();
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, updatedTasks);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
    }
}