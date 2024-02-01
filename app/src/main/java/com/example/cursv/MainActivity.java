package com.example.cursv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends DatabaseUtils {

    private final String loginPref = "login";
    private final String passwordPref = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int create = Preference.getIntIdHuman("createStatus", MainActivity.this);
        if(create==0){
            createDatabase();
            Preference.setIntIdHuman("createStatus", 1, MainActivity.this);
        }

        String login = Preference.getAuthSettings(loginPref, MainActivity.this);
        String password = Preference.getAuthSettings(passwordPref, MainActivity.this);
        if(!login.equals("") || !password.equals("")){
            startActivity(hello_there.class);
        }

        EditText edLogin = findViewById(R.id.edLogin);
        EditText edPassword = findViewById(R.id.edPassword);
        EditText edConfirmPassword = findViewById(R.id.edConfirmPassword);
        Button btEnter = findViewById(R.id.btEnter);

        btEnter.setOnClickListener(view -> {
            if (edLogin.getText().length() == 0 || edPassword.getText().length() == 0 || edConfirmPassword.getText().length() == 0){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Заполнены не все необходимые поля", Toast.LENGTH_SHORT);
                toast.show();
            }
            else{
                if(edPassword.getText().toString().equals(edConfirmPassword.getText().toString())){
                    Preference.setAuthSettings(loginPref, edLogin.getText().toString(), MainActivity.this);
                    Preference.setAuthSettings(passwordPref, edPassword.getText().toString(), MainActivity.this);
                    startActivity(hello_there.class);
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Введенные пароли не совпадают", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }


    private void startActivity(Class clas){
        Intent intent = new Intent(MainActivity.this, clas);
        startActivity(intent);
    }

}