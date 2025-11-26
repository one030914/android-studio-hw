package com.ehappy.excontextmenu;

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
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout myLayout;
    TextView txtShow1;
    TextView txtShow2;

//    private int[] mealIds = new int[]{R.drawable.img01, R.drawable.img02, R.drawable.img03, R.drawable.img04};

    String name = "墾丁中信飯店", phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                uri = Uri.parse("geo:0,0?q="+name);
                intent = new Intent(Intent.ACTION_VIEW, uri);
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
}
