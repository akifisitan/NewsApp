package com.akifisitan.newsapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

// Adapter for ViewPager
public class ViewPager2Adapter extends FragmentStateAdapter {
    private final int tabCount;

    public ViewPager2Adapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle,
                             int tabCount) {
        super(fragmentManager, lifecycle);
        this.tabCount = tabCount;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new FragmentPolitics();
            case 2:
                return new FragmentSports();
            default:
                return new FragmentEconomics();
        }
    }

    @Override
    public int getItemCount() {
        return tabCount;
    }
}
