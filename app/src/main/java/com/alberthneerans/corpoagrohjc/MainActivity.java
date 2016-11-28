package com.alberthneerans.corpoagrohjc;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager=(ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener= new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };
        ActionBar.Tab tab = actionBar.newTab().setText("Servicios").setTabListener(tabListener);
        actionBar.addTab(tab);
        tab = actionBar.newTab().setText("Informacion").setTabListener(tabListener);
        actionBar.addTab(tab);
        tab = actionBar.newTab().setText("Eventos").setTabListener(tabListener);
        actionBar.addTab(tab);
        tab = actionBar.newTab().setText("Medios").setTabListener(tabListener);
        actionBar.addTab(tab);
        tab = actionBar.newTab().setText("Historia").setTabListener(tabListener);
        actionBar.addTab(tab);

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            public void onPageSelected(int position){
                getSupportActionBar().setSelectedNavigationItem(position);

            }
        });

        Bundle extras=getIntent().getExtras();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (id){

            case R.id.menu_perfil:
                Intent intent=new Intent(MainActivity.this, PerfilActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_map:
                Intent intent2=new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent2);
                break;
            case R.id.menu_Cerrar:
                logout();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    public class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return new ServiciosFragment();
                case 1: return new InformacionFragment();
                case 2: return new EventosFragment();
                case 3: return new MediosFragment();
                case 4: return new HistoriaFragment();
                default: return null;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    public void logout() {
        LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();
        goLogginActivity();
    }

    private void goLogginActivity() {
        Intent intent = new Intent(getApplicationContext(), LogginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
