package com.ehappy.exintent03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Second extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page2);

        // 取得介面元件
        TextView txtShow=(TextView)findViewById(R.id.txtShow);
        Button btnHome=(Button)findViewById(R.id.btnHome);

        // 設定 button 的 Listener
        btnHome.setOnClickListener(btnHomeListener);

        // 取得 bundle
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String str = bundle.getString("RESULT");
        txtShow.setText(str);
    }

    private Button.OnClickListener btnHomeListener=new Button.OnClickListener(){
        public void onClick(View v){
            finish();
        }
    };
}