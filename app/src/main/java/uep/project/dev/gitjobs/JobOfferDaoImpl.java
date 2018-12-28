package uep.project.dev.gitjobs;

import java.util.ArrayList;
import java.util.List;

public class JobOfferDaoImpl implements JobOfferDao {
    List<JobOffer> jobOffers;

    public JobOfferDaoImpl() {
        jobOffers = new ArrayList<JobOffer>();
    }

    @Override
    public List<JobOffer> getJobOffers() {
        return null;
    }
}
