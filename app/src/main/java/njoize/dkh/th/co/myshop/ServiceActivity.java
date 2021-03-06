package njoize.dkh.th.co.myshop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zj.wfsdk.WifiCommunication;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity {

    //    Explicit
    private MyConstant myConstant = new MyConstant();
    private String nameString;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private boolean aBoolean = true; // True ==> Check Internet
    private WifiCommunication wifiCommunication;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

//        Huawei Policy
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        Check Internet
        checkInternet();

//        Check Printer
        checkPrinter();

//        Get ValueLogin
        getValueLogin();

//        Create Toolbar
        createToolbar();

//        Add Fragment
        addFragment(savedInstanceState);

//        Create DrawerMenu
        createDrawerMenu();


    } // Main Method

    private void openCashDrawer() {
        MyConstant myConstant = new MyConstant();
        wifiCommunication = new WifiCommunication(ocHandler);
        wifiCommunication.initSocket(myConstant.getIpAddressPrinter(), myConstant.getPortPrinter());
    }

    private final Handler ocHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case WifiCommunication.WFPRINTER_CONNECTED:


                    wifiCommunication.sndByte(Command.openCashDrawer);
                    wifiCommunication.close();

                    Log.d("ServiceActivity", "Connected Printer");

                    break;
                case WifiCommunication.WFPRINTER_DISCONNECTED:

                    Log.d("ServiceActivity", "Disconnected Printer");
                    break;
                default:
                    break;

            } // switch
        } // handleMessage
    };

    private void createDrawerMenu() {
        RecyclerView recyclerView = findViewById(R.id.recyclerDrawerMenu);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ServiceActivity.this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        MyConstant myConstant = new MyConstant();
        int[] ints = myConstant.getIconDrawerInts();
        String[] strings = myConstant.getTitleDrawerStrings();

        ArrayList<Integer> integerArrayList = new ArrayList<>();
        ArrayList<String> stringArrayList = new ArrayList<>();

        for (int i = 0; i < strings.length; i += 1) {
            integerArrayList.add(ints[i]);
            stringArrayList.add(strings[i]);
        }

        DrawerMenuAdapter drawerMenuAdapter = new DrawerMenuAdapter(ServiceActivity.this,
                integerArrayList, stringArrayList, new OnClickItem() {
            @Override
            public void onClickItem(View view, int positions) {
                Log.d("ServiceActivity", "You Click menu ==> " + positions);
                activeClick(positions);
                drawerLayout.closeDrawers();
            }
        });
        recyclerView.setAdapter(drawerMenuAdapter);

    }

    private void activeClick(int positions) {

        switch (positions) {
            case 0:
//                Profile

                break;

            case 1:

//                Test Print
                startActivity(new Intent(ServiceActivity.this, TestPrintActivity.class));

                break;

            case 2:

//                Open CashDrawer
                openCashDrawer();

                break;

            case 3:
//                Sign Out
                MyConstant myConstant = new MyConstant();
                SharedPreferences sharedPreferences = getSharedPreferences(myConstant.getSharePreferFileUserLogin(), MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Username", "");
                editor.putString("Firstname", "");
                editor.putString("Name", "");
                editor.putString("UserCat", "");
                editor.putBoolean("Remember", false);
                editor.putString("JSON", "");
                editor.commit();

                startActivity(new Intent(ServiceActivity.this, MainActivity.class));
                finish();


//                finish(); // Close App
                break;
        }

    }

    private void checkPrinter() {

        MyConstant myConstant = new MyConstant();
        WifiCommunication wifiCommunication = new WifiCommunication(handler);
        wifiCommunication.initSocket(myConstant.getIpAddressPrinter(), myConstant.getPortPrinter());


    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == WifiCommunication.WFPRINTER_CONNECTED) {
                Log.d("ServiceActivity", "Printer Connected");
//                Toast.makeText(ServiceActivity.this, "เชื่อมต่อปริ้นเตอร์สำเร็จ", Toast.LENGTH_SHORT).show();
//                wifiCommunication.close();
            } else {
                Log.d("ServiceActivity", "Printer Cannot Connected");
                Toast.makeText(ServiceActivity.this, "เชื่อมต่อปริ้นเตอร์ไม่สำเร็จ", Toast.LENGTH_LONG).show();
            }

        }
    };

    private void checkInternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (!(networkInfo != null && networkInfo.isConnected() && aBoolean)) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ServiceActivity.this);
            alertDialogBuilder.setTitle("Internet Checking").setMessage("ไม่ได้เชื่อมต่ออินเทอร์เน็ต").setPositiveButton("ออกจากโปรแกรม", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    dialog.dismiss();
                }
            }).setNegativeButton("ยืนยันเข้าใช้งาน", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    aBoolean = false;
                    dialog.dismiss();
                }
            }).show();

        }
    }

    private void addFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentServiceFragment, new ServicesFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        Intent intent = getIntent();
//        finish();
//        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate();
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    private void getValueLogin() {
        MyConstant myConstant = new MyConstant();

        SharedPreferences sharedPreferences = getSharedPreferences(myConstant.getSharePreferFileUserLogin(), Context.MODE_PRIVATE);
        String jsonString = sharedPreferences.getString("JSON", "");

        nameString = sharedPreferences.getString("Name", "");
    }

    private void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarService);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(myConstant.getNameShopString());
        getSupportActionBar().setSubtitle("Login by : " + nameString);

        drawerLayout = findViewById(R.id.layoutDrawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                ServiceActivity.this,
                drawerLayout,
                R.string.open,
                R.string.close);

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_action_hamberger);


    }
} // Main Class
