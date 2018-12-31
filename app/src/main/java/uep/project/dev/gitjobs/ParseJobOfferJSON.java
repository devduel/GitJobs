package uep.project.dev.gitjobs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParseJobOfferJSON {

    //TODO: Load companyURL and companyLogo in proper way
    public static List<JobOffer> parseFrom(String data) throws JSONException {
        List<JobOffer> jobOffers = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(data);

        String id;
        String type;
        String url;
        String createdAt;
        String company;
        String companyUrl;
        String location;
        String title;
        String description;
        String howToApply;
        String companyLogo;

        JobOffer jobOffer;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);

            id = (String) jsonObject.get("id");
            type = (String) jsonObject.get("type");
            url = (String) jsonObject.get("url");
            createdAt = (String) jsonObject.get("created_at");
            company = (String) jsonObject.get("company");
            companyUrl = jsonObject.get("company_url").toString();
            location = (String) jsonObject.get("location");
            title = (String) jsonObject.get("title");
            description = (String) jsonObject.get("description");
            howToApply = (String) jsonObject.get("how_to_apply");
            companyLogo = jsonObject.get("company_logo").toString();

            jobOffer = new JobOffer(id, type, url, createdAt, company, companyUrl, location, title, description, howToApply, companyLogo);

            jobOffers.add(jobOffer);
        }
        return jobOffers;
    }
}
