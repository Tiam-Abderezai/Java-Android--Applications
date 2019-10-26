// AssessmentDatabase

package com.example.term_tracker_wgu.DataBase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.term_tracker_wgu.DataAccessObjects.AssessmentDao;
import com.example.term_tracker_wgu.Model.Assessment;

@Database(entities = Assessment.class, version = 1)
public abstract class AssessmentDatabase extends RoomDatabase {

    private static AssessmentDatabase instance;

    public abstract AssessmentDao assessmentDao();

    public static synchronized AssessmentDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AssessmentDatabase.class, "assessment_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private AssessmentDao assessmentDao;

        private PopulateDbAsyncTask(AssessmentDatabase db) {
            assessmentDao = db.assessmentDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            assessmentDao.insert(new Assessment("Term1", "1/1/19", "8/15/19"));
            assessmentDao.insert(new Assessment("Term2", "9/1/19", "12/15/19"));
            assessmentDao.insert(new Assessment("Term3", "1/1/20", "8/1/20"));
            return null;
        }
    }
}
