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

    private boolean isHeartFilled = false;
    private static String SERIALIZED_JOB_OFFER_DATA = "SERIALIZED_JOB_OFFER_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        final JobOffer jobOffer = (JobOffer) getIntent().getSerializableExtra(SERIALIZED_JOB_OFFER_DATA);

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
                Log.d("Result", jobOffer.getCompanyUrl());
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(jobOffer.getCompanyUrl()));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.root_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            case R.id.action_favorite:
                if (isHeartFilled) {
                    MakeToast("Offer removed from favourites");
                    item.setIcon(R.mipmap.icon_heart_border_lay);
                    isHeartFilled = false;
                } else {
                    MakeToast("Offer added to favourites");
                    item.setIcon(R.mipmap.icon_heartfilled_border_lay);
                    isHeartFilled = true;
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void MakeToast(String toastMessage) {
        CharSequence toastText = toastMessage;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, toastText, duration);
        toast.show();
    }
}
