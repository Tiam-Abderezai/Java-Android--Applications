// MentorViewModel

package com.example.term_tracker_wgu.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.term_tracker_wgu.Model.Mentor;
import com.example.term_tracker_wgu.Repository.MentorRepository;

import java.util.List;

public class MentorViewModel extends AndroidViewModel {
    private MentorRepository repository;
    private LiveData<List<Mentor>> allMentors;

    public MentorViewModel(@NonNull Application application) {
        super(application);
        repository = new MentorRepository(application);
        allMentors = repository.getAllMentors();
    }

    public void insert(Mentor mentor) {
        repository.insert(mentor);
    }

    public void update(Mentor mentor) {
        repository.update(mentor);
    }

    public void delete(Mentor mentor) {
        repository.delete(mentor);
    }

    public void deleteAllMentors() {
        repository.deleteAllMentors();
    }

    public LiveData<List<Mentor>> getAllMentors() {
        return allMentors;
    }
}
