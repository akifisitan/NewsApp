package com.akifisitan.newsapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

// Main Activity
public class MainActivity extends AppCompatActivity {
    // Access ui components
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    Toolbar toolbar;
    ProgressBar prgBar;
    ViewPager2Adapter pagerAdapter2;
    String[] layout = {"Economics", "Sports", "Politics"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Set ui components
        toolbar = findViewById(R.id.toolbarNews);
        viewPager2 = findViewById(R.id.viewPagerNews);
        prgBar = findViewById(R.id.prgBarListMain);
        tabLayout = findViewById(R.id.tabLayoutNews);
        // Set ViewPager2's adapter & specify tab count
        pagerAdapter2 = new ViewPager2Adapter(getSupportFragmentManager(), getLifecycle(), 3);
        viewPager2.setAdapter(pagerAdapter2);
        // Attach the TabLayout to the ViewPager2
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(layout[position]));
        mediator.attach();
    }
}