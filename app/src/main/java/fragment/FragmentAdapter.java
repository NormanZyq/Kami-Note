package fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by TALK_SWORD on 2018/3/23.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment>mfragments;
    public FragmentAdapter(FragmentManager fm,ArrayList<Fragment> fragment) {
        super(fm);
        this.mfragments = fragment;
    }

    @Override
    public Fragment getItem(int position) {
        return mfragments.get(position);
    }

    @Override
    public int getCount() {
        return mfragments.size();
    }
}
