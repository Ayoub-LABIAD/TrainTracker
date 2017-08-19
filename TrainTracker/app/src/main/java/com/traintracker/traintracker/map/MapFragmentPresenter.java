package com.traintracker.traintracker.map;

import com.traintracker.traintracker.data.train.Train;
import com.traintracker.traintracker.data.train.TrainDataControl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ayoub on 17-Aug-17.
 */

public class MapFragmentPresenter implements IMapFragmentPresenter {

    private IMapFragment iMapFragment;
    private ITrainManager iTrainManager;
    private ArrayList<Train> trains;
    private HashSet<Train> filteredTrains;

    public MapFragmentPresenter(IMapFragment iMapFragment) {
        this.iMapFragment = iMapFragment;
        iTrainManager = new TrainDataControl();
        iTrainManager.setConnection();
        trains = iTrainManager.initAllTrains();
        iTrainManager.disconnectFromServer();
        iMapFragment.setMap(trains);
    }

    public void init(){
        filteredTrains = new HashSet<>(trains);
    }

    public ArrayList<Train> getTrains() {
        return trains;
    }

    public void setTrains(ArrayList<Train> trains) {
        this.trains = trains;
    }

    public Set<String> setSpinner(int index) {
        return iTrainManager.getFields(trains, index);
    }

    public HashSet<Train> getFilteredTrains() {
        return filteredTrains;
    }

    public void onFilterClick(int index, String selectedItem) {
        if (index == 1) {

            HashSet<Train> TMP = new HashSet<>();
            for (Train tmp : trains) {
                if (tmp.getName().equals(selectedItem))
                    TMP.add(tmp);
            }
            filteredTrains.clear();
            filteredTrains.addAll(TMP);

        } else {

            if (index == 2) {
                HashSet<Train> TMP = new HashSet<>();
                for (Train tmp : trains) {
                    if (tmp.getDeparture().equals(selectedItem))
                        TMP.add(tmp);
                }
                filteredTrains.retainAll(TMP);
            }

            if (index == 3) {
                HashSet<Train> TMP = new HashSet<>();
                for (Train tmp : trains) {
                    if (tmp.getDestination().equals(selectedItem))
                        TMP.add(tmp);
                }
                filteredTrains.retainAll(TMP);
            }

            if (index == 4) {
                HashSet<Train> TMP = new HashSet<>();
                for (Train tmp : trains) {
                    if (tmp.getType().equals(selectedItem))
                        TMP.add(tmp);
                }
                filteredTrains.retainAll(TMP);
            }

        }
    }

    public void setTrainOnMap() {
        iMapFragment.setMap(filteredTrains);
    }

    public String getNames(HashSet<Train> filteredTrains) {
        StringBuilder sb = new StringBuilder();
        for (Train tmp : filteredTrains) {
            sb.append(tmp.getName()).append(" ");
        }
        return sb.toString();
    }
}
