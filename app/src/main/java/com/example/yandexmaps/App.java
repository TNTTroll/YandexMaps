package com.example.yandexmaps;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

public class App extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static Context context;
    Button sights, list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        context = this;

        Map.separate = true;

        sights = findViewById(R.id.appBtn1);
        sights.setOnClickListener(v -> getSupportFragmentManager().beginTransaction().replace(R.id.appFragment, new FragmentSights()).addToBackStack(null).commit());

        list = findViewById(R.id.appBtn2);
        list.setOnClickListener(v -> getSupportFragmentManager().beginTransaction().replace(R.id.appFragment, new FragmentPaths()).addToBackStack(null).commit());
    }
}