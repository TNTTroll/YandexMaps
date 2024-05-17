package com.example.yandexmaps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.PlacemarkMapObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String API_KEY = "c5b6f0f4-75cc-458a-add4-4cb3ec0084a8";

    String needName = "Android";
    String needLast = "Studio";
    String needEmail = "123@mail.ru";

    EditText firstName, lastName, email;
    Button loginBtn;

    public static ArrayList<Place> places = new ArrayList<>();
    public static ArrayList<String[]> paths = new ArrayList<>();

    public static ArrayList<MapObject> objects = new ArrayList<>();

    public static InputListener inputListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(API_KEY);
        setContentView(R.layout.activity_main);

        readPlaces();
        readPaths();

        startActivity( new Intent(this, App.class) );

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(v -> {
            firstName = findViewById(R.id.loginFirstName);
            lastName = findViewById(R.id.loginLastName);
            email = findViewById(R.id.loginEmail);

            if (firstName.getText().length() == 0 || lastName.getText().length() == 0 || email.getText().length() == 0 )
                Toast.makeText(getApplicationContext(), R.string.errorEmpty, Toast.LENGTH_SHORT).show();
            else if (!firstName.getText().toString().equals(needName))
                Toast.makeText(getApplicationContext(), R.string.errorFirstName, Toast.LENGTH_SHORT).show();
            else if (!lastName.getText().toString().equals(needLast))
                Toast.makeText(getApplicationContext(), R.string.errorLastName, Toast.LENGTH_SHORT).show();
            else if (!email.getText().toString().equals(needEmail))
                Toast.makeText(getApplicationContext(), R.string.errorEmail, Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(getApplicationContext(), "Добро пожаловать, " + firstName.getText(), Toast.LENGTH_LONG).show();
                startActivity( new Intent(this, App.class) );
            }
        });
    }

    private void readPlaces() {
        String text = "";
        try {
            InputStream file = this.getAssets().open("Places.txt");
            byte[] buffer = new byte[file.available()];

            file.read(buffer);

            text = new String(buffer);
            String[] lines = text.split("&");

            for (int i = 0; i < lines.length; i+=4) {
                double latitude = Math.round( Float.parseFloat(lines[i]) * 1000000.0 ) / 1000000.0;
                double longtitude = Math.round( Float.parseFloat(lines[i+1]) * 1000000.0 ) / 1000000.0;
                places.add( new Place(lines[i + 3], lines[i + 2], new Point(latitude, longtitude)) );
            }

        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    private void readPaths() {
        String text = "";
        try {
            InputStream file = this.getAssets().open("Paths.txt");
            byte[] buffer = new byte[file.available()];

            file.read(buffer);

            text = new String(buffer);
            String[] lines = text.split("&");

            for (String line : lines) {
                String[] picks = line.split(" ");
                paths.add(picks);
            }

        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }
}