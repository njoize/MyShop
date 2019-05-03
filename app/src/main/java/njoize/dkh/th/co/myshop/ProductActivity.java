package njoize.dkh.th.co.myshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        String mID = getIntent().getStringExtra("MID");


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contentProductFragment, ProductFragment.productInstante(mID))
                    .commit();
        }

    }
}
