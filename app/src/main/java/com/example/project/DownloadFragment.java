package com.example.project;

import static com.example.project.MainActivity.id;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.project.databinding.FragmentDownloadBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DownloadFragment extends Fragment {
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference;
    FragmentDownloadBinding binding;
    String newId;
    String queueName;
    Queue queue=null;
    ValueEventListener valueEventListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =
                FragmentDownloadBinding
                        .inflate(inflater, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("User"+id);
        binding.buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newId = binding.inputId.getText().toString();
                queueName = binding.inputName.getText().toString();
                databaseReference1 = FirebaseDatabase.getInstance().getReference("User"+newId);
                get_date();
                if (queue==null){
                    Toast toast = Toast.makeText(requireContext(), "Некорекктные данные", Toast.LENGTH_SHORT);
                    Log.d("WWWWWWWW", "NOOOOOO");
                }else{
                    databaseReference.push().setValue(queue);
                    Navigation.findNavController(binding.getRoot()).popBackStack();
                }
                //databaseReference1.removeEventListener(valueEventListener);
            }
        });
        return binding.getRoot();
    }
    private void get_date(){
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot e:snapshot.getChildren()){
                    Queue q = e.getValue(Queue.class);
                    assert q!=null;
                    if (q.getQueueName().equals(queueName)){
                        queue = q;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference1.addValueEventListener(valueEventListener);
    }
}
