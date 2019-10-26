package com.example.term_tracker_wgu.Model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mentor_table")
public class Mentor {

    @PrimaryKey(autoGenerate = true)
    private int mentorId;
    private String mentorTitle = "";
    private String mentorDateStart = "";
    private String mentorDateEnd = "";

    public Mentor(String mentorTitle, String mentorDateStart, String mentorDateEnd) {

        this.mentorTitle = mentorTitle;
        this.mentorDateStart = mentorDateStart;
        this.mentorDateEnd = mentorDateEnd;
    }



    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorTitle(String mentorTitle) {
        this.mentorTitle = mentorTitle;
    }

    public String getMentorTitle() {
        return mentorTitle;
    }

    public void setMentorDateStart(String mentorDateStart) {
        this.mentorDateStart = mentorDateStart;
    }

    public String getMentorDateEnd() {
        return mentorDateEnd;
    }

    public void setMentorDateEnd(String mentorDateEnd) {
        this.mentorDateEnd = mentorDateEnd;
    }

    public String getMentorDateStart() {
        return mentorDateStart;
    }


}
