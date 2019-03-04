package njoize.dkh.th.co.myshop;


import android.os.Bundle;
import android.support.annotation.Nullable;
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


    public BillFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Check Status
        checkStatus();

//        Create RecyclerView
        createRecyclerView();

    } // Main Method

    private void createRecyclerView() {

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
            readAllDataThread.execute(myConstant.getUrlGetAllReceipts());
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
                    totalPriceStringArrayList,new OnClickItem() {
                @Override
                public void onClickItem(View view, int positions) {
                    Log.d("2decV2", "You Click ==> " + positions);

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contentServiceFragment,
                                    BillDetailFragment.billDetailInstance(idBillStringArrayList.get(positions),
                                            channelStringArrayList.get(positions),
                                            methodStringArrayList.get(positions),
                                            detailLine1StringArrayList.get(positions),
                                            detailLine2StringArrayList.get(positions),
                                            detailLine3StringArrayList.get(positions)))
                            .addToBackStack(null)
                            .commit();

                }
            });
            recyclerView.setAdapter(billRecyclerViewAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    } // createRecyclerView

    private void checkStatus() {
        TextView title0TextView = getView().findViewById(R.id.txtTitle0);
        TextView title1TextView = getView().findViewById(R.id.txtTitle1);

        String[] strings = myConstant.getBillTitleStrings();
        title0TextView.setText(strings[0]);
        title1TextView.setText(strings[1]);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill, container, false);
    }

} // Main Class
