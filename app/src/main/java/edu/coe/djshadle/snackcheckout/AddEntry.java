package edu.coe.djshadle.snackcheckout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AddEntry extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setButtons();
        setContorls();
    }

    private void setContorls(){

    }

    private void setButtons(){
        Button cancel = (Button) findViewById(R.id.btnCancel);
        Button checkout = (Button) findViewById(R.id.btnCheckout);

        cancel.setOnClickListener(this);
        checkout.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        int temp;

        if(v.getId() == R.id.btnCheckout){
            int q1 = 0, q2 = 0, q3 = 0;
            //get all UDboxes quantiti

            if (q1>0 || q2>0 || q3>0) {
                Intent i = new Intent("edu.coe.djshadle.SnackCheckout.Checkout");

                i.putExtra("q1", q1);
                i.putExtra("q2", q2);
                i.putExtra("q3", q3);

                startActivity(i);
            } else {
                Toast.makeText(this, "Please enter a quantity", Toast.LENGTH_SHORT).show();
            }
        }
        if(v.getId() == R.id.btnCancel) {
            //set all UDboxes to 0
        }
    }
}
