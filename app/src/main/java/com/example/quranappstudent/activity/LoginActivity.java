package com.example.quranappstudent.activity;

import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.quranappstudent.model.Admin;
import com.example.quranappstudent.R;
import com.example.quranappstudent.SharedPrefManager;
import com.example.quranappstudent.URLs;
import com.example.quranappstudent.ViewDialog;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    String PhoneNumber;
    String Password;

    ViewDialog viewDialog;
    EditText phoneEditText;
    EditText passEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, DashboardActivity.class));
            return;
        }

        viewDialog = new ViewDialog(this);

        phoneEditText = findViewById(R.id.input_text_phonenumber);
        passEditText = findViewById(R.id.input_text_password);

        Button loginButton = findViewById(R.id.btn_login);

        loginButton.setOnClickListener(v -> {
            PhoneNumber = phoneEditText.getText().toString();
            Password = passEditText.getText().toString();
            Sigin(PhoneNumber, Password);

        });
    }

    private void Sigin(String phoneNumber, String password) {
        if (!validate()) {
            return;
        }
        viewDialog.showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLs.Login + "?PhoneNumber=" + phoneNumber + "&Password=" + password, null,
                (JSONObject response) -> {
                    try {
                        String name = response.getString("Name");
                        Admin admin = new Admin(response.getInt("IdAdmin"), response.getInt("UserType"), response.getString("Name"), response.getInt("IdTeacher") ,response.getString("PhoneNumber"));
                        SharedPrefManager.getInstance(getApplicationContext()).adminLogin(admin);
                        onSiginSuccess();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        onSiginFailed();
                    }


                }, error -> Log.d("Error getting response", "" + error));

        requestQueue.add(jsonObjectRequest);
        Log.d("rs", "" + jsonObjectRequest);

    }

    private void onSiginFailed() {
        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "فشل تسجيل الدخول", Snackbar.LENGTH_LONG)
                .setAction("Try Again", v -> {
                    Sigin(PhoneNumber, Password);
                }).show();
    }

    private void onSiginSuccess() {
        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "تم تسجيل الدخول بنجاح", Snackbar.LENGTH_LONG)
                .show();
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }

    private boolean validate() {
        boolean valid = true;

        if (PhoneNumber == null && !Patterns.PHONE.matcher(PhoneNumber).matches()) {
            phoneEditText.setError("الرجاء ادخال رقم هاتف صالح");
            valid = false;
        } else {
            phoneEditText.setError(null);
        }

        if (Password == null && Password.length() < 2 && Password.length() > 4) {
            passEditText.setError("كلمة السر يجب ان تحتوي علي اربعة حروف علي الاقل");
            valid = false;
        } else {
            passEditText.setError(null);
        }
        return valid;
    }

}