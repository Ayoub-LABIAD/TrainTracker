package com.traintracker.traintracker.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.traintracker.traintracker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterTrainsDialogFragment extends DialogFragment {

    private static IMapFragment iMapFragment;
    private static IMapFragmentPresenter iMapFragmentPresenter;
    @BindView(R.id.train_filter_departure)
    Spinner mDeparture;
    @BindView(R.id.train_filter_destination)
    Spinner mDestination;
    @BindView(R.id.train_filter_type)
    Spinner mType;
    @BindView(R.id.train_filter_name)
    Spinner mName;

    public FilterTrainsDialogFragment() {
    }

    public static FilterTrainsDialogFragment newInstance(IMapFragment iMapFrag) {
        FilterTrainsDialogFragment frag = new FilterTrainsDialogFragment();
        iMapFragment = iMapFrag;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_trains, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        iMapFragmentPresenter = new MapFragmentPresenter(iMapFragment);
        setSpinner(R.id.train_filter_name, iMapFragmentPresenter.setSpinner(1), view);
        setSpinner(R.id.train_filter_departure, iMapFragmentPresenter.setSpinner(2), view);
        setSpinner(R.id.train_filter_destination, iMapFragmentPresenter.setSpinner(3), view);
        setSpinner(R.id.train_filter_type, iMapFragmentPresenter.setSpinner(4), view);
    }

    public void setSpinner(int spinnerId, Set<String> stringSet, View view) {
        List trainSet = new ArrayList(stringSet);
        trainSet.add("All");
        Collections.sort(trainSet);
        Spinner spinner = (Spinner) view.findViewById(spinnerId);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, trainSet);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @OnClick(R.id.train_filter_filter)
    public void onFilterClick(View view) {

        String departure = mDeparture.getSelectedItem().toString();
        String destination = mDestination.getSelectedItem().toString();
        String type = mType.getSelectedItem().toString();
        String name = mName.getSelectedItem().toString();

        iMapFragmentPresenter.init();

        if (!name.equals("All"))
            iMapFragmentPresenter.onFilterClick(1, name);
        if (!departure.equals("All"))
            iMapFragmentPresenter.onFilterClick(2, departure);
        if (!destination.equals("All"))
            iMapFragmentPresenter.onFilterClick(3, destination);
        if (!type.equals("All"))
            iMapFragmentPresenter.onFilterClick(4, type);

        iMapFragmentPresenter.setTrainOnMap();
        dismiss();
    }

    @OnClick(R.id.train_filter_reset)
    public void onResetClick(View view){
        dismiss();
    }

}
