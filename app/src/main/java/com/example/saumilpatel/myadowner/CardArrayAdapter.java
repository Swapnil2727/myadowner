package com.example.saumilpatel.myadowner; /**
 * Created by Saumil Patel on 14-03-17.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CardArrayAdapter  extends ArrayAdapter<Card> {
    private static final String TAG = "com.example.saumilpatel.myadowner.CardArrayAdapter";
    private List<Card> cardList = new ArrayList<Card>();
    String confirm,url="";
    String confirmarray[];
    Activity activity=null;

    static class CardViewHolder {
        TextView category_title;
        TextView newspaper;
        TextView edition;
        TextView date;
        TextView view_btn;
        TextView confirmtext;
        TextView orderid;

    }

    public CardArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public void add(Card object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public void remove(Card object) {
        cardList.remove(object);
        super.remove(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public Card getItem(int index) {
        return this.cardList.get(index);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        final CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item_card, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.category_title = (TextView) row.findViewById(R.id.category_id);
            viewHolder.newspaper = (TextView) row.findViewById(R.id.newspaper_id);
            viewHolder.edition = (TextView) row.findViewById(R.id.edition_id);
            viewHolder.date=(TextView)row.findViewById(R.id.date_id);
            viewHolder.view_btn=(TextView)row.findViewById(R.id.view_btn);
            viewHolder.confirmtext= (TextView) row.findViewById(R.id.confirmtext);
            viewHolder.orderid= (TextView) row.findViewById(R.id.orderid);


            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder)row.getTag();
        }
        final Card card = getItem(position);
        viewHolder.category_title.setText(card.getCategory());
        viewHolder.newspaper.setText(card.getNews());
        viewHolder.edition.setText(card.getEdition());
        viewHolder.date.setText(card.getDate());
        viewHolder.confirmtext.setText(card.getConfirmtext());
        viewHolder.orderid.setText(card.getOrder_id());
        viewHolder.view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),detailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("NEWS",card.getNews());
                intent.putExtra("EDITION",card.getEdition());
                intent.putExtra("DATE",viewHolder.date.getText());
                intent.putExtra("MAINCATEGORY",card.getMaincategory().replace('.',' '));
                intent.putExtra("PAYMENT",card.getPayment());
                intent.putExtra("FIRST_NAME",card.getFirstname());
                intent.putExtra("LAST_NAME",card.getLastname());
                intent.putExtra("EMAIL",card.getEmail());
                intent.putExtra("PHONE",card.getPhone());
                intent.putExtra("COMPOSETEXT",card.getComposetext());
                intent.putExtra("FONTCOLOR",card.getFontcolor());
                intent.putExtra("BACKCOLOR",card.getBackgroundcolor());
                intent.putExtra("IMAGE_HEIGHT",card.getImageheight());
                intent.putExtra("IMAGE_WIDTH",card.getImagewidth());
                intent.putExtra("SPECIAL",card.getSpecial_request().replace('.',' '));
                intent.putExtra("ORDER_ID",card.getOrder_id());
                intent.putExtra("CONFIRM",card.getConfirm());
                getContext().startActivity(intent);



            }
        });



        return row;
    }


    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

}