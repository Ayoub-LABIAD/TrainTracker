package com.traintracker.traintracker.admin.train;

import com.traintracker.traintracker.data.train.Train;
import com.traintracker.traintracker.data.train.TrainDataControl;

import java.util.ArrayList;

import static android.R.attr.name;

/**
 * Created by ayoub on 16-Aug-17.
 */

public class EditTrainAdminPresenter implements IEditTrainAdminPresenter {
    IEditTrainAdminFragment iEditTrainAdminFragment;
    ITrainAdmin iTrainAdmin;

    public EditTrainAdminPresenter(IEditTrainAdminFragment iEditTrainAdminFragment) {
        this.iEditTrainAdminFragment = iEditTrainAdminFragment;
        this.iTrainAdmin = new TrainDataControl();
    }

    public ArrayList<Train> getAllTrains() {
        iTrainAdmin.setConnection();
        ArrayList<Train> trains = iTrainAdmin.initAllTrains();
        iTrainAdmin.disconnectFromServer();
        return trains;
    }

    public void editTrain(Train train, int position) {
        iEditTrainAdminFragment.showEditDialog(train, position);
    }

    public void removeTrain(String id) {
        iTrainAdmin.setConnection();
        iTrainAdmin.removeTrain(id);
        iTrainAdmin.disconnectFromServer();
    }

    public int modifyTrain(Train oldTrain, Train newTrain, int position) {
        iTrainAdmin.setConnection();
        Train train = new Train();

        if (!newTrain.getId().equals(oldTrain.getId())) {
            if (!newTrain.getId().isEmpty()) {
                if (!iTrainAdmin.isExist(newTrain.getId())) {
                    train.setId(newTrain.getId());
                    if (!newTrain.getName().equals(name) && !newTrain.getName().isEmpty()) {
                        if (!newTrain.getName().isEmpty()) {
                            train.setName(newTrain.getName());
                        } else {
                            return -1;
                            // nameTrain.setError("This name is invalide.");
                        }
                    } else {
                        train.setName(oldTrain.getId());
                    }

                    if (!newTrain.getDeparture().equals(oldTrain.getDeparture())) {
                        if (!newTrain.getDeparture().isEmpty()) {
                            train.setDeparture(newTrain.getDeparture());
                        } else {
                            return -2;
                            // departureTrain.setError("This departure is invalide.");
                        }
                    } else {
                        train.setDeparture(oldTrain.getDeparture());
                    }

                    if (!newTrain.getDestination().equals(oldTrain.getDestination())) {
                        if (!newTrain.getDestination().isEmpty()) {
                            train.setDestination(newTrain.getDestination());
                        } else {
                            return -3;
                            // destinationTrain.setError("This destination is invalide.");
                        }
                    } else {
                        train.setDestination(oldTrain.getDestination());
                    }

                    if (!newTrain.getType().equals(oldTrain.getType())) {
                        if (!newTrain.getType().isEmpty()) {
                            train.setType(newTrain.getType());
                        } else {
                            return -4;
                            // typeTrain.setError("This type is invalide.");
                        }
                    } else {
                        train.setType(oldTrain.getType());
                    }
                    iTrainAdmin.removeTrain(oldTrain.getId());
                    iTrainAdmin.addTrain(train);
                    iTrainAdmin.disconnectFromServer();
                    iEditTrainAdminFragment.getTrains().set(position, train);
                    iEditTrainAdminFragment.getAdapter().notifyDataSetChanged();
                    return 1;


                } else {
                    return -5;
                    // idTrain.setError("This id exists already! Please choose another one.");
                }
            } else {
                return -6;
                // idTrain.setError("This id is invalide.");

            }
        } else {
            train.setId(oldTrain.getId());
            if (!newTrain.getName().equals(name) && !newTrain.getName().isEmpty()) {
                if (!newTrain.getName().isEmpty()) {
                    train.setName(newTrain.getName());
                } else {
                    return -1;
                    // nameTrain.setError("This name is invalide.");
                }
            } else {
                train.setName(oldTrain.getId());
            }

            if (!newTrain.getDeparture().equals(oldTrain.getDeparture())) {
                if (!newTrain.getDeparture().isEmpty()) {
                    train.setDeparture(newTrain.getDeparture());
                } else {

                    return -2;
                    //  departureTrain.setError("This departure is invalide.");
                }
            } else {
                train.setDeparture(oldTrain.getDeparture());
            }

            if (!newTrain.getDestination().equals(oldTrain.getDestination())) {
                if (!newTrain.getDestination().isEmpty()) {
                    train.setDestination(newTrain.getDestination());
                } else {
                    return -3;
                    // destinationTrain.setError("This destination is invalide.");
                }
            } else {
                train.setDestination(oldTrain.getDestination());
            }

            if (!newTrain.getType().equals(oldTrain.getType())) {
                if (!newTrain.getType().isEmpty()) {
                    train.setType(newTrain.getType());
                } else {

                    return -4;
                    // typeTrain.setError("This type is invalide.");
                }
            } else {
                train.setType(oldTrain.getType());
            }
            //TODO
            iTrainAdmin.removeTrain(oldTrain.getId());
            iTrainAdmin.addTrain(train);
            iTrainAdmin.disconnectFromServer();
            iEditTrainAdminFragment.getTrains().set(position, train);
            iEditTrainAdminFragment.getAdapter().notifyDataSetChanged();
            return 1;
        }

    }

    public void addTrain(Train train) {
        iTrainAdmin.setConnection();
        iTrainAdmin.addTrain(train);
        iTrainAdmin.disconnectFromServer();
    }
}
