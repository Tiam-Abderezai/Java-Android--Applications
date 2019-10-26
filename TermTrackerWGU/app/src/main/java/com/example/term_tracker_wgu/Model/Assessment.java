//Assessment

package com.example.term_tracker_wgu.Model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessment_table")
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private int assessmentId;
    private String assessmentTitle = "";
    private String assessmentDateStart = "";
    private String assessmentDateEnd = "";

    public Assessment(String assessmentTitle, String assessmentDateStart, String assessmentDateEnd) {

        this.assessmentTitle = assessmentTitle;
        this.assessmentDateStart = assessmentDateStart;
        this.assessmentDateEnd = assessmentDateEnd;
    }



    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }

    public int getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentDateStart(String assessmentDateStart) {
        this.assessmentDateStart = assessmentDateStart;
    }

    public String getAssessmentDateEnd() {
        return assessmentDateEnd;
    }

    public void setAssessmentDateEnd(String assessmentDateEnd) {
        this.assessmentDateEnd = assessmentDateEnd;
    }

    public String getAssessmentDateStart() {
        return assessmentDateStart;
    }


}
