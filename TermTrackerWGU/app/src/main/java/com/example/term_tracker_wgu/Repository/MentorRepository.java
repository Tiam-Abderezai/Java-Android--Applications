// MentorRepository

package com.example.term_tracker_wgu.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.term_tracker_wgu.DataAccessObjects.MentorDao;
import com.example.term_tracker_wgu.DataBase.MentorDatabase;
import com.example.term_tracker_wgu.Model.Mentor;

import java.util.List;

public class MentorRepository {
    private MentorDao mentorDao;
    private LiveData<List<Mentor>> allMentors;

    public MentorRepository(Application application){
        MentorDatabase database = MentorDatabase.getInstance(application);
        mentorDao = database.mentorDao();
        allMentors = mentorDao.getAllMentors();
    }

    public void insert(Mentor mentor) {
        new InsertMentorAsyncTask(mentorDao).execute(mentor);
    }

    public void update(Mentor mentor) {
        new UpdateMentorAsyncTask(mentorDao).execute(mentor);
    }

    public void delete(Mentor mentor) {
        new DeleteMentorAsyncTask(mentorDao).execute(mentor);
    }

    public void deleteAllMentors() {
        new DeleteAllMentorsAsyncTask(mentorDao).execute();
    }

    public LiveData<List<Mentor>> getAllMentors(){
        return allMentors;
    }

    private static class InsertMentorAsyncTask extends AsyncTask<Mentor, Void, Void> {
        private MentorDao mentorDao;

        private InsertMentorAsyncTask(MentorDao mentorDao) {
            this.mentorDao = mentorDao;
        }

        @Override
        protected Void doInBackground(Mentor... mentors) {
            mentorDao.insert(mentors[0]);
            return null;
        }
    }

    private static class UpdateMentorAsyncTask extends AsyncTask<Mentor, Void, Void> {
        private MentorDao mentorDao;

        private UpdateMentorAsyncTask(MentorDao mentorDao) {
            this.mentorDao = mentorDao;
        }

        @Override
        protected Void doInBackground(Mentor... mentors) {
            mentorDao.update(mentors[0]);
            return null;
        }
    }

    private static class DeleteMentorAsyncTask extends AsyncTask<Mentor, Void, Void> {
        private MentorDao mentorDao;

        private DeleteMentorAsyncTask(MentorDao mentorDao) {
            this.mentorDao = mentorDao;
        }

        @Override
        protected Void doInBackground(Mentor... mentors) {
            mentorDao.delete(mentors[0]);
            return null;
        }
    }

    private static class DeleteAllMentorsAsyncTask extends AsyncTask<Void, Void, Void> {
        private MentorDao mentorDao;

        private DeleteAllMentorsAsyncTask(MentorDao mentorDao) {
            this.mentorDao = mentorDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mentorDao.deleteAllMentors();
            return null;
        }
    }

}
