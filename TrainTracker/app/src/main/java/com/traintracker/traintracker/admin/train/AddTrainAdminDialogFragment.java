package com.traintracker.traintracker.admin.train;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.traintracker.traintracker.R;
import com.traintracker.traintracker.data.train.Train;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTrainAdminDialogFragment extends DialogFragment {

    private static IEditTrainAdminFragment iEditTrainAdminFragment;
    @BindView(R.id.add_train_id)
    EditText id;
    @BindView(R.id.add_train_name)
    EditText name;
    @BindView(R.id.add_train_departure)
    EditText departure;
    @BindView(R.id.add_train_destination)
    EditText destination;
    @BindView(R.id.add_train_type)
    EditText type;

    private String mId, mName, mDeparture, mDestination, mType;
    private Train train;

    public AddTrainAdminDialogFragment() {
    }

    public static AddTrainAdminDialogFragment newInstance(IEditTrainAdminFragment iEditTrainFragment) {
        AddTrainAdminDialogFragment frag = new AddTrainAdminDialogFragment();
        iEditTrainAdminFragment = iEditTrainFragment;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_train, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.cancel_train_add)
    public void OnCancel() {
        dismiss();
    }

    @OnClick(R.id.save_train_add)
    public void onSaveClick() {
        mId = id.getText().toString();
        mName = name.getText().toString();
        mDeparture = departure.getText().toString();
        mDestination = destination.getText().toString();
        mType = type.getText().toString();
        train = new Train(mId, mName, mDeparture, mDestination, mType);
        IEditTrainAdminPresenter iEditTrainAdminPresenter = new EditTrainAdminPresenter(iEditTrainAdminFragment);
        iEditTrainAdminPresenter.addTrain(train);
        iEditTrainAdminFragment.getTrains().add(train);
        iEditTrainAdminFragment.getAdapter().notifyDataSetChanged();
        dismiss();
    }

}
