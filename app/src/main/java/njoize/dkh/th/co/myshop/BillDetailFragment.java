package njoize.dkh.th.co.myshop;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class BillDetailFragment extends Fragment {

    private String idBillString, channelString, methodString, detail1String, detail2String, detail3String;
    private String tag = "BillDetailFragment";
    private MyConstant myConstant = new MyConstant();

    public BillDetailFragment() {
        // Required empty public constructor
    }

    public static BillDetailFragment billDetailInstance(String idString,
                                                        String channelString,
                                                        String methodString,
                                                        String detail1String,
                                                        String detail2String,
                                                        String detail3String) {

        BillDetailFragment billDetailFragment = new BillDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("idBill", idString);
        bundle.putString("channel", channelString);
        bundle.putString("method", methodString);
        bundle.putString("detail1", detail1String);
        bundle.putString("detail2", detail2String);
        bundle.putString("detail3", detail3String);
        billDetailFragment.setArguments(bundle);
        return billDetailFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create Toolbar
        createToolbar();

//        Get RID
        getRID();

//        Create Detail
        createDetail();

//        Show Text
        showText();

    } // Main Method


    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarBillDetail);
        ((BillDetailActivity) getActivity()).setSupportActionBar(toolbar);
        ((BillDetailActivity) getActivity()).getSupportActionBar().setTitle("Detail");
        ((BillDetailActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((BillDetailActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    private void showText() {
        TextView leftTextView = getView().findViewById(R.id.txtLeft);
        TextView left2TextView = getView().findViewById(R.id.txtLeft2);
        TextView rightTextView = getView().findViewById(R.id.txtRight);

        leftTextView.setText(detail1String + "\n" + detail2String);
        left2TextView.setText(detail3String);
        rightTextView.setText(channelString + " " + methodString);
    }

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
        ArrayList<String> discountStringArrayList = new ArrayList<>();
        ArrayList<String> sumStringArrayList = new ArrayList<>();

        try {

            GetDetailWhereID getBillDetail = new GetDetailWhereID(getActivity());
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
                total = total + Double.parseDouble(s.replace(",", ""));
            }

//            Currency Format
            DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
            DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
            symbols.setCurrencySymbol(""); // Don't use null.
            formatter.setDecimalFormatSymbols(symbols);

            TextView textView = getView().findViewById(R.id.txtTotal);
            textView.setText("รวมทั้งสิ้น " + formatter.format(total));

            double totalWeight = 0;
            for (String s : weightStringArrayList) {
                totalWeight = totalWeight + Double.parseDouble(s.replace(",", ""));
            }

            TextView weightTextView = getView().findViewById(R.id.txtTotalWeight);
            weightTextView.setText(formatter.format(totalWeight) + " KG");

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(tag, "e at createDetail ==> " + e.toString());
        }
    }

    private void getRID() {
        idBillString = getArguments().getString("idBill");
        channelString = getArguments().getString("channel");
        methodString = getArguments().getString("method");
        detail1String = getArguments().getString("detail1");
        detail2String = getArguments().getString("detail2");
        detail3String = getArguments().getString("detail3");
        Log.d(tag, "idBill ==> " + idBillString);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill_detail, container, false);
    }

}