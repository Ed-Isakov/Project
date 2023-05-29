package com.example.project;



import static com.example.project.MainActivity.id;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.project.databinding.FragmentCreateBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateFragment extends Fragment { // Фрагмент создания очереди
    DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentCreateBinding binding =
                FragmentCreateBinding.inflate(inflater, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        binding.createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.queueName.getText().toString();
                String elements = binding.elements.getText().toString();
                Queue queue = new Queue(name, elements);
                DatabaseReference pushedRef = databaseReference.child("Queues").push();
                String pushId = pushedRef.getKey();
                queue.id = pushId;
                String userId = "User"+id;
                queue.adminId = userId;
                databaseReference.child("Queues").child(pushId).setValue(queue);
                databaseReference.child("Users").child("User"+id).push().setValue(pushId);
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });
        return binding.getRoot();
    }

}
