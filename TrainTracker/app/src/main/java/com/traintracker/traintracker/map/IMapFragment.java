package com.traintracker.traintracker.map;

import com.traintracker.traintracker.data.train.Train;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by ayoub on 17-Aug-17.
 */

public interface IMapFragment {
    void setMap(HashSet<Train> trains);

    void setMap(ArrayList<Train> trains);

}
