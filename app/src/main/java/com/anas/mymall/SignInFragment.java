package com.anas.mymall;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.anas.mymall.LoginActivity.onResetPasswordFragment;
import static com.anas.mymall.RegisterFragment.CLOSE_BTN;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment {

    private TextView createAccount;
    private FrameLayout parentFrameLayout;
    private EditText email;
    private EditText password;
    private TextView forgetPass;
    private Button signIn;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";


    private FirebaseAuth firebaseAuth;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        createAccount = view.findViewById(R.id.haveAccount);
        parentFrameLayout = getActivity().findViewById(R.id.login_framLayout);
        email = view.findViewById(R.id.log_email);
        password = view.findViewById(R.id.log_pass);
        forgetPass = view.findViewById(R.id.log_forgetPass);
        signIn = view.findViewById(R.id.log_btn);

        firebaseAuth = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new RegisterFragment());

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailandPass();
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetPasswordFragment = true;
                setFragment(new ResetPasswordFragment());
            }
        });
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right, R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(email.getText().toString())) {
            if (!TextUtils.isEmpty(password.getText().toString())) {
                signIn.setEnabled(true);
                signIn.setTextColor(Color.YELLOW);
            } else {
                signIn.setEnabled(false);
                signIn.setTextColor(Color.BLACK);
            }
        } else {
            signIn.setEnabled(false);
            signIn.setTextColor(Color.BLACK);
        }
    }

    private void checkEmailandPass() {
        if (email.getText().toString().matches(emailPattern)) {
            if (password.length() >= 8) {
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                                    CLOSE_BTN=false;
                                    startActivity(mainIntent);
                                    getActivity().finish();
                                } else {
                                    String Error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), Error, Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            } else {
                password.setError("Wrong Password");
            }
        } else {
            email.setError("Invalid Email");
        }

    }
}
