package com.android.anup.creditcardapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv_card_bal, tv_int_yrly, tv_min_pay, tv_fin_bal, tv_month_rem, tv_int_pay;
    EditText et_card_bal, et_int_yrly, et_min_pay, et_fin_bal, et_month_rem, et_int_pay;
    Button comp, clear;
    float principle, rate, min_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_card_bal = (TextView)findViewById(R.id.tv_card_bal);
        tv_int_yrly = (TextView)findViewById(R.id.tv_interest);
        tv_min_pay = (TextView)findViewById(R.id.tv_min_pay);
        tv_fin_bal = (TextView)findViewById(R.id.tv_final_bal);
        tv_month_rem = (TextView)findViewById(R.id.tv_month_rem);
        tv_int_pay = (TextView)findViewById(R.id.tv_int_paid);

        et_card_bal = (EditText) findViewById(R.id.et_card_bal);
        et_int_yrly = (EditText) findViewById(R.id.et_interest);
        et_min_pay = (EditText) findViewById(R.id.et_min_pay);
        et_fin_bal = (EditText) findViewById(R.id.et_final_bal);
        et_month_rem = (EditText) findViewById(R.id.et_month_rem);
        et_int_pay = (EditText) findViewById(R.id.et_int_paid);


        et_fin_bal.setEnabled(false);
        et_month_rem.setEnabled(false);
        et_int_pay.setEnabled(false);

        comp = (Button)findViewById(R.id.but_comput);
        clear = (Button)findViewById(R.id.but_clear);
            comp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    principle = Float.parseFloat(String.valueOf(et_card_bal.getText()));
                    rate = Float.parseFloat((et_int_yrly.getText()).toString());
                    min_payment = Float.parseFloat(String.valueOf(et_min_pay.getText()));
                    float[] soln=total_floatinterest_payable(principle, rate, min_payment);
                    et_fin_bal.setText(String.valueOf(soln[1]));
                    et_month_rem.setText(String.valueOf(soln[2]));
                    et_int_pay.setText(String.valueOf(soln[0]));
                }
            });
        clear.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ViewGroup group = (ViewGroup)findViewById(R.id.activity_main);
                for (int i = 0, count = group.getChildCount(); i < count; ++i) {
                    View view = group.getChildAt(i);
                    if (view instanceof EditText) {
                        ((EditText)view).setText("");
                    }
                }
            }
        });
    }

    private float[] total_floatinterest_payable(float principle, float rate, float minimum_payment){
        float monthlyfloatInterestPaid, monthlyPrinciple, monthlyBalance;
        float data[] = new float[3];
        float count = 0;


        do {

            count++;
            monthlyfloatInterestPaid = Math.round((principle * (rate / (100 * 12))));// 1
            monthlyPrinciple = minimum_payment - monthlyfloatInterestPaid;// 2
            monthlyBalance = principle - monthlyPrinciple;// 3
            principle = monthlyBalance;// 4

            if (count == 1) { //for 1stt month

                data[0] = monthlyfloatInterestPaid;

                data[1] = monthlyBalance;

                Log.e("Monthly", "Interest and balance" + monthlyfloatInterestPaid +"," +monthlyBalance);


            }

        } while (monthlyBalance > 0);


        Log.e("Count", "is " + count);
        data[2] = count - 1; //months remaining

        return data;


    }
}
