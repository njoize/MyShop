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
public class BillDetailFragment extends Fragment {

    private String idBillString;
    private String tag = "BillDetailFragment";
    private MyConstant myConstant = new MyConstant();

    public BillDetailFragment() {
        // Required empty public constructor
    }

    public static BillDetailFragment billDetailInstance(String idString) {

        BillDetailFragment billDetailFragment = new BillDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("idBill", idString);
        billDetailFragment.setArguments(bundle);
        return billDetailFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Get RID
        getRID();

//        Create Detail
        createDetail();


    } // Main Method

    private void createDetail() {

        RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewBillDetail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<String> nameStringArrayList = new ArrayList<>();
        ArrayList<String> pdetailStringArrayList = new ArrayList<>();
        ArrayList<String> rdetailStringArrayList = new ArrayList<>();
        ArrayList<String> weightStringArrayList = new ArrayList<>();
        ArrayList<String> amountStringArrayList = new ArrayList<>();
        ArrayList<String> priceStringArrayList = new ArrayList<>();
        ArrayList<String>  discountStringArrayList = new ArrayList<>();
        ArrayList<String>  sumStringArrayList = new ArrayList<>();

        try {

            GetBillDetail getBillDetail = new GetBillDetail(getActivity());
            getBillDetail.execute(idBillString, myConstant.getUrlGetReceiptsDetail());
            String jsonString = getBillDetail.get();
            Log.d(tag, jsonString);

            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                nameStringArrayList.add(jsonObject.getString("name"));
                pdetailStringArrayList.add(jsonObject.getString("pdetail"));
                rdetailStringArrayList.add(jsonObject.getString("rdetail"));
                weightStringArrayList.add(jsonObject.getString("weight"));
                amountStringArrayList.add(jsonObject.getString("quantity"));
                priceStringArrayList.add(jsonObject.getString("price"));
                discountStringArrayList.add(jsonObject.getString("discount"));
                sumStringArrayList.add(jsonObject.getString("sum"));

            }

            BillDetailAdapter billDetailAdapter = new BillDetailAdapter(getActivity(), nameStringArrayList,
                    pdetailStringArrayList,
                    rdetailStringArrayList,
                    weightStringArrayList,
                    amountStringArrayList,
                    priceStringArrayList,
                    discountStringArrayList,
                    sumStringArrayList);
            recyclerView.setAdapter(billDetailAdapter);

            double total = 0;
            for (String s : sumStringArrayList) {
                total = total + Double.parseDouble(s.trim());
            }

            TextView textView = getView().findViewById(R.id.txtTotal);
            textView.setText("รวมทั้งสิ้น " + Double.toString(total).format("%.2f", total) + " บาท");

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(tag, "e at createDetail ==> " + e.toString());
        }
    }

    private void getRID() {
        idBillString = getArguments().getString("idBill");
        Log.d(tag, "idBill ==> " + idBillString);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill_detail, container, false);
    }

}