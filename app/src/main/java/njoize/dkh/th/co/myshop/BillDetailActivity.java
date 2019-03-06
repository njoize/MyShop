package njoize.dkh.th.co.myshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BillDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

//        Get Value
        MyConstant myConstant = new MyConstant();
        String[] strings = myConstant.getBillDetailStrings();
        String[] valueStrings1 = new String[strings.length];

        for (int i = 0; i < strings.length; i += 1) {
            valueStrings1[i] = getIntent().getStringExtra(strings[i]);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentBillDetailFreagment, BillDetailFragment.billDetailInstance(
                            valueStrings1[0],
                            valueStrings1[1],
                            valueStrings1[2],
                            valueStrings1[3],
                            valueStrings1[4],
                            valueStrings1[5])).commit();
        }

    }
}
