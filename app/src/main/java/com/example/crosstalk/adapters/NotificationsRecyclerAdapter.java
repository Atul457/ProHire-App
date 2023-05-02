package com.example.crosstalk.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crosstalk.R;
import com.example.crosstalk.fragments.homeActivity.NotificationsFragment;
import com.example.crosstalk.models.NotificationServiceModel;

import java.util.List;

public class NotificationsRecyclerAdapter extends RecyclerView.Adapter<NotificationsRecyclerAdapter.NotificationsJobRecyclerViewHolder> {

    NotificationsFragment notificationsFragment;
    private FragmentManager fragmentManager;
    private List<NotificationServiceModel.NotificationModel> notifications;

    public void setNotifications(List<NotificationServiceModel.NotificationModel> notifications, FragmentManager fragmentManager) {
        this.notifications = notifications;
        this.fragmentManager = fragmentManager;
        notificationsFragment = new NotificationsFragment();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationsJobRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_notification, parent, false);
        return new NotificationsJobRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsJobRecyclerViewHolder holder, int position) {

        NotificationServiceModel.NotificationModel user = notifications.get(position);
        String message = user.getMessage();
        holder.message.setText(message.trim());

    }

    @Override
    public int getItemCount() {
        if (this.notifications != null) return this.notifications.size();
        else return 0;
    }

    public class NotificationsJobRecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView message;

        public NotificationsJobRecyclerViewHolder(@NonNull View itemView) {

            super(itemView);

            message = itemView.findViewById(R.id.message);

        }

    }

}
