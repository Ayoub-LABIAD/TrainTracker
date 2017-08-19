package com.traintracker.traintracker.admin;

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
import com.traintracker.traintracker.data.user.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private ArrayList<User> mUsers;
    private Context mContext;
    private IEditUserAdminPresenter iEditUserAdminPresenter;

    public UsersAdapter(Context context, ArrayList<User> users, IEditUserAdminPresenter iEditUserAdminPresenter) {
        mUsers = users;
        mContext = context;
        this.iEditUserAdminPresenter = iEditUserAdminPresenter;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UsersAdapter.ViewHolder viewHolder, int position) {
        User user = mUsers.get(position);
        viewHolder.username.setText(user.getUsername());
        viewHolder.type.setText(user.getType());
        viewHolder.EditUser.setText("Edit");
        if (user.getType().equals("admins"))
            viewHolder.userColor.setBackgroundColor(Color.parseColor("#2196F3"));

        if (user.getType().equals("users"))
            viewHolder.userColor.setBackgroundColor(Color.parseColor("#9C27B0"));

        if (user.getType().equals("managers"))
            viewHolder.userColor.setBackgroundColor(Color.parseColor("#E91E63"));

        viewHolder.EditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iEditUserAdminPresenter.editUser(user, position);
            }
        });
        viewHolder.deleteUser.setText("Delete");
        viewHolder.deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iEditUserAdminPresenter.removeUser(user);
                Toast.makeText(getContext(), "User deleted.", Toast.LENGTH_SHORT).show();
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.username)
        TextView username;
        @BindView(R.id.user_type)
        TextView type;
        @BindView(R.id.edit_user)
        Button EditUser;
        @BindView(R.id.delete_user)
        Button deleteUser;
        @BindView(R.id.start_color_user)
        LinearLayout userColor;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
