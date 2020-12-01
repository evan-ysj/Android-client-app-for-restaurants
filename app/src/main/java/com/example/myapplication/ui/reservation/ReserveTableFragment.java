package com.example.myapplication.ui.reservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;

public class ReserveTableFragment extends Fragment {

    private ReservationViewModel reservationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reservationViewModel =
                new ViewModelProvider(this).get(ReservationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reserve_table, container, false);
        final TextView textView = root.findViewById(R.id.text_reserve_table);
        reservationViewModel.getTextReserveTable().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        return root;
    }
}