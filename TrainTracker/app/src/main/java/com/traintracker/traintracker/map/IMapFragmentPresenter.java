package com.traintracker.traintracker.map;

import com.traintracker.traintracker.data.train.Train;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ayoub on 17-Aug-17.
 */

public interface IMapFragmentPresenter {
    public Set<String> setSpinner(int index);

    ArrayList<Train> getTrains();

    void onFilterClick(int index, String selectedItem);

    HashSet<Train> getFilteredTrains();

    String getNames(HashSet<Train> filteredTrains);

    void setTrainOnMap();

    void init();
}
