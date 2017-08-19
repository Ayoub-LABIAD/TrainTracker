package com.traintracker.traintracker.manager;

import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;

/**
 * Created by ayoub on 17-Aug-17.
 */

public interface IManagerFragment {
    void setGraph(View view, GraphView graph, DataPoint[] data, String[] labels);
}
