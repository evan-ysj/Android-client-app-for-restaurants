package com.example.myapplication.ui.reservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myapplication.R;

public class ReservationFragment extends Fragment {

    private ReservationViewModel reservationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reservationViewModel =
                new ViewModelProvider(this).get(ReservationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reservation, container, false);
        final TextView textView = root.findViewById(R.id.text_reservation);
        reservationViewModel.getTextReserveRecord().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        final Button newReservation = root.findViewById(R.id.reservation_new);
        newReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v)
                        .navigate(R.id.action_navigation_reservation_to_navigation_reserve_table);
            }
        });
        return root;
    }
}