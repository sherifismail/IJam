package com.Example.iJam;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    //private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mPager;
    private MyPagerAdapter mAdapter;
    public static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        //mToolbar = (Toolbar) findViewById(R.id.app_bar);
        //setSupportActionBar(mToolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        mTabLayout.setTabsFromPagerAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        Intent inte = getIntent();
        int uid = inte.getIntExtra("user_id", 0);
        String uname = inte.getStringExtra("user_name");
        String password = inte.getStringExtra("password");
        String fname = inte.getStringExtra("first_name");
        String lname = inte.getStringExtra("last_name");
        user = new User(uid, uname, password, fname, lname);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_third, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MyFragment extends Fragment {
        public static final java.lang.String ARG_PAGE = "arg_page";

        public MyFragment() {

        }

        public static MyFragment newInstance(int pageNumber) {
            MyFragment myFragment = new MyFragment();
            Bundle arguments = new Bundle();
            arguments.putInt(ARG_PAGE, pageNumber);

            myFragment.setArguments(arguments);
            return myFragment;
        }
    }
}

class MyPagerAdapter extends FragmentStatePagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position==0)
        return fragmentTopTracks.newInstance("fragmentTopTracks, Instance 1");

        else if(position==1)
            return fragmentSearch.newInstance("fragmentSearch, Instance 1");
        else if (position==2)
            return fragmentBands.newInstance("fragmentBands, Instance 1");
        else if (position==3)
            return fragmentProfile.newInstance("fragmentProfile, Instance 1");

        //MainActivity.MyFragment myFragment = MainActivity.MyFragment.newInstance(position);
        //return myFragment;
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
            return "Top Tracks";
        else if(position==1)
            return "Search";
        else if(position==2)
            return "Bands";
        else if(position==3)
            return "Profile";

         return "Tab " + (position + 1);
    }
}

