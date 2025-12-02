package com.ehappy.excontextmenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Second extends AppCompatActivity {
    private ListView listView;
    private TextView result;
    private Button sumbit;

    private String[] meals = new String[]{"一號餐", "二號餐", "三號餐", "四號餐"};
    private String hotel, name;
    private int price;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_page);

        listView = (ListView) findViewById(R.id.meals);
        result = (TextView) findViewById(R.id.result);
        sumbit = (Button) findViewById(R.id.submit);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        hotel = bundle.getString("HOTEL");

        ArrayAdapter<String> adapterMeals = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, meals);
        listView.setAdapter(adapterMeals);
        listView.setOnItemClickListener(
            new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    name = meals[position];
                    switch (position) {
                        case 0:
                            price = 100;
                            break;
                        case 1:
                            price = 120;
                            break;
                        case 2:
                            price = 130;
                            break;
                        case 3:
                            price = 140;
                            break;
                    }
                }
            }
        );
        sumbit.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                result.setText("你選擇：" + hotel + ", " + name + " " + price + " 元");
            }
        });
    }
}
