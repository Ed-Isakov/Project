package com.example.project;

import static com.example.project.MainActivity.id;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.project.databinding.FragmentHeadBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;


public class HeadFragment extends Fragment { // головной фрагмент
    ArrayList <String> ids;
    ArrayList <Queue> elements;
    HeadAdapter adapter;
    DatabaseReference userReference;
    DatabaseReference queueReference;
    FragmentHeadBinding binding;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        binding =
                FragmentHeadBinding
                        .inflate(inflater, container, false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        userReference = FirebaseDatabase.getInstance().getReference("Users").child("User"+id);
        queueReference = FirebaseDatabase.getInstance().getReference("Queues");
        ids = new ArrayList<>();
        elements = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!ids.isEmpty()) ids.clear();
                for (DataSnapshot e: snapshot.getChildren()){
                    String queue = e.getValue(String.class);
                    assert queue!=null;
                    ids.add(queue);
                }
                queueReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!elements.isEmpty()) elements.clear();
                        for (DataSnapshot e: snapshot.getChildren()){
                            if (ids.contains(e.getKey())){
                                elements.add(e.getValue(Queue.class));
                            }
                        }
                        adapter = new HeadAdapter(elements);
                        binding.recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(requireContext(), "Нет доступа к базе данных", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        userReference.addValueEventListener(valueEventListener);
        binding.buttonDownloadQueue.setOnClickListener(v -> {
            scanCode();
        });
        binding.buttonNewQueue.setOnClickListener(v -> Navigation
                .findNavController(binding.getRoot()).navigate(R.id.action_headFragment_to_createFragment));
        return binding.getRoot();
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }
    ActivityResultLauncher <ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents()!=null && !ids.contains(result.getContents())){
            userReference.push().setValue(result.getContents());
        }
    });
}
