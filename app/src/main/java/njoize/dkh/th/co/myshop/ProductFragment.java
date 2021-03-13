package njoize.dkh.th.co.myshop;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.LongDef;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    //    Explicit
    private String tag = "ProductFragment";
    private String mIDString;
    private int tabAnInt = 1; // ราคาปกติ
    private int status = 0; // ราคาปกติ
    private MyManageSQLite myManageSQLite;

    private MyConstant myConstant = new MyConstant();

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment productInstante(String mID) {
        ProductFragment productFragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putString("MID", mID);
        productFragment.setArguments(bundle);
        return productFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myManageSQLite = new MyManageSQLite(getActivity());

//        Create TabLayout
        createTabLayout();

//        Create RecyclerView
        createRecyclerView(tabAnInt);

//        Check Order
        checkOrder();

//        Cancel Controller
        cancelController();

//        Order Controller
        orderController();



    } // Main Method

    private void orderController() {
        Button button = getView().findViewById(R.id.btnOrder);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("ยืนยันรายการสั่งซื้อ").setMessage("คุณต้องการทำรายการสั่งซื้อ แน่ใจหรือไม่ ?").setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MyOpenHelper.database_name, Context.MODE_PRIVATE, null);
                        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orderTable", null);
                        cursor.moveToFirst();

                        for (int i = 0; i < cursor.getCount(); i += 1) {

                            String idPricelist = cursor.getString(1);
                            String price = cursor.getString(3);
                            String quantity = cursor.getString(4);
                            String amount = cursor.getString(5);

                            Log.d(tag + " - Order", "pid[" + i + "] = " + idPricelist);
                            Log.d(tag, "price[" + i + "] = " + price);
                            Log.d(tag, "amount[" + i + "] = " + amount);

//                            Upload to Server
                            /*try {

                                OrderThread orderThread = new OrderThread(getActivity());
                                orderThread.execute(user, numcus, billtype, tid, pid, price, amount, myConstant.getUrlAddOrder());

                                emptySQLite();


                                GetUserWhereName getUserWhereName = new GetUserWhereName(getActivity());

                                getUserWhereName.execute(user, myConstant.getUrlGetOrderWhereUser());
                                String resultJSoN = getUserWhereName.get();
                                Log.d("10marV1", "resultJSoN ==> " + resultJSoN);

                                JSONArray jsonArray = new JSONArray(resultJSoN);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
//
//
                                Intent intent = new Intent(getActivity(), BillDetailActivity.class); // go to BillDetailActivity
//                                intent.putExtra("Login", resultJSoN);
////                                intent.putExtra("Status", true);
////                                intent.putExtra("mid", idString);
////                                Log.d("28FebV1", "mid adapter ==> " + idString);
//
//
                                intent.putExtra("idBill", jsonObject.getString("id"));



                                startActivity(intent);
                                getActivity().finish();


                            } catch (Exception e) {
                                Log.d(tag, "e upload ==> " + e.toString());
                            }*/

                            cursor.moveToNext();
                        }


                        dialog.dismiss();
                    }
                }).setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();


            }
        });
    }

    private boolean checkHaveProduct(String idProductString) {

        try {

            SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MyOpenHelper.database_name, Context.MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orderTABLE", null);
            cursor.moveToFirst();

            boolean resule = false; // false Without IdFood in Database

            for (int i = 0; i < cursor.getCount(); i += 1) {

                if (idProductString.equals(cursor.getString(1))) {
                    resule = true;
                }

                cursor.moveToNext();
            }
            cursor.close();

            Log.d(tag, "result ==> " + resule);
            return resule;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }

    private void cancelController() {
        Button button = getView().findViewById(R.id.btnCancle);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("ลบรายการสั่งซื้อ").setMessage("คุณต้องการทำลบรายการสั่งซื้อ แน่ใจหรือไม่ ?").setPositiveButton("ลบทิ้งเลย", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        emptySQLite();

                        dialog.dismiss();
                    }
                }).setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }
        });
    }

    private void emptySQLite() {
        try {

            SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MyOpenHelper.database_name, Context.MODE_PRIVATE, null);
            sqLiteDatabase.delete(MyOpenHelper.database_table, null, null);
            checkOrder();

        } catch (Exception e) {
            Log.d(tag, "e cancel ==> " + e.toString());
        }
    }

    private void checkOrder() {
        try {

            SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MyOpenHelper.database_name, Context.MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orderTABLE", null);
            cursor.moveToFirst();

            ArrayList<String> nameProductStringArrayList = new ArrayList<>();
            ArrayList<String> quantityStringArrayList = new ArrayList<>();
            ArrayList<String> amountStringArrayList = new ArrayList<>();
            ArrayList<String> priceStringArrayList = new ArrayList<>();
            ArrayList<String> priceSumStringArrayList = new ArrayList<>();
            final ArrayList<String> idSQLiteStringArrayList = new ArrayList<>();

            double totalAInt = 0;

            for (int i = 0; i < cursor.getCount(); i += 1) {
                nameProductStringArrayList.add(cursor.getString(2));
                quantityStringArrayList.add(cursor.getString(4));
                amountStringArrayList.add(cursor.getString(5));
                priceStringArrayList.add(cursor.getString(3));
                idSQLiteStringArrayList.add(cursor.getString(0));

                int quantityInt = Integer.parseInt(cursor.getString(4));
                double priceInt = Double.parseDouble(cursor.getString(3));
                int amountInt = Integer.parseInt(amountStringArrayList.get(i));

                priceSumStringArrayList.add(String.format("%.2f",quantityInt * priceInt * amountInt));

                totalAInt = totalAInt + (quantityInt * priceInt * amountInt);

                cursor.moveToNext();

            } // for

            cursor.close();

            Log.d(tag, "nameProduct ==> " + nameProductStringArrayList.toString());
//            RecyclerView recyclerView = getView();

            RecyclerView recyclerView = getView().findViewById(R.id.recyclerOrder);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);

            OrderAdapter orderAdapter = new OrderAdapter(getActivity(), nameProductStringArrayList, quantityStringArrayList, amountStringArrayList,
                    priceSumStringArrayList, new OnClickImage() {
                @Override
                public void onClickImage(View view, int position, boolean status) {
                    Log.d(tag, "position = " + position + " Status" + status);
                    Log.d(tag, "idSQLiteStringArrayList.get(position) = " + idSQLiteStringArrayList.get(position));

                    toolIncDec(idSQLiteStringArrayList.get(position), status);

                }
            });

//            increaseOrDecrease(idSQLiteStringArrayList.get(positions));

            recyclerView.setAdapter(orderAdapter);

//            Show AmountPrice
            TextView textView = getView().findViewById(R.id.txtTotal);
            textView.setText("รวมทั้งสิ้น :   " + String.format("%.2f",totalAInt));
//            textView.setText("รวมทั้งสิ้น : " + Integer.toString(totalAInt) + " บาท");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toolIncDec(String idSQLite, boolean status) {

        SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MyOpenHelper.database_name, Context.MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orderTABLE WHERE id=" + "'" + idSQLite + "'", null);
        cursor.moveToFirst();

        Log.d(tag, "idSQLite ==> " + idSQLite);
        Log.d(tag, "status ==> " + status);

        String amountString = cursor.getString(5);
//        Log.d(tag, "cursor 0 ==> " + cursor.getString(0));
//        Log.d(tag, "cursor 3 ==> " + cursor.getString(3));
//        Log.d(tag, "cursor 4 ==> " + cursor.getString(4));
        Log.d(tag, "Current Amount ==> " + amountString);


        int amountInt = Integer.parseInt(amountString);
        cursor.close();

        if (status) {
//            Increase Status
            amountInt += 1;
            sqLiteDatabase.execSQL("UPDATE orderTABLE SET amount=" + "'" + Integer.toString(amountInt) + "'" + " WHERE id=" + "'" + idSQLite + "'");
            checkOrder();
        } else {
//            Decrease Status
            if (amountInt == 1) {
                sqLiteDatabase.delete("orderTABLE", "id" + "=" + idSQLite, null);
                checkOrder();
            } else {
                amountInt -= 1;
                sqLiteDatabase.execSQL("UPDATE orderTABLE SET amount=" + "'" + Integer.toString(amountInt) + "'" + " WHERE id=" + "'" + idSQLite + "'");
                checkOrder();
            }

        }


    }

    private void createTabLayout() {
        TabLayout tabLayout = getView().findViewById(R.id.tabLayoutProduct);
        String[] strings = myConstant.getProductCatStrings();
        for (String s : strings) {
            tabLayout.addTab(tabLayout.newTab().setText(s));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

//        Start tabAnInt = 1; // ราคาปกติ
        tabLayout.getTabAt(tabAnInt).select();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabAnInt = tab.getPosition();
                Log.d(tag, "tabAnInt ==> " + tabAnInt);
                if (tabAnInt == 0) {
                    status = 2; // ราคาปลีก
                }
                if (tabAnInt == 1) {
                    status = 0;  // ราคาปกติ
                }
                if (tabAnInt == 2) {
                    status = 1;  // ราคาส่ง
                }
                createRecyclerView(status);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    } // createTabLayout

    private void createRecyclerView(int tabPosition) {

        SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences(myConstant.getSharePreferFileUserLogin(), Context.MODE_PRIVATE);
        String userLogined = sharedPreferences.getString("Username", "");


        RecyclerView recyclerView = getView().findViewById(R.id.recyclerProduct);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        final ArrayList<String> productStringArrayList = new ArrayList<>();
        final ArrayList<String> productDetailStringArrayList = new ArrayList<>();
        final ArrayList<String> priceStringArrayList = new ArrayList<>();
        final ArrayList<String> quantityStringArrayList = new ArrayList<>();
        final ArrayList<String> remarkStringArrayList = new ArrayList<>();
        final ArrayList<String> idPriceListStringArrayList = new ArrayList<>();


        try {
            ReadAllDataThread readAllDataThread = new ReadAllDataThread(getActivity());
            readAllDataThread.execute(userLogined,
                    Integer.toString(status),
                    myConstant.getUrlGetAllProduct());
            String jsonString = readAllDataThread.get();
            Log.d(tag, "jsonString ==> " + jsonString);

            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i += 1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                productStringArrayList.add(jsonObject.getString("p_shortname"));
                productDetailStringArrayList.add(jsonObject.getString("p_detail"));
                priceStringArrayList.add(jsonObject.getString("price"));
                quantityStringArrayList.add(jsonObject.getString("pl_quantity"));
                remarkStringArrayList.add(jsonObject.getString("pl_remark"));
//                idProductStringArrayList.add(jsonObject.getString("p_id"));
                idPriceListStringArrayList.add(jsonObject.getString("pl_id"));

            } // for

            ProductRecyclerViewAdapter productRecyclerViewAdapter = new ProductRecyclerViewAdapter(getActivity(),
                    productStringArrayList,
                    productDetailStringArrayList,
                    priceStringArrayList,
                    quantityStringArrayList,
                    remarkStringArrayList, new OnClickItem() {
                @Override
                public void onClickItem(View view, int positions) {
                    Log.d(tag, "You Click ==> " + positions);
                    Log.d(tag, "Pricelist ID ==> " + idPriceListStringArrayList.get(positions));
                    Log.d(tag, "Product Name ==> " + productStringArrayList.get(positions));
                    Log.d(tag, "Price ==> " + priceStringArrayList.get(positions));
                    Log.d(tag, "Quantity ==> " + quantityStringArrayList.get(positions));

                    if (checkHaveProduct(idPriceListStringArrayList.get(positions))) {
//                        Increase Old Order
                        increaseAmountProduct(idPriceListStringArrayList.get(positions));

                    } else {
//                        Add New Order
                        myManageSQLite.addValueToSQLite(idPriceListStringArrayList.get(positions),
                                productStringArrayList.get(positions),
                                priceStringArrayList.get(positions),
                                quantityStringArrayList.get(positions), "1");
                    }

                    checkOrder();

//                    Intent intent = new Intent(getActivity(), BillDetailActivity.class);
//                    MyConstant myConstant = new MyConstant();
//                    String[] strings = myConstant.getBillDetailStrings();
//
//                    intent.putExtra(strings[0], idPriceListStringArrayList.get(positions));
//                    intent.putExtra(strings[1], productStringArrayList.get(positions));
//                    intent.putExtra(strings[2], productDetailStringArrayList.get(positions));
//                    intent.putExtra(strings[3], priceStringArrayList.get(positions));
//                    intent.putExtra(strings[4], quantityStringArrayList.get(positions));
//                    intent.putExtra(strings[5], remarkStringArrayList.get(positions));
//
//                    startActivity(intent);


                }
            });
            recyclerView.setAdapter(productRecyclerViewAdapter);


        } catch (Exception e) {
            e.printStackTrace();
            Log.d(tag, "e ==> " + e.toString());
        } // try


    } // createRecyclerView

    private void increaseAmountProduct(String idPricelistString) {

        SQLiteDatabase sqLiteDatabase = getActivity()
                .openOrCreateDatabase(MyOpenHelper.database_name, Context.MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM orderTABLE WHERE idPricelist = " + "'" + idPricelistString + "'", null);
        cursor.moveToFirst();

        String amountString = cursor.getString(5);
        int amountInt = Integer.parseInt(amountString);
        amountInt += 1;
        amountString = Integer.toString(amountInt);

        Log.d(tag, "idPricelistString ==> " + idPricelistString);
        Log.d(tag, "amountString ==> " + amountString);

        cursor.close();

        sqLiteDatabase.execSQL("UPDATE orderTABLE SET amount=" + "'" + amountString + "'" + " WHERE idPricelist = " + "'" + idPricelistString + "'");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

}
