package com.example.yandexmaps;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class Plate extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static Place place;

    private String mParam1;
    private String mParam2;

    View view;
    Place innerPlace;

    boolean choose = false;

    public Plate(Place _place) {
        innerPlace = _place;
    }

    public static Plate newInstance(String param1, String param2) {
        Plate fragment = new Plate(place);
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

        view = inflater.inflate(R.layout.fragment_plate, container, false);

        TextView name = view.findViewById(R.id.plateName);
        name.setText( innerPlace.getName() );

        TextView coords = view.findViewById(R.id.plateCoords);
        coords.setText(innerPlace.getPoint().getLatitude() + " " + innerPlace.getPoint().getLongitude());

        CheckBox check = view.findViewById(R.id.plateCheck);
        check.setEnabled(false);

        Button btn = view.findViewById(R.id.plateBtn);
        btn.setOnClickListener(v -> {
            choose = !choose;

            check.setChecked(choose);
        });

        return view;
    }
}