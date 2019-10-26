
package com.example.term_tracker_wgu.DataBase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.term_tracker_wgu.DataAccessObjects.MentorDao;
import com.example.term_tracker_wgu.Model.Mentor;

@Database(entities = Mentor.class, version = 1)
public abstract class MentorDatabase extends RoomDatabase {

    private static MentorDatabase instance;

    public abstract MentorDao mentorDao();

    public static synchronized MentorDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MentorDatabase.class, "mentor_database")
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
        private MentorDao mentorDao;

        private PopulateDbAsyncTask(MentorDatabase db) {
            mentorDao = db.mentorDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mentorDao.insert(new Mentor("Term1", "1/1/19", "8/15/19"));
            mentorDao.insert(new Mentor("Term2", "9/1/19", "12/15/19"));
            mentorDao.insert(new Mentor("Term3", "1/1/20", "8/1/20"));
            return null;
        }
    }
}
