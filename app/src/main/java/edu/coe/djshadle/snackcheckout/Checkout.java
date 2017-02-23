package edu.coe.djshadle.snackcheckout;

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
import android.widget.TextView;
import android.widget.Toast;

public class Checkout extends AppCompatActivity implements View.OnClickListener {
    private TextView total, change, item1, item2, item3, price1, price2, price3;
    private EditText paid;
    private double q1, q2, q3, totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        q1 = getIntent().getIntExtra("q1", 0);
        q2 = getIntent().getIntExtra("q2", 0);
        q3 = getIntent().getIntExtra("q3", 0);
        totalPrice = q1 + q2*.75 + q3*1.5;
        setViews();
        setFields(q1,q2,q3);
        setButtons();

        paid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //not used
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!paid.getText().toString().isEmpty()) {
                    double difference = Double.parseDouble(paid.getText().toString()) - totalPrice;
                    change.setText("$" + String.format("%.2f", difference));
                }
            }
        });
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
        total = (TextView) findViewById(R.id.txtTotal);
        change = (TextView) findViewById(R.id.txtChange);
        item1 = (TextView) findViewById(R.id.txtItem1);
        item2 = (TextView) findViewById(R.id.txtItem2);
        item3 = (TextView) findViewById(R.id.txtItem3);
        price1 = (TextView) findViewById(R.id.txtPrice1);
        price2 = (TextView) findViewById(R.id.txtPrice2);
        price3 = (TextView) findViewById(R.id.txtPrice3);
        paid = (EditText) findViewById(R.id.edtChange);
    }

    private void setFields(double q1, double q2, double q3){
        item1.setText(String.valueOf((int) q1) + " " + "Hotdog");
        item2.setText(String.valueOf((int) q2) + " " + "Candy");
        item3.setText(String.valueOf((int) q3) + " " + "Pop");


        price1.setText("$" + String.format("%.2f", q1));
        price2.setText("$" + String.format("%.2f", q2*.75));
        price3.setText("$" + String.format("%.2f", q3*1.5));

        total.setText("$" + String.format("%.2f", totalPrice));
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
            Intent newIntent = new Intent(this,AddEntry.class);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent);
        }

        if(v.getId() == R.id.btnConfirmCheckout) {
            //checkout: save all the items and then
            if(!paid.getText().toString().isEmpty()) {
                if(Integer.parseInt(paid.getText().toString()) >= totalPrice) {
                    float savedValue = 0;
                    SharedPreferences s = getSharedPreferences("myFile", 0);
                    SharedPreferences.Editor e = s.edit();

                    savedValue = s.getFloat("quantityItem1", 0) + (float) q1;
                    e.putFloat("quantityItem1", savedValue);

                    savedValue = s.getFloat("quantityItem2", 0) + (float) q2;
                    e.putFloat("quantityItem2", savedValue);

                    savedValue = s.getFloat("quantityItem3", 0) + (float) q3;
                    e.putFloat("quantityItem3", savedValue);

                    savedValue = s.getFloat("totalRevenue", 0) + (float) totalPrice;
                    e.putFloat("totalRevenue", savedValue);

                    e.apply();

                    Intent newIntent = new Intent(this,AddEntry.class);
                    newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(newIntent);

                } else {
                    Toast.makeText(this, "Please enter greater amount paid", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter amount paid", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
