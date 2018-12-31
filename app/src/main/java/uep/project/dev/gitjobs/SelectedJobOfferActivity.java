package uep.project.dev.gitjobs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SelectedJobOfferActivity extends AppCompatActivity {

    private Button openOnGithubButton;
    private Button openCompanyWebsiteButton;

    private TextView companyAndLocationTextView;
    private TextView titleTextView;
    private TextView descriptionTextView;

    private ImageView companyLogoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_job_offer);

        companyAndLocationTextView = findViewById(R.id.companyAndLocationTextViewId);
        titleTextView = findViewById(R.id.titleTextViewId);
        descriptionTextView = findViewById(R.id.descriptionTextViewId);

        openOnGithubButton = findViewById(R.id.openOnGithubButtonId);
        openCompanyWebsiteButton = findViewById(R.id.openCompanyWebsiteButtonId);

        companyLogoImageView = findViewById(R.id.companyLogoImageViewId);

        final JobOffer jobOffer = (JobOffer) getIntent().getSerializableExtra("serialize_data");

        Log.d("JobOffer comp url", jobOffer.getCompanyUrl());
        Log.d("JobOffer comp", jobOffer.getCompany());
        Log.d("JobOffer created", jobOffer.getCreatedAt());
        Log.d("JobOffer id", jobOffer.getId());
        Log.d("JobOffer type", jobOffer.getCompanyLogo());
        Log.d("JobOffer url", jobOffer.getUrl());

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
}
