package com.ehappy.exradiobutton02;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView txtResult;
    private RadioGroup dish, drink, serving;
    private Button submit;
    private String str, s1, s2, s3;
    private int summary, p1, p2, p3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 取得介面元件 id
        txtResult=(TextView) findViewById(R.id.txtResult);
        submit = (Button) findViewById(R.id.submit);

        dish = (RadioGroup) findViewById(R.id.dish);
        drink = (RadioGroup) findViewById(R.id.drink);
        serving = (RadioGroup) findViewById(R.id.serving);

        dish.setOnCheckedChangeListener(main_dish);
        drink.setOnCheckedChangeListener(Drink);
        serving.setOnCheckedChangeListener(Serving);
        submit.setOnClickListener(Submit);
    }

    private RadioGroup.OnCheckedChangeListener main_dish = new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.pork){
                s1 = "豬排飯";
                p1 = 120;
            }else if(checkedId == R.id.chicken){
                s1 = "雞腿飯";
                p1 = 150;
            }else if(checkedId == R.id.rice){
                s1 = "炒飯";
                p1 = 65;
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener Drink = new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.juice){
                s2 = "果汁";
                p2 = 20;
            }else if(checkedId == R.id.blacktea){
                s2 = "紅茶";
                p2 = 15;
            }else if(checkedId == R.id.greentea){
                s2 = "綠茶";
                p2 = 10;
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener Serving = new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.one){
                s3 = "一份";
                p3 = 1;
            }else if(checkedId == R.id.two){
                s3 = "兩份";
                p3 = 2;
            }else if(checkedId == R.id.three){
                s3 = "三份";
                p3 = 3;
            }
        }
    };

    private Button.OnClickListener Submit = new Button.OnClickListener(){
        public void onClick(View v){
            summary = (p1 + p2) * p3;
            str = "你選擇 " + s1 + ", " + s2 + ", " + s3 + ", 共 " + summary + " 元";
            txtResult.setText(str);
        }
    };
}