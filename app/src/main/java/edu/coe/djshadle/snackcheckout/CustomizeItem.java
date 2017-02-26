package edu.coe.djshadle.snackcheckout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CustomizeItem extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final int MAX_ITEMS = 8;
    private int numItems = 0;

    private ListView mItemList;
    private EditText mItemName;
    private EditText mItemPrice;
    private Button mAddButton, mSaveButton, mResetButton;
    private ArrayList<String> customUDBList;
    private ArrayAdapter<String> customUDBListAdapter;
    private SharedPreferences s, totalShared;
    private SharedPreferences.Editor e, totalEdit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        s = getSharedPreferences("myFile", 0);
        e = s.edit();
        numItems = s.getInt("CInumItems", 0);

        totalShared = getSharedPreferences("myTotalMine", 0);
        totalEdit = totalShared.edit();

        setControls();
        startList();

        e.commit();
        totalEdit.commit();
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
        mItemList.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        String itemName = mItemName.getText().toString(), stringItemPrice = mItemPrice.getText().toString();

        switch (v.getId()) {
            case R.id.CIbtnAdd:
                if (numItems < MAX_ITEMS) {
                    if (!itemName.isEmpty() && !stringItemPrice.isEmpty()) {
                        float itemPrice = Float.parseFloat(stringItemPrice);
                        String itemCollection = itemName + " - $" + String.format("%.2f", itemPrice);

                        customUDBList.add(itemCollection);
                        customUDBListAdapter.notifyDataSetChanged();

                        numItems++;
                    } else {
                        Toast.makeText(this, "Enter item name and price", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Maximum number of items reached", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.CIbtnReset:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to reset the list?").setTitle("Warning");

                builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked reset button
                        numItems = 0;
                        customUDBList.clear();
                        customUDBListAdapter.notifyDataSetChanged();
                        ClearSharedPrefAllItems();
                        e.clear();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked cancel button
                    }
                });
                AlertDialog alert =builder.create();
                alert.show();
                break;
            case R.id.CIbtnSave:
                //save all list items, prices, and numItems in shared preferences
                Toast.makeText(this, "Items have been saved", Toast.LENGTH_SHORT).show();
                SharedPrefAllItems();

                break;
            case R.id.CIListItems:
                Collections.sort(customUDBList);
                customUDBListAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    private void SharedPrefAllItems(){
        e.putInt("CInumItems", numItems);
        Set<String> keys = totalShared.getStringSet("Keys", new HashSet<String>());

        for(int i = 0; i < numItems; i++){
            String itemCollection = customUDBList.get(i);
            int dashIndex = itemCollection.indexOf("-"), dollarIndex = itemCollection.indexOf("$");
            String itemName = itemCollection.substring(0,dashIndex-1);
            Float itemPrice = Float.parseFloat(itemCollection.substring(dollarIndex+1, itemCollection.length()));

            e.putString("CIitemName" + String.valueOf(i), itemName);
            e.putFloat("CIitemPrice" + String.valueOf(i), itemPrice);

            keys.add(itemName);
        }
        totalEdit.putStringSet("Keys", keys);
        Log.d("DA", String.valueOf(keys.size()));
        totalEdit.putInt("numTotalItems", keys.size());

        e.commit();
        totalEdit.commit();
    }

    private void ClearSharedPrefAllItems(){
        e.clear();
        e.putInt("CInumItems", 0);
        e.commit();
    }

    private void startList(){
        for(int i = 0; i < numItems; i++){
            String item = s.getString("CIitemName"+String.valueOf(i), "") + " - $" + String.format("%.2f", s.getFloat("CIitemPrice"+String.valueOf(i), 0));
            customUDBList.add(item);
        }

        customUDBListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        String itemValue = (String)mItemList.getItemAtPosition(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(itemValue);

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked delete button
                numItems--;
                deleteOneSharePref(position);
                //numItems--;
                customUDBList.remove(position);
                customUDBListAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked edit button
                // Creating alert Dialog with one Button
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustomizeItem.this);
                alertDialog.setTitle("Edit");

                // Setting Dialog Message
                alertDialog.setMessage("Enter new item name and item price");
                Context context = CustomizeItem.this;
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText nameBox = new EditText(context);
                nameBox.setHint("Item Name");
                nameBox.setImeActionLabel("Done",EditorInfo.IME_ACTION_DONE);
                layout.addView(nameBox);

                final EditText priceBox = new EditText(context);
                priceBox.setHint("Item Price");
                priceBox.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                priceBox.setImeActionLabel("Done",EditorInfo.IME_ACTION_DONE);
                layout.addView(priceBox);

                alertDialog.setView(layout);

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                // Write your code here to execute after dialog
                                if(!nameBox.getText().toString().isEmpty()&&!priceBox.getText().toString().isEmpty()){
                                    String itemCollection = nameBox.getText().toString() + " - $" + String.format("%.2f", Float.parseFloat(priceBox.getText().toString()));
                                    customUDBList.remove(position);
                                    customUDBList.add(position, itemCollection);
                                    customUDBListAdapter.notifyDataSetChanged();
                                }

                            }
                        });
                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });


                // Showing Alert Message
                alertDialog.show();

            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //user clicked cancel button
            }
        });

        AlertDialog alert =builder.create();
        alert.show();

        Toast.makeText(this, itemValue,Toast.LENGTH_SHORT).show();
    }


    private void deleteOneSharePref(int pos){

        e.remove("CIitemName" + String.valueOf(pos));
        e.remove("CIitemPrice" + String.valueOf(pos));

        for(int i = pos; i < numItems; i++){
            e.putString("CIitemName" + String.valueOf(i), s.getString("CIitemName" + String.valueOf(i+1), ""));
            e.putInt("CIitemPrice" + String.valueOf(i), s.getInt("CIitemPrice" + String.valueOf(i+1), 0));
        }
    }
}
