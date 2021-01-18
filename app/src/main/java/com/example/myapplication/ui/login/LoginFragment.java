package com.example.myapplication.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.NetworkUtils;
import com.example.myapplication.R;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private View root;
    private EditText tvUserName;
    private EditText tvPassword;
    private Toast toast;
    private SharedPreferences sp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel =
                new ViewModelProvider(this, new LoginViewModel.Factory(requireActivity().getApplication()))
                        .get(LoginViewModel.class);
        sp = getActivity().getSharedPreferences(getString(R.string.shared_preference_login), Context.MODE_PRIVATE);
        root = inflater.inflate(R.layout.fragment_login, container, false);
        final TextView textView = root.findViewById(R.id.text_login);
        loginViewModel.getTextLogin().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        tvUserName = root.findViewById(R.id.login_email);
        tvPassword = root.findViewById(R.id.login_passwd);
        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        Button signUp = root.findViewById(R.id.login_signup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v)
                        .navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
        Button signIn = root.findViewById(R.id.login_submit);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tvUserNameString = tvUserName.getText().toString().trim();
                String tvPasswordString = tvPassword.getText().toString().trim();
                loginSubmit(tvUserNameString, tvPasswordString);
            }
        });
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String tvUserNameString = sp.getString(getString(R.string.hint_username), null);
        String tvPasswordString = sp.getString(getString(R.string.hint_password), null);
        if(tvUserNameString != null && tvPasswordString != null) {
            loginSubmit(tvUserNameString, tvPasswordString);
        }
    }

    private void loginSubmit(String tvUserNameString, String tvPasswordString) {
        if(TextUtils.isEmpty(tvUserNameString)) {
            toast.setText("Please enter username or email!");
            toast.show();
            return;
        }
        if(TextUtils.isEmpty(tvPasswordString)) {
            toast.setText("Please enter password!");
            toast.show();
            return;
        }

        NetworkUtils.RESPONSE_CODE responseCode = loginViewModel.loginServer(tvUserNameString, tvPasswordString);

        if(responseCode == NetworkUtils.RESPONSE_CODE.SUCCESS) {
            toast.setText(NetworkUtils.MESSAGE);
            toast.show();
            Log.e("status:", "login success!");
            sp.edit().putString(getString(R.string.hint_username), tvUserNameString)
                     .putString(getString(R.string.hint_password), tvPasswordString)
                     .apply();
            navBack();
        } else {
            if(responseCode == NetworkUtils.RESPONSE_CODE.NO_RESPONSE) {
                toast.setText("Server no response!");
            } else {
                toast.setText(NetworkUtils.MESSAGE);
            }
            toast.show();
            tvPassword.setText("");
            tvUserName.setText("");
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