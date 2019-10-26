package com.example.term_tracker_wgu.Model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table")
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int courseId;
    private String courseTitle = "";
    private String courseDateStart = "";
    private String courseDateEnd = "";

    public Course(String courseTitle, String courseDateStart, String courseDateEnd) {

        this.courseTitle = courseTitle;
        this.courseDateStart = courseDateStart;
        this.courseDateEnd = courseDateEnd;
    }


    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseDateStart(String courseDateStart) {
        this.courseDateStart = courseDateStart;
    }

    public String getCourseDateEnd() {
        return courseDateEnd;
    }

    public void setCourseDateEnd(String courseDateEnd) {
        this.courseDateEnd = courseDateEnd;
    }

    public String getCourseDateStart() {
        return courseDateStart;
    }


}
