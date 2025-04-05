package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText email, password;
    Button login, deleteAccount;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        deleteAccount = findViewById(R.id.deleteAccount); // Nuevo botón

        login.setOnClickListener(view -> {
            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();

            if (db.checkUser(userEmail, userPassword)) {
                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, UserMenu.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

        deleteAccount.setOnClickListener(view -> {
            String userEmail = email.getText().toString();
            int userId = db.getUserIdByEmail(userEmail);

            if (userId != -1 && db.deleteUser(userId)) {
                Toast.makeText(MainActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Deletion Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}