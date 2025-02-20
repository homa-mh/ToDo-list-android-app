package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "todoListDB", TABLE_NAME = "tasksTable", ID_COL = "ID",
            TASK_COL = "task", DATE_COL = "date", COMPLETED_COL = "isCompleted";
    private static final int DB_VERSION = 1;
    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TASK_COL + " TEXT NOT NULL, "
                + DATE_COL + " TEXT, "
                + COMPLETED_COL + " INTEGER DEFAULT 0)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


//  now my own methods:

//  1. get all tasks :
    public List<TaskModel> showAllTAsks(){
        List<TaskModel> tasks = new ArrayList<>();
        SQLiteDatabase SQLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COMPLETED_COL + " DESC, " +
                DATE_COL + " ASC";
        Cursor cursor = SQLiteDatabase.rawQuery(query, null);
        if (cursor == null) {
            return tasks;
        }
        if (cursor.moveToFirst()) {
            do {
                int taskIndex = cursor.getColumnIndex(TASK_COL);
                int dateIndex = cursor.getColumnIndex(DATE_COL);
                if (taskIndex == -1 || dateIndex == -1) {
                    continue;
                }
                String taskName = cursor.getString(taskIndex);
                String date = cursor.getString(dateIndex);
                tasks.add(new TaskModel(taskName, date));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        SQLiteDatabase.close();
        return tasks;
    }

//  2. add new task
    public void addNewTask(String task, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TASK_COL, task);
        values.put(DATE_COL, date);
        values.put(COMPLETED_COL, 0);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

//  3. edit task
    public void editTask(int id,String task, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_COL, task);
        values.put(COMPLETED_COL, 0);
        values.put(DATE_COL, date);
        db.update(TABLE_NAME,values, "ID =? ", new String[]{String.valueOf(id)});
        db.close();
    }

//  4. complete task
    public void completeTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COMPLETED_COL, 1);
        db.update(TABLE_NAME,values, "ID =? ", new String[]{String.valueOf(id)});
        db.close();
    }

//  5. delete task
    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID =? ", new String[]{String.valueOf(id)});
        db.close();
    }
}
