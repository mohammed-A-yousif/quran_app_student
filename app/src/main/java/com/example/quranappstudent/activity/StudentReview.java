package com.example.quranappstudent.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quranappstudent.InternetStatus;
import com.example.quranappstudent.R;
import com.example.quranappstudent.SharedPrefManager;
import com.example.quranappstudent.URLs;
import com.example.quranappstudent.ViewDialog;
import com.example.quranappstudent.adapter.ReviewAdapter;
import com.example.quranappstudent.model.Review;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentReview extends Fragment {
    String selectedDates, initialDate, ReviewDec, NumberOfParts;
    int IdStudent;
    ViewDialog viewDialog;

    private JSONArray jsonArray;
    List<Review> listItems ;
    private ReviewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steudent_review, container, false);

        FloatingActionButton add_review_Button = rootView.findViewById(R.id.add_review_Button);

        viewDialog = new ViewDialog(getActivity());

        IdStudent = SharedPrefManager.getInstance(getActivity()).getAdmin().getId();

        RecyclerView recyclerView = rootView.findViewById(R.id.review_recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listItems = new ArrayList<>();

        adapter = new ReviewAdapter(listItems, getActivity());
        recyclerView.setAdapter(adapter);

        add_review_Button.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddReviewActivity.class));
        });

        if (InternetStatus.getInstance(getActivity()).isOnline()) {
            GetReview();
        } else {
            Snackbar.make(getView(), " غير متصل بالانترت حاليا ، الرجاء مراجعةالأنترنت " , Snackbar.LENGTH_LONG)
                    .setAction("محاولة مرة اخري", v -> GetReview()).show();
        }


        return rootView;
    }

    private void GetReview() {
        viewDialog.showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.GetReviews + IdStudent + "/"  , response -> {
            try {
                jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i ++){
                    JSONObject ReviewObject = jsonArray.getJSONObject(i);
                    int IdReview = ReviewObject.getInt("IdReview");
                    String Student = ReviewObject.getString("Student");
                    String Teacher = ReviewObject.getString("Student");
                    String ReviewDec = ReviewObject.getString("ReviewDec");
                    String NumberOfParts = ReviewObject.getString("NumberOfParts");
                    String CreatedAt = ReviewObject.getString("CreatedAt");
                    Review listItem = new Review(IdReview, Student,Teacher , ReviewDec, NumberOfParts, CreatedAt);
                    listItems.add(listItem);
                }

                adapter.notifyDataSetChanged();
                viewDialog.hideDialog();
                Log.d("res", jsonArray.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                viewDialog.hideDialog();
                Snackbar.make(getView(), "تعذر عرض المهات " + e , Snackbar.LENGTH_LONG)
                        .setAction("محاولة مرة اخري", v -> GetReview()).show();
            }

        }, error -> {
            error.printStackTrace();
            viewDialog.hideDialog();
            Snackbar.make(getView(), " تعذر عرض المهات " + error , Snackbar.LENGTH_LONG)
                    .setAction("محاولة مرة اخري", v -> GetReview()).show();
        });

        requestQueue.add(stringRequest);

    }

}
