package com.traintracker.traintracker.manager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.traintracker.traintracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ayoub on 16-Aug-17.
 */

public class ManagerFragment extends Fragment implements IManagerFragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    IManagerPresenter iManagerPresenter;
    @BindView(R.id.graph)
    GraphView graph;
    @BindView(R.id.graph1)
    GraphView graph1;
    private int mPage;

    public static ManagerFragment newInstance(int page) {
        ManagerFragment fragment = new ManagerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager, container, false);
        ButterKnife.bind(this, view);
        iManagerPresenter = new ManagerPresenter(this);
        iManagerPresenter.setGraph(view, "graph1", graph);
        iManagerPresenter.setGraph(view, "graph2", graph1);
        return view;
    }

    public void setGraph(View view, GraphView graph, DataPoint[] data, String[] labels) {
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(labels);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data);
        graph.addSeries(series);
    }
}

