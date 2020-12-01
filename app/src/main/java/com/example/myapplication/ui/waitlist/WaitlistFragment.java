package com.example.myapplication.ui.waitlist;

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

public class WaitlistFragment extends Fragment {

    private WaitlistViewModel waitlistViewModel;

    public WaitlistFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static WaitlistFragment newInstance() {
        WaitlistFragment fragment = new WaitlistFragment();
        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        waitlistViewModel =
                new ViewModelProvider(this).get(WaitlistViewModel.class);
        View root = inflater.inflate(R.layout.fragment_waitlist, container, false);
        final TextView textView = root.findViewById(R.id.text_waitlist);
        waitlistViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        return root;
    }
}