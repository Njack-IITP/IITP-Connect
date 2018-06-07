package com.iitp.njack.iitp_connect.core.calendar.list.viewholder;

import android.support.v7.widget.RecyclerView;

import com.iitp.njack.iitp_connect.data.contest.Contest;
import com.iitp.njack.iitp_connect.databinding.CodingCalendarItemBinding;

public class CodingCalendarViewHolder extends RecyclerView.ViewHolder {
    private final CodingCalendarItemBinding binding;

    public CodingCalendarViewHolder(CodingCalendarItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Contest contest) {
        binding.setContest(contest);
    }
}
