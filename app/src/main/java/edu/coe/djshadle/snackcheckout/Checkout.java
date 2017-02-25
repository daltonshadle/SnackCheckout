package edu.coe.djshadle.snackcheckout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Checkout extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout wholeLayout;
    private TextView totalText, changeText;
    private EditText moneyPaid;
    private int numItems;
    private float totalPrice;
    private SharedPreferences s;
    private SharedPreferences.Editor e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        s = getSharedPreferences("myFile", 0);
        e = s.edit();

        numItems = s.getInt("CInumItems", 0);
        totalPrice = calcTotalPrice();


        //get all values and totalPrice
        setViews();
        setButtons();
        putAllTextsInLayout();

        //edit text. addTextChangedListener(new TextWatcher)

        moneyPaid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                float paid = Float.parseFloat(moneyPaid.getText().toString());
                float change = paid - totalPrice;

                changeText.setText("$" + String.format("%.2f", change));

            }
        });


        e.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sales) {
            Intent i = new Intent("edu.coe.djshadle.SnackCheckout.TotalSales");
            startActivity(i);
            return true;
        }

        if (id == R.id.action_customize) {
            Intent i = new Intent("edu.coe.djshadle.SnackCheckout.CustomizeItem");
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setViews(){
        moneyPaid = (EditText) findViewById(R.id.edtMoneyPaid);
        wholeLayout = (LinearLayout) findViewById(R.id.content_checkout);
        changeText = (TextView) findViewById(R.id.txtChange);
        totalText = (TextView) findViewById(R.id.txtTotal);

        totalText.setText("$" + String.format("%.2f", totalPrice));
    }


    private void setButtons(){
        Button confirm = (Button) findViewById(R.id.btnConfirmCheckout);
        Button cancel = (Button) findViewById(R.id.btnCancelCheckout);

        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnCancelCheckout) {
            //go back to the other activity with everything reset
            Intent returnIntent = new Intent();
            //newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        }

        if(v.getId() == R.id.btnConfirmCheckout) {
            //checkout: save all the items in total page shared pref and restart the first intent blank
            if(!moneyPaid.getText().toString().isEmpty()) {
                if(Float.parseFloat(moneyPaid.getText().toString()) >= totalPrice) {

                    //put all the stuff in the total thing



                    Intent newIntent = new Intent();
                    //newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    setResult(Activity.RESULT_OK, newIntent);
                    finish();

                } else {
                    Toast.makeText(this, "Please enter greater amount paid", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter amount paid", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private float calcTotalPrice(){
        float tempTotalPrice = 0;
        float itemPrice = 0;
        int itemQuant = 0;
        for(int i = 0; i < numItems; i++){
            itemPrice = s.getFloat("CIitemPrice" + String.valueOf(i), 0);
            itemQuant = s.getInt("AEitemQuantity" + String.valueOf(i), 0);
            tempTotalPrice += itemPrice * itemQuant;
        }

        return tempTotalPrice;
    }

    private void putAllTextsInLayout(){
        int pos = 0;
        for(int i = 0; i < numItems; i ++) {
            if(s.getInt("AEitemQuantity" + String.valueOf(i), 0) != 0) {
                LinearLayout temp = new LinearLayout(this);
                temp.setOrientation(LinearLayout.HORIZONTAL);
                temp.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                TextView itemName = new TextView(this);
                TextView itemPrice = new TextView(this);

                String name = s.getInt("AEitemQuantity" + String.valueOf(i), 0) + " x " + s.getString("CIitemName" + String.valueOf(i), "");
                String price = "$" + String.format("%.2f", s.getInt("AEitemQuantity" + String.valueOf(i), 0)*s.getFloat("CIitemPrice" + String.valueOf(i), 0));

                itemName.setText(name);
                itemPrice.setText(price);
                itemPrice.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

                temp.addView(itemName);
                temp.addView(itemPrice);


                wholeLayout.addView(temp, pos);
                pos++;
            }
        }
    }

    private void updateAllItemsInTotal(){

        //save key as the name of the item
        //how to retrieve item?


        for(int i = 0; i < numItems; i++){
            float tempTotal = s.getFloat("CIitemPrice" + String.valueOf(i), 0);
            int tempItemQuant = s.getInt("AEitemQuantity" + String.valueOf(i), 0);
            String tempName = s.getString("CIitemName" + String.valueOf(i), "");

            



        }
    }

}
