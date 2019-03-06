package njoize.dkh.th.co.myshop;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BillFragment extends Fragment {

    private MyConstant myConstant = new MyConstant();
    private String tag = "BillFragment";
    private int tabAnInt = 0;


    public BillFragment() {
        // Required empty public constructor
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
        TabLayout tabLayout = getView().findViewById(R.id.tabLayoutBill);
        String[] strings = myConstant.getBillTitleStrings();
        for (String s : strings) {
            tabLayout.addTab(tabLayout.newTab().setText(s));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabAnInt = tab.getPosition();
                Log.d(tag, "tabAnInt ==> " + tabAnInt);
                createRecyclerView(tabAnInt);
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


        RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewBill);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        final ArrayList<String> channelStringArrayList = new ArrayList<>();
        final ArrayList<String> methodStringArrayList = new ArrayList<>();
        final ArrayList<String> detailLine1StringArrayList = new ArrayList<>();
        final ArrayList<String> detailLine2StringArrayList = new ArrayList<>();
        final ArrayList<String> detailLine3StringArrayList = new ArrayList<>();
        final ArrayList<String> totalPriceStringArrayList = new ArrayList<>();
        final ArrayList<String> idBillStringArrayList = new ArrayList<>();


        try {
            ReadAllDataThread readAllDataThread = new ReadAllDataThread(getActivity());
            readAllDataThread.execute(userLogined,
                    Integer.toString(tabPosition),
                    myConstant.getUrlGetAllReceipts());
            String jsonString = readAllDataThread.get();
            Log.d(tag, "jsonString ==> " + jsonString);
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i += 1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                channelStringArrayList.add(jsonObject.getString("channel"));
                methodStringArrayList.add(jsonObject.getString("method"));
                detailLine1StringArrayList.add(jsonObject.getString("r_no") + " " + jsonObject.getString("cname"));
                detailLine2StringArrayList.add(jsonObject.getString("r_date") + " ผู้ขาย: " + jsonObject.getString("r_sales"));
                detailLine3StringArrayList.add(jsonObject.getString("r_remark"));
                totalPriceStringArrayList.add(jsonObject.getString("TotalPrice"));
                idBillStringArrayList.add(jsonObject.getString("r_id"));

            } // for

            BillRecyclerViewAdapter billRecyclerViewAdapter = new BillRecyclerViewAdapter(getActivity(),
                    channelStringArrayList,
                    methodStringArrayList,
                    detailLine1StringArrayList,
                    detailLine2StringArrayList,
                    detailLine3StringArrayList,
                    totalPriceStringArrayList, new OnClickItem() {
                @Override
                public void onClickItem(View view, int positions) {
                    Log.d("2decV2", "You Click ==> " + positions);

                    Intent intent = new Intent(getActivity(), BillDetailActivity.class);
                    MyConstant myConstant = new MyConstant();
                    String[] strings = myConstant.getBillDetailStrings();

                    intent.putExtra(strings[0], idBillStringArrayList.get(positions));
                    intent.putExtra(strings[1], channelStringArrayList.get(positions));
                    intent.putExtra(strings[2], methodStringArrayList.get(positions));
                    intent.putExtra(strings[3], detailLine1StringArrayList.get(positions));
                    intent.putExtra(strings[4], detailLine2StringArrayList.get(positions));
                    intent.putExtra(strings[5], detailLine3StringArrayList.get(positions));

                    startActivity(intent);



                }
            });
            recyclerView.setAdapter(billRecyclerViewAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        } // try


    } // createRecyclerView

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill, container, false);
    }

} // Main Class
