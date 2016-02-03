package com.kingofgranges.max.animeultimetv.tv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.support.v7.app.ActionBar;

import com.kingofgranges.max.animeultimetv.R;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class animeDetailsTv extends AppCompatActivity{

    public String title;
    public String synopsis;
    public Bitmap cover;
    public String[] episode;
    private String[] link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_tabs);
        Intent intent = getIntent();

        /* data */
        this.title = intent.getStringExtra("title");
        this.synopsis = intent.getStringExtra("synopsis");
        this.episode = intent.getStringArrayExtra("episode");
        this.link = intent.getStringArrayExtra("link");

        try {
            URL coverUrl = new URL(intent.getStringExtra("img"));
            this.cover = BitmapFactory.decodeStream(coverUrl.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


        /* TAB Management */
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        /* Back button */
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        animeFragmentDetailsTv details = new animeFragmentDetailsTv();
        details.setDetails(this.title, this.synopsis, this.cover);

        animeFragmentEpisodeTv episodes = new animeFragmentEpisodeTv();
        episodes.setEpisode(this.episode, this.link);

        adapter.addFragment(details, "Information");
        adapter.addFragment(episodes, "Épisode(s)");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
