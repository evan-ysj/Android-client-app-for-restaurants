package com.example.myapplication.ui.reservation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.db.entity.ReserveHistoryEntity;
import com.example.myapplication.model.ReserveHistory;

import java.util.List;

public class ReserveAdapter extends RecyclerView.Adapter<ReserveAdapter.ReserveViewHolder> {

    public static class ReserveViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ReserveViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.recycler_item);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    private List<ReserveHistoryEntity> mReservations;

    public ReserveAdapter() {
        setHasStableIds(true);
    }

    public void setmReservations(final List<ReserveHistoryEntity> reservations) {
        if(mReservations == null) {
            mReservations = reservations;
            notifyItemRangeInserted(0, reservations.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mReservations.size();
                }

                @Override
                public int getNewListSize() {
                    return reservations.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mReservations.get(oldItemPosition).getRsv_number() ==
                            reservations.get(newItemPosition).getRsv_number();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    ReserveHistory oldReservation = mReservations.get(oldItemPosition);
                    ReserveHistory newReservation = reservations.get(newItemPosition);
                    return oldReservation.getRsv_number() == newReservation.getRsv_number() &&
                            oldReservation.getNo_of_guests() == newReservation.getNo_of_guests() &&
                            oldReservation.getExpired() == newReservation.getExpired() &&
                            TextUtils.equals(oldReservation.getDate().toString(), newReservation.getDate().toString());
                }
            });
            mReservations = reservations;
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    @NonNull
    public ReserveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_row_item, parent, false);
        return new ReserveViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReserveViewHolder holder, int position) {
        holder.getTextView().setText(mReservations.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mReservations == null ? 0 : mReservations.size();
    }

    @Override
    public long getItemId(int position) {
        return mReservations.get(position).getRsv_number();
    }
}
