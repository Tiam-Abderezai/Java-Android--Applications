//CoursesActivity

package com.example.term_tracker_wgu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.term_tracker_wgu.Adapters.CourseAdapter;
import com.example.term_tracker_wgu.Model.Course;
import com.example.term_tracker_wgu.ViewModel.AddCourseActivity;
import com.example.term_tracker_wgu.ViewModel.CourseViewModel;
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


public class CoursesActivity extends AppCompatActivity {

//    private Button mainButton;

    public static final int ADD_COURSE_REQUEST = 1;
    public static final int EDIT_COURSE_REQUEST = 2;

    public CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        FloatingActionButton buttonAddCourse = findViewById(R.id.button_add_course);
        buttonAddCourse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CoursesActivity.this, AddCourseActivity.class);
                startActivityForResult(intent, ADD_COURSE_REQUEST);
            }

        });


        RecyclerView recyclerView = findViewById(R.id.recycler_view_courses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CourseAdapter adapter = new CourseAdapter();
        recyclerView.setAdapter(adapter);

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getAllCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(@Nullable List<Course> courses) {
                adapter.setCourses(courses);
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
                courseViewModel.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(CoursesActivity.this, "Course deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                Intent intent = new Intent(CoursesActivity.this, AddCourseActivity.class);
                intent.putExtra(AddCourseActivity.EXTRA_ID, course.getCourseId());
                intent.putExtra(AddCourseActivity.EXTRA_TITLE, course.getCourseTitle());
                intent.putExtra(AddCourseActivity.EXTRA_DATE_START, course.getCourseDateStart());
                intent.putExtra(AddCourseActivity.EXTRA_DATE_END, course.getCourseDateEnd());
                startActivityForResult(intent, EDIT_COURSE_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddCourseActivity.EXTRA_TITLE);
            String date_start = data.getStringExtra(AddCourseActivity.EXTRA_DATE_START);
            String date_end = data.getStringExtra(AddCourseActivity.EXTRA_DATE_END);

            Course course = new Course(title, date_start, date_end);
            courseViewModel.insert(course);

            Toast.makeText(this, "Course saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddCourseActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Course can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddCourseActivity.EXTRA_TITLE);
            String date_start = data.getStringExtra(AddCourseActivity.EXTRA_DATE_START);
            String date_end = data.getStringExtra(AddCourseActivity.EXTRA_DATE_END);

            Course course = new Course(title, date_start, date_end);
            course.setCourseId(id);
            courseViewModel.update(course);

            Toast.makeText(this, "Course updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Course not saved", Toast.LENGTH_SHORT).show();


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
            case R.id.delete_all_courses:
                courseViewModel.deleteAllCourses();
                Toast.makeText(this, "All courses deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}


