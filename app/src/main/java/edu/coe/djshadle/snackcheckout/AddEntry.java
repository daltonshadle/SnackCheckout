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

public class AddEntry extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "Dalton";
    TextView itemQ1, itemQ2, itemQ3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setButtons();
        setTextFields();
    }

    private void setTextFields(){
        itemQ1 = (TextView) findViewById(R.id.txtQuantity1);
        itemQ2 = (TextView) findViewById(R.id.txtQuantity2);
        itemQ3 = (TextView) findViewById(R.id.txtQuantity3);
    }

    private void setButtons(){
        Button cancel = (Button) findViewById(R.id.btnCancel);
        Button checkout = (Button) findViewById(R.id.btnCheckout);
        Button m1 = (Button) findViewById(R.id.btnMinus1);
        Button p1 = (Button) findViewById(R.id.btnPlus1);
        Button m2 = (Button) findViewById(R.id.btnMinus2);
        Button p2 = (Button) findViewById(R.id.btnPlus2);
        Button m3 = (Button) findViewById(R.id.btnMinus3);
        Button p3 = (Button) findViewById(R.id.btnPlus3);
        cancel.setOnClickListener(this);
        checkout.setOnClickListener(this);
        m1.setOnClickListener(this);
        p1.setOnClickListener(this);
        m2.setOnClickListener(this);
        p2.setOnClickListener(this);
        m3.setOnClickListener(this);
        p3.setOnClickListener(this);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        int temp;
        if(b.getText().equals("-")){
            if(b.getTag().toString().equals("1")){
                temp = Integer.parseInt(itemQ1.getText().toString());
                if(temp>0){
                    temp--;
                    itemQ1.setText(String.valueOf(temp));
                }
            }
            if(b.getTag().toString().equals("2")){
                temp = Integer.parseInt(itemQ2.getText().toString());
                if(temp>0){
                    temp--;
                    itemQ2.setText(String.valueOf(temp));
                }
            }
            if(b.getTag().toString().equals("3")){
                temp = Integer.parseInt(itemQ3.getText().toString());
                if(temp>0){
                    temp--;
                    itemQ3.setText(String.valueOf(temp));
                }
            }
        }
        if(b.getText().equals("+")){
            if(b.getTag().toString().equals("1")){
                temp = Integer.parseInt(itemQ1.getText().toString());
                temp++;
                itemQ1.setText(String.valueOf(temp));
            }
            if(b.getTag().toString().equals("2")){
                temp = Integer.parseInt(itemQ2.getText().toString());
                temp++;
                itemQ2.setText(String.valueOf(temp));
            }
            if(b.getTag().toString().equals("3")){
                temp = Integer.parseInt(itemQ3.getText().toString());
                temp++;
                itemQ3.setText(String.valueOf(temp));
            }
        }
        if(v.getId() == R.id.btnCheckout){
            int q1 = 0, q2 = 0, q3 = 0;
            q1 = Integer.parseInt(itemQ1.getText().toString());
            q2 = Integer.parseInt(itemQ2.getText().toString());
            q3 = Integer.parseInt(itemQ3.getText().toString());

            Intent i = new Intent("edu.coe.djshadle.SnackCheckout.Checkout");

            i.putExtra("q1", q1);
            i.putExtra("q2", q2);
            i.putExtra("q3", q3);

            Log.d(TAG, "Got to before starting activity");
            startActivity(i);
            Log.d(TAG, "Got to after starting activity");
        }
        if(v.getId() == R.id.btnCancel) {
            itemQ3.setText("0");
            itemQ2.setText("0");
            itemQ1.setText("0");
        }
    }
}
