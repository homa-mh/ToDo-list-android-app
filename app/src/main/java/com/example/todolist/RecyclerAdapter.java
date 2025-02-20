package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<TaskModel> taskModel;
    public RecyclerAdapter(Context context, List<TaskModel> taskModel){
        this.taskModel = taskModel;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_items, parent, false);
        return new RecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        MyViewHolder MyViewHolder = (MyViewHolder) holder;
        TaskModel task = this.taskModel.get(position);
        MyViewHolder.txtTaskName.setText(task.getTaskName());
        MyViewHolder.txtTaskDate.setText(task.getDate());

        DBHandler dbHandler = new DBHandler(context , "todoListDB", null, 1);
        MyViewHolder.btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHandler.deleteTask(position);
            }
        });
        MyViewHolder.btnCompleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHandler.completeTask(position);

            }
        });
        MyViewHolder.btnEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
