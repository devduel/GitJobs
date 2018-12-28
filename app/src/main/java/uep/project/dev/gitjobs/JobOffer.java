package uep.project.dev.gitjobs;

public class JobOffer {
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

    public JobOffer(String id, String type, String url, String createdAt, String company, String companyUrl, String location, String title, String description, String howToApply, String companyLogo) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.createdAt = createdAt;
        this.company = company;
        this.companyUrl = companyUrl;
        this.location = location;
        this.title = title;
        this.description = description;
        this.howToApply = howToApply;
        this.companyLogo = companyLogo;
    }
}
