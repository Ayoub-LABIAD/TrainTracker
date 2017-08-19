package com.traintracker.traintracker.manager;

import android.view.View;

import com.jjoe64.graphview.GraphView;

/**
 * Created by ayoub on 17-Aug-17.
 */

public interface IManagerPresenter {
     void setGraph(View view, String graph, GraphView graphView);
}
