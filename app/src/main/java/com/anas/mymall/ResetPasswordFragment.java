package com.anas.mymall;


import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firestore.v1.TransactionOptions;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {
    private FrameLayout parentFrameLayout;

    private EditText email;
    private TextView backToLogin;
    private Button reset_Btn;
    private FirebaseAuth firebaseAuth;

    private ViewGroup imageIconContainer;
    private ImageView imageIcon;
    private TextView textofImageIcon;
    private ProgressBar progressBar;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        parentFrameLayout = getActivity().findViewById(R.id.login_framLayout);

        email = view.findViewById(R.id.reset_email);
        backToLogin = view.findViewById(R.id.reset_back);
        reset_Btn = view.findViewById(R.id.reset_btn);

        imageIconContainer = view.findViewById(R.id.reset_image_text_container);
        imageIcon = view.findViewById(R.id.reset_emailImageIcon);
        textofImageIcon = view.findViewById(R.id.reset_emailDescrip);
        progressBar = view.findViewById(R.id.reset_progressBar);


        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reset_Btn.setEnabled(false);
        backToLogin.setOnClickListener(new View.OnClickListener() {
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
        reset_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TransitionManager.beginDelayedTransition(imageIconContainer);
                imageIcon.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                reset_Btn.setEnabled(false);
                reset_Btn.setTextColor(Color.BLACK);
                firebaseAuth.sendPasswordResetEmail(email.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0, imageIcon.getWidth() / 2, imageIcon.getHeight() / 2);
                                    scaleAnimation.setDuration(1000);
                                    scaleAnimation.setInterpolator(new AccelerateInterpolator());
                                    scaleAnimation.setRepeatMode(Animation.REVERSE);
                                    scaleAnimation.setRepeatCount(1);


                                    scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            textofImageIcon.setText("Recovery email sent successfully ! check your inbox");
                                            textofImageIcon.setTextColor(Color.GREEN);
                                            textofImageIcon.setVisibility(View.VISIBLE);
                                            imageIcon.setColorFilter(Color.GREEN);

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {
                                        }
                                    });

                                    imageIcon.startAnimation(scaleAnimation);


                                } else {
                                    String Error = task.getException().getMessage();
                                   // Toast.makeText(getActivity(), Error, Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                    TransitionManager.beginDelayedTransition(imageIconContainer);
                                    textofImageIcon.setText(Error);
                                    textofImageIcon.setTextColor(Color.RED);

                                    textofImageIcon.setVisibility(View.VISIBLE);
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });
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
            reset_Btn.setEnabled(true);
            reset_Btn.setTextColor(Color.GREEN);
        } else {
            reset_Btn.setEnabled(false);
            reset_Btn.setTextColor(Color.BLACK);
        }
    }

}
