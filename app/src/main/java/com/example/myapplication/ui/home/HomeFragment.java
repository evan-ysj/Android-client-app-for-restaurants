package com.example.myapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.myapplication.R;
import com.example.myapplication.service.Session;
import com.example.myapplication.ui.waitlist.WaitlistFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        final Button joinWaitList = root.findViewById(R.id.home_take_a_number);
        joinWaitList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v)
                        .navigate(R.id.action_navigation_home_to_navigation_waitlist);
            }
        });
        final Button reserveTable = root.findViewById(R.id.home_book_a_table);
        reserveTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session session = Session.getInstance();
                if(session.getUserid() == -1) {
                    Navigation.findNavController(v)
                            .navigate(R.id.action_navigation_home_to_loginFragment);
                } else {
                    Navigation.findNavController(v)
                            .navigate(R.id.action_navigation_home_to_navigation_reservation);
                }
            }
        });
        return root;
    }
}