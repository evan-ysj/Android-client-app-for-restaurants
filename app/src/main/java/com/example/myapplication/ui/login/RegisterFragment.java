package com.example.myapplication.ui.login;

import android.graphics.RectF;
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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.NetworkUtils;
import com.example.myapplication.R;
import com.example.myapplication.model.User;

import java.util.ArrayList;
import java.util.List;

public class RegisterFragment extends Fragment {

    private LoginViewModel loginlViewModel;
    private final List<EditText> tvRegisterInfos = new ArrayList<>();;
    private View root;
    private Toast toast;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginlViewModel =
                new ViewModelProvider(this, new LoginViewModel.Factory(requireActivity().getApplication()))
                        .get(LoginViewModel.class);
        root = inflater.inflate(R.layout.fragment_register, container, false);

        final TextView textView = root.findViewById(R.id.text_register);
        loginlViewModel.getTextRegister().observe(getViewLifecycleOwner(), s -> textView.setText(s));

        tvRegisterInfos.add(root.findViewById(R.id.register_username));
        tvRegisterInfos.add(root.findViewById(R.id.register_firstname));
        tvRegisterInfos.add(root.findViewById(R.id.register_lastname));
        tvRegisterInfos.add(root.findViewById(R.id.register_password));
        tvRegisterInfos.add(root.findViewById(R.id.register_confirm_password));
        tvRegisterInfos.add(root.findViewById(R.id.register_email));

        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);

        Button signUp = root.findViewById(R.id.register_submit);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSubmit();
            }
        });
        return root;
    }

    private void registerSubmit() {
        List<String> params = new ArrayList<>();
        for(TextView tvRegisterInfo: tvRegisterInfos) {
            params.add(tvRegisterInfo.getText().toString().trim());
        }
        if(TextUtils.isEmpty(params.get(0))) {
            toast.setText("Username can not be empty!");
            toast.show();
            return;
        }
        if(TextUtils.isEmpty(params.get(1))) {
            toast.setText("Firstname can not be empty!");
            toast.show();
            return;
        }
        if(TextUtils.isEmpty(params.get(2))) {
            toast.setText("Lastname can not be empty!");
            toast.show();
            return;
        }
        if(TextUtils.isEmpty(params.get(3))) {
            toast.setText("Password can not be empty!");
            toast.show();
            return;
        }
        if(TextUtils.isEmpty(params.get(4))) {
            toast.setText("Please enter the password again!");
            toast.show();
            return;
        }
        if(TextUtils.isEmpty(params.get(5))) {
            toast.setText("Email can not be empty!");
            toast.show();
            return;
        }

        NetworkUtils.RESPONSE_CODE responseCode = loginlViewModel.registerServer(params);

        if(responseCode == NetworkUtils.RESPONSE_CODE.SUCCESS) {
            toast.setText(NetworkUtils.MESSAGE);
            toast.show();
            Log.e("status:", "register success!");
            navBack();
        } else {
            if(responseCode == NetworkUtils.RESPONSE_CODE.NO_RESPONSE) {
                toast.setText("Server no response!");
            } else {
                toast.setText(NetworkUtils.MESSAGE);
            }
            toast.show();
            for(TextView tvRegisterInfo: tvRegisterInfos) {
                tvRegisterInfo.setText("");
            }
        }
    }

    private void navBack() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Navigation.findNavController(root).popBackStack();
            }
        });
    }
}