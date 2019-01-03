package uep.project.dev.gitjobs;

import java.io.Serializable;

public class JobOffer implements Serializable {
    public static final String TABLE_NAME = "joboffers";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_JOB_ID = "jobid";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_CREATED_AT = "createdat";
    public static final String COLUMN_COMPANY = "company";
    public static final String COLUMN_COMPANY_URL = "companyurl";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_HOW_TO_APPLY = "howtoapply";
    public static final String COLUMN_COMPANY_LOGO = "companylogo";
    //public static final String COLUMN_IS_FAVOURITE = "isfavourite";


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
    //int isFavourite;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_JOB_ID + " TEXT,"
                    + COLUMN_TYPE + " TEXT,"
                    + COLUMN_URL + " TEXT,"
                    + COLUMN_CREATED_AT + " TEXT,"
                    + COLUMN_COMPANY + " TEXT,"
                    + COLUMN_COMPANY_URL + " TEXT,"
                    + COLUMN_LOCATION + " TEXT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_HOW_TO_APPLY + " TEXT,"
                    + COLUMN_COMPANY_LOGO + " TEXT"
                    //+ COLUMN_IS_FAVOURITE + " INTEGER"
                    + ")";

    public JobOffer(String id, String type, String url, String createdAt, String company, String companyUrl, String location, String title, String description, String howToApply, String companyLogo/*, int isFavourite*/) {
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
        //this.isFavourite = isFavourite;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCompany() {
        return company;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getHowToApply() {
        return howToApply;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    /*public int getIsFavourite() { return isFavourite; }

    public void setIsFavorutie(int isFavourite) {
        this.isFavourite = isFavourite;
    }*/
}
