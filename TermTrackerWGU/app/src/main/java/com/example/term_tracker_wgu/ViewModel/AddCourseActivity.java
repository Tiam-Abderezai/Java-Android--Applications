//AddCourseActivity

package com.example.term_tracker_wgu.ViewModel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.term_tracker_wgu.R;

public class AddCourseActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_ID";

    public static final String EXTRA_TITLE =
            "com.example.term_tracker_wgu.ViewModel.EXTRA_TITLE";

    public static final String EXTRA_DATE_START =
            "com.example.term_tracker_wgu.ViewModel.DATE_START";

    public static final String EXTRA_DATE_END =
            "com.example.term_tracker_wgu.ViewModel.DATE_END";

    private EditText editTextTitle;
    private EditText editTextStartDate;
    private EditText editTextEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextStartDate = findViewById(R.id.edit_text_date_start);
        editTextEndDate = findViewById(R.id.edit_text_date_end);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Course");
            editTextTitle.setText(intent.getStringExtra(Intent.EXTRA_TITLE));
            editTextStartDate.setText(intent.getStringExtra(EXTRA_DATE_START));
            editTextEndDate.setText(intent.getStringExtra(EXTRA_DATE_END));

        } else {
            setTitle("Add Course");
        }

        setTitle("Add Course");

    }

    private void saveNote() {
        String title = editTextTitle.getText().toString();
        String date_start = editTextStartDate.getText().toString();
        String date_end = editTextEndDate.getText().toString();


        if (title.trim().isEmpty() || date_start.trim().isEmpty()) {
            Toast.makeText(this, "Please insert the name and dates.", Toast.LENGTH_SHORT).show();


            return;
        }


        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DATE_START, date_start);
        data.putExtra(EXTRA_DATE_END, date_end);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_course_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_course:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
