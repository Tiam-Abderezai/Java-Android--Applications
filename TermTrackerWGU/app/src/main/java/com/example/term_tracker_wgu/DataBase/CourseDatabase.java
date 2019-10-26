
package com.example.term_tracker_wgu.DataBase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.term_tracker_wgu.DataAccessObjects.CourseDao;
import com.example.term_tracker_wgu.Model.Course;

@Database(entities = Course.class, version = 1)
public abstract class CourseDatabase extends RoomDatabase {

    private static CourseDatabase instance;

    public abstract CourseDao courseDao();

    public static synchronized CourseDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), CourseDatabase.class, "course_database")
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
        private CourseDao courseDao;

        private PopulateDbAsyncTask(CourseDatabase db) {
            courseDao = db.courseDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            courseDao.insert(new Course("Term1", "1/1/19", "8/15/19"));
            courseDao.insert(new Course("Term2", "9/1/19", "12/15/19"));
            courseDao.insert(new Course("Term3", "1/1/20", "8/1/20"));
            return null;
        }
    }
}