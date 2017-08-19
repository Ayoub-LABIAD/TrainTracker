package com.traintracker.traintracker.admin.train;

import com.traintracker.traintracker.data.train.Train;

import java.util.ArrayList;

/**
 * Created by ayoub on 16-Aug-17.
 */

public interface IEditTrainAdminPresenter {
    ArrayList<Train> getAllTrains();

    void editTrain(Train train, int position);

    int modifyTrain(Train oldTrain, Train newTrain, int position);

    void removeTrain(String id);

    void addTrain(Train train);
}
