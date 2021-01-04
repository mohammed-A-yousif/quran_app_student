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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.quranappstudent.R;
import com.example.quranappstudent.SharedPrefManager;
import com.example.quranappstudent.URLs;
import com.example.quranappstudent.ViewDialog;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentReview extends Fragment {
    String selectedDates, initialDate, ReviewDec, NumberOfParts;
    int IdStudent, IdTeacher;
    ViewDialog viewDialog;

    EditText ReivewDecText, NumberOfPartsText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steudent_review, container, false);
        CardView cardView = rootView.findViewById(R.id.review_cardView);

        Button addReviewTextButton = rootView.findViewById(R.id.add_review_textButton);
        final TextView yearTextView = rootView.findViewById(R.id.year_textView);


        viewDialog = new ViewDialog(getActivity());
        IdStudent = SharedPrefManager.getInstance(getActivity()).getAdmin().getId();
        IdTeacher = SharedPrefManager.getInstance(getActivity()).getAdmin().getIdTeacher();

        CalendarView calendarView = rootView.findViewById(R.id.calenderView);
        SimpleDateFormat initial_sdf = new SimpleDateFormat("dd-MM-yyyy");

        initialDate = initial_sdf.format(new Date(calendarView.getDate()));
        selectedDates = initialDate;
        yearTextView.setText(initialDate);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            selectedDates = sdf.format(new Date(year - 1900, month, dayOfMonth));
            yearTextView.setText(selectedDates);
        });

        addReviewTextButton.setOnClickListener(v -> {

            final AlertDialog dialogBuilder = new AlertDialog.Builder(getActivity()).create();
            LayoutInflater inflater1 = getActivity().getLayoutInflater();

            View dialogView = inflater1.inflate(R.layout.custom_dialog, null);

            Button btn_Submit = dialogView.findViewById(R.id.btn_Submit);
            Button btn_dismiss = dialogView.findViewById(R.id.btn_dismiss);

            ReivewDecText = dialogView.findViewById(R.id.input_review);
            NumberOfPartsText = dialogView.findViewById(R.id.input_JuzzNum);

            btn_dismiss.setOnClickListener(view -> dialogBuilder.dismiss());

            btn_Submit.setOnClickListener(view -> {
                ReviewDec = ReivewDecText.getText().toString();
                NumberOfParts = NumberOfPartsText.getText().toString();
                AddReview(ReviewDec, NumberOfParts, selectedDates, IdTeacher, IdStudent);

            });

            dialogBuilder.setView(dialogView);
            dialogBuilder.show();
        });
        return rootView;
    }

    private void AddReview(String reviewDec, String numberOfParts, String selectedDates, int idTeacher, int idStudent) {
        if (!validate()) {
            return;
        }
        viewDialog.showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLs.AddReview + "?IdTeacher=" + idTeacher + "&IdStudent=" + idStudent + "&IdStudent=" + idStudent + "&ReviewDec=" + reviewDec + "&NumberOfParts=" + numberOfParts + "&ReviewDate=" + selectedDates + "&Enabled=" + 1, null,
                (JSONObject response) -> {
                    try {
                        String Teacher = response.getString("Teacher");
                        Log.d("res", response.toString());
                        onInsertSuccess();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        onInsertFailed();
                    }

                    Log.d("String Response : ", "" + response.toString());

                }, error -> Log.d("Error getting response", "" + error));

        requestQueue.add(jsonObjectRequest);
        Log.d("rs", "" + jsonObjectRequest);

    }


    private void onInsertFailed() {
        viewDialog.hideDialog();
        Snackbar.make(getActivity().findViewById(android.R.id.content), "فشل اضافة المراجعة", Snackbar.LENGTH_LONG).show();
    }

    private void onInsertSuccess() {
        viewDialog.hideDialog();
        Snackbar.make(getActivity().findViewById(android.R.id.content), "نمت اضافة المراجعة بنجاح", Snackbar.LENGTH_LONG)
                .show();
//        startActivity(new Intent(this, TeacherActivity.class));
//        finish();
    }

    private boolean validate() {
        boolean valid = true;

        if (ReviewDec.length() == 0 ) {
            ReivewDecText.setError("الرجاء ادخال تفاصيل المراجعة ");
            valid = false;
        } else {
            ReivewDecText.setError(null);
        }

        if (NumberOfParts.length()  == 0) {
            NumberOfPartsText.setError("الرجاء ادخال عدد الاجزاء ");
            valid = false;
        } else {
            NumberOfPartsText.setError(null);
        }


        return valid;
    }
}
