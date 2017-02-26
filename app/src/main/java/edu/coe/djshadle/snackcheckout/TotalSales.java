package edu.coe.djshadle.snackcheckout;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Set;

public class TotalSales extends AppCompatActivity implements View.OnClickListener {

    private TextView total;
    private LinearLayout textLayout;
    private Button reset;
    private SharedPreferences s, totalShared;
    private SharedPreferences.Editor e, totalEdit;
    private int numItems, numTotalItems;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_sales);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        s =  getSharedPreferences("myFile", 0);
        e = s.edit();
        totalShared = getSharedPreferences("myTotalFile", 0);
        totalEdit = totalShared.edit();

        numItems = s.getInt("CInumItems", 0);
        numTotalItems = totalShared.getInt("numTotalItems", 0);
        Log.d("DA", "numTotalItems = " + String.valueOf(numTotalItems));

        setIDControls();
        setTextFields();
        setButton();

        e.commit();
    }

    private void setIDControls(){
        total = (TextView) findViewById(R.id.txtPriceTotal);
        textLayout = (LinearLayout) findViewById(R.id.lnrLayoutText);
    }

    private void setTextFields(){
        float savedValue = 0;
        Set<String> keys = totalShared.getStringSet("Keys", new HashSet<String>());
        int temp = keys.size();

        Log.d("DA", "Got in the setTextFields function");

        if(keys!=null) {
            String[] names = keys.toArray(new String[keys.size()]);
            Log.d("DA", String.valueOf(numItems));
            Log.d("DA", String.valueOf(numTotalItems));

            for (int i = 0; i < temp; i++) {
                String name = names[i];

                TextView t = new TextView(this);
                t.setText(name);

                textLayout.addView(t, i);

                Log.d("DA", name);

            }
        }


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

                //delete all shared pref for total items if possible
                totalEdit.clear();
                textLayout.removeAllViewsInLayout();
                textLayout.removeAllViews();
                totalEdit.commit();
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
