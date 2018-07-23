package com.example.saumilpatel.myadowner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class detailActivity extends AppCompatActivity {
    String order_id, confirm,url="";
     TextView firstname, lastname, email, phone, date, height, payment, special, font, back, width, news, category, compose, edition;
     Button confirm_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("ORDER HISTORY");
        getSupportActionBar().hide();
        //  getSupportActionBar().setIcon(R.drawable.splash);



        news = (TextView) findViewById(R.id.news_id);
        edition = (TextView) findViewById(R.id.edition_id);
        category = (TextView) findViewById(R.id.category_id);
        date = (TextView) findViewById(R.id.date_id);
        firstname = (TextView) findViewById(R.id.firstname_id);
        lastname = (TextView) findViewById(R.id.lastname_id);
        email = (TextView) findViewById(R.id.email_id);
        phone = (TextView) findViewById(R.id.phone_id);
        compose = (TextView) findViewById(R.id.composetext_id);
        height = (TextView) findViewById(R.id.height_id);
        payment = (TextView) findViewById(R.id.payment_id);
        font = (TextView) findViewById(R.id.font_id);
        back = (TextView) findViewById(R.id.back_id);
        width = (TextView) findViewById(R.id.width_id);
        special = (TextView) findViewById(R.id.special_id);
        confirm_btn = (Button) findViewById(R.id.confirm_id);

        news.setText(getIntent().getStringExtra("NEWS"));
        edition.setText(getIntent().getStringExtra("EDITION"));
        category.setText(getIntent().getStringExtra("MAINCATEGORY"));
        date.setText(getIntent().getStringExtra("DATE"));
        firstname.setText(getIntent().getStringExtra("FIRST_NAME"));
        lastname.setText(getIntent().getStringExtra("LAST_NAME"));
        email.setText(getIntent().getStringExtra("EMAIL"));
        phone.setText(getIntent().getStringExtra("PHONE"));
        compose.setText(getIntent().getStringExtra("COMPOSETEXT"));
        height.setText(getIntent().getStringExtra("IMAGE_HEIGHT"));
        payment.setText(getIntent().getStringExtra("PAYMENT"));
        font.setText(getIntent().getStringExtra("FONTCOLOR"));
        back.setText(getIntent().getStringExtra("BACKCOLOR"));
        special.setText(getIntent().getStringExtra("SPECIAL"));
        width.setText(getIntent().getStringExtra("IMAGE_WIDTH"));
        order_id = getIntent().getStringExtra("ORDER_ID");
        confirm = getIntent().getStringExtra("CONFIRM");
        if(confirm.equals("1"))
        {
            confirm_btn.setText("This Order has been Confirmed");
        }
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AsyncCall asyncCall =new AsyncCall();
                asyncCall.execute(url);


            }
        });

    }

    public class AsyncCall extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private StringBuilder sb;
        String user1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(detailActivity.this);
            progressDialog.setMessage("Confirmation Sending To Customer");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                URL url = new URL("http://sapatel2727.esy.es/confirmvalue.php?order_id="+order_id+"");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestMethod("GET");

                sb = new StringBuilder();
                String line = "";

                int requestCode = connection.getResponseCode();
                if (requestCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }
                    if (sb.equals("") && sb.equals(null)) {
                        return "";
                    } else {
                        return sb.toString();
                    }
                } else if (requestCode == HttpURLConnection.HTTP_FORBIDDEN) {

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

            try {
                if (!(output.equals("") || output.equals(null))) {


                }

            } catch (Exception ex) {
                Log.e("AsyncCall", ex.getMessage());

            }
            try {
                if (progressDialog.isShowing() && progressDialog != null) {
                    progressDialog.dismiss();
                    new Thread(new Runnable() {

                        public void run() {

                            try {
                                GMailSender sender = new GMailSender(

                                        "adstudio27@gmail.com",

                                        "use your password");

                                //  sender.addAttachment(Environment.getExternalStorageDirectory().getPath()+"/image.jpg");

                                sender.sendMail("AdStudio", "Dear " + firstname.getText() + ",\n\nYour Order Of Advertise In\n\nNewsPaper :" + news.getText()
                                                + "\n\nEdition : " + edition.getText() + "\nhas been confirmed by agency.\n\nThank you.\nRegards\nMyAd Team",

                                        "adstudio27@gmail.com",

                                        email.getText().toString());


                            } catch (Exception e) {

                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

                            }

                        }

                    }).start();
                    Toast.makeText(getApplicationContext(), "Confirmation Mail Sent to Customer", Toast.LENGTH_LONG).show();
                    confirm_btn.setText("This Order has been Confirmed");
                    confirm_btn.setClickable(false);
                    Intent intent=new Intent(detailActivity.this,MainActivity.class);
                    startActivity(intent);

                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Confirmation Failed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


        }

    }
}
