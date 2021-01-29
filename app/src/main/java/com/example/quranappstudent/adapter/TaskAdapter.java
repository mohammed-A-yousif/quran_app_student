package com.example.quranappstudent.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.example.quranappstudent.R;
import com.example.quranappstudent.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> implements Filterable {
    private ArrayList<Task> listItems;
    private ArrayList<Task> listItemsFiltered;




//  New @@@@@@@@@

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


//  End of New ########


    public class ViewHolder extends RecyclerView.ViewHolder {
        //        public TextView textStudentName;
        public TextView textteacherName;
        public TextView textTaskName;
        public TextView textViewDate;
        public LinearLayout task_layout;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
//            textStudentName = itemView.findViewById(R.id.textStudentName);
            textteacherName = itemView.findViewById(R.id.textteacherName);
            textTaskName = itemView.findViewById(R.id.textTaskName);
            textViewDate = itemView.findViewById(R.id.timestamp);
            task_layout = itemView.findViewById(R.id.task_layout);

            //            new
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
//            end of new
        }
    }


    public TaskAdapter(ArrayList<Task> listItems) {
        this.listItems = listItems;
        listItemsFiltered = new ArrayList<>(listItems);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_task, parent, false);
        return new ViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task listItem = listItems.get(position);
//        holder.textStudentName.setText("اسم الدارس : " + listItem.getStudent());
        holder.textteacherName.setText("اسم الشيخ : " + listItem.getTeacher());
        holder.textTaskName.setText("عنوان المهمة : " + listItem.getTaskName());
        holder.textViewDate.setText(listItem.getCreatedAt());
        if(listItem.getTaskStatus() != 1){
            holder.task_layout.setBackgroundColor(Color.parseColor("#FE7171"));
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public Filter getFilter() {
        return contactFilter;
    }


    private Filter contactFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Task> filteredList =new ArrayList<>();

            if (constraint==null|| constraint.length()==0){
                filteredList.addAll(listItemsFiltered);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Task item : listItemsFiltered){
                    if (item.getTaskName().toLowerCase().contains(filterPattern) || item.getTaskDec().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results =new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listItems.clear();
            listItems.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };

}
