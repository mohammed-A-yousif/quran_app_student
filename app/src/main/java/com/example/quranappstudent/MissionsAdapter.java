package com.example.quranappstudent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MissionsAdapter extends RecyclerView.Adapter<MissionsAdapter.ViewHolder> implements Filterable {
    private List<Task> listItems;
    private List<Task> listItemsFiltered;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewPhone;
        public TextView textViewDate;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.mission_row_name_textView);
            textViewPhone = itemView.findViewById(R.id.mission_row_phone_textView);
            textViewDate = itemView.findViewById(R.id._mission_row_date_textView);

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
        }
    }

    public MissionsAdapter(List<Task> listItems) {
        this.listItems = listItems;
        listItemsFiltered = new ArrayList<>(listItems);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_mission, parent, false);
        return new ViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task listItem = listItems.get(position);
        holder.textViewName.setText(listItem.getTaskName());
        holder.textViewPhone.setText(listItem.getTaskDec());
        holder.textViewDate.setText(listItem.getCreatedAt());
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
            List<Task> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listItemsFiltered);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Task item : listItemsFiltered) {
                    if (item.getTaskName().toLowerCase().contains(filterPattern) || item.getTaskDec().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listItems.clear();
            listItems.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };

}
