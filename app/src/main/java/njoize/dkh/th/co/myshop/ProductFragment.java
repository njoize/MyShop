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
public class ProductFragment extends Fragment {

    //    Explicit
    private String mIDString;

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

//        Receive Value
        receiveValue();



    } // Main Method

    private void receiveValue() {
        mIDString = getArguments().getString("MID");
        Log.d("ProductFragment", "MID ==> " + mIDString);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

}
