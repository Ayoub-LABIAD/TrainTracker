package com.traintracker.traintracker.admin.train;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.traintracker.traintracker.R;
import com.traintracker.traintracker.data.train.Train;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditTrainAdminFragment extends Fragment implements IEditTrainAdminFragment {
    @BindView(R.id.add_train)
    Button addTrain;
    @BindView(R.id.remove_all_trains)
    Button remove_all;
    private ArrayList<Train> trains;
    private View view;
    private IEditTrainAdminPresenter iEditTrainAdminPresenter;
    private TrainsAdapter adapter;
    private RecyclerView rvTrains;

    public static EditTrainAdminFragment newInstance() {
        EditTrainAdminFragment fragment = new EditTrainAdminFragment();
        return fragment;
    }

    public TrainsAdapter getAdapter() {
        return adapter;
    }

    public ArrayList<Train> getTrains() {
        return trains;
    }

    public void setTrains(ArrayList<Train> trains) {
        this.trains = trains;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_train_admin, parent, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        addTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog();
            }
        });

        remove_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"TODO",Toast.LENGTH_LONG).show();
            }
        });
        iEditTrainAdminPresenter = new EditTrainAdminPresenter(this);
        rvTrains = (RecyclerView) view.findViewById(R.id.rvTrains);
        trains = iEditTrainAdminPresenter.getAllTrains();
        adapter = new TrainsAdapter(getContext(), trains, iEditTrainAdminPresenter, this);
        rvTrains.setAdapter(adapter);
        rvTrains.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void showEditDialog(Train train, int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("id", train.getId());
        args.putString("name", train.getName());
        args.putString("departure", train.getDeparture());
        args.putString("destination", train.getDestination());
        args.putString("type", train.getType());
        FragmentManager fm = getFragmentManager();
        EditTrainAdminDialogFragment editNameDialogFragment = EditTrainAdminDialogFragment.newInstance(this);
        editNameDialogFragment.setArguments(args);
        editNameDialogFragment.show(fm, "fragment_edit_train");
    }

    public void showEditDialog() {
        FragmentManager fm = getFragmentManager();
        AddTrainAdminDialogFragment addTrainAdminDialogFragment = AddTrainAdminDialogFragment.newInstance(this);
        addTrainAdminDialogFragment.show(fm, "fragment_add_train");
    }
}
