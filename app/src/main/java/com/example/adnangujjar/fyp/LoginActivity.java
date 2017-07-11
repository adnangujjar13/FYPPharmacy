package com.example.adnangujjar.fyp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    Intent i;
    EditText name, password;
    String Name, Password;

    String NAME=null, PASSWORD=null, EMAIL=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        name = (EditText) findViewById(R.id.main_name);
        password = (EditText) findViewById(R.id.main_password);
    }
    public void main_register(View v){
        startActivity(new Intent(this,RegisterActivity.class));
    }
    public void main_login(View v){
        Name = name.getText().toString();
        Password = password.getText().toString();
        BackGround b = new BackGround();
        b.execute(Name, Password);

    }
    class BackGround extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            Toast.makeText(LoginActivity.this," bg",Toast.LENGTH_LONG).show();
            String name = params[0];
            String password = params[1];
            String data="";
            int tmp;
            try {
                URL url = new URL("http://192.168.43.219/login.php");
                String urlParams = "name="+name+"&password="+password;
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();
                InputStream is = httpURLConnection.getInputStream();
                while((tmp=is.read())!=-1){
                    data+= (char)tmp;
                }
                is.close();
                httpURLConnection.disconnect();
                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            }
        }
        @Override
        protected void onPostExecute(String s) {
            String err=null;
            try {
                JSONObject root = new JSONObject(s);
                JSONObject user_data = root.getJSONObject("user_data");
                NAME = user_data.getString("name");
                PASSWORD = user_data.getString("password");
                EMAIL = user_data.getString("email");
                Log.d("value_login", "Email:"+ EMAIL+"password:"+PASSWORD);
                Toast.makeText(getApplicationContext()," name is "+ NAME,Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext()," name is "+ PASSWORD,Toast.LENGTH_SHORT);
                Toast.makeText(getApplicationContext()," name is "+ EMAIL,Toast.LENGTH_SHORT);
            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: "+e.getMessage();
            }

            i = new Intent(getApplicationContext(),OrderByPrescription.class);
            if(Name.equals(NAME)&& Password.equals(PASSWORD))
            {
                startActivity(i);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT);
            }

        }
    }
}