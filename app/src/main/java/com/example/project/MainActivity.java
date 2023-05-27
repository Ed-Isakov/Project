package com.example.project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.databinding.ActivityMainBinding;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    static String id;
    public static final String APP_PREFERENCES = "mysettings";
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences mySharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (mySharedPreferences.contains("id")){
            id = mySharedPreferences.getString("id", "null");
        }else{
            id = UUID.randomUUID().toString();
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            editor.putString("id", id);
            editor.apply();
        }
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}