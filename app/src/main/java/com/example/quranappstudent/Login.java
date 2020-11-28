package com.example.quranappstudent;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.quranappstudent.URLs.BaseUrl;

public class Login extends AppCompatActivity {

    String PhoneNumber;
    String Password;

    ViewDialog viewDialog;
    EditText phoneEditText;
    EditText passEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
//            finish();
//            startActivity(new Intent(this, ControlStudent.class));
//            return;
//        }

        viewDialog = new ViewDialog(this);

        phoneEditText = (EditText) findViewById(R.id.input_phone);
        passEditText = (EditText) findViewById(R.id.input_password);

        Button loginButton = (Button) findViewById(R.id.btn_signin);

        loginButton.setOnClickListener(v -> {
            PhoneNumber = phoneEditText.getText().toString();
            Password = passEditText.getText().toString();
            startActivity(new Intent(this, ControlStudent.class));

//            Sigin(PhoneNumber, Password);

        });
    }

    private void Sigin(String phoneNumber, String password) {
        if (!validate()) {
            return;
        }
        viewDialog.showDialog();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLs.GetStudents + "?PhoneNumber=" + phoneNumber + "&Password=" + password, null,
                (JSONObject response) -> {
                    try {
                        String name = response.getString("Name");
                        Admin admin = new Admin(response.getInt("IdAdmin"), response.getInt("UserType"), response.getString("Name"), response.getString("PhoneNumber"));
                        SharedPrefManager.getInstance(getApplicationContext()).adminLogin(admin);
                        onSiginSuccess();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        onSiginFailed();
                    }

                    Log.d("String Response : ", "" + response.toString());
                    Log.d("name", String.valueOf(SharedPrefManager.getInstance(this).isLoggedIn()));
                }, error -> Log.d("Error getting response", "" + error));

        requestQueue.add(jsonObjectRequest);
        Log.d("rs", "" + jsonObjectRequest);

    }

    private void onSiginFailed() {
        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "Sign in Failed", Snackbar.LENGTH_LONG)
                .setAction("Try Again", v -> {
                    Sigin(PhoneNumber, Password);
                }).show();
    }

    private void onSiginSuccess() {
        viewDialog.hideDialog();
        Snackbar.make(findViewById(android.R.id.content), "Sign in Successfully", Snackbar.LENGTH_LONG)
                .show();
        startActivity(new Intent(this, ControlStudent.class));
        finish();
    }

    private boolean validate() {
        boolean valid = true;

        if (PhoneNumber == null && !Patterns.PHONE.matcher(PhoneNumber).matches()) {
            phoneEditText.setError("Enter a valid phone number");
            valid = false;
        } else {
            phoneEditText.setError(null);
        }

        if (Password == null && Password.length() < 2 && Password.length() > 4) {
            passEditText.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passEditText.setError(null);
        }
        return valid;
    }

}