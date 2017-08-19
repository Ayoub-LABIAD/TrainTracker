package com.traintracker.traintracker.map;

import com.traintracker.traintracker.data.train.Train;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by ayoub on 17-Aug-17.
 */

public interface ITrainManager {
    ArrayList<Train> initAllTrains();

    void setConnection();

    void disconnectFromServer();

    Set<String> getFields(ArrayList<Train> trains, int index);

}
