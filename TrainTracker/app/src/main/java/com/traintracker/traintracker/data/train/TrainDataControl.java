package com.traintracker.traintracker.data.train;

import com.jjoe64.graphview.series.DataPoint;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.traintracker.traintracker.admin.train.ITrainAdmin;
import com.traintracker.traintracker.manager.IManagerData;
import com.traintracker.traintracker.map.ITrainManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TrainDataControl implements ITrainAdmin, ITrainManager, IManagerData {
    private final String SET = "trains";
    private final String HOST = "192.168.201.1";
    private final String PORT = "6379";
    private final String TRAIN_DB = "1";
    private final String ID = "id";
    private final String NAME = "name";
    private final String DEPART = "departure";
    private final String DEST = "destination";
    private final String TYPE = "type";

    private RedisClient redisClient;
    private StatefulRedisConnection<String, String> connection;
    private RedisCommands<String, String> syncCommands;

    public void setConnection() {
        StringBuilder sb = new StringBuilder();
        sb.append("redis://").append(HOST).append(":").append(PORT).append("/").append(TRAIN_DB);
        redisClient = RedisClient.create(sb.toString());
        connection = redisClient.connect();
        syncCommands = connection.sync();
    }

    public void disconnectFromServer() {
        connection.close();
    }


    public boolean isExist(String id) {
        if (syncCommands.sismember(SET, id))
            return true;
        return false;
    }

    public Train getTrain(String id) {
        Train train = new Train(id, syncCommands.hget(id, NAME), syncCommands.hget(id, DEPART),
                syncCommands.hget(id, DEST), syncCommands.hget(id, TYPE));
        return train;
    }

    public ArrayList<Train> initAllTrains() {
        ArrayList<Train> allTrains = new ArrayList<>();
        Set<String> trains = syncCommands.smembers(SET);
        for (String train : trains) {
            allTrains.add(getTrain(train));
        }
        Collections.sort(allTrains,ALPHA_ORDER);
        return allTrains;
    }

    private static Comparator<Train> ALPHA_ORDER = new Comparator<Train>() {
        public int compare(Train train1, Train train2) {
            int x = String.CASE_INSENSITIVE_ORDER.compare(train1.getName(), train2.getName());
            if (x== 0) {
                x= train1.getName().compareTo(train2.getName());
            }
            return x;
        }
    };



    public Set<String> getFields(ArrayList<Train> trains, int index) {
        Set<String> trainsByField = new HashSet<>();
        for (Train tmp : trains) {
            if (index == 1)
                trainsByField.add(tmp.getName());
            if (index == 2)
                trainsByField.add(tmp.getDeparture());
            if (index == 3)
                trainsByField.add(tmp.getDestination());
            if (index == 4)
                trainsByField.add(tmp.getType());
        }

        return trainsByField;
    }


    public DataPoint[] getData(String graph) {

        List<String> list = syncCommands.hvals(graph);
        DataPoint[] data = new DataPoint[list.size()];
        int i = 0;
        for (String tmp : list) {
            data[i] = new DataPoint(i, Integer.parseInt(list.get(i)));
            i++;
        }
        return data;
    }

    public String[] getLabels(String graph) {
        List<String> list;
        list = syncCommands.hkeys(graph);
        String[] labels = new String[list.size()];
        int i = 0;
        for (String tmp : list) {
            labels[i] = list.get(i);
            i++;
        }
        return labels;
    }

    public void addTrain(Train train) {
        syncCommands.sadd(SET, train.getId());
        syncCommands.hset(train.getId(), NAME, train.getName());
        syncCommands.hset(train.getId(), DEPART, train.getDeparture());
        syncCommands.hset(train.getId(), DEST, train.getDestination());
        syncCommands.hset(train.getId(), TYPE, train.getType());
    }

    public void removeTrain(String id) {
        syncCommands.srem(SET, id);
        syncCommands.del(id);
    }
}
