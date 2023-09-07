package com.example.project;

import static com.example.project.MainActivity.id;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.project.databinding.FragmentQueueBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class QueueFragment extends Fragment {
    FragmentQueueBinding binding;
    QueueAdapter adapter;
    ArrayList<String> elements;
    DatabaseReference databaseReference;
    String queueKey;
    Queue queue;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =
                FragmentQueueBinding.inflate(inflater, container, false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        databaseReference = FirebaseDatabase.getInstance().getReference("Queues");
        queueKey = getArguments().getString("key");
        assert queueKey!=null;
        Log.d("XXXXXXXX", queueKey);
        elements = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot e: snapshot.getChildren()){
                    if (e.getKey().equals(queueKey)){
                        queue = e.getValue(Queue.class);
                        elements = queue.toArrayList();
                        if (!queue.adminId.equals("User"+id)){
                            binding.buttonUpdateElement.hide();
                            binding.buttonNext.hide();
                        }
                    }
                }
                adapter = new QueueAdapter(elements);
                binding.recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);
        binding.buttonBack.setOnClickListener(v -> {
            databaseReference.removeEventListener(valueEventListener);
            Navigation.findNavController(binding.getRoot()).popBackStack();
        });
        binding.buttonNext.setOnClickListener(v -> {
            if (!queue.adminId.equals("User"+id)){
                v.setVisibility(View.GONE);
                //Toast.makeText(requireContext(), "У вас нет прав модерации", Toast.LENGTH_SHORT).show();
            } else if (queue.toArrayList().size()>0){
                queue.next();
                databaseReference.child(queueKey).child("elements").setValue(queue.elements);
            }
        });
        binding.buttonUpdateElement.setOnClickListener(v -> {
            if (!queue.adminId.equals("User"+id)) {
                v.setVisibility(View.GONE);
                //Toast.makeText(requireContext(), "У вас нет прав модерации", Toast.LENGTH_SHORT).show();
            } else {
                Bundle bundle = new Bundle();
                bundle.putString("key", queueKey);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_queueFragment_to_updateFragment, bundle);
            }
        });
        binding.buttonUpload.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("key", queueKey);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_queueFragment_to_uploadFragment, bundle);
        });
        return binding.getRoot();
    }



}
