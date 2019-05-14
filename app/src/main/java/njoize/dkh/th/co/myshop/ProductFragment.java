package njoize.dkh.th.co.myshop;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;

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

    private MyConstant myConstant = new MyConstant();

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment productInstante(String mID){
        ProductFragment productFragment = new ProductFragment();
        Bundle bundle = new Bundle();
        bundle.putString("MID", mID);
        productFragment.setArguments(bundle);
        return productFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create TabLayout
        createTabLayout();

//        Create RecyclerView
        createRecyclerView(tabAnInt);


    } // Main Method

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
                productStringArrayList.add(jsonObject.getString("p_name"));
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

                    Intent intent = new Intent(getActivity(), BillDetailActivity.class);
                    MyConstant myConstant = new MyConstant();
                    String[] strings = myConstant.getBillDetailStrings();

                    intent.putExtra(strings[0], idPriceListStringArrayList.get(positions));
                    intent.putExtra(strings[1], productStringArrayList.get(positions));
                    intent.putExtra(strings[2], productDetailStringArrayList.get(positions));
                    intent.putExtra(strings[3], priceStringArrayList.get(positions));
                    intent.putExtra(strings[4], quantityStringArrayList.get(positions));
                    intent.putExtra(strings[5], remarkStringArrayList.get(positions));

                    startActivity(intent);



                }
            });
            recyclerView.setAdapter(productRecyclerViewAdapter);


        } catch (Exception e) {
            e.printStackTrace();
            Log.d(tag, "e ==> " + e.toString());
        } // try


    } // createRecyclerView

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

}
