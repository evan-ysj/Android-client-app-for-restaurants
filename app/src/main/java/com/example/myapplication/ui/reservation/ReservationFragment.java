package com.example.myapplication.ui.reservation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.NetworkUtils;
import com.example.myapplication.R;
import com.example.myapplication.db.entity.ReserveHistoryEntity;

import java.util.List;
import java.util.Objects;

public class ReservationFragment extends Fragment {

    private ReservationViewModel reservationViewModel;
    private ReserveAdapter reserveAdapter;
    private SharedPreferences sp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reservationViewModel =
                new ViewModelProvider(this, new ReservationViewModel.Factory(requireActivity().getApplication()))
                        .get(ReservationViewModel.class);
        reserveAdapter = new ReserveAdapter();
        sp = getActivity().getSharedPreferences(getString(R.string.shared_preference_login), Context.MODE_PRIVATE);
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

        RecyclerView recyclerView = root.findViewById(R.id.list);
        recyclerView.setAdapter(reserveAdapter);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(reservationViewModel.getUser().getValue().getUserId() == -1) {
            Toast.makeText(getActivity(), "Please sign in!", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(R.id.action_navigation_reservation_to_loginFragment);
        }
        else if(reservationViewModel.getReservations().getValue() == null ||
                reservationViewModel.getReservations().getValue().size() == 0) {
            loadReservations();
        }
        subscribeUi(reservationViewModel.getReservations());
    }

    private void subscribeUi(LiveData<List<ReserveHistoryEntity>> liveData) {
        liveData.observe(getViewLifecycleOwner(), history -> {
            reserveAdapter.setmReservations(history);
        });

    }

    @Override
    public void onDestroyView() {
        reserveAdapter = null;
        super.onDestroyView();
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
}