package uep.project.dev.gitjobs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FavouritesFragment extends Fragment {

    public static List<JobOffer> jobOffers;
    private RecyclerView recyclerView;
    public static JobOfferAdapter jobOfferAdapter;

    private DatabaseHelper database;

    public FavouritesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View favouritesView = inflater.inflate(R.layout.fragment_favourites, container, false);
        jobOffers = new ArrayList<>();

        recyclerView = favouritesView.findViewById(R.id.recycler_view);

        jobOfferAdapter = new JobOfferAdapter(jobOffers, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(jobOfferAdapter);

        database = new DatabaseHelper(getContext());

        prepareJobOffersData();

        return favouritesView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void prepareJobOffersData() {
        jobOffers.addAll(database.getStoredJobOffers());
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


