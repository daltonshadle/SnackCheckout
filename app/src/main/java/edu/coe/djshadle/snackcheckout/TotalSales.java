package edu.coe.djshadle.snackcheckout;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TotalSales extends AppCompatActivity implements View.OnClickListener {

    private TextView total;
    private Button reset;
    private SharedPreferences s;
    private SharedPreferences.Editor e;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_sales);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        s =  getSharedPreferences("myFile", 0);
        e = s.edit();


        setIDControls();
        setTextFields();
        setButton();

        e.commit();
    }

    private void setIDControls(){
        //total = (TextView) findViewById(R.id.txtPriceTotal);
    }

    private void setTextFields(){
        float savedValue = 0;



    }

    private void setButton(){
        reset = (Button) findViewById(R.id.btnResetInventory);
        reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to reset inventory?").setTitle("Warning");

        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked reset button
                SharedPreferences.Editor e = s.edit();

                //delete all shared pref for total items if possible
                e.apply();

                setTextFields();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked cancel button
            }
        });
        AlertDialog alert =builder.create();
        alert.show();
    }
}
