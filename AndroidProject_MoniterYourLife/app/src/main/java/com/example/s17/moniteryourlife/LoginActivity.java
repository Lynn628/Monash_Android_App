package com.example.s17.moniteryourlife;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.s17.moniteryourlife.Network.RestClient;
import com.example.s17.moniteryourlife.SQLite.DatabaseHelper;
import com.example.s17.moniteryourlife.SQLite.USER;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText userNameView;
    private EditText passwordView;
    private Button loginButton;
    private Button registButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameView = (EditText) findViewById(R.id.userNameEditV);
        passwordView = (EditText) findViewById(R.id.passwordEditV);
        loginButton = (Button) findViewById(R.id.login);
        registButton = (Button) findViewById(R.id.register);

        //Get database helper
        dbHelper = new DatabaseHelper(getApplicationContext());
        loginButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String userName = userNameView.getText().toString();
                String password = passwordView.getText().toString();
                loginAction(userName, password);
            }
        });

        registButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    public void loginAction(final String userName, String password) {
        //statues indicate the exisitence of user at sqlite
        String encodedPassword = encoding(password);
        boolean statues = dbHelper.getUsers(userName, encodedPassword);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        String date = dateFormat.format(new java.util.Date());
        if (statues == true) {
            Config.cacheUserName(LoginActivity.this, userName);
            //Create an entry in server's report table
            String questresult = RestClient.addUserToReport(userName,date);
            if (questresult.equals("Success")) {
                Toast.makeText(LoginActivity.this, " One Record Insert.", Toast.LENGTH_SHORT).show();
            }
            //createUserReport(userName, date);
            Intent i = new Intent();
            i.setClass(LoginActivity.this, HomeActivity.class);
            startActivity(i);
        } else {
            userNameView = (EditText) findViewById(R.id.userNameEditV);
            String name = userNameView.getText().toString();
            new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... params) {
                    return RestClient.findUserByName(params[0]);
                }
                @Override
                protected void onPostExecute(String result) {
                    if (result.equals("NOT FOUND")) {
                        Toast.makeText(LoginActivity.this, "User doesn't exist.", Toast.LENGTH_SHORT).show();
                    } else if (result.equals("OK")) {
                        //User exists in the server's database but no record in Local database
                        Config.cacheUserName(LoginActivity.this, userName);
                        Intent i = new Intent();
                        i.setClass(LoginActivity.this, HomeActivity.class);
                        String password = userName;
                        String encodedPassword = encoding(password);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
                        String date = dateFormat.format(new java.util.Date());
                        //Add the user exists in the server database to local sqlite database
                        dbHelper.addUser(new USER(userName,encodedPassword, date, 0, 0));
                        //createUserReport(userName,date);
                        String questresult = RestClient.addUserToReport(userName, date);
                        System.out.println(">>>>>>>>>>>>>>>login");
                        if (questresult.equals("Success")) {
                            Toast.makeText(LoginActivity.this, "Record Insert.", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(i);
                    }
                }
            }.execute(name);
        }
    }
    //Encoding  password method
      public final static String encoding(String text)
    {
        char hexDigits[] =
                { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                        'e', 'f' };
        String encodingStr = null;
        try
        {
            byte[] strTemp = text.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++)
            {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            encodingStr = new String(str);
        }
        catch (Exception e)
        {
        }
        return encodingStr;
    }

}








