package com.example.quranappstudent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentMission extends Fragment {
    public StudentMission(){

    }
    private MissionsAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mission_calender, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.missions_recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        List<Contact> listItems = new ArrayList<>();

        for (int i = 0; i <10; i++) {
            Contact listItem = new Contact("Mohammed Ahmed" + (i + 1), "0909041441", "9/26/2020");
            listItems.add(listItem);
        }
        adapter = new MissionsAdapter(listItems, getActivity());
        recyclerView.setAdapter(adapter);


        return rootView;
    }

}
