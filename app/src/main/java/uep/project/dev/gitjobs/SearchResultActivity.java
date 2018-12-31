package uep.project.dev.gitjobs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private List<JobOffer> jobOffers = new ArrayList<>();
    private RecyclerView recyclerView;
    private JobOfferAdapter jobOfferAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        recyclerView = findViewById(R.id.recycler_view);

        jobOfferAdapter = new JobOfferAdapter(jobOffers, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(jobOfferAdapter);

        prepareJobOffersData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, SearchFragment.class);
        startActivity(intent);
        return true;
    }

    private void prepareJobOffersData() {
        for (JobOffer offer : SearchFragment.jobOffers
                ) {
            jobOffers.add(offer);
        }
        sortOffersByTitle();
        jobOfferAdapter.notifyDataSetChanged();
    }

    private void sortOffersByTitle() {
        Collections.sort(jobOffers, new Comparator<JobOffer>() {
            public int compare(JobOffer firstOffer, JobOffer secondOffer) {
                return firstOffer.title.compareTo(secondOffer.title);
            }
        });
    }
}
