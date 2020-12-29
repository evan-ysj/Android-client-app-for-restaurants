package com.example.myapplication.ui.reservation;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.NetworkUtils;
import com.example.myapplication.R;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ReserveTableFragment extends Fragment {

    private EditText tvNumberOfGuests;
    private EditText tvDiningDate;
    private Toast toast;
    private ReservationViewModel reservationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reservationViewModel =
                new ViewModelProvider(this, new ReservationViewModel.Factory(requireActivity().getApplication()))
                        .get(ReservationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reserve_table, container, false);
        final TextView textView = root.findViewById(R.id.text_reserve_table);
        reservationViewModel.getTextReserveTable().observe(getViewLifecycleOwner(), s -> textView.setText(s));

        tvNumberOfGuests = root.findViewById(R.id.reservation_number_of_guest);
        tvDiningDate = root.findViewById(R.id.reservation_date);

        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);

        Button submit = root.findViewById(R.id.reservation_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                bookTable();
            }
        });
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void bookTable() {
        reservationViewModel.invalidateCache();
        int numberOfGuests;
        Date diningDate;
        try {
            numberOfGuests = Integer.parseInt(tvNumberOfGuests.getText().toString());
            LocalDate date = LocalDate.parse(tvDiningDate.getText().toString(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            diningDate = Date.from(Instant.from(date.atStartOfDay(ZoneId.systemDefault())));
        } catch(Exception e) {
            toast.setText("Please provide valid format of information!");
            toast.show();
            return;
        }
        if(numberOfGuests < 1) {
            toast.setText("The number of guests cannot be zero!");
            toast.show();
            return;
        }
        if(diningDate.getTime() <= (new Date()).getTime()) {
            toast.setText("Please pick another date that is later than today!");
            toast.show();
            return;
        }

        NetworkUtils.RESPONSE_CODE responseCode = reservationViewModel.bookingServer(numberOfGuests, diningDate);

        if(responseCode == NetworkUtils.RESPONSE_CODE.SUCCESS) {
            toast.setText("Congratulations! We have reserved a table for you!");
            //Log.e("status:", "success!");
            loadReservations();
            navToReservations();
        } else {
            if(responseCode == NetworkUtils.RESPONSE_CODE.NO_RESPONSE) {
                toast.setText("Server no response!");
            } else {
                toast.setText(NetworkUtils.MESSAGE);
            }
            toast.show();
            tvNumberOfGuests.setText("");
            tvDiningDate.setText("");
        }
    }

    private void loadReservations() {
        NetworkUtils.RESPONSE_CODE responseCode = reservationViewModel.loadData();
        if(responseCode == NetworkUtils.RESPONSE_CODE.SUCCESS) {
            Toast.makeText(getActivity(), "Load reservation history successfully!", Toast.LENGTH_SHORT).show();
            Log.e("status:", "success!");
        } else {
            if(responseCode == NetworkUtils.RESPONSE_CODE.NO_RESPONSE) {
                Toast.makeText(getActivity(), "Server no response!!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), NetworkUtils.MESSAGE, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navToReservations() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NavHostFragment.findNavController(ReserveTableFragment.this).navigate(R.id.action_navigation_reserve_table_to_navigation_reservation);
            }
        });
    }
}