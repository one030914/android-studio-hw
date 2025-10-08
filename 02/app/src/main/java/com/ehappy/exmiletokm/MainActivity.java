package com.ehappy.exmiletokm;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 宣告全域變數
    private EditText SysV;
    private EditText DiaV;
    private TextView output;
    private Button analysis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 取得介面元件
        SysV=(EditText)findViewById(R.id.SysV);
        DiaV=(EditText)findViewById(R.id.DiaV);
        analysis=(Button)findViewById(R.id.analysis);
        output=(TextView)findViewById(R.id.output);


        //為Button元件加入Click事件的偵聽，觸發時執行自訂方法 btnTranListener
        analysis.setOnClickListener(btnTranListener);
    }

    private Button.OnClickListener btnTranListener=new Button.OnClickListener(){
        public void onClick(View v){
            int x = Integer.parseInt(SysV.getText().toString());
            int y = Integer.parseInt(DiaV.getText().toString());
            if(x >= 140 && y >= 100){
                output.setText("極度危險 (血壓太高)");
                output.setTextColor(Color.RED);
            }else if(x >= 120 && y >= 80){
                output.setText("中度危險 (血壓稍高)");
                output.setTextColor(Color.RED);
            }else if(x >= 100 && y >= 60){
                output.setText("血壓正常");
                output.setTextColor(Color.BLACK);
            }else if(x < 100 && y >= 60 || x >= 100 && y < 60){
                output.setText("中度危險 (血壓稍低)");
                output.setTextColor(Color.GREEN);
            }else{
                output.setText("極度危險 (血壓太低)");
                output.setTextColor(Color.GREEN);
            }
        }
    };
}
