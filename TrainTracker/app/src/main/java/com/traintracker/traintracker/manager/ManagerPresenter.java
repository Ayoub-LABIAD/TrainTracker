package com.traintracker.traintracker.manager;

import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.traintracker.traintracker.data.train.TrainDataControl;

/**
 * Created by ayoub on 17-Aug-17.
 */

public class ManagerPresenter implements IManagerPresenter {

    IManagerFragment iManagerFragment;
    IManagerData iManagerData;

    public ManagerPresenter(IManagerFragment iManagerFragment) {
        this.iManagerFragment = iManagerFragment;
        iManagerData = new TrainDataControl();
    }

    public void setGraph(View view, String graph, GraphView graphView) {
        iManagerData.setConnection();
        DataPoint[] data = iManagerData.getData(graph);
        String[] labels = iManagerData.getLabels(graph);
        iManagerData.disconnectFromServer();
        iManagerFragment.setGraph(view, graphView, data, labels);
    }
}
