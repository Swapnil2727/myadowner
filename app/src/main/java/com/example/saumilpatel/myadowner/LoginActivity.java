package com.example.saumilpatel.myadowner;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    ImageView topback;
    AnimationDrawable animationDrawable;
    Button login_btn;
    EditText user,password;
    String user_name,user_password;
    String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
        user= (EditText) findViewById(R.id.login_user);
        password= (EditText) findViewById(R.id.login_password);
        login_btn=(Button)findViewById(R.id.btnLogin);
        topback= (ImageView) findViewById(R.id.backdrop);
        topback.setBackgroundResource(R.drawable.imageslider);
        animationDrawable=(AnimationDrawable) topback.getBackground();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_name=user.getText().toString();
                user_password=password.getText().toString();

                AsyncCall asyncCall =new AsyncCall();
                asyncCall.execute(url);

            }
        });



    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        animationDrawable.start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        animationDrawable.stop();
    }
    public class AsyncCall extends AsyncTask<String,Void,String>
    {

        private ProgressDialog progressDialog;
        private StringBuilder sb;
        String user1;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setTitle("LogIn");
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                try{
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                URL url = new URL("http://sapatel2727.esy.es/admin_login.php?u="+user_name+"&p="+user_password+"");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestMethod("GET");

                sb = new StringBuilder();
                String line ="";

                int requestCode  = connection.getResponseCode();
                if (requestCode == HttpURLConnection.HTTP_OK)
                {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    if (sb.equals("") && sb.equals(null)) {
                        return "";
                    } else {
                        return sb.toString();
                    }
                }else if (requestCode == HttpURLConnection.HTTP_FORBIDDEN)
                {

                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    if (sb.equals("") && sb.equals(null)) {
                        return "";
                    } else {
                        return sb.toString();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String output) {
            super.onPostExecute(output);


            JSONObject jsonObject;
            try {
                if (!(output.equals("") || output.equals(null))) {

                    jsonObject = new JSONObject(output);


                    JSONArray loginArray=  jsonObject.getJSONArray("login");
                    for (int i = 0; i < loginArray.length(); i++) {
                        JSONObject jobj = loginArray.getJSONObject(i);

                        // sb.append(jobj.getString("id")+":"+jobj.getString("name")+":"+jobj.getString("result")+"\n");
                        user1 = jobj.getString("user").toString();

                    }

                }

            }
            catch (Exception ex)
            {
                Log.e("AsyncCall",ex.getMessage());

            }
            try
            {
                if (progressDialog.isShowing() && progressDialog!= null) {
                    progressDialog.dismiss();

                    Integer id = Integer.parseInt(user1);
                    if (id >= 1) {
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.open,R.anim.closescale);
                        Toast.makeText(LoginActivity.this,"Logged In Successfully",Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder ad = new AlertDialog.Builder(LoginActivity.this);
                        ad.setTitle("Oops!");
                        ad.setMessage("Not Registered As Admin");
                        ad.setIcon(R.drawable.alertoutline);
                        ad.show();
                        Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"Poor Connection",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

    }


}
