package com.example.todolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<TaskModel> taskModel;
    FragmentManager fragmentManager;
    public RecyclerAdapter(Context context, List<TaskModel> taskModel, FragmentManager fragmentManager){
        this.taskModel = taskModel;
        this.context = context;
        this.fragmentManager = fragmentManager;
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
        if(task.isCompleted() == 1){
            MyViewHolder.btnCompleteTask.setImageResource(R.drawable.completed_task);

                    MyViewHolder.txtTaskName.setPaintFlags(MyViewHolder.txtTaskName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    MyViewHolder.txtTaskName.setTextColor(Color.GRAY);
;

        }else {
            MyViewHolder.btnCompleteTask.setImageResource(R.drawable.not_completed);

                    MyViewHolder.txtTaskName.setPaintFlags(MyViewHolder.txtTaskName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    MyViewHolder.txtTaskName.setTextColor(Color.BLACK);



        }

        DBHandler dbHandler = new DBHandler(context , "todoListDB", null, 1);
        MyViewHolder.btnDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = task.getId();
                dbHandler.deleteTask(id);

                taskModel.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, taskModel.size());
            }
        });
        MyViewHolder.btnCompleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = task.getId();
                if(task.isCompleted() == 0){
                    dbHandler.completeTask(id);
                    task.setCompleted(1);
                }else{
                    task.setCompleted(0);
                    dbHandler.unCompleteTask(id);
                }
                notifyDataSetChanged();
            }
        });
        MyViewHolder.btnEditTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id",task.getId());
                bundle.putString("task", task.getTaskName());
                TaskFragment taskFragment = new TaskFragment();
                taskFragment.setArguments(bundle);
                taskFragment.show(fragmentManager,"TaskFragment");
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

    public void refresh (List<TaskModel> newTasks){
        this.taskModel = newTasks;
        notifyDataSetChanged();
    }
}
