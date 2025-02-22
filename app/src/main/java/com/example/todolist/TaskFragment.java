package com.example.todolist;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class TaskFragment extends DialogFragment {


    private String date;
    private TaskModel taskToEdit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        EditText taskInput = view.findViewById(R.id.txtInputTask);
        Button btnSaveTask = view.findViewById(R.id.btnSaveTask);
        ImageButton btnCalendar = view.findViewById(R.id.btnCalendar);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        date = year + "-" + month + "-" + day;
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        (v, selectedYear, selectedMonth, selectedDay) -> {
                            date = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });


        Bundle bundle = getArguments();
        int id = bundle.getInt("id");
        String taskName = bundle.getString("task");

        if(taskName != null){
            taskInput.setText(taskName);
        }



        DBHandler dbHandler = new DBHandler(requireContext(), "todoListDB", null, 1);

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String task = taskInput.getText().toString().trim();
                if(id == -1){
                    TaskModel taskModel = new TaskModel(task,date);
                    if ( !task.isEmpty() ){
                        dbHandler.addNewTask(taskModel.getTaskName(), taskModel.getDate());
                        ((MainActivity) getActivity()).onTasksUpdated();
                    }
                }else {
                    if ( !task.isEmpty() ){
                    dbHandler.editTask(id,task,date);
                    ((MainActivity) getActivity()).onTasksUpdated();}
                }
                dismiss();
            }
        });





        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}