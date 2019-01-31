package uep.project.dev.gitjobs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "joboffers_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(JobOffer.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + JobOffer.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertJobOffer(JobOffer jobOffer) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(JobOffer.COLUMN_JOB_ID, jobOffer.getId());
        values.put(JobOffer.COLUMN_TYPE, jobOffer.getType());
        values.put(JobOffer.COLUMN_URL, jobOffer.getUrl());
        values.put(JobOffer.COLUMN_CREATED_AT, jobOffer.getCreatedAt());
        values.put(JobOffer.COLUMN_COMPANY, jobOffer.getCompany());
        values.put(JobOffer.COLUMN_COMPANY_URL, jobOffer.getCompanyUrl());
        values.put(JobOffer.COLUMN_LOCATION, jobOffer.getLocation());
        values.put(JobOffer.COLUMN_TITLE, jobOffer.getTitle());
        values.put(JobOffer.COLUMN_DESCRIPTION, jobOffer.getDescription());
        values.put(JobOffer.COLUMN_HOW_TO_APPLY, jobOffer.getHowToApply());
        values.put(JobOffer.COLUMN_COMPANY_LOGO, jobOffer.getCompanyLogo());

        long id = database.insert(JobOffer.TABLE_NAME, null, values);
        database.close();
        return id;
    }

    public boolean getStoredJobOffer(String jobId) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(JobOffer.TABLE_NAME,
                new String[]{JobOffer.COLUMN_JOB_ID, JobOffer.COLUMN_TITLE},
                JobOffer.COLUMN_JOB_ID + " = ?",
                new String[]{String.valueOf(jobId)}, null, null, null, null);

        if (cursor.getCount() <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public List<JobOffer> getStoredJobOffers() {
        List<JobOffer> jobOffers = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + JobOffer.TABLE_NAME;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String jobId = cursor.getString(cursor.getColumnIndex(JobOffer.COLUMN_JOB_ID));
                String type = cursor.getString(cursor.getColumnIndex(JobOffer.COLUMN_TYPE));
                String url = cursor.getString(cursor.getColumnIndex(JobOffer.COLUMN_URL));
                String createdAt = cursor.getString(cursor.getColumnIndex(JobOffer.COLUMN_CREATED_AT));
                String company = cursor.getString(cursor.getColumnIndex(JobOffer.COLUMN_COMPANY));
                String companyUrl = cursor.getString(cursor.getColumnIndex(JobOffer.COLUMN_COMPANY_URL));
                String location = cursor.getString(cursor.getColumnIndex(JobOffer.COLUMN_LOCATION));
                String title = cursor.getString(cursor.getColumnIndex(JobOffer.COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(JobOffer.COLUMN_DESCRIPTION));
                String howToApply = cursor.getString(cursor.getColumnIndex(JobOffer.COLUMN_HOW_TO_APPLY));
                String companyLogo = cursor.getString(cursor.getColumnIndex(JobOffer.COLUMN_COMPANY_LOGO));

                JobOffer jobOffer = new JobOffer(jobId, type, url, createdAt, company, companyUrl, location, title, description, howToApply, companyLogo/*, isFavourite*/);
                jobOffers.add(jobOffer);
            } while (cursor.moveToNext());
        }
        database.close();

        return jobOffers;
    }

    public void deleteJobOffer(JobOffer jobOffer) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(JobOffer.TABLE_NAME, JobOffer.COLUMN_JOB_ID + " = ?", new String[]{String.valueOf(jobOffer.getId())});
        database.close();
    }
}
