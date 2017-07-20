package project.weather.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Crystal on 11/30/2016.
 */
    public class ViewAdapter extends FragmentPagerAdapter {
        public ViewAdapter(FragmentManager fm) {
            super(fm);
        }

    String cityName;

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            Bundle args = null;
            switch (position) {
                case 0:
                    fragment = new FragmentOne();
                    args = new Bundle();
                    args.putString("cityName", cityName);
                    fragment.setArguments(args);
                    break;
                case 1:
                    fragment = new FragmentTwo();
                    args = new Bundle();
                    args.putString("cityName", cityName);
                    fragment.setArguments(args);
                    break;
                default:
                    fragment = new FragmentOne();
                    break;
            }

            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Main";
                case 1:
                    return "Details";
            }

            return "Main";
        }

        @Override
        public int getCount() {
            return 2;
        }

    }
