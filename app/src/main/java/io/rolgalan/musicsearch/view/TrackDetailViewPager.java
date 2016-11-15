package io.rolgalan.musicsearch.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import io.rolgalan.musicsearch.TrackDetailFragment;
import io.rolgalan.musicsearch.data.DataProvider;

/**
 * Created by Roldán Galán on 15/11/2016.
 */

public class TrackDetailViewPager extends ViewPager {
    private boolean isInitialized = false;

    public TrackDetailViewPager(Context context) {
        super(context);
    }

    public TrackDetailViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void init(int startingItem, FragmentManager fragmentManager, final OnPageSelectedListener listener) {
        isInitialized = true;
        PagerAdapter pagerAdapter = new TrackDetailsPagerAdapter(fragmentManager);
        setAdapter(pagerAdapter);
        setPageTransformer(true, new ZoomOutPageTransformer());

        if (listener != null) {
            addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    listener.onPageSelected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        setCurrentItem(startingItem);
    }

    private class TrackDetailsPagerAdapter extends FragmentStatePagerAdapter {
        public TrackDetailsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle arguments = new Bundle();
            arguments.putInt(TrackDetailFragment.ARG_ITEM_ID, position);
            TrackDetailFragment fragment = new TrackDetailFragment();
            fragment.setArguments(arguments);
            return fragment;
        }

        @Override
        public int getCount() {
            return DataProvider.ITEMS.size();
        }
    }

    public interface OnPageSelectedListener {
        void onPageSelected(int position);
    }
}
