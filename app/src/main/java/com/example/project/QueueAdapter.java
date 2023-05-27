package com.example.project;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.databinding.QueueItemBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.QueueViewHolder> {
    private final ArrayList<String> data;
    public QueueAdapter(ArrayList<String> data){
        this.data=data;
    }

    @NonNull
    @Override
    public QueueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QueueItemBinding binding = QueueItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new QueueViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull QueueViewHolder holder, int position) {
        String item = data.get(position);
        holder.binding.elementName.setText(item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class QueueViewHolder extends RecyclerView.ViewHolder{
        QueueItemBinding binding;

        public QueueViewHolder(@NonNull QueueItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
