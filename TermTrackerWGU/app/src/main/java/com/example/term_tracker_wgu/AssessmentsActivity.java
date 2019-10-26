package com.example.term_tracker_wgu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.term_tracker_wgu.Adapters.AssessmentAdapter;
import com.example.term_tracker_wgu.Model.Assessment;
import com.example.term_tracker_wgu.ViewModel.AddAssessmentActivity;
import com.example.term_tracker_wgu.ViewModel.AssessmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class AssessmentsActivity extends AppCompatActivity {

//    private Button mainButton;

    public static final int ADD_ASSESSMENT_REQUEST = 1;
    public static final int EDIT_ASSESSMENT_REQUEST = 2;

    public AssessmentViewModel assessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);

        FloatingActionButton buttonAddAssessment = findViewById(R.id.button_add_assessment);
        buttonAddAssessment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AssessmentsActivity.this, AddAssessmentActivity.class);
                startActivityForResult(intent, ADD_ASSESSMENT_REQUEST);
            }

        });


        RecyclerView recyclerView = findViewById(R.id.recycler_view_assessment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final AssessmentAdapter adapter = new AssessmentAdapter();
        recyclerView.setAdapter(adapter);

        assessmentViewModel = ViewModelProviders.of(this).get(AssessmentViewModel.class);
        assessmentViewModel.getAllAssessments().observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(@Nullable List<Assessment> assessments) {
                adapter.setAssessments(assessments);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                assessmentViewModel.delete(adapter.getAssessmentAt(viewHolder.getAdapterPosition()));
                Toast.makeText(AssessmentsActivity.this, "Assessment deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new AssessmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Assessment assessment) {
                Intent intent = new Intent(AssessmentsActivity.this, AddAssessmentActivity.class);
                intent.putExtra(AddAssessmentActivity.EXTRA_ID, assessment.getAssessmentId());
                intent.putExtra(AddAssessmentActivity.EXTRA_TITLE, assessment.getAssessmentTitle());
                intent.putExtra(AddAssessmentActivity.EXTRA_DATE_START, assessment.getAssessmentDateStart());
                intent.putExtra(AddAssessmentActivity.EXTRA_DATE_END, assessment.getAssessmentDateEnd());
                startActivityForResult(intent, EDIT_ASSESSMENT_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_ASSESSMENT_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddAssessmentActivity.EXTRA_TITLE);
            String date_start = data.getStringExtra(AddAssessmentActivity.EXTRA_DATE_START);
            String date_end = data.getStringExtra(AddAssessmentActivity.EXTRA_DATE_END);

            Assessment assessment = new Assessment(title, date_start, date_end);
            assessmentViewModel.insert(assessment);

            Toast.makeText(this, "Assessment saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_ASSESSMENT_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddAssessmentActivity.EXTRA_ID, -1);
            if(id == -1) {
                Toast.makeText(this, "Assessment can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddAssessmentActivity.EXTRA_TITLE);
            String date_start = data.getStringExtra(AddAssessmentActivity.EXTRA_DATE_START);
            String date_end = data.getStringExtra(AddAssessmentActivity.EXTRA_DATE_END);

            Assessment assessment = new Assessment(title, date_start, date_end);
            assessment.setAssessmentId(id);
            assessmentViewModel.update(assessment);

            Toast.makeText(this, "Assessment updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Assessment not saved", Toast.LENGTH_SHORT).show();



        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_assessment:
                assessmentViewModel.deleteAllAssessments();
                Toast.makeText(this, "All assessments deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}


