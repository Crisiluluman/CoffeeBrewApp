package com.example.coffeebrewapp.UI.Login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.coffeebrewapp.MainActivity;
import com.example.coffeebrewapp.R;

public class LoginActivity extends AppCompatActivity {

    Button buttonLogin;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = findViewById(R.id.toolbar_sub);


        buttonLogin = findViewById(R.id.loginButton);

        buttonLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

        });
    }
}
