package com.example.solom.finalproject020618.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.solom.finalproject020618.MainActivity;
import com.example.solom.finalproject020618.R;
import com.example.solom.finalproject020618.db.Place;
import com.example.solom.finalproject020618.network.MyServiceSearchPlaces;
import com.example.solom.finalproject020618.network.OnLocationClick;

import java.util.ArrayList;

public class List_Fragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<Place> mPlaceArrayList;
    private RV_Adapter mAdapter;
    private ProgressBar mProgressBar;

    private View v;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.list_fragment, container, false);
        mPlaceArrayList = new ArrayList<Place>();
        mRecyclerView = v.findViewById(R.id.recyclerView);


        return v;
    }

    public void setRecyclerView(ArrayList<Place> places) {
        mAdapter = new RV_Adapter(places, getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar=v.findViewById(R.id.progress_loading_recycler);
        mProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void onStart() {
        super.onStart();



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }
}
