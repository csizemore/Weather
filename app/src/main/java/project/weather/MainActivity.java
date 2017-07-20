package project.weather;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import project.weather.fragment.MessageFragment;
import project.weather.fragment.OnMessageFragmentAnswer;

public class MainActivity extends AppCompatActivity
        implements OnMessageFragmentAnswer {

    public static final String KEY_MSG = "KEY_MSG";

    private CityAdapter cityAdapter;
    private CoordinatorLayout layoutContent;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<City> cityList = project.weather.City.listAll(City.class);

        cityAdapter = new CityAdapter(cityList, this);
        RecyclerView recyclerViewCity = (RecyclerView) findViewById(
                R.id.recyclerViewCity);
        recyclerViewCity.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCity.setAdapter(cityAdapter);

        CityListTouchHelperCallback touchHelperCallback = new CityListTouchHelperCallback(
                cityAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(
                touchHelperCallback);
        touchHelper.attachToRecyclerView(recyclerViewCity);

        layoutContent = (CoordinatorLayout) findViewById(
                R.id.layoutContent);

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCity();
            }
        });


        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.action_add:
                                addCity();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                break;
                            case R.id.action_about:
                                showSnackBarMessage(getString(R.string.txt_about));
                                drawerLayout.closeDrawer(GravityCompat.START);
                                break;
                        }

                        return false;
                    }
                });
    }

    private void addCity() {
        MessageFragment messageFragment = new MessageFragment();
        messageFragment.setCancelable(false);

        Bundle bundle = new Bundle();
        bundle.putString(KEY_MSG, getString(R.string.press));
        messageFragment.setArguments(bundle);

        messageFragment.show(getSupportFragmentManager(),
                getString(R.string.messfrag));
    }

    private void showSnackBarMessage(String message) {
        Snackbar.make(layoutContent,
                message,
                Snackbar.LENGTH_LONG
        ).setAction(R.string.action_hide, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //...
            }
        }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                addCity();
                return true;
            default:
                addCity();
                return true;
        }
    }

    @Override
    public void onNegativeSelected() {
        Toast.makeText(this, R.string.cancel, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPositiveSelected(String name) {
        Toast.makeText(this, getString(R.string.added) + name, Toast.LENGTH_SHORT).show();
        cityAdapter.addCity(new City(name));
    }
}