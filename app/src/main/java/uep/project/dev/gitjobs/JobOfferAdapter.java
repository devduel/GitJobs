package uep.project.dev.gitjobs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class JobOfferAdapter extends RecyclerView.Adapter<JobOfferAdapter.MyViewHolder> {

    private List<JobOffer> jobOffers;
    private Context context;
    private static final String SERIALIZED_JOB_OFFER_DATA = "SERIALIZED_JOB_OFFER_DATA";
    private static final String JOB_OFFER_POSITION = "JOB_OFFER_POSITION";
    private static final String CALLING_ACTIVITY = "CALLING_ACTIVITY";

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView company;
        private TextView location;
        private RelativeLayout relativeLayout;

        public MyViewHolder(View view) {
            super(view);

            relativeLayout = view.findViewById(R.id.relativeLayout);
            title = view.findViewById(R.id.title);
            company = view.findViewById(R.id.company);
            location = view.findViewById(R.id.location);
        }
    }

    public JobOfferAdapter(List<JobOffer> jobOffers, Context context) {
        this.jobOffers = jobOffers;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_offer_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final JobOffer jobOffer = jobOffers.get(position);
        holder.title.setText(jobOffer.getTitle());
        holder.company.setText(jobOffer.getCompany());
        holder.location.setText(jobOffer.getLocation());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobOffer jobOffer = jobOffers.get(position);
                int positionOnTheList = position;
                openSelectedJobOfferActivity(jobOffer, positionOnTheList, context.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobOffers.size();
    }

    private void openSelectedJobOfferActivity(JobOffer jobOffer, int position, String callingActivity) {
        Intent intent = new Intent(context, SelectedJobOfferActivity.class);
        intent.putExtra(SERIALIZED_JOB_OFFER_DATA, jobOffer);
        intent.putExtra(JOB_OFFER_POSITION, position);
        intent.putExtra(CALLING_ACTIVITY, callingActivity);
        context.startActivity(intent);
    }
}
