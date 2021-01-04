package com.example.quranappstudent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.quranappstudent.R;
import com.example.quranappstudent.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> implements Filterable {
    private List<Review> listItems;
    private List<Review> listItemsFiltered;

    public ReviewAdapter(List<Review> listItems, Context context) {
        this.listItems = listItems;
        listItemsFiltered = new ArrayList<>(listItems);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_review, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review listItem = listItems.get(position);
        holder.textReviewStudent.setText("عدد الاجزاء : " + listItem.getNumberOfParts());
//        holder.textReviewTeacher.setText("اسم الشيخ : " + listItem.getTeacher());
        holder.textReviewDec.setText("تفاصيل المراجعة : " + listItem.getReviewDec());
        holder.timestamp.setText(listItem.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textReviewStudent;
//        public TextView textReviewTeacher;
        public TextView textReviewDec;
        public TextView timestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textReviewStudent = itemView.findViewById(R.id.textReviewStudent);
//            textReviewTeacher = itemView.findViewById(R.id.textReviewTeacher);
            textReviewDec = itemView.findViewById(R.id.textReviewDec);
            timestamp = itemView.findViewById(R.id.timestamp);

        }
    }

    @Override
    public Filter getFilter() {
        return contactFilter;
    }

    private Filter contactFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Review> filteredList =new ArrayList<>();

            if (constraint==null|| constraint.length()==0){
                filteredList.addAll(listItemsFiltered);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Review item : listItemsFiltered){
                    if (item.getReviewDec().toLowerCase().contains(filterPattern) || item.getNumberOfParts().contains(filterPattern)){
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
    public interface ReviewAdapterListener {
        void onReviewSelected(Review review);
    }
}
