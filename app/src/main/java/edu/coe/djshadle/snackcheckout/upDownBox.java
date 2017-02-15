package edu.coe.djshadle.snackcheckout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.StringBuilderPrinter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Shadle on 2/14/2017.
 */

public class upDownBox extends LinearLayout implements View.OnClickListener {

    private Button mUpButton, mDownButton;
    private TextView mValueText, mItemName;
    private int startVal;
    //private String startItemName = "Item";
    //m is for member variables

    public upDownBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.upDownBox);
        startVal = ta.getInt(R.styleable.upDownBox_startValue, 0);
        //startItemName = ta.getString(R.styleable.upDownBox_itemName);
        ta.recycle();
    }

    public upDownBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.upDownBox);
        startVal = ta.getInt(R.styleable.upDownBox_startValue, 0);
        //startItemName = ta.getString(R.styleable.upDownBox_itemName);
        ta.recycle();
    }

    public upDownBox(Context context) {
        super(context);
        initializeViews(context);
    }

    private void initializeViews(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.updownbox, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mDownButton = (Button) findViewById(R.id.btnMinusUDB);
        mUpButton = (Button) findViewById(R.id.btnPlusUDB);
        mValueText = (TextView) findViewById(R.id.txtValueUDB);
        mItemName = (TextView) findViewById(R.id.txtItemNameUDB);

        mUpButton.setOnClickListener(this);
        mDownButton.setOnClickListener(this);
        setValue(startVal);
        //setItemName(startItemName);
    }

    public int getValue(){
        return Integer.parseInt(mValueText.getText().toString());
    }

    public void setValue(int v){
        if(v>=0) {
            mValueText.setText(String.valueOf(v));
        }
    }

    /*
    public String getItemName(){
        return mItemName.getText().toString();
    }

    public void setItemName(String s){
        if(!s.isEmpty()){
           mItemName.setText(s);
        }
    }
    */

    @Override
    public void onClick(View v) {
        int val = getValue();

        if(v.getId()==R.id.btnPlusUDB){
            val++;
        } else {
            if(val>0){
                val--;
            }
        }

        setValue(val);
    }
}
