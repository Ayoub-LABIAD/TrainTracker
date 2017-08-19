package com.traintracker.traintracker.admin.train;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.traintracker.traintracker.R;
import com.traintracker.traintracker.data.train.Train;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ayoub on 16-Aug-17.
 */

public class TrainsAdapter extends RecyclerView.Adapter<TrainsAdapter.ViewHolder> {

    private ArrayList<Train> mTrains;
    private Context mContext;
    private IEditTrainAdminPresenter iEditTrainAdminPresenter;
    private IEditTrainAdminFragment iEditTrainAdminFragment;

    public TrainsAdapter(Context context, ArrayList<Train> trains,
                         IEditTrainAdminPresenter iEditTrainAdminPresenter, IEditTrainAdminFragment iEditTrainAdminFragment) {
        mTrains = trains;
        mContext = context;
        this.iEditTrainAdminPresenter = iEditTrainAdminPresenter;
        this.iEditTrainAdminFragment = iEditTrainAdminFragment;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public TrainsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View trainView = inflater.inflate(R.layout.item_train, parent, false);
        ViewHolder viewHolder = new ViewHolder(trainView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrainsAdapter.ViewHolder viewHolder, int position) {
        Train train = mTrains.get(position);
        viewHolder.name.setText(train.getName());
        viewHolder.depart.setText(train.getDeparture());
        viewHolder.dest.setText(train.getDestination());
        viewHolder.type.setText(train.getType());
        viewHolder.editTrain.setText("Edit");
        viewHolder.deleteTrain.setText("Delete");

        if(train.getType().equals("Type A"))
            viewHolder.color.setBackgroundColor(Color.parseColor("#2196F3"));

        if(train.getType().equals("Type B"))
            viewHolder.color.setBackgroundColor(Color.parseColor("#9C27B0"));

        if(train.getType().equals("Typce C"))
            viewHolder.color.setBackgroundColor(Color.parseColor("#E91E63"));

        if(train.getType().equals("Typce D"))
            viewHolder.color.setBackgroundColor(Color.parseColor("#009688"));

        viewHolder.editTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iEditTrainAdminPresenter.editTrain(train, position);
            }
        });

        viewHolder.deleteTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iEditTrainAdminPresenter.removeTrain(train.getId());
                iEditTrainAdminFragment.getTrains().remove(train);
                iEditTrainAdminFragment.getAdapter().notifyDataSetChanged();
                Toast.makeText(getContext(), "Train deleted successfully!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTrains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.train_name)
        TextView name;
        @BindView(R.id.train_departure)
        TextView depart;
        @BindView(R.id.train_destination)
        TextView dest;
        @BindView(R.id.train_type)
        TextView type;
        @BindView(R.id.edit_train)
        Button editTrain;
        @BindView(R.id.delete_train)
        Button deleteTrain;
        @BindView(R.id.start_color_train)
        LinearLayout color;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
