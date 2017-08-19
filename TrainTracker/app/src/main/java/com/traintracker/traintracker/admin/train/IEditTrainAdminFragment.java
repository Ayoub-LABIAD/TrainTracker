package com.traintracker.traintracker.admin.train;

import com.traintracker.traintracker.data.train.Train;

import java.util.ArrayList;

/**
 * Created by ayoub on 16-Aug-17.
 */

public interface IEditTrainAdminFragment {

    void showEditDialog(Train train, int position);

    ArrayList<Train> getTrains();

    TrainsAdapter getAdapter();
}
