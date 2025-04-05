package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterUser extends AppCompatActivity {

    EditText name, lastname, age, email, password;
    Button register, update;
    DatabaseHelper db;
    int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        db = new DatabaseHelper(this);

        name = findViewById(R.id.name);
        lastname = findViewById(R.id.lastname);
        age = findViewById(R.id.age);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        update = findViewById(R.id.update);


        Intent intent = getIntent();
        userId = intent.getIntExtra("USER_ID", -1);

        if (userId != -1) {
            loadUserData(userId);
            register.setVisibility(View.GONE);
            update.setVisibility(View.VISIBLE);
        }

        register.setOnClickListener(view -> {
            String userName = name.getText().toString();
            String userLastName = lastname.getText().toString();
            int userAge = Integer.parseInt(age.getText().toString());
            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();

            boolean inserted = db.registerUser(userName, userLastName, userAge, userEmail, userPassword);
            if (inserted) {
                Toast.makeText(RegisterUser.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(RegisterUser.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(view -> {
            String userName = name.getText().toString();
            String userLastName = lastname.getText().toString();
            int userAge = Integer.parseInt(age.getText().toString());
            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();

            boolean updated = db.updateUser(userId, userName, userLastName, userAge, userEmail, userPassword);
            if (updated) {
                Toast.makeText(RegisterUser.this, "User Updated Successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(RegisterUser.this, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUserData(int userId) {
        Cursor cursor = db.getUserById(userId);
        if (cursor.moveToFirst()) {
            name.setText(cursor.getString(1));
            lastname.setText(cursor.getString(2));
            age.setText(String.valueOf(cursor.getInt(3)));
            email.setText(cursor.getString(4));
            password.setText(cursor.getString(5));
        }
        cursor.close();
    }
}