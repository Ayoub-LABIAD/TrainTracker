package com.traintracker.traintracker.admin.train;

import android.content.DialogInterface;
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

public class EditTrainAdminDialogFragment extends DialogFragment {

    private static IEditTrainAdminFragment iEditTrainAdminFragment;
    @BindView(R.id.edit_train_id)
    EditText id;
    @BindView(R.id.edit_train_name)
    EditText name;
    @BindView(R.id.edit_train_departure)
    EditText departure;
    @BindView(R.id.edit_train_destination)
    EditText destination;
    @BindView(R.id.edit_train_type)
    EditText type;
    private DialogInterface.OnDismissListener onDismissListener;
    private Train oldTrain, newTrain;
    private int position;

    public EditTrainAdminDialogFragment() {
    }

    public static EditTrainAdminDialogFragment newInstance(IEditTrainAdminFragment iEditTrainFragment) {
        EditTrainAdminDialogFragment frag = new EditTrainAdminDialogFragment();
        iEditTrainAdminFragment = iEditTrainFragment;
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmnet_edit_train, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Bundle mArgs = getArguments();
        position = mArgs.getInt("position");
        String mId = mArgs.getString("id");
        String mName = mArgs.getString("name");
        String mDeparture = mArgs.getString("departure");
        String mDestination = mArgs.getString("destination");
        String mType = mArgs.getString("type");
        oldTrain = new Train(mId, mName, mDeparture, mDestination, mType);
        id.setText(mId);
        name.setText(mName);
        departure.setText(mDeparture);
        destination.setText(mDestination);
        type.setText(mType);
    }

    @OnClick(R.id.cancel_train)
    public void OnCancel() {
        dismiss();
    }

    @OnClick(R.id.save_train)
    public void onSaveClick() {
        newTrain = new Train(id.getText().toString(), name.getText().toString(), departure.getText().toString(),
                destination.getText().toString(), type.getText().toString());
        IEditTrainAdminPresenter iEditTrainAdminPresenter = new EditTrainAdminPresenter(iEditTrainAdminFragment);
        int i = iEditTrainAdminPresenter.modifyTrain(oldTrain, newTrain, position);

        switch (i) {
            case -1:
                name.setError("This name is invalide.");
                break;
            case -2:
                departure.setError("This departure is invalide.");
                break;
            case -3:
                destination.setError("This destination is invalide.");
                break;
            case -4:
                type.setError("This type is invalide.");
                break;
            case -5:
                id.setError("This id exists already! Please choose another one.");
                break;
            case -6:
                id.setError("This id is invalide.");
                break;
            case 1:
                Toast.makeText(getContext(), "Train updated successfuly!", Toast.LENGTH_LONG).show();
                dismiss();
                break;
        }


    }

}
