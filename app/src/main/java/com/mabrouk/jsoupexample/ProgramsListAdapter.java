package com.mabrouk.jsoupexample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mabrouk.model.Program;

import java.util.List;

public class ProgramsListAdapter extends RecyclerView.Adapter<ProgramsListAdapter.ProgramsListViewHolder> {

    private List<Program> programList;

    public ProgramsListAdapter(List<Program> programList) {
        this.programList = programList;
    }

    @NonNull
    @Override
    public ProgramsListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_program, viewGroup, false);
        return new ProgramsListAdapter.ProgramsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramsListViewHolder programsListViewHolder, int position) {
        Program program = programList.get(position);

        // bind values
        if(program.getTime() == null)  programsListViewHolder.programTimeTextView.setVisibility(View.GONE);
        else programsListViewHolder.programTimeTextView.setText(program.getTime());

        if(program.getTimeType() == null) programsListViewHolder.programTimeTypeTextView.setVisibility(View.GONE);
        else programsListViewHolder.programTimeTypeTextView.setText(program.getTimeType());

        programsListViewHolder.programTitleTextView.setText(program.getTitle());

        if(program.getSubTitle() == null) programsListViewHolder.programSubtitleTextView.setVisibility(View.GONE);
        else programsListViewHolder.programSubtitleTextView.setText(program.getSubTitle());
    }

    @Override
    public int getItemCount() {
        return programList == null ? 0 : programList.size();
    }

    class ProgramsListViewHolder extends RecyclerView.ViewHolder {
        // ui widgets
        TextView programTimeTextView, programTimeTypeTextView, programTitleTextView, programSubtitleTextView;

        ProgramsListViewHolder(View itemView) {
            super(itemView);

            programTimeTextView = itemView.findViewById(R.id.tv_program_time);
            programTimeTypeTextView = itemView.findViewById(R.id.tv_program_time_type);
            programTitleTextView = itemView.findViewById(R.id.tv_program_title);
            programSubtitleTextView = itemView.findViewById(R.id.tv_program_subtitle);
        }
    }
}
