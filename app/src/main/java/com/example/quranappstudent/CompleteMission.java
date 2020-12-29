package com.example.quranappstudent;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class CompleteMission extends AppCompatActivity  {

    int TaskId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_mission);

        TextView task_dec_text = findViewById(R.id.task_dec_text);

        TaskId  = getIntent().getIntExtra("TaskId", 0);
        Log.d("task Id"," " + TaskId);
        String TaskName = getIntent().getStringExtra("TaskName");
        String TaskDec = getIntent().getStringExtra("TaskDec");


        task_dec_text.setText(TaskDec);

        Button ChangeTaskStatus = findViewById(R.id.btn_change_task_status);

        ChangeTaskStatus.setOnClickListener(v -> {

            ChangeTask(TaskId);
        });

    }

    private void ChangeTask(int taskId) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URLs.ChangeTaskStatus + taskId + "?TaskStatus=" + 1 ,
                null, (JSONObject response) -> {
            try {

                int TaskId = response.getInt("TaskStatus");
                Log.d("Task Id", "" + TaskId);
                onSiginSuccess();

            } catch (JSONException e) {
                e.printStackTrace();
                onSiginFailed();
            }

            Log.d("String Response : ", "" + response.toString());

        }, error -> Log.d("Error getting response", "" + error));

        requestQueue.add(jsonObjectRequest);
        Log.d("rs", "" + jsonObjectRequest);

    }

    private void onSiginFailed() {
//        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "Sign in Failed", Snackbar.LENGTH_LONG)
                .setAction("Try Again", v -> {
                    ChangeTask(TaskId);
                }).show();
    }

    private void onSiginSuccess() {
//        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "Sign in Successfully", Snackbar.LENGTH_LONG)
                .show();
        startActivity(new Intent(this, ControlStudent.class));
        finish();
    }

}
