package com.ehappy.excontextmenu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout myLayout;
    TextView txtShow1;
    TextView txtShow2;

    private int[] hotelIds = new int[]{R.drawable.img01, R.drawable.img02, R.drawable.img03, R.drawable.img04};

    String name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        GridView hotels = (GridView) findViewById(R.id.hotels);

        HotelsAdapter hotelsAdapter = new HotelsAdapter(this);
        hotels.setAdapter(hotelsAdapter);
        hotels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                switch (position){
                    case 0:
                        name = "墾丁福華飯店";
                        phone = "088862323";
                        break;
                    case 1:
                        name = "河堤墾丁渡假酒店";
                        phone = "088893138";
                        break;
                    case 2:
                        name = "墾丁凱悅會館";
                        phone = "0938856181";
                        break;
                    case 3:
                        name = "墾丁凱薩飯店";
                        phone = "088861888";
                        break;
                }
            }
        });

        myLayout=(ConstraintLayout)findViewById(R.id.myLayout);
        txtShow1=(TextView)findViewById(R.id.txtShow1);
        txtShow2=(TextView)findViewById(R.id.txtShow2);

        registerForContextMenu(txtShow1);
        registerForContextMenu(txtShow2);
    }

    protected static final int MENU_HOTELMAP = Menu.FIRST ;
    protected static final int MENU_HOTELPHONE = Menu.FIRST +1;
    protected static final int MENU_MEALORDER = Menu.FIRST +2;
    protected static final int MENU_SOUVENIRORDER = Menu.FIRST +3;
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v==txtShow1){
            menu.add(0,MENU_HOTELMAP ,1,"顯示飯店地圖");
            menu.add(0,MENU_HOTELPHONE ,2,"撥電話給飯店");
        }
        else if (v==txtShow2){
            menu.add(0,MENU_MEALORDER ,1,"飯店餐點訂購");
            menu.add(0,MENU_SOUVENIRORDER ,2,"飯店紀念品訂購");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Uri uri;
        Intent intent = new Intent();
        switch (item.getItemId()){
            case MENU_HOTELMAP:
                String query = Uri.encode(name);
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + query);
                intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
                break;
            case MENU_HOTELPHONE:
                uri = Uri.parse("tel:"+phone);
                intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
                break;
            case MENU_MEALORDER:
                intent.setClass(MainActivity.this, Second.class);
                Bundle bundle = new Bundle();
                bundle.putString("HOTEL", name);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case MENU_SOUVENIRORDER:
                break;
        }

        return super.onContextItemSelected(item);
    }

    class HotelsAdapter extends BaseAdapter {
        private Context mContext;
        public HotelsAdapter(Context c){
            mContext=c;
        }
        @Override
        public int getCount(){
            return hotelIds.length; // 圖片共有多少張
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
            iv.setImageResource(hotelIds[position]);
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iv.setLayoutParams(new GridView.LayoutParams(200, 150));
            return iv;
        }
    }
}
