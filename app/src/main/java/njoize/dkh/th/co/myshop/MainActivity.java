package njoize.dkh.th.co.myshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Add Fragment to Activity
        if (savedInstanceState == null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contMainFragment, new AuthenFragment())
                    .commit();


                    // .add(R.id.contMainFragment, new MainFragment()) << เปลี่ยนหน้าแรก App
        }

    } // Main Method

} // Main Class
