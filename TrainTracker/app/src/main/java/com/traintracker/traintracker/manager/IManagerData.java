package com.traintracker.traintracker.manager;

import com.jjoe64.graphview.series.DataPoint;

/**
 * Created by ayoub on 17-Aug-17.
 */

public interface IManagerData {

    void setConnection();
    void disconnectFromServer();
    DataPoint[] getData(String graph);
    String[] getLabels(String graph);
}
