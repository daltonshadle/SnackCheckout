package edu.coe.djshadle.snackcheckout;

import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Checkout extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "Dalton";
    private TextView total, change, item1, item2, item3, price1, price2, price3;
    private EditText paid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Got to the other activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG, "Got to after activity creation");
        double tempTotal = 0;
        int q1 = getIntent().getIntExtra("q1", 0), q2 = getIntent().getIntExtra("q2", 0), q3 = getIntent().getIntExtra("q3", 0);
        setViews();
        setFields(q1,q2,q3);
        setButtons();
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

    private void setFields(int q1, int q2, int q3){
        item1.setText(String.valueOf(q1) + " " + "Hotdog");
        item2.setText(String.valueOf(q2) + " " + "Candy");
        item3.setText(String.valueOf(q3) + " " + "Pop");

        price1.setText("$" + String.valueOf(q1));
        price2.setText("$" + String.valueOf(q2));
        price3.setText("$" + String.valueOf(q3));

        total.setText("$" + String.valueOf(q1+q2*.75+q3*1.5));
    }

    private void setButtons(){
        Button confirm = (Button) findViewById(R.id.btnConfirmCheckout);
        Button cancel = (Button) findViewById(R.id.btnCancelCheckout);

        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCancelCheckout:

        }
    }
}
