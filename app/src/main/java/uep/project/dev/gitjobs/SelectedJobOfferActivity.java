package uep.project.dev.gitjobs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class SelectedJobOfferActivity extends AppCompatActivity {

    private Button openOnGithubButton;
    private Button openCompanyWebsiteButton;
    private TextView companyAndLocationTextView;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView companyLogoImageView;

    private DatabaseHelper database;

    private JobOffer jobOffer;
    private int jobOfferPosition;
    private String callingActivity;

    private boolean isHeartFilled = false;
    private static final String SERIALIZED_JOB_OFFER_DATA = "SERIALIZED_JOB_OFFER_DATA";
    private static final String JOB_OFFER_POSITION = "JOB_OFFER_POSITION";
    private static final String CALLING_ACTIVITY = "CALLING_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_job_offer);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        companyAndLocationTextView = findViewById(R.id.companyAndLocationTextViewId);
        titleTextView = findViewById(R.id.titleTextViewId);
        descriptionTextView = findViewById(R.id.descriptionTextViewId);
        openOnGithubButton = findViewById(R.id.openOnGithubButtonId);
        openCompanyWebsiteButton = findViewById(R.id.openCompanyWebsiteButtonId);
        companyLogoImageView = findViewById(R.id.companyLogoImageViewId);

        database = new DatabaseHelper(this);

        jobOffer = (JobOffer) getIntent().getSerializableExtra(SERIALIZED_JOB_OFFER_DATA);
        jobOfferPosition = getIntent().getIntExtra(JOB_OFFER_POSITION, -1);
        callingActivity = getIntent().getStringExtra(CALLING_ACTIVITY);

        Log.d("Result", callingActivity);

        companyAndLocationTextView.setText(jobOffer.getCompany() + " / " + jobOffer.getLocation());
        titleTextView.setText(jobOffer.getTitle());
        descriptionTextView.setText(Html.fromHtml(jobOffer.getDescription()));

        Picasso.get().load(jobOffer.getCompanyLogo()).into(companyLogoImageView);

        openOnGithubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(jobOffer.getUrl()));
                startActivity(browserIntent);
            }
        });

        openCompanyWebsiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(jobOffer.getCompanyUrl()));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.root_menu, menu);

        if (database.getStoredJobOffer(jobOffer.id)) {
            menu.findItem(R.id.action_favorite).setIcon(R.mipmap.icon_heartfilled_border_lay);
            isHeartFilled = true;
        } else {
            menu.findItem(R.id.action_favorite).setIcon(R.mipmap.icon_heart_border_lay);
            isHeartFilled = false;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                if (isHeartFilled) {
                    MakeToast("Offer removed from favourites");

                    if (callingActivity.contains("RootActivity")) {
                        RemoveJobOfferFromFavourites();
                    }

                    item.setIcon(R.mipmap.icon_heart_border_lay);
                    isHeartFilled = false;
                    finish();
                } else {
                    MakeToast("Offer added to favourites");
                    createJobOffer(jobOffer);
                    item.setIcon(R.mipmap.icon_heartfilled_border_lay);
                    isHeartFilled = true;
                    finish();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createJobOffer(JobOffer jobOffer) {
        database.insertJobOffer(jobOffer);
    }

    private void deleteJobOffer(JobOffer jobOffer) {
        database.deleteJobOffer(jobOffer);
    }

    private void RemoveJobOfferFromFavourites() {
        deleteJobOffer(jobOffer);
        FavouritesFragment.jobOffers.remove(jobOfferPosition);
        FavouritesFragment.jobOfferAdapter.notifyItemRemoved(jobOfferPosition);
        FavouritesFragment.jobOfferAdapter.notifyDataSetChanged();
    }

    private void MakeToast(String toastMessage) {
        CharSequence toastText = toastMessage;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, toastText, duration);
        toast.show();
    }
}
