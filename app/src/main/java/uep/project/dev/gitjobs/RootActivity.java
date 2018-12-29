package uep.project.dev.gitjobs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class RootActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    JobOfferDao jobOfferDao = new JobOfferDaoImpl();
    public static List<JobOffer> jobOffers = new ArrayList<>();
    public static ProgressDialog progressDialog;
    ApiCalls apiCalls = new ApiCalls();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        //setUpProgressDialog();
        //apiCalls.execute();

        /*progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                jobOffers = jobOfferDao.getJobOffers();
                Log.d("Result", Integer.toString(jobOffers.size()));
                Log.d("Result", apiCalls.getStatus().toString());
            }
        });*/

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.bottom_menu);
        fragmentManager = getSupportFragmentManager();
        setInitialBottomNavigation();


        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.action_search:
                        fragment = new SearchFragment();
                        break;
                    case R.id.action_favourites:
                        fragment = new FavouritesFragment();
                        break;
                    case R.id.action_settings:
                        fragment = new SettingsFragment();
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                return true;
            }
        });
    }

    void setInitialBottomNavigation() {
        fragment = new SearchFragment();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, fragment).commit();
    }
}
