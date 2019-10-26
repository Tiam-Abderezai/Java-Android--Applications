
package com.example.term_tracker_wgu.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.term_tracker_wgu.Model.Mentor;
import com.example.term_tracker_wgu.R;

import java.util.ArrayList;
import java.util.List;

public class MentorAdapter extends RecyclerView.Adapter<MentorAdapter.MentorHolder> {

    private List<Mentor> mentors = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public MentorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mentor_items, parent, false);

        return new MentorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorHolder holder, int position) {
        Mentor currentMentor = mentors.get(position);
        holder.textViewTitle.setText(currentMentor.getMentorTitle());
        holder.textViewStartDate.setText(currentMentor.getMentorDateStart());
        holder.textViewEndDate.setText(currentMentor.getMentorDateEnd());
    }

    @Override
    public int getItemCount() {
        return mentors.size();
    }

    public void setMentors(List<Mentor> mentors) {
        this.mentors = mentors;
        notifyDataSetChanged();
    }

    public Mentor getMentorAt(int position) {
        return mentors.get(position);
    }

    class MentorHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewStartDate;
        private TextView textViewEndDate;

        public MentorHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewStartDate = itemView.findViewById(R.id.text_view_date_start);
            textViewEndDate = itemView.findViewById(R.id.text_view_date_end);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mentors.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Mentor mentor);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
