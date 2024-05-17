package com.example.yandexmaps;

import static com.example.yandexmaps.MainActivity.places;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class FragmentSights extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    LinearLayout list;
    ArrayList<Plate> plates = new ArrayList<>();

    public FragmentSights() {
    }

    public static FragmentSights newInstance(String param1, String param2) {
        FragmentSights fragment = new FragmentSights();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_sights, container, false);

        Button map = view.findViewById(R.id.sightsBtn);
        map.setOnClickListener(v -> {
            for (Plate plate : plates)
                if (plate.choose)
                    Map.points.add( plate.innerPlace.getPoint() );

            startActivity( new Intent(App.context, Map.class) );
        } );

        list = view.findViewById(R.id.sightsList);
        Map.separate = true;

        androidx.fragment.app.FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        for (int i = 0; i < places.size(); i++) {
            Plate plate = new Plate(places.get(i));
            transaction.add(list.getId(), plate);
            plates.add(plate);
        }

        transaction.addToBackStack(null);
        transaction.commit();

        return view;
    }
}