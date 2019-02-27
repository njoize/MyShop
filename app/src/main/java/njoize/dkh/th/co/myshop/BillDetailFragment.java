package njoize.dkh.th.co.myshop;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
        try {

            GetBillDetail getBillDetail = new GetBillDetail(getActivity());
            getBillDetail.execute(idBillString, myConstant.getUrlGetReceipts());
            String jsonString = getBillDetail.get();
            Log.d(tag, jsonString);

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