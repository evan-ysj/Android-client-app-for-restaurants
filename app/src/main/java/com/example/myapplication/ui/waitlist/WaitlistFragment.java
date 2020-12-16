package com.example.myapplication.ui.waitlist;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.NetworkUtils;
import com.example.myapplication.R;
import com.example.myapplication.model.Waitlist;

public class WaitlistFragment extends Fragment {

    private WaitlistViewModel waitlistViewModel;
    private EditText tvGuestNumber;
    private EditText tvLastname;
    private final Fragment current = this;
    private Toast toast;

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
                new ViewModelProvider(this, new WaitlistViewModel.Factory(requireActivity().getApplication()))
                        .get(WaitlistViewModel.class);
        View root = inflater.inflate(R.layout.fragment_waitlist, container, false);
        final TextView textView = root.findViewById(R.id.text_waitlist);
        waitlistViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));

        tvGuestNumber = root.findViewById(R.id.waitlist_number_of_guest);
        tvLastname = root.findViewById(R.id.waitlist_lastname);

        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);

        Button takeNumber = root.findViewById(R.id.waitlist_submit);
        takeNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinWaitlist();
            }
        });
        Button checkState = root.findViewById(R.id.waitlist_check_status);
        checkState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkWaitState();
            }
        });
        return root;
    }

    private void joinWaitlist() {
        String tvGuestNumberString = tvGuestNumber.getText().toString().trim();
        String tvLastnameString = tvLastname.getText().toString().trim();
        if(TextUtils.isEmpty(tvGuestNumberString)) {
            toast.setText("Please provide the total number of guests!");
            toast.show();
            return;
        }
        if(TextUtils.isEmpty(tvLastnameString)) {
            toast.setText("Please enter your last name!");
            toast.show();
            return;
        }

        NetworkUtils.RESPONSE_CODE responseCode = waitlistViewModel.waitlistServer(tvGuestNumberString, tvLastnameString);

        if(responseCode == NetworkUtils.RESPONSE_CODE.SUCCESS) {
            toast.setText(NetworkUtils.MESSAGE);
            toast.show();
            Log.e("status:", "success!");
            navToWaitState();
        } else {
            if(responseCode == NetworkUtils.RESPONSE_CODE.NO_RESPONSE) {
                toast.setText("Server no response!");
            } else {
                toast.setText(NetworkUtils.MESSAGE);
            }
            toast.show();
            tvGuestNumber.setText("");
            tvLastname.setText("");
        }
    }

    private void checkWaitState() {
        int waitId = waitlistViewModel.getWaitId();
        String waitCategory = waitlistViewModel.getWaitCategory();
        Log.e("waitlist:", waitCategory + waitId);
        if(waitId < 0) {
            toast.setText("Please take a number first!");
            toast.show();
            return;
        }

        NetworkUtils.RESPONSE_CODE responseCode = waitlistViewModel.waitStateServer(waitId, waitCategory);

        if(responseCode == NetworkUtils.RESPONSE_CODE.SUCCESS) {
            toast.setText(NetworkUtils.MESSAGE);
            toast.show();
            Log.e("status:", "success!");
            navToWaitState();
        } else {
            if(responseCode == NetworkUtils.RESPONSE_CODE.NO_RESPONSE) {
                toast.setText("Server no response!");
            } else {
                toast.setText(NetworkUtils.MESSAGE);
            }
            toast.show();
        }
    }

    private void navToWaitState() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                NavHostFragment.findNavController(current)
                        .navigate(R.id.action_navigation_waitlist_to_navigation_wait_state);
            }
        });
    }
}