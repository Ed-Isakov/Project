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


import com.example.project.databinding.FragmentUpdateBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UpdateFragment extends Fragment {
    ValueEventListener valueEventListener;
    FragmentUpdateBinding binding;
    DatabaseReference databaseReference;
    String elements;
    String key;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUpdateBinding.inflate(inflater, container, false);
        key = getArguments().getString("key");
        databaseReference = FirebaseDatabase.getInstance().getReference("Queues");
        get_elements();
        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newElements = binding.addText.getText().toString();
                databaseReference.child(key).child("elements").setValue(elements+" "+newElements);
                databaseReference.removeEventListener(valueEventListener);
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });
        return binding.getRoot();
    }
    private void get_elements(){
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                elements = snapshot.child(key).child("elements").getValue(String.class);
                assert elements != null;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }
}
