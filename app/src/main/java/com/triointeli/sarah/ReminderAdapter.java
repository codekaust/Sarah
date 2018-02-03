package com.triointeli.sarah;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ritik on 03-02-2018.
 */

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private ArrayList<Remainder> remainders;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView remainder;
        TextView dateTime;
        CheckBox mCheckbox;

        public ViewHolder(View view) {
            super(view);
            remainder = (TextView) view.findViewById(R.id.reminder);
            dateTime = (TextView) view.findViewById(R.id.dateTime);
            mCheckbox = (CheckBox) view.findViewById(R.id.checkbox);
        }
    }

    public ReminderAdapter(ArrayList<Remainder> remainders) {
        this.remainders = remainders;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Remainder remainder = remainders.get(position);

        holder.remainder.setText(remainder.getRemainder());
        holder.dateTime.setText(remainder.getDateTime());
        holder.mCheckbox.setChecked(remainder.isDone());


    }

    @Override
    public int getItemCount() {
        return remainders.size();
    }


}
