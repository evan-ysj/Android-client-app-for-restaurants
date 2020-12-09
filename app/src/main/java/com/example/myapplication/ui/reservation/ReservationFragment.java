package com.example.myapplication.ui.reservation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.example.myapplication.model.User;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        User user = User.getInstance();
        if(user.getUserid() == -1) {
            Toast.makeText(getActivity(), "Please sign in!", Toast.LENGTH_LONG).show();
            Navigation.findNavController(view).navigate(R.id.loginFragment);
        }
    }

}