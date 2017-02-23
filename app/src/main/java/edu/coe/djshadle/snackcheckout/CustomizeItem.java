package edu.coe.djshadle.snackcheckout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomizeItem extends AppCompatActivity implements View.OnClickListener {

    private static final int MAX_ITEMS = 8;
    private int numItems = 0;

    private ListView mItemList;
    private EditText mItemName;
    private EditText mItemPrice;
    private Button mAddButton, mSaveButton, mResetButton;
    private ArrayList<String> customUDBList;
    private ArrayAdapter<String> customUDBListAdapter;
    private SharedPreferences s;
    private SharedPreferences.Editor e;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        s = getSharedPreferences("myFile", 0);
        e = s.edit();

        setControls();
    }

    private void setControls(){
        mItemName = (EditText) findViewById(R.id.CIedtItemName);
        mItemPrice = (EditText) findViewById(R.id.CIedtItemPrice);
        mAddButton = (Button) findViewById(R.id.CIbtnAdd);
        mSaveButton = (Button) findViewById(R.id.CIbtnSave);
        mResetButton = (Button) findViewById(R.id.CIbtnReset);
        mItemList = (ListView) findViewById(R.id.CIListItems);

        mAddButton.setOnClickListener(this);
        mSaveButton.setOnClickListener(this);
        mResetButton.setOnClickListener(this);

        customUDBList = new ArrayList<String>();

        customUDBListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, customUDBList);
        mItemList.setAdapter(customUDBListAdapter);
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
        String itemName = mItemName.getText().toString(), stringItemPrice = mItemPrice.getText().toString();

        switch (v.getId()) {
            case R.id.CIbtnAdd:
                if (numItems < 9) {
                    if (!itemName.isEmpty() && !stringItemPrice.isEmpty()) {
                        float itemPrice = Float.parseFloat(stringItemPrice);
                        String itemCollection = itemName + " - $" + String.format("%.2f", itemPrice);

                        customUDBList.add(itemCollection);
                        customUDBListAdapter.notifyDataSetChanged();

                        numItems++;
                        //maybe put these when they save the item list
                        e.putString("itemName" + String.valueOf(numItems), itemName);
                        e.putFloat("itemPrice" + String.valueOf(numItems), itemPrice);
                    } else {
                        Toast.makeText(this, "Enter item name and price", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Maximum number of items reached", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.CIbtnReset:

                break;
            case R.id.CIbtnSave:

                break;
            default:
                break;
        }

    }
}
