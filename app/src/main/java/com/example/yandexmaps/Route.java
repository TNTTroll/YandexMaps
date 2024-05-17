package com.example.yandexmaps;

import static com.example.yandexmaps.MainActivity.places;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yandex.mapkit.geometry.Point;

import java.util.Arrays;

public class Route extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static String[] picks;

    private String mParam1;
    private String mParam2;

    View view;
    String[] innerPicks;

    public Route(String[] _picks) {
        innerPicks = _picks;
    }

    public static Route newInstance(String param1, String param2) {
        Route fragment = new Route(picks);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_route, container, false);

        TextView name = view.findViewById(R.id.routeName);
        name.setText( innerPicks[0] );

        Button btn = view.findViewById(R.id.routeBtn);
        btn.setOnClickListener(v -> {
            Point[] points = new Point[innerPicks.length-1];
            for (int i = 1; i < innerPicks.length; i++)
                points[i-1] = places.get( Integer.parseInt(innerPicks[i])-1 ).getPoint();

            Map.points.addAll(Arrays.asList(points));

            startActivity( new Intent(App.context, Map.class) );
        });

        return view;
    }
}