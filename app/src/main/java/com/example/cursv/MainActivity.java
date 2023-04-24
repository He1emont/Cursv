package com.example.cursv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends DatabaseUtils {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDatabase();

        EditText edLogin = findViewById(R.id.edLogin);
        EditText edPassword = findViewById(R.id.edPassword);
        EditText edConfirmPassword = findViewById(R.id.edConfirmPassword);
        Button btEnter = findViewById(R.id.btEnter);

        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edLogin.getText().length() == 0 || edPassword.getText().length() == 0 || edConfirmPassword.getText().length() == 0){
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Заполнены не все необходимые поля", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    if(edPassword.getText().toString().equals(edConfirmPassword.getText().toString())){
                        Intent intent = new Intent(MainActivity.this, hello_there.class);
                        intent.putExtra("humanLogin", edLogin.getText().toString());
                        intent.putExtra("humanPassword", edPassword.getText().toString());
                        startActivity(intent);
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Введенные пароли не совпадают", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });

    }
}