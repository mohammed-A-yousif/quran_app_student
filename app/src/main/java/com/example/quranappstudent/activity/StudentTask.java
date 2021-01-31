package com.example.quranappstudent.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quranappstudent.InternetStatus;
import com.example.quranappstudent.R;
import com.example.quranappstudent.SharedPrefManager;
import com.example.quranappstudent.URLs;
import com.example.quranappstudent.ViewDialog;
import com.example.quranappstudent.adapter.TaskAdapter;
import com.example.quranappstudent.model.Task;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentTask extends Fragment {

    private TaskAdapter adapter;
    int StudentId;
    ArrayList<Task> listItems ;
    private JSONArray jsonArray;
    ViewDialog viewDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mission_calender, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.missions_recyclerView);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        StudentId = SharedPrefManager.getInstance(getActivity()).getAdmin().getId();
        listItems = new ArrayList<>();

        viewDialog = new ViewDialog(getActivity());

        adapter = new TaskAdapter(listItems);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Task mission = listItems.get(position);
                String title = mission.getTaskName();

//                Toast.makeText(getActivity(),"View Yr Dialog Now ^_*",Toast.LENGTH_SHORT).show();
                //              ################

                final AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
                LayoutInflater inflater1 = getActivity().getLayoutInflater();

                View dialogView = inflater1.inflate(R.layout.task_dialog, null);
                TextView titleTaskTextView= dialogView.findViewById(R.id.task_dialog_tv);
                titleTaskTextView.setText(title);

                dialogBuilder.setView(dialogView);

                FrameLayout mDialogNo = dialogView.findViewById(R.id.frmNo);
                mDialogNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(),"انجزي الشغله دي سرعه ^_^" ,Toast.LENGTH_LONG).show();
                        dialogBuilder.dismiss();
                    }
                });

                FrameLayout mDialogOk = dialogView.findViewById(R.id.frmOk);
                mDialogOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(),"فااااالحه ^_^" ,Toast.LENGTH_LONG).show();
                        dialogBuilder.cancel();
                    }
                });


                dialogBuilder.show();
            }
        });


        if (InternetStatus.getInstance(getActivity()).isOnline()) {
            GetTask();
        } else {
            Snackbar.make(getView(), " غير متصل بالانترت حاليا ، الرجاء مراجعةالأنترنت " , Snackbar.LENGTH_LONG)
                    .setAction("محاولة مرة اخري", v -> GetTask()).show();
        }

        return rootView;
    }

    private void GetTask() {
        viewDialog.showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GetTask + StudentId , response -> {
            try {
                jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i ++){
                    JSONObject TaskObject = jsonArray.getJSONObject(i);
                    int Id = TaskObject.getInt("IdTask");
                    String TaskName = TaskObject.getString("TaskName");
                    String TaskDec = TaskObject.getString("TaskDec");
                    String Teacher = TaskObject.getString("Teacher");
                    String Student = TaskObject.getString("Student");
                    int TaskStatus = TaskObject.getInt("TaskStatus");
                    String CreatedAt = TaskObject.getString("CreatedAt");
                    Task listItem = new Task(Id,TaskName, TaskDec, Teacher, Student,TaskStatus, CreatedAt);
                    listItems.add(listItem);
                }

                adapter.notifyDataSetChanged();
                viewDialog.hideDialog();
                Log.d("res", jsonArray.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                viewDialog.hideDialog();
                Snackbar.make(getView(), " فشل عرض المهات  " + e , Snackbar.LENGTH_LONG)
                        .setAction(" محاولة مرة اخري", v -> GetTask()).show();
            }

        }, error -> {
            error.printStackTrace();
            viewDialog.hideDialog();
            Snackbar.make(getView()," فشل عرض المهات " + error , Snackbar.LENGTH_LONG)
                    .setAction(" محاولة مرة اخري", v -> GetTask()).show();
        });

        requestQueue.add(stringRequest);

    }

//    @Override
//    public void onTaskSelected(Task task) {
//        Log.d("tasK Id","" + task.getId());
//        Intent intent = new Intent(getActivity(), CompleteMission.class);
//        intent.putExtra("TaskId", task.getId());
//        intent.putExtra("TaskName", task.getTaskName());
//        intent.putExtra("TaskDec", task.getTaskDec());
//        startActivity(intent);
//
//    }
}
