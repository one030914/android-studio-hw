package com.ehappy.exlistview04;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ListView lstPrefer;
    private TextView txtresult;

    private int[] mealIds = new int[]{R.drawable.img01, R.drawable.img02, R.drawable.img03, R.drawable.img04};
    private int[] drinkIds = new int[]{R.drawable.blacktea, R.drawable.latte,R.drawable.cola};
    private String[] drinks = new String[] {"冰紅茶", "熱拿鐵", "可樂"};
    private String[] engNames = {"iced black tea", "hot latte", "cola"};

    private int p1 = 0, p2 = 0,summary = 0, count;
    private String s1, str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 取得介面元件
        GridView meals = (GridView) findViewById(R.id.meals);
        lstPrefer=(ListView)findViewById(R.id.lstPrefer);
        txtresult = (TextView) findViewById(R.id.txtresult);

        // 建立自訂的 Adapter
        MealsAdapter mealsAdapter = new MealsAdapter(this);
        DrinkAdapter drinkadapter = new DrinkAdapter(this);

        count = drinkadapter.getCount();

        meals.setAdapter(mealsAdapter);
        meals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                switch (position){
                    case 0:
                        p1 = 100;
                        s1 = "一號餐";
                        break;
                    case 1:
                        p1 = 120;
                        s1 = "二號餐";
                        break;
                    case 2:
                        p1 = 140;
                        s1 = "三號餐";
                        break;
                    case 3:
                        p1 = 150;
                        s1 = "四號餐";
                        break;
                }
            }
        });

        // 設定 ListView 的資料來源
        lstPrefer.setAdapter(drinkadapter);
        lstPrefer.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lstPrefer.setOnItemClickListener(lstPreferListener);
    }

    class MealsAdapter extends BaseAdapter {
        private Context mContext;
        public MealsAdapter(Context c){
            mContext=c;
        }
        @Override
        public int getCount(){
            return mealIds.length; // 圖片共有多少張
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
            iv.setImageResource(mealIds[position]);
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iv.setLayoutParams(new GridView.LayoutParams(200, 150));
            return iv;
        }
    }

    public class DrinkAdapter extends BaseAdapter {
        private LayoutInflater myInflater;
        public DrinkAdapter(Context c){
            myInflater = LayoutInflater.from(c);
        }
        @Override
        public int getCount(){
            return drinks.length;
        }
        @Override
        public Object getItem(int position){
            return drinks[position];
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

            // 取得 mylayout.xml 中的元件
            CheckedTextView checkedTextView = (CheckedTextView) convertView.findViewById(R.id.check);
            ImageView imgLogo = (ImageView) convertView.findViewById(R.id.imgLogo);
            TextView txtName = ((TextView) convertView.findViewById(R.id.txtName));
            TextView txtengName = ((TextView) convertView.findViewById(R.id.txtengName));

            // 設定元件內容
            boolean isChecked = lstPrefer.isItemChecked(position);
            checkedTextView.setChecked(isChecked);
            imgLogo.setImageResource(drinkIds[position]);
            txtName.setText(drinks[position]);
            //或 txtName.setText(""+getItem(position));
            txtengName.setText(engNames[position]);

            return convertView;
        }
    }

    private ListView.OnItemClickListener lstPreferListener=
        new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (lstPrefer.isItemChecked(position)){ // 巳核選
                    switch (position){
                        case 0:
                            p2 += 30;
                            break;
                        case 1:
                            p2 += 60;
                            break;
                        case 2:
                            p2 += 40;
                            break;
                    }
                }else{
                    switch (position){
                        case 0:
                            p2 -= 30;
                            break;
                        case 1:
                            p2 -= 60;
                            break;
                        case 2:
                            p2 -= 40;
                            break;
                    }
                }
                if(s1 != null){
                    String selAll="";
                    for(int p = 0; p < count; p++){
                        if (lstPrefer.isItemChecked(p)) // 巳核選
                            selAll += ", " + drinks[p];
                    }
                    summary = p1 + p2;
                    str = "你選擇" + s1 + selAll + " 共 " + summary + " 元";
                    txtresult.setText(str);
                }
            }
        };
}