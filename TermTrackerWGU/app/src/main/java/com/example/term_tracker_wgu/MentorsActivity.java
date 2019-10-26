// MentorsActivity

package com.example.term_tracker_wgu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.term_tracker_wgu.Adapters.MentorAdapter;
import com.example.term_tracker_wgu.Model.Mentor;
import com.example.term_tracker_wgu.ViewModel.AddMentorActivity;
import com.example.term_tracker_wgu.ViewModel.MentorViewModel;
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


public class MentorsActivity extends AppCompatActivity {

//    private Button mainButton;

    public static final int ADD_MENTOR_REQUEST = 1;
    public static final int EDIT_MENTOR_REQUEST = 2;

    public MentorViewModel mentorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentors);

        FloatingActionButton buttonAddMentor = findViewById(R.id.button_add_mentor);
        buttonAddMentor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MentorsActivity.this, AddMentorActivity.class);
                startActivityForResult(intent, ADD_MENTOR_REQUEST);
            }

        });


        RecyclerView recyclerView = findViewById(R.id.recycler_view_mentors);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final MentorAdapter adapter = new MentorAdapter();
        recyclerView.setAdapter(adapter);

        mentorViewModel = ViewModelProviders.of(this).get(MentorViewModel.class);
        mentorViewModel.getAllMentors().observe(this, new Observer<List<Mentor>>() {
            @Override
            public void onChanged(@Nullable List<Mentor> mentors) {
                adapter.setMentors(mentors);
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
                mentorViewModel.delete(adapter.getMentorAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MentorsActivity.this, "Mentor deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new MentorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Mentor mentor) {
                Intent intent = new Intent(MentorsActivity.this, AddMentorActivity.class);
                intent.putExtra(AddMentorActivity.EXTRA_ID, mentor.getMentorId());
                intent.putExtra(AddMentorActivity.EXTRA_TITLE, mentor.getMentorTitle());
                intent.putExtra(AddMentorActivity.EXTRA_DATE_START, mentor.getMentorDateStart());
                intent.putExtra(AddMentorActivity.EXTRA_DATE_END, mentor.getMentorDateEnd());
                startActivityForResult(intent, EDIT_MENTOR_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_MENTOR_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddMentorActivity.EXTRA_TITLE);
            String date_start = data.getStringExtra(AddMentorActivity.EXTRA_DATE_START);
            String date_end = data.getStringExtra(AddMentorActivity.EXTRA_DATE_END);

            Mentor mentor = new Mentor(title, date_start, date_end);
            mentorViewModel.insert(mentor);

            Toast.makeText(this, "Mentor saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_MENTOR_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddMentorActivity.EXTRA_ID, -1);
            if(id == -1) {
                Toast.makeText(this, "Mentor can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddMentorActivity.EXTRA_TITLE);
            String date_start = data.getStringExtra(AddMentorActivity.EXTRA_DATE_START);
            String date_end = data.getStringExtra(AddMentorActivity.EXTRA_DATE_END);

            Mentor mentor = new Mentor(title, date_start, date_end);
            mentor.setMentorId(id);
            mentorViewModel.update(mentor);

            Toast.makeText(this, "Mentor updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Mentor not saved", Toast.LENGTH_SHORT).show();



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
            case R.id.delete_all_mentors:
                mentorViewModel.deleteAllMentors();
                Toast.makeText(this, "All mentors deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}


