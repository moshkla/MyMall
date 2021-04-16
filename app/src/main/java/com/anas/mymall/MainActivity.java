package com.anas.mymall;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;


import android.view.MenuItem;

import androidx.annotation.NonNull;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.anas.mymall.DatabaseQueries.currentUser;
import static com.anas.mymall.LoginActivity.setSignUpFragment;
import static com.anas.mymall.RegisterFragment.CLOSE_BTN;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    public Toolbar toolbar;

    public static DrawerLayout drawer;
    private Dialog signInDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_myMall, R.id.nav_orders, R.id.nav_rewards,
                R.id.nav_cart, R.id.nav_WishList, R.id.nav_account, R.id.nav_sign_out)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        if (currentUser == null) {
            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(false);
        } else {
            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(true);
        }
        signInDialog = new Dialog(this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button signInD_Btn = signInDialog.findViewById(R.id.dialogSignInBtn);
        Button signUpD_Btn = signInDialog.findViewById(R.id.dialogSignUpBtn);
        final Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        signInD_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInDialog.dismiss();

                setSignUpFragment = true;
                startActivity(loginIntent);
            }
        });
        signUpD_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CLOSE_BTN = true;
                signInDialog.dismiss();
                setSignUpFragment = false;
                startActivity(loginIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem cartItem = menu.findItem(R.id.main_cart_icon);
        if (DatabaseQueries.cartListModels_ID.size() > 0) {
            cartItem.setActionView(R.layout.badge_layout);
            ConstraintLayout cartContainer = cartItem.getActionView().findViewById(R.id.new_cart_container);
            TextView badeCount = cartItem.getActionView().findViewById(R.id.badge_count);
            badeCount.setText(String.valueOf(DatabaseQueries.cartListModels_ID.size()));

            cartContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "will show the cart soon", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            cartItem.setActionView(null);
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.main_search_icon:

                break;
            case R.id.main_notification_icon:

                break;
            case R.id.main_cart_icon:
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    myCart();

                }
                break;
            case R.id.nav_sign_out:
                signOutUser();
                return true;

            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void signOutUser() {
        FirebaseAuth.getInstance().signOut();
        DatabaseQueries.clearData();
        Intent regIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(regIntent);
        finish();
    }

    private void myCart() {
        invalidateOptionsMenu();
        navigationView.getMenu().getItem(3).setChecked(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser == null) {
            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(false);
        } else {
            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(true);

        }
        invalidateOptionsMenu();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}
