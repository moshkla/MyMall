package com.anas.mymall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button saveBtn;

    private EditText city;
    private EditText locality;
    private EditText flatNo;
    private EditText pinCode;
    private Spinner state;
    private EditText landMark;
    private EditText name;
    private EditText phoneNumber;
    private EditText anotherPhoneNumber;

    private String[] stateList;
    private String selectedState;

    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ADD NEW ADDRESS");
        saveBtn = findViewById(R.id.add_address_save_btn);

        //----------Loading_Dilaog---------------
        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //----------Loading_Dilaog---------------

        city = findViewById(R.id.add_address_city);
        locality = findViewById(R.id.add_address_locality);
        flatNo = findViewById(R.id.add_address_flat_no);
        pinCode = findViewById(R.id.add_address_pin_code);
        state = findViewById(R.id.add_address_state);
        landMark = findViewById(R.id.add_address_land_mark);
        name = findViewById(R.id.add_address_name);
        phoneNumber = findViewById(R.id.add_address_phone_number);
        anotherPhoneNumber = findViewById(R.id.add_address_another_phone_number);

        stateList = getResources().getStringArray(R.array.egypt_states);
        //state Spinner
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, stateList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(spinnerAdapter);
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedState = stateList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //state Spinner

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(city.getText())) {
                    if (!TextUtils.isEmpty(locality.getText())) {
                        if (!TextUtils.isEmpty(flatNo.getText())) {
                            if (!TextUtils.isEmpty(pinCode.getText()) && pinCode.getText().length() == 5) {
                                if (!TextUtils.isEmpty(name.getText())) {
                                    if (!TextUtils.isEmpty(phoneNumber.getText())) {
                                        loadingDialog.show();
                                        final String fullAddress = city.getText().toString() + "/" + locality.getText().toString() + "/" + flatNo.getText().toString() + "/" + landMark.getText().toString();
                                        Map<String, Object> newAddress = new HashMap<>();
                                        newAddress.put("list_size", (long) DatabaseQueries.addressModelList.size() + 1);
                                        newAddress.put("fullName_" + String.valueOf((long) DatabaseQueries.addressModelList.size() + 1), name.getText().toString() + "/" + phoneNumber.getText().toString());
                                        newAddress.put("address_" + String.valueOf((long) DatabaseQueries.addressModelList.size() + 1), fullAddress);
                                        newAddress.put("pinCode_" + String.valueOf((long) DatabaseQueries.addressModelList.size() + 1), pinCode.getText().toString());
                                        newAddress.put("selected_" + String.valueOf((long) DatabaseQueries.addressModelList.size() + 1), true);
                                        if (DatabaseQueries.addressModelList.size() > 0) {
                                            newAddress.put("selected_" + (DatabaseQueries.selectedAddress + 1), false);
                                        }
                                        FirebaseFirestore.getInstance()
                                                .collection("USERS")
                                                .document(FirebaseAuth.getInstance().getUid())
                                                .collection("USER_DATA")
                                                .document("MY_ADDRESSES")
                                                .update(newAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    if (DatabaseQueries.addressModelList.size() > 0) {
                                                        DatabaseQueries.addressModelList.get(DatabaseQueries.selectedAddress).setSelected(false);
                                                    }
                                                    DatabaseQueries.addressModelList.add(new AddressModel(fullAddress
                                                            , name.getText().toString() + "/" + phoneNumber.getText().toString()
                                                            , pinCode.getText().toString()
                                                            , true));

                                                    Intent deliveryIntent = new Intent(AddAddressActivity.this, DeliveryActivity.class);
                                                    startActivity(deliveryIntent);
                                                    finish();
                                                } else {
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(AddAddressActivity.this, error, Toast.LENGTH_LONG).show();
                                                }
                                                loadingDialog.dismiss();
                                            }
                                        });

                                    } else {
                                        phoneNumber.setSelected(true);
                                        Toast.makeText(AddAddressActivity.this, "Please Add Valid PhoneNumber", Toast.LENGTH_LONG).show();

                                    }
                                } else {
                                    name.setSelected(true);
                                }
                            } else {
                                pinCode.setSelected(true);
                                Toast.makeText(AddAddressActivity.this, "Please Add Valid PinCode", Toast.LENGTH_LONG).show();

                            }
                        } else {
                            flatNo.setSelected(true);
                        }
                    } else {
                        locality.setSelected(true);
                    }
                } else {
                    city.setSelected(true);
                }


            }
        });
    }
}
