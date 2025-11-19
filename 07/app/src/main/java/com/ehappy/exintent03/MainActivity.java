package com.ehappy.exintent03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private RadioGroup size;
    private MealsAdapter mealsAdapter;

    private int[] mealsIds = {R.drawable.img01, R.drawable.img02, R.drawable.img03};
    private String[] meals = {"一號餐", "二號餐", "三號餐"};
    private String[] price = {"$250", "$200", "$150"};

    private double p1 = 0, p2 = 0, summary = 0, count;
    private String s1, s2, str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // component
        listView = (ListView)findViewById(R.id.listview);
        size = (RadioGroup)findViewById(R.id.size);
        Button btnPage2 = (Button)findViewById(R.id.btnPage2);

        // adapter
        mealsAdapter = new MealsAdapter(this);
        count = mealsAdapter.getCount();

        // listener
        listView.setAdapter(mealsAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(listViewListener);
        size.setOnCheckedChangeListener(Size);
        btnPage2.setOnClickListener(btnPage2Listener);
    }

    public class MealsAdapter extends BaseAdapter {
        private LayoutInflater myInflater;
        public MealsAdapter(Context c){
            myInflater = LayoutInflater.from(c);
        }
        @Override
        public int getCount(){
            return meals.length;
        }
        @Override
        public Object getItem(int position){
            return meals[position];
        }
        @Override
        public long getItemId(int position){
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if (convertView == null){
                convertView = myInflater.inflate(R.layout.mylayout, parent, false);
            }

            // component
            ImageView imgLogo = (ImageView) convertView.findViewById(R.id.imgLogo);
            TextView txtName = ((TextView) convertView.findViewById(R.id.txtName));
            TextView txtengName = ((TextView) convertView.findViewById(R.id.price));
            CheckedTextView checkedTextView = (CheckedTextView) convertView.findViewById(R.id.check);

            boolean isChecked = listView.isItemChecked(position);
            checkedTextView.setChecked(isChecked);

            // component's content
            imgLogo.setImageResource(mealsIds[position]);
            txtName.setText(meals[position]);
            txtengName.setText(price[position]);

            return convertView;
        }
    }

    private ListView.OnItemClickListener listViewListener=new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (listView.isItemChecked(position)){
                switch (position){
                    case 0:
                        p1 += 250;
                        break;
                    case 1:
                        p1 += 200;
                        break;
                    case 2:
                        p1 += 150;
                        break;
                }
            }else{
                switch (position){
                    case 0:
                        p1 -= 250;
                        break;
                    case 1:
                        p1 -= 200;
                        break;
                    case 2:
                        p1 -= 150;
                        break;
                }
            }

            mealsAdapter.notifyDataSetChanged();
        }
    };

    private RadioGroup.OnCheckedChangeListener Size=
        new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.big:
                        p2 = 2;
                        s2 = "大 (2倍)";
                        break;
                    case R.id.mid:
                        p2 = 1.5;
                        s2 = "中 (1.5倍)";
                        break;
                    case R.id.small:
                        p2 = 1;
                        s2 = "小";
                        break;
                }
            }
        };

    private Button.OnClickListener btnPage2Listener=new Button.OnClickListener(){
        public void onClick(View v){
            Intent intent=new Intent();
            intent.setClass(MainActivity.this,Second.class);
            Bundle bundle=new Bundle();

            s1 = "";
            for(int p = 0; p < count; p++){
                if (listView.isItemChecked(p))
                    s1 += meals[p] + " " + price[p] + "\n";
            }

            summary = p1 * p2;
            str = s1 + s2 + "\n共 " + summary + " 元";
            bundle.putString("RESULT", str);
            intent.putExtras(bundle);

            // 執行附帶資料的 Intent
            startActivity(intent);
        }
    };
}
