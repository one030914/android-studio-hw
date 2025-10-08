package com.ehappy.exmultibutton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 宣告全域變數
    private TextView result, displayOperation;
    private Button btnZero,btnOne,btnTwo,btnThree,btnFour;
    private Button btnFive,btnSix,btnSeven,btnEight,btnNine;
    private Button plus, sub, mul, div, clear, sum, del, not, sqr, sqrt, left, right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 取得資源類別檔中的介面元件
        result=(TextView)findViewById(R.id.result);
        displayOperation=(TextView)findViewById(R.id.displayOperation);

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

        btnZero.setOnClickListener(number);
        btnOne.setOnClickListener(number);
        btnTwo.setOnClickListener(number);
        btnThree.setOnClickListener(number);
        btnFour.setOnClickListener(number);
        btnFive.setOnClickListener(number);
        btnSix.setOnClickListener(number);
        btnSeven.setOnClickListener(number);
        btnEight.setOnClickListener(number);
        btnNine.setOnClickListener(number);

//        plus.setOnClickListener(myListener);
//        sub.setOnClickListener(myListener);
//        mul.setOnClickListener(myListener);
//        div.setOnClickListener(myListener);
//        clear.setOnClickListener(myListener);
//        sum.setOnClickListener(myListener);
//        del.setOnClickListener(myListener);
//        not.setOnClickListener(myListener);
//        sqr.setOnClickListener(myListener);
//        sqrt.setOnClickListener(myListener);
//        left.setOnClickListener(myListener);
//        right.setOnClickListener(myListener);
    }

    private Button.OnClickListener number = new Button.OnClickListener(){
        @Override
        public void onClick(View v){
            Button b = (Button) v;
            result.append(b.getText().toString());
        }
    };
}
