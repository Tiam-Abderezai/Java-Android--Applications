package com.example.term_tracker_wgu.DataBase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.term_tracker_wgu.DataAccessObjects.TermDao;
import com.example.term_tracker_wgu.Model.Term;

@Database(entities = Term.class, version = 1)
public abstract class TermDatabase extends RoomDatabase {

    private static TermDatabase instance;

    public abstract TermDao termDao();

    public static synchronized TermDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), TermDatabase.class, "term_database")
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
        private TermDao termDao;

        private PopulateDbAsyncTask(TermDatabase db) {
            termDao = db.termDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
