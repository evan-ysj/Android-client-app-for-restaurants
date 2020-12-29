package com.example.myapplication.ui.personal;

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
import com.example.myapplication.service.Repository;

public class PersonalFragment extends Fragment {

    private PersonalViewModel personalViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        personalViewModel =
                new ViewModelProvider(this, new PersonalViewModel.Factory(requireActivity().getApplication()))
                        .get(PersonalViewModel.class);
        View root = inflater.inflate(R.layout.fragment_personal, container, false);
        final TextView textView = root.findViewById(R.id.text_personal);
        personalViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        final TextView usernameKey = root.findViewById(R.id.text_username_key);
        personalViewModel.getUsernameKey().observe(getViewLifecycleOwner(), s -> usernameKey.setText(s));
        final TextView firstnameKey = root.findViewById(R.id.text_firstname_key);
        personalViewModel.getFirstnameKey().observe(getViewLifecycleOwner(), s -> firstnameKey.setText(s));
        final TextView lastnameKey = root.findViewById(R.id.text_lastname_key);
        personalViewModel.getLastnameKey().observe(getViewLifecycleOwner(), s -> lastnameKey.setText(s));
        final TextView emailKey = root.findViewById(R.id.text_email_key);
        personalViewModel.getEmailKey().observe(getViewLifecycleOwner(), s -> emailKey.setText(s));
        TextView usernameValue = root.findViewById(R.id.text_username_value);
        personalViewModel.getUser().observe(getViewLifecycleOwner(), s -> usernameValue.setText(s.getUsername()));
        TextView firstnameValue = root.findViewById(R.id.text_firstname_value);
        personalViewModel.getUser().observe(getViewLifecycleOwner(), s -> firstnameValue.setText(s.getFirstname()));
        TextView lastnameValue = root.findViewById(R.id.text_lastname_value);
        personalViewModel.getUser().observe(getViewLifecycleOwner(), s -> lastnameValue.setText(s.getLastname()));
        TextView emailValue = root.findViewById(R.id.text_email_value);
        personalViewModel.getUser().observe(getViewLifecycleOwner(), s -> emailValue.setText(s.getEmail()));
        final Button logout = root.findViewById(R.id.personal_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        personalViewModel.clearData();
                    }
                });
                t.start();
                Navigation.findNavController(root).navigate(R.id.navigation_home);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(personalViewModel.getUser().getValue().getUserId() == -1) {
            Toast.makeText(getActivity(), "Please sign in!", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(view).navigate(R.id.action_navigation_personal_to_loginFragment);
        }
    }
}