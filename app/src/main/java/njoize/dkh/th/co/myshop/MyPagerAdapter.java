package njoize.dkh.th.co.myshop;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private FragmentManager fragmentManager;
    private int anInt;

    public MyPagerAdapter(FragmentManager fragmentManager, int anInt) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        this.anInt = anInt;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                BillFragment billFragment = new BillFragment();
                return billFragment;
            case 1:
                MemberFragment memberFragment = new MemberFragment();
                return memberFragment;
            case 2:
                ProductFragment productFragment = new ProductFragment();
                return productFragment;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return anInt;
    }
} // Main Class
