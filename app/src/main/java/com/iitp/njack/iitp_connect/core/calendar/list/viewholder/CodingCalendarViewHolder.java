package com.iitp.njack.iitp_connect.core.calendar.list.viewholder;

import android.support.v7.widget.RecyclerView;

import com.iitp.njack.iitp_connect.common.Pipe;
import com.iitp.njack.iitp_connect.data.contest.Contest;
import com.iitp.njack.iitp_connect.databinding.CodingCalendarItemBinding;

public class CodingCalendarViewHolder extends RecyclerView.ViewHolder {
    private final CodingCalendarItemBinding binding;
    private Contest contest;
    private Pipe<Long> clickAction;

    public CodingCalendarViewHolder(CodingCalendarItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;

        binding.getRoot().setOnClickListener(view -> {
            if (clickAction != null) {
                clickAction.push(contest.getId());
            }
        });
    }

    public void bind(Contest contest) {
        this.contest = contest;
        binding.setContest(contest);
        binding.executePendingBindings();
    }

    public void setClickAction(Pipe<Long> clickAction) {
        this.clickAction = clickAction;
    }
}
