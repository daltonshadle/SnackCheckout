package edu.coe.djshadle.snackcheckout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

public class AddEntry extends AppCompatActivity implements View.OnClickListener {

    private String TAG;
    private int numItems;
    private LinearLayout wholeLayout;
    private upDownBox[] udbArray;
    private SharedPreferences s;
    private SharedPreferences.Editor e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TAG = "Dalton";
        s = getSharedPreferences("myFile", 0);
        e = s.edit();

        //only temp
        e.putInt("CInumItems", 0);
        e.apply();

        setButtons();
        setContorls();
    }

    private void setContorls(){
        Log.d(TAG, "Got in the function");
        numItems = s.getInt("CInumItems", 0);
        udbArray = new upDownBox[8];
        wholeLayout = (LinearLayout) findViewById(R.id.content_add_entry);

        TypedArray a = this.obtainStyledAttributes(R.styleable.upDownBox);


        a.recycle();


        for(int i = 0; i < numItems; i++){
            String name = s.getString("CIitemName" + String.valueOf(i), "") + " - $" + String.format("%.2f", s.getFloat("CIitemPrice" + String.valueOf(i), 0));

            udbArray[i] = new upDownBox(this);
            if(udbArray[i]==null){
                Log.d("Dalton", "Prob here");
            }

            udbArray[i].setItemName(name);


            udbArray[i].setLayoutParams(new LinearLayout.LayoutParams(
                   LinearLayout.LayoutParams.MATCH_PARENT,
                   LinearLayout.LayoutParams.WRAP_CONTENT));

            wholeLayout.addView(udbArray[i], i);

        }
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


    @Override
    protected void onResume(){
        super.onResume();
        //need to delete old items before adding new
        setContorls();
    }
}
