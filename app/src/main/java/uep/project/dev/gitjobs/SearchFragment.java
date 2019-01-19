package uep.project.dev.gitjobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private Fragment fragment;

    private EditText descriptionEditText;
    private EditText locationEditText;
    private Switch fullTimeSwitch;
    private Button searchJobButton;
    private Button showOnMapButton;

    JobOfferDao jobOfferDao = new JobOfferDaoImpl();
    public static List<JobOffer> jobOffers = new ArrayList<>();
    public static ProgressDialog progressDialog;
    ApiCalls apiCalls;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View searchView = inflater.inflate(R.layout.fragment_search, container, false);

        descriptionEditText = searchView.findViewById(R.id.descriptionEditTextID);
        locationEditText = searchView.findViewById(R.id.locationEditTextID);
        fullTimeSwitch = searchView.findViewById(R.id.fullTimeSwitchID);
        searchJobButton = searchView.findViewById(R.id.searchJobButtonID);
        showOnMapButton = searchView.findViewById(R.id.showOnMapButtonID);

        searchJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkInternetConnection()) {
                    MakeToast("Internet is unavailable \nPlease try again later");
                    return;
                }

                String parameters = retrieveAndBuildSearchJobParameters();

                showProgressDialog();
                apiCalls = new ApiCalls();
                apiCalls.execute(parameters);

                progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        jobOffers = jobOfferDao.getJobOffers();
                        if (jobOffers.isEmpty()) {
                            MakeToast("No job offers found");

                            fragment = new SearchFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.main_container, fragment).commit();

                            return;
                        }
                        Intent intent = new Intent(getContext(), SearchResultActivity.class);
                        getContext().startActivity(intent);
                    }
                });
            }
        });

        showOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                getContext().startActivity(intent);
            }
        });

        return searchView;
    }

    private String retrieveAndBuildSearchJobParameters() {
        String description = descriptionEditText.getText().toString();
        String location = locationEditText.getText().toString();
        Boolean fullTime = fullTimeSwitch.isChecked();

        return buildApiParameters(description, location, fullTime);
    }

    private String buildApiParameters(String description, String location, boolean fullTime) {
        String parameters = "";

        if (!description.isEmpty()) {
            parameters += "description=" + description;
        }

        if (!location.isEmpty() && parameters.isEmpty()) {
            parameters += "location=" + location;
        } else if (!location.isEmpty() && !parameters.isEmpty()) {
            parameters += "&location=" + location;
        }

        if (fullTime && parameters.isEmpty()) {
            parameters += "full_time=true";
        } else if (fullTime && !parameters.isEmpty()) {
            parameters += "&full_time=true";
        }

        return parameters.replace(" ", "+");
    }

    void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching job offers");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private void MakeToast(String toastMessage) {
        CharSequence toastText = toastMessage;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getContext(), toastText, duration);
        toast.show();
    }
}


