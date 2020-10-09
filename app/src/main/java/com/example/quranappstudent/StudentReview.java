package com.example.quranappstudent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentReview extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steudent_review, container, false);
        CardView cardView = rootView.findViewById(R.id.review_cardView);
        final TextView yearTextView = rootView.findViewById(R.id.year_textView);
        CalendarView calendarView = rootView.findViewById(R.id.calenderView);
        SimpleDateFormat initial_sdf = new SimpleDateFormat("dd/MM/yyyy");
        String initialDate = initial_sdf.format(new Date(calendarView.getDate()));
        yearTextView.setText(initialDate);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String selectedDates = sdf.format(new Date(year - 1900, month, dayOfMonth));
                yearTextView.setText(selectedDates);
            }
        });
        return rootView;
    }
}
