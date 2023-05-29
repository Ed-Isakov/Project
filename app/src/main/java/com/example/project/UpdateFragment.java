package com.example.project;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
        binding.addButton.setOnClickListener(v -> {
            String newElements = binding.addText.getText().toString();
            databaseReference.child(key).child("elements").setValue(elements+" "+newElements);
            binding.addText.setText("");
            Toast.makeText(requireContext(), "Элемент добавлен в очередь", Toast.LENGTH_SHORT).show();
        });
        binding.buttonBack.setOnClickListener(v -> {
            databaseReference.removeEventListener(valueEventListener);
            Navigation.findNavController(binding.getRoot()).popBackStack();
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
