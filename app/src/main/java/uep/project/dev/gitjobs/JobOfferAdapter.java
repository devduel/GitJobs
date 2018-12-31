package uep.project.dev.gitjobs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class JobOfferAdapter extends RecyclerView.Adapter<JobOfferAdapter.MyViewHolder> {

    private List<JobOffer> jobOffers;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView company;
        private TextView location;
        //private TextView type;
        private RelativeLayout relativeLayout;

        public MyViewHolder(View view) {
            super(view);

            relativeLayout = view.findViewById(R.id.relativeLayout);
            title = view.findViewById(R.id.title);
            company = view.findViewById(R.id.company);
            location = view.findViewById(R.id.location);
            //type = view.findViewById(R.id.type);
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
        //holder.type.setText(jobOffer.getLocation());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence toastText = "You clicked " + jobOffers.get(position).title;
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(view.getContext(), toastText, duration);
                toast.show();

                JobOffer jobOffer = jobOffers.get(position);
                openSelectedJobOfferActivity(jobOffer);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobOffers.size();
    }

    private void openSelectedJobOfferActivity(JobOffer jobOffer) {
        Intent intent = new Intent(context, SelectedJobOfferActivity.class);
        intent.putExtra("serialize_data", jobOffer);
        context.startActivity(intent);
    }
}
