package com.anas.mymall;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private TextView HaveAccount;
    private FrameLayout parentFrameLayout;
    private EditText name;
    private EditText email;
    private EditText pass1;
    private EditText pass2;
    private Button SignUp;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    private androidx.appcompat.widget.AppCompatImageView closeBtn;
    public static boolean CLOSE_BTN = false;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);


        HaveAccount = view.findViewById(R.id.reg_haveAcc);
        parentFrameLayout = getActivity().findViewById(R.id.login_framLayout);
        name = view.findViewById(R.id.reg_name);
        email = view.findViewById(R.id.reg_email);
        pass1 = view.findViewById(R.id.reg_pass1);
        pass2 = view.findViewById(R.id.reg_pass2);
        SignUp = view.findViewById(R.id.reg_btn);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        closeBtn = view.findViewById(R.id.close_reg_frag);
        if (CLOSE_BTN) {
            closeBtn.setVisibility(View.GONE);
        } else {
            closeBtn.setVisibility(View.VISIBLE);
        }

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(mainIntent);
                CLOSE_BTN = false;
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());

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
        pass1.addTextChangedListener(new TextWatcher() {
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
        pass2.addTextChangedListener(new TextWatcher() {
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

        SignUp.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                checkEmailandPass();
            }

        });
    }


    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left, R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    private void checkInputs() {
        if (!TextUtils.isEmpty(name.getText().toString())) {
            if (!TextUtils.isEmpty(email.getText().toString())) {
                if (!TextUtils.isEmpty(pass1.getText().toString()) && pass1.length() >= 8) {
                    if (!TextUtils.isEmpty(pass2.getText().toString())) {
                        SignUp.setEnabled(true);
                        SignUp.setTextColor(Color.YELLOW);
                    } else {

                        SignUp.setEnabled(false);
                        SignUp.setTextColor(Color.YELLOW);
                    }
                } else {

                    SignUp.setEnabled(false);
                    SignUp.setTextColor(Color.YELLOW);
                }
            } else {
                SignUp.setEnabled(false);
                SignUp.setTextColor(Color.YELLOW);
            }
        } else {
            SignUp.setEnabled(false);
            SignUp.setTextColor(Color.YELLOW);
        }

    }

    private void checkEmailandPass() {
        //Custom error Icon
        Drawable custom_Error_Icon = getResources().getDrawable(R.drawable.custom_error_icon);
        custom_Error_Icon.setBounds(0, 0, custom_Error_Icon.getIntrinsicWidth(), custom_Error_Icon.getIntrinsicHeight());
        //-------------------------
        if (email.getText().toString().matches(emailPattern)) {
            if (pass1.getText().toString().equals(pass2.getText().toString())) {

                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), pass1.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //upload data to firestore
                            Map<Object, String> userData = new HashMap<>();
                            userData.put("name", name.getText().toString());
                            firebaseFirestore.collection("USERS").document(firebaseAuth.getUid())
                                    .set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        CollectionReference userDataReference = firebaseFirestore
                                                .collection("USERS")
                                                .document(firebaseAuth.getUid())
                                                .collection("USER_DATA");

                                        final List<String> documentNames = new ArrayList<>();
                                        documentNames.add("MY_WISHLIST");
                                        documentNames.add("MY_RATINGS");
                                        documentNames.add("MY_CART");
                                        documentNames.add("MY_ADDRESSES");


                                        Map<String, Object> wishListMap = new HashMap<>();
                                        wishListMap.put("list_size", (long) 0);

                                        Map<String, Object> ratingsMap = new HashMap<>();
                                        ratingsMap.put("list_size", (long) 0);

                                        Map<String, Object> cartMap = new HashMap<>();
                                        cartMap.put("list_size", (long) 0);

                                        Map<String, Object> addressesMap = new HashMap<>();
                                        addressesMap.put("list_size", (long) 0);

                                        List<Map<String, Object>> documentFields = new ArrayList<>();
                                        documentFields.add(wishListMap);
                                        documentFields.add(ratingsMap);
                                        documentFields.add(cartMap);
                                        documentFields.add(addressesMap);
                                        for (int x = 0; x < documentNames.size(); x++) {
                                            final int finalX = x;
                                            userDataReference.document(documentNames.get(x))
                                                    .set(documentFields.get(x)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        if (finalX == documentNames.size() - 1) {
                                                            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                                                            startActivity(mainIntent);
                                                            getActivity().finish();
                                                        }
                                                    } else {
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                pass2.setError("Password Doesn't match", custom_Error_Icon);

            }
        } else {
            email.setError("Invalid Email", custom_Error_Icon);

        }
    }

}