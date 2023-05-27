package com.example.project;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.databinding.HeadItemBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HeadAdapter extends RecyclerView.Adapter<HeadAdapter.HeadViewHolder> {
    private final ArrayList <Queue> data;
    public HeadAdapter(ArrayList<Queue> data) {
        this.data = data;
    }
    @NonNull
    @Override
    public HeadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HeadItemBinding binding =
                HeadItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new HeadViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HeadViewHolder holder, int position) {
        Queue queue = data.get(position);
        String key = queue.id;
        holder.binding.nameQueue.setText(queue.getQueueName());
        holder.binding.dateQueue.setText(queue.getDate());
        holder.binding.navigateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("key", key);
                Navigation.findNavController(holder.binding.getRoot()).navigate(R.id.action_headFragment_to_queueFragment, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    static class HeadViewHolder extends RecyclerView.ViewHolder{
        HeadItemBinding binding;
        public HeadViewHolder (HeadItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
