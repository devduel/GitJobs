package uep.project.dev.gitjobs;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private EditText descriptionEditText;
    private EditText locationEditText;
    private Switch fullTimeSwitch;
    private Button searchJobButton;

    JobOfferDao jobOfferDao = new JobOfferDaoImpl();
    public static List<JobOffer> jobOffers = new ArrayList<>();
    public static ProgressDialog progressDialog;
    ApiCalls apiCalls = new ApiCalls();

    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View searchView = inflater.inflate(R.layout.fragment_search, container, false);

        descriptionEditText = searchView.findViewById(R.id.descriptionEditTextID);
        locationEditText = searchView.findViewById(R.id.locationEditTextID);
        fullTimeSwitch = searchView.findViewById(R.id.fullTimeSwitchID);
        searchJobButton = searchView.findViewById(R.id.searchJobButtonID);

        searchJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String parameters = retrieveAndBuildSearchJobParameters();

                showProgressDialog();
                apiCalls.execute(parameters);

                progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        jobOffers = jobOfferDao.getJobOffers();
                        Log.d("Result", Integer.toString(jobOffers.size()));
                    }
                });
            }
        });

        return searchView;
    }

    //TODO: Add form validation to force entering some values

    private String retrieveAndBuildSearchJobParameters() {
        String description = descriptionEditText.getText().toString();
        String location = locationEditText.getText().toString();
        Boolean fullTime = fullTimeSwitch.isChecked();
        Log.d("Result", description);
        Log.d("Result", location);
        Log.d("Result", fullTime.toString());

        Log.d("Result", buildApiParameters(description, location, fullTime));
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
}


