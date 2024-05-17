package com.example.yandexmaps;

import static com.example.yandexmaps.MainActivity.paths;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FragmentPaths extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    View view;

    LinearLayout list;

    public FragmentPaths() {
    }

    public static FragmentPaths newInstance(String param1, String param2) {
        FragmentPaths fragment = new FragmentPaths();
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

        view = inflater.inflate(R.layout.fragment_paths, container, false);

        list = view.findViewById(R.id.pathsList);
        Map.separate = false;
        Log.d("MAP", Map.separate + "");

        androidx.fragment.app.FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        for (String[] path : paths)
            transaction.add(list.getId(), new Route(path));

        transaction.addToBackStack(null);
        transaction.commit();

        return view;
    }
}