package edu.coe.djshadle.snackcheckout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TotalSales extends AppCompatActivity {

    private TextView item1, item2, item3, price1, price2, price3, total;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_sales);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setIDControls();
        setTextFields();
    }

    private void setIDControls(){
        item1 = (TextView) findViewById(R.id.txtItemTotal1);
        item2 = (TextView) findViewById(R.id.txtItemTotal2);
        item3 = (TextView) findViewById(R.id.txtItemTotal3);
        price1 = (TextView) findViewById(R.id.txtPriceTotal1);
        price2 = (TextView) findViewById(R.id.txtPriceTotal2);
        price3 = (TextView) findViewById(R.id.txtPriceTotal3);
        total = (TextView) findViewById(R.id.txtPriceTotal);
    }

    private void setTextFields(){
        float savedValue = 0;
        SharedPreferences s = getSharedPreferences("myFile", 0);

        savedValue = s.getFloat("quantityItem1", 0);
        item1.setText(String.valueOf((int)savedValue) + " Hotdog");
        price1.setText("$" + String.format("%.2f", savedValue));

        savedValue = s.getFloat("quantityItem2", 0);
        item2.setText(String.valueOf((int)savedValue) + " Candy");
        price2.setText("$" + String.format("%.2f", savedValue*.75));

        savedValue = s.getFloat("quantityItem3", 0);
        item3.setText(String.valueOf((int)savedValue) + " Pop");
        price3.setText("$" + String.format("%.2f", savedValue*1.5));

        savedValue = s.getFloat("totalRevenue", 0);
        total.setText("$" + String.format("%.2f", savedValue));

    }

}
