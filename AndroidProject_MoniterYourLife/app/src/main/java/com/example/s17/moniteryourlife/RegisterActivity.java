package com.example.s17.moniteryourlife;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.s17.moniteryourlife.Network.RestClient;
import com.example.s17.moniteryourlife.SQLite.DatabaseHelper;
import com.example.s17.moniteryourlife.SQLite.USER;

import java.text.SimpleDateFormat;

public class RegisterActivity extends AppCompatActivity {
        private Button continueButton;
        private EditText usernameEditV;
        private EditText passwordEditV;
        private EditText confirmEditV;
        private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        usernameEditV = (EditText)findViewById(R.id.userNameEditV);
        passwordEditV = (EditText)findViewById(R.id.passwordEditV);
        confirmEditV = (EditText)findViewById(R.id.confirmEditV);
        dbHelper = new DatabaseHelper(getApplicationContext());

        continueButton = (Button)findViewById(R.id.registerbtn1);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
              //  String userName = usernameEditV.getText().toString().trim();
             //   String password = passwordEditV.getText().toString().trim();
                //String confpassword = confirmEditV.getText().toString().trim();
                registerAction( );
          }
     });
    }
        public void registerAction( ) {
            usernameEditV = (EditText)findViewById(R.id.userNameEditV);
            String userName = usernameEditV.getText().toString();
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        return RestClient.findUserByName(params[0]);
                    }
                    @Override
                    protected void onPostExecute(String result) {
                        if (result.equals("NOT FOUND")) {
                            formattingRequirment( );
                        } else if (result.equals("OK")) {
                            Toast.makeText(RegisterActivity.this, "Username already exist.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }.execute(userName);
            }
       // }

    public void formattingRequirment( ){
        String userName = usernameEditV.getText().toString();
        String password = passwordEditV.getText().toString();
        String confpassword = confirmEditV.getText().toString();

        if (((password.equals("")) == false ) && ((userName.equals(""))==false) && (password.equals(confpassword))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
            String registerDate = dateFormat.format(new java.util.Date());
            Config.cacheUserName(RegisterActivity.this, userName);
            String encodedPassword = LoginActivity.encoding(password);
            dbHelper.addUser(new USER(userName, encodedPassword, registerDate, 0, 0));
            Intent i = new Intent();
            i.setClass(RegisterActivity.this, DataEntryActivity.class);
            startActivity(i);
        } else if (password.equals("")) {
            Toast.makeText(RegisterActivity.this, "Password can't be empty.", Toast.LENGTH_SHORT).show();
        } else if (userName.equals("")) {
            Toast.makeText(RegisterActivity.this, "Username can't be empty.", Toast.LENGTH_SHORT).show();
        } else if ((password.equals(confpassword)) == false) {
            Toast.makeText(RegisterActivity.this, "Password inconsistent.", Toast.LENGTH_SHORT).show();
        }
    }
}