package com.example.awsshaheen_ghassanqandeel_encs5150_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static DataBaseHelper instance;

    private DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public static synchronized DataBaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseHelper(context.getApplicationContext(), "TODO_TASKS", null, 1);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {


            db.execSQL("CREATE TABLE USERS(" +
                    "EMAIL TEXT PRIMARY KEY," +
                    "FIRSTNAME TEXT," +
                    "LASTNAME TEXT," +
                    "PASSWORD TEXT)");

            db.execSQL("CREATE TABLE TASKS(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "EMAIL TEXT ," +
                    "TITTLE TEXT," +
                    "DESCRIPTION TEXT," +
                    "DUEDATE DATETIME ," +
                    "COMPLETION_STATE  TEXT," +
                    "PRIORITY_LEVEL  TEXT,"+
                    "Reminder Boolean)");


        } catch (Exception e) {
            Toast.makeText(context, "Email or Password null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insertUser(User user) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("EMAIL", user.getEmail());
        contentValues.put("FIRSTNAME", user.getFirstName());
        contentValues.put("LASTNAME", user.getSecondName());
        contentValues.put("PASSWORD", user.getPassword());

        sqLiteDatabase.insert("USERS", null, contentValues);
    }

    public void insertTask(Task task) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("EMAIL", task.getEmail());
        contentValues.put("TITTLE", task.getTittle());
        contentValues.put("DESCRIPTION", task.getDescription());
        contentValues.put("DUEDATE", task.getDueDate());
        contentValues.put("COMPLETION_STATE", task.getCompletionStatus().toString());
        contentValues.put("PRIORITY_LEVEL", task.getPriorityLevel().toString());
        contentValues.put("Reminder", task.getReminder());
        sqLiteDatabase.insert("TASKS", null, contentValues);
    }

    public String findUserEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        String foundEmail = null;

        Cursor cursor = db.rawQuery("SELECT EMAIL FROM USERS WHERE EMAIL = ?", new String[]{email});

        // Check if a result exists
        if (cursor != null && cursor.moveToFirst()) {
            foundEmail = cursor.getString(0);
        }

        // Close the cursor
        if (cursor != null) {
            cursor.close();
        }

        return foundEmail; // Will return null if the email is not found
    }
    public String findUserPassword(String password) {
        SQLiteDatabase db = getReadableDatabase();
        String foundPassword = null;

        Cursor cursor = db.rawQuery("SELECT PASSWORD FROM USERS WHERE PASSWORD = ?", new String[]{password});

        // Check if a result exists
        if (cursor != null && cursor.moveToFirst()) {
            foundPassword = cursor.getString(0);
        }

        // Close the cursor
        if (cursor != null) {
            cursor.close();
        }

        return foundPassword; // Will return null if the email is not found
    }
    public String getAllTasks() {
        SQLiteDatabase db = getReadableDatabase();
        StringBuilder taskList = new StringBuilder();
        Cursor cursor = db.rawQuery("SELECT ID, TITTLE, DESCRIPTION, DUEDATE, COMPLETION_STATE, PRIORITY_LEVEL, Reminder FROM TASKS", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                String dueDate = cursor.getString(3);
                String completionState = cursor.getString(4);
                String priorityLevel = cursor.getString(5);
                int reminder = cursor.getInt(6);
                taskList.append("Task").append(id)
                        .append("\n")
                        .append("Title: ").append(title)
                        .append("\n")
                        .append(", Description: ").append(description)
                        .append("\n")
                        .append(", Due Date: ").append(dueDate)
                        .append("\n")
                        .append(", Completion State: ").append(completionState)
                        .append("\n")
                        .append(", Priority: ").append(priorityLevel)
                        .append("\n")
                        .append(", Reminder: ").append(reminder == 1 ? "Yes" : "No")
                        .append("\n")
                        .append("-----------------------------------------------------")
                        .append("\n");
            }
            cursor.close();
        }

        return taskList.toString();
    }
    public String getAllTasksSorted() {
        SQLiteDatabase db = getReadableDatabase();
        StringBuilder taskList = new StringBuilder();


        Cursor cursor = db.rawQuery("SELECT ID,TITTLE, DESCRIPTION, DUEDATE FROM TASKS ORDER BY DUEDATE ASC", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id =cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                String dueDate = cursor.getString(3);
                taskList.append("Task").append(id)
                        .append("\n")
                        .append("Title: ").append(title)
                        .append("\n")
                        .append(", Description: ").append(description)
                        .append("\n")
                        .append(", Due Date: ").append(dueDate)
                        .append("-----------------------------------------------------")
                        .append("\n");
            }
            cursor.close();
        }

        return taskList.toString();
    }



}
