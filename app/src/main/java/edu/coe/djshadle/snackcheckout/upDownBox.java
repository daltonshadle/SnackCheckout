package edu.coe.djshadle.snackcheckout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
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
    private int startVal, plusBtnColor, minusBtnColor;
    private String startItemName;
    //m is for member variables

    public upDownBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);

        startItemName = "Item";
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.upDownBox);
        startVal = ta.getInt(R.styleable.upDownBox_startValue, 0);
        startItemName = ta.getString(R.styleable.upDownBox_startItemName);
        plusBtnColor = ta.getColor(R.styleable.upDownBox_plusButtonColor, Color.GRAY);
        minusBtnColor = ta.getColor(R.styleable.upDownBox_minusButtonColor, Color.GRAY);
        ta.recycle();
    }

    public upDownBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);

        startItemName = "Item";
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.upDownBox);
        startVal = ta.getInt(R.styleable.upDownBox_startValue, 0);
        startItemName = ta.getString(R.styleable.upDownBox_startItemName);
        plusBtnColor = ta.getColor(R.styleable.upDownBox_plusButtonColor, Color.GRAY);
        minusBtnColor = ta.getColor(R.styleable.upDownBox_minusButtonColor, Color.GRAY);
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
        setItemName(startItemName);
        setPlusButtonColor(plusBtnColor);
        setMinusButtonColor(minusBtnColor);
    }


    //value getter and setter
    public int getValue(){
        return Integer.parseInt(mValueText.getText().toString());
    }
    public void setValue(int v){
        if(v>=0) {
            mValueText.setText(String.valueOf(v));
        }
    }

    //item name getter and setter
    public String getItemName(){
        return mItemName.getText().toString();
    }
    public void setItemName(String s){
        if(s!=null){
            if(!s.isEmpty()) {
                Log.d("Dalton", "Set name function");
                Log.d("Dalton", s);
                mItemName.setText(s);
                Log.d("Dalton", s + " After");
            }
        }
    }

    //buton color setters
    public void setPlusButtonColor(int c){
        mUpButton.setBackgroundColor(c);
    }
    public void setMinusButtonColor(int c){
        mDownButton.setBackgroundColor(c);
    }


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
