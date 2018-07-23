package com.example.saumilpatel.myadowner;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


        private static final String TAG = "CardListActivity";
        private CardArrayAdapter cardArrayAdapter;
        private ListView listView;
       String confirmtext;

       String firstname="",lastname="",email="",phone="",category="",fontcolor="",backcolor="",imageheight="",imagewidth="",payment="",special="",newspaper="",edition="",composetext="",date="",confirm="",order_id="",url="";
       String newspaperarray[],editionarray[],composetextarray[],datearray[],firstnamearray[],lastnamearray[],emailarray[],phonearray[],categoryarray[],fontcolorarray[],backgroundcolorarray[],imageheightarray[],imagewidtharray[],paymentarray[],specialarray[],confirmarray[],orderidarray[];
       String categorytitle;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("ORDER HISTORY");
            getSupportActionBar().hide();
           // getSupportActionBar().setIcon(R.drawable.splash);

            AsyncCall asyncCall =new AsyncCall();
            asyncCall.execute(url);
           // Toast.makeText(getApplicationContext(),firstnamearray[2],Toast.LENGTH_LONG).show();


        }
    public class AsyncCall extends AsyncTask<String,Void,String>
    {

        private ProgressDialog progressDialog;
        private StringBuilder sb;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Wait");
            progressDialog.setMessage("hold on!!");
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
                URL url = new URL("http://sapatel2727.esy.es/orderdetail.php");
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
                    JSONArray orderArray=  jsonObject.getJSONArray("order");
                    for (int i = 0; i < orderArray.length(); i++) {

                        JSONObject firstnameobj= orderArray.getJSONObject(i);
                         firstname=firstname+firstnameobj.getString("firstname")+"-";
                         lastname=lastname+firstnameobj.getString("lastname")+"-";
                        email=email+firstnameobj.getString("email")+"-";
                        phone=phone+firstnameobj.getString("phone")+"-";
                        category=category+firstnameobj.getString("category")+"-";
                        newspaper=newspaper+firstnameobj.getString("News_paper")+"-";
                        edition=edition+firstnameobj.getString("edition")+"-";
                        composetext=composetext+firstnameobj.getString("composetext")+"-";
                        fontcolor=fontcolor+firstnameobj.getString("fontcolor")+"-";
                        backcolor=backcolor+firstnameobj.getString("backgroundcolor")+"-";
                        imageheight=imageheight+firstnameobj.getString("imageheight")+"-";
                        imagewidth=imagewidth+firstnameobj.getString("imagewidth")+"-";
                        date=date+firstnameobj.getString("publish_date")+"-";
                        payment=payment+firstnameobj.getString("payment")+"-";
                        special=special+firstnameobj.getString("special_request")+"-";
                        order_id=order_id+firstnameobj.getString("order_id")+"-";
                        confirm=confirm+firstnameobj.getString("confirm")+"-";




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

                    firstnamearray=firstname.replace('.',' ').split("-");
                    lastnamearray=lastname.replace('.',' ').split("-");
                    emailarray=email.split("-");
                    phonearray=phone.split("-");
                    composetextarray=composetext.replace('.',' ').split("-");
                    fontcolorarray=fontcolor.split("-");
                    backgroundcolorarray=backcolor.split("-");
                    imageheightarray=imageheight.split("-");
                    imagewidtharray=imagewidth.split("-");
                    paymentarray=payment.split("-");
                    specialarray=special.split("-");
                    newspaperarray=newspaper.replace('_',' ').split("-");
                    editionarray=edition.split("-");
                    datearray=date.split("-");
                    categoryarray=category.split("-");
                    confirmarray=confirm.split("-");
                    orderidarray=order_id.split("-");

                    listView = (ListView) findViewById(R.id.card_listView);

                    listView.addHeaderView(new View(getApplicationContext()));
                    listView.addFooterView(new View(getApplicationContext()));

                    cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);

                    for (int i = 0; i <firstnamearray.length; i++) {

                        if(composetextarray[i].matches("NotApplicable"))
                        {
                            categorytitle ="Display Classified";
                        }
                        else
                        {
                            categorytitle ="Text Classified";
                        }
                        if (confirmarray[i].matches("1"))
                        {
                            confirmtext="CONFIRMED";
                        }
                        else
                        {
                            confirmtext="PENDING";
                        }
                        Card card = new Card(categorytitle,categoryarray[i],newspaperarray[i],editionarray[i],datearray[i],firstnamearray[i],lastnamearray[i],emailarray[i],phonearray[i],composetextarray[i],fontcolorarray[i],backgroundcolorarray[i],imageheightarray[i],imagewidtharray[i],paymentarray[i],specialarray[i],confirmarray[i],orderidarray[i],confirmtext);

                        cardArrayAdapter.add(card);
                    }
                    listView.setAdapter(cardArrayAdapter);
//                    Toast.makeText(getApplicationContext(),firstnamearray[2],Toast.LENGTH_LONG).show();



                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
    }
