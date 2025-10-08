package com.ehappy.exmiletokm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 宣告全域變數
    private EditText edtMile;
    private TextView txtKm;
    private Button btnTran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 取得介面元件
        edtMile=(EditText)findViewById(R.id.edtMile);
        txtKm=(TextView)findViewById(R.id.txtKm);
        btnTran=(Button)findViewById(R.id.btnTran);

        //為Button元件加入Click事件的偵聽，觸發時執行自訂方法 btnTranListener
        btnTran.setOnClickListener(btnTranListener);
    }

    private Button.OnClickListener btnTranListener=new Button.OnClickListener(){
        public void onClick(View v){
            double miles=Double.parseDouble(edtMile.getText().toString());
            double km=Math.round((miles - 32) / 1.8 * 100) / 100.0;
            txtKm.setText("攝氏= " + km + " 度");
        }
    };
}
