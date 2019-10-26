package com.example.term_tracker_wgu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.term_tracker_wgu.Adapters.TermAdapter;
import com.example.term_tracker_wgu.Model.Term;
import com.example.term_tracker_wgu.ViewModel.EditTermActivity;
import com.example.term_tracker_wgu.ViewModel.TermViewModel;
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


public class TermsActivity extends AppCompatActivity {

//    private Button mainButton;

    public static final int ADD_TERM_REQUEST = 1;
    public static final int EDIT_TERM_REQUEST = 2;

    public TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        FloatingActionButton buttonAddTerm = findViewById(R.id.button_add_term);
        buttonAddTerm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermsActivity.this, EditTermActivity.class);
                startActivityForResult(intent, ADD_TERM_REQUEST);
            }

        });


        RecyclerView recyclerView = findViewById(R.id.recycler_view_terms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TermAdapter adapter = new TermAdapter();
        recyclerView.setAdapter(adapter);

        termViewModel = ViewModelProviders.of(this).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(@Nullable List<Term> terms) {
                adapter.setTerms(terms);
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
                termViewModel.delete(adapter.getTermAt(viewHolder.getAdapterPosition()));
                Toast.makeText(TermsActivity.this, "Term deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TermAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Term term) {
                Intent intent = new Intent(TermsActivity.this, EditTermActivity.class);
                intent.putExtra(EditTermActivity.EXTRA_ID, term.getTermId());
                intent.putExtra(EditTermActivity.EXTRA_TITLE, term.getTermTitle());
                intent.putExtra(EditTermActivity.EXTRA_DATE_START, term.getTermDateStart());
                intent.putExtra(EditTermActivity.EXTRA_DATE_END, term.getTermDateEnd());
                intent.putExtra(EditTermActivity.EXTRA_STATUS, term.getTermStatus());

                startActivityForResult(intent, EDIT_TERM_REQUEST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TERM_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(EditTermActivity.EXTRA_TITLE);
            String date_start = data.getStringExtra(EditTermActivity.EXTRA_DATE_START);
            String date_end = data.getStringExtra(EditTermActivity.EXTRA_DATE_END);
            String status = data.getStringExtra(EditTermActivity.EXTRA_STATUS);

            Term term = new Term(title, date_start, date_end, status);
            termViewModel.insert(term);

            Toast.makeText(this, "Term saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_TERM_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EditTermActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Term can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(EditTermActivity.EXTRA_TITLE);
            String date_start = data.getStringExtra(EditTermActivity.EXTRA_DATE_START);
            String date_end = data.getStringExtra(EditTermActivity.EXTRA_DATE_END);
            String status = data.getStringExtra(EditTermActivity.EXTRA_STATUS);

            Term term = new Term(title, date_start, date_end, status);
            term.setTermId(id);
            termViewModel.update(term);

            Toast.makeText(this, "Term updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Term not saved", Toast.LENGTH_SHORT).show();


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
            case R.id.delete_all_terms:
                termViewModel.deleteAllTerms();
                Toast.makeText(this, "All terms deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}


