package com.ehappy.exmultibutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 宣告全域變數
    private TextView txtShow;
    private Button btnZero,btnOne,btnTwo,btnThree,btnFour;
    private Button btnFive,btnSix,btnSeven,btnEight,btnNine;
    private Button plus, sub, mul, div, clear, sum, del, not, sqr, sqrt, left, right;
    private double result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 取得資源類別檔中的介面元件
        txtShow=(TextView)findViewById(R.id.txtShow);
        btnZero=(Button)findViewById(R.id.btnZero);
        btnOne=(Button)findViewById(R.id.btnOne);
        btnTwo=(Button)findViewById(R.id.btnTwo);
        btnThree=(Button)findViewById(R.id.btnThree);
        btnFour=(Button)findViewById(R.id.btnFour);
        btnFive=(Button)findViewById(R.id.btnFive);
        btnSix=(Button)findViewById(R.id.btnSix);
        btnSeven=(Button)findViewById(R.id.btnSeven);
        btnEight=(Button)findViewById(R.id.btnEight);
        btnNine=(Button)findViewById(R.id.btnNine);


        plus=(Button)findViewById(R.id.plus);
        sub=(Button)findViewById(R.id.sub);
        mul=(Button)findViewById(R.id.mul);
        div=(Button)findViewById(R.id.div);
        clear=(Button)findViewById(R.id.clear);
        sum=(Button)findViewById(R.id.sum);
        del=(Button)findViewById(R.id.del);
        not=(Button)findViewById(R.id.not);
        sqr=(Button)findViewById(R.id.sqr);
        sqrt=(Button)findViewById(R.id.sqrt);
        left=(Button)findViewById(R.id.left);
        right=(Button)findViewById(R.id.right);

        // 設定 button 元件 Click 事件共用   myListener
        btnZero.setOnClickListener(myListener);
        btnOne.setOnClickListener(myListener);
        btnTwo.setOnClickListener(myListener);
        btnThree.setOnClickListener(myListener);
        btnFour.setOnClickListener(myListener);
        btnFive.setOnClickListener(myListener);
        btnSix.setOnClickListener(myListener);
        btnSeven.setOnClickListener(myListener);
        btnEight.setOnClickListener(myListener);
        btnNine.setOnClickListener(myListener);

        plus.setOnClickListener(myListener);
        sub.setOnClickListener(myListener);
        mul.setOnClickListener(myListener);
        div.setOnClickListener(myListener);
        clear.setOnClickListener(myListener);
        sum.setOnClickListener(myListener);
        del.setOnClickListener(myListener);
        not.setOnClickListener(myListener);
        sqr.setOnClickListener(myListener);
        sqrt.setOnClickListener(myListener);
        left.setOnClickListener(myListener);
        right.setOnClickListener(myListener);
    }

    // 定義  onClick() 方法
    private Button.OnClickListener myListener=new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            String s=txtShow.getText().toString();
            switch (v.getId()) {
                case R.id.btnZero: {
                    txtShow.setText(s + "0");
                    break;
                }
                case R.id.btnOne: {
                    txtShow.setText(s + "1");
                    break;
                }
                case R.id.btnTwo:{
                    txtShow.setText(s + "2");
                    break;
                }
                case R.id.btnThree:{
                    txtShow.setText(s + "3");
                    break;
                }
                case R.id.btnFour:{
                    txtShow.setText(s + "4");
                    break;
                }
                case R.id.btnFive:{
                    txtShow.setText(s + "5");
                    break;
                }
                case R.id.btnSix:{
                    txtShow.setText(s + "6");
                    break;
                }
                case R.id.btnSeven:{
                    txtShow.setText(s + "7");
                    break;
                }
                case R.id.btnEight:{
                    txtShow.setText(s + "8");
                    break;
                }
                case R.id.btnNine:{
                    txtShow.setText(s + "9");
                    break;
                }
                case R.id.clear:{
                    txtShow.setText("");
                    break;
                }
                case R.id.sum:{
                    break;
                }
                case R.id.plus:{
                    txtShow.setText(s + "+");
                    break;
                }
                case R.id.sub:{
                    txtShow.setText(s + "-");
                    break;
                }
                case R.id.mul:{
                    txtShow.setText(s + "*");
                    break;
                }
                case R.id.div:{
                    txtShow.setText(s + "/");
                    break;
                }
                case R.id.del:{
                    if(s.length()>0) {
                        s=s.substring(0, s.length()-1);
                        txtShow.setText(s);
                    }
                    break;
                }
//                case R.id.not:{
//
//                    break;
//                }
                case R.id.sqr:{
                    txtShow.setText(s + "^2");
                    break;
                }
                case R.id.sqrt:{
                    txtShow.setText("√" + s);
                    break;
                }
                case R.id.left:{
                    txtShow.setText(s + "(");
                    break;
                }
                case R.id.right:{
                    txtShow.setText(s + ")");
                    break;
                }
            }
        }
    };
}
