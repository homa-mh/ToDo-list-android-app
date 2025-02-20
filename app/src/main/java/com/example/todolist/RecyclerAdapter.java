package com.example.todolist;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<TaskModel> taskModel;
    public RecyclerAdapter(Context context, ArrayList<TaskModel> taskModel){
        this.taskModel = taskModel;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return taskModel.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtTaskName, txtTaskDate;
        ImageButton btnDeleteTask, btnEditTask, btnCompleteTask;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTaskName = itemView.findViewById(R.id.txtTaskName);
            txtTaskDate = itemView.findViewById(R.id.txtTaskDate);

            btnDeleteTask = itemView.findViewById(R.id.btnDeleteTask);
            btnEditTask = itemView.findViewById(R.id.btnEditTask);
            btnCompleteTask = itemView.findViewById(R.id.btnCompleteTask);

        }
    }
}
