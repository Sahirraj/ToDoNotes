package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.myapplication.prefConstant.IS_LOGGED_IN;

public class LoginActivity extends AppCompatActivity {
    EditText editTextFullName, editTextUserName;
    Button buttonLogin;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextUserName = findViewById(R.id.editTextUserName);
        buttonLogin = findViewById(R.id.buttonLogin);

        setupSharedPreferences();

        View.OnClickListener clickAction = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullName = editTextFullName.getText().toString();
                String userName = editTextUserName.getText().toString();

                if(!TextUtils.isEmpty(fullName) && !TextUtils.isEmpty(userName)){
                    Intent intent = new Intent(LoginActivity.this, MyNotesActivity.class);
                    intent.putExtra(AppConstant.FULL_NAME,fullName);
                    startActivity(intent);
                    saveLoginStatus();
                    saveFullName(fullName);

                }
                else{
                    Toast.makeText(LoginActivity.this,"Please Enter Valid Fullname And Username",Toast.LENGTH_SHORT).show();
                }
            }
        };
        buttonLogin.setOnClickListener(clickAction);

    }

    private void saveFullName(String fullName) {
        editor = sharedPreferences.edit();
        editor.putString(prefConstant.FULL_NAME,fullName);
        editor.apply();

    }

    private void saveLoginStatus() {
        editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGGED_IN,true);
        editor.apply();
    }

    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(prefConstant.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
    }
}
