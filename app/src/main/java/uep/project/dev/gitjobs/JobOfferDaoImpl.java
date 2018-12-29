package uep.project.dev.gitjobs;

import java.util.ArrayList;
import java.util.List;

public class JobOfferDaoImpl implements JobOfferDao {
    public static List<JobOffer> jobOffers;

    public JobOfferDaoImpl() {
        jobOffers = new ArrayList<>();
        //jobOffers =
    }

    @Override
    public List<JobOffer> getJobOffers() {
        return jobOffers;
    }
}
