package com.traintracker.traintracker.admin.train;

import com.traintracker.traintracker.data.train.Train;

import java.util.ArrayList;

/**
 * Created by ayoub on 16-Aug-17.
 */

public interface ITrainAdmin {

    Train getTrain(String id);

    ArrayList<Train> initAllTrains();

    void disconnectFromServer();

    void setConnection();

    boolean isExist(String id);

    void addTrain(Train train);

    void removeTrain(String id);
}
