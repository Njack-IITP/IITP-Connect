package com.iitp.njack.iitp_connect.core.calendar.list;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.iitp.njack.iitp_connect.R;
import com.iitp.njack.iitp_connect.core.calendar.list.viewholder.CodingCalendarViewHolder;
import com.iitp.njack.iitp_connect.data.contest.Contest;

import java.util.List;

public class CodingCalendarAdapter extends RecyclerView.Adapter<CodingCalendarViewHolder> {
    private List<Contest> contests;

    @NonNull
    @Override
    public CodingCalendarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new CodingCalendarViewHolder(
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.coding_calendar_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CodingCalendarViewHolder codingCalendarViewHolder, int position) {
        codingCalendarViewHolder.bind(contests.get(position));
    }

    @Override
    public int getItemCount() {
        return contests != null ? contests.size() : 0;
    }

    protected void setContests(final List<Contest> newContests) {
        if (contests == null) {
            contests = newContests;
            notifyItemRangeInserted(0, newContests.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return contests.size();
                }

                @Override
                public int getNewListSize() {
                    return newContests.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return contests.get(oldItemPosition).getId() ==
                            newContests.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return contests.get(oldItemPosition).equals(newContests.get(newItemPosition));
                }
            });
            contests = newContests;
            result.dispatchUpdatesTo(this);
        }
    }
}
