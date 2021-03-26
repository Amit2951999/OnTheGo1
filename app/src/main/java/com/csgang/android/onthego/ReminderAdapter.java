package com.csgang.android.onthego;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class ReminderAdapter  extends RecyclerView.Adapter<ReminderAdapter.MyViewHolder> {

    public static TextView txtLocation, txtRange, txtMessage;
    private List<Reminder> reminderList;
    RecyclerView recyclerView;
    int lastPosition = -1;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtReminderId, txtLocation, txtRange, txtMessage, txtStatus;
        public Button btnEdit, btnDelete;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            txtReminderId = (TextView) view.findViewById(R.id.txtReminderId);
            txtLocation = (TextView) view.findViewById(R.id.txtLocation);
            txtRange = (TextView) view.findViewById(R.id.txtRange);
            txtMessage = (TextView) view.findViewById(R.id.txtMessage);
            txtStatus = (TextView) view.findViewById(R.id.txtStatus);
            btnEdit = (Button) view.findViewById(R.id.btnEdit);
            btnDelete = (Button) view.findViewById(R.id.btnDelete);
        }
    }

    public ReminderAdapter(List<Reminder> reminderList) {
        this.reminderList = reminderList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminderview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Reminder reminder = reminderList.get(position);
        holder.txtReminderId.setText(reminder.reminderid);
        holder.txtLocation.setText("Location : " + reminder.location);
        holder.txtRange.setText("Range : " + reminder.range + " meters");
        holder.txtMessage.setText("Message : " + reminder.message);
        holder.txtStatus.setText("Reminded : " + reminder.reminded);
        holder.btnEdit.setTag(position);
        holder.btnDelete.setTag(position);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddReminderActivity.reminderid = reminder.reminderid;
               ReminderAdapter.txtLocation = holder.txtLocation;
                ReminderAdapter.txtRange = holder.txtRange;
                ReminderAdapter.txtMessage = holder.txtMessage;
                Intent intent = new Intent(context, AddReminderActivity.class);
                context.startActivity(intent);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder customBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.AppTheme));
                customBuilder.setTitle("Are you sure to delete?");
                customBuilder.setIcon(R.drawable.onthego);
                customBuilder.setNegativeButton("Yes, Delete", new  DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String query = "DELETE FROM reminders WHERE reminderid = " + holder.txtReminderId.getText().toString();
                        DBClass.execNonQuery(query);
                        removeAt(holder.getPosition());
                    }
                });
                customBuilder.setPositiveButton("No, Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = customBuilder.create();
                dialog.show();
            }
        });
        if(reminder.reminded.equals("Yes"))
        {
            holder.btnEdit.setVisibility(View.INVISIBLE);
        }
    }

    public void removeAt(int position) {
        reminderList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, reminderList.size());
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }
}