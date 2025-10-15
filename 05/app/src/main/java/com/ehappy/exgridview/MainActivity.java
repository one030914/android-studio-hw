package com.ehappy.exgridview;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int[] imageIds = {
            R.drawable.img01,R.drawable.img02,R.drawable.img03,
            R.drawable.img04
    };

    private RadioGroup serving;
    private Button submit;
    private TextView txtresult;
    private int p1, p2, summary;
    private String s1, s2, str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 取得介面元件
        GridView gridView = (GridView) findViewById(R.id.GridView01);
        serving = (RadioGroup) findViewById(R.id.serving);
        submit = (Button) findViewById(R.id.submit);
        txtresult = (TextView) findViewById(R.id.txtresult);

        // 建立自訂的 Adapter
        MyAdapter adapter=new MyAdapter(this);

        // 設定 GridView 的資料來源
        gridView.setAdapter(adapter);

        // 建立 GridView 的 ItemClick 事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                switch (position){
                    case 0:
                        p1 = 150;
                        s1 = "一號餐";
                        break;
                    case 1:
                        p1 = 200;
                        s1 = "二號餐";
                        break;
                    case 2:
                        p1 = 250;
                        s1 = "三號餐";
                        break;
                    case 3:
                        p1 = 280;
                        s1 = "四號餐";
                        break;
                }
            }
        });

        serving.setOnCheckedChangeListener(Serving);
        submit.setOnClickListener(Submit);
    }

    private RadioGroup.OnCheckedChangeListener Serving = new RadioGroup.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.one){
                s2 = "一份";
                p2 = 1;
            }else if(checkedId == R.id.two){
                s2 = "兩份";
                p2 = 2;
            }else if(checkedId == R.id.three){
                s2 = "三份";
                p2 = 3;
            }
        }
    };

    private Button.OnClickListener Submit = new Button.OnClickListener(){
        public void onClick(View v){
            summary = p1 * p2;
            str = "你選擇 " + s1 + s2 + ", 共 " + summary + " 元";
            txtresult.setText(str);
        }
    };

    // 自訂的 MyAdapter 類別，繼承 BaseAdapter 類別
    class MyAdapter extends BaseAdapter {
        private Context mContext;
        public MyAdapter(Context c){
            mContext=c;
        }
        @Override
        public int getCount(){
            return imageIds.length; // 圖片共有多少張
        }
        @Override
        public Object getItem(int position){
            return position;
        }
        @Override
        public long getItemId(int position){
            return position; // 目前圖片索引
        }

        // 定義 GridView 顯示的圖片
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            ImageView iv = new ImageView(mContext);
            iv.setImageResource(imageIds[position]);
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iv.setLayoutParams(new GridView.LayoutParams(200, 150));
            return iv;
        }
    }
}