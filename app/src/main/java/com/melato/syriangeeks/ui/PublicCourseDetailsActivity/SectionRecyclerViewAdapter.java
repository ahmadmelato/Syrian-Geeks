package com.melato.syriangeeks.ui.PublicCourseDetailsActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.model.CourseDetalsModel;

import java.util.ArrayList;
import java.util.List;


public class SectionRecyclerViewAdapter extends RecyclerView.Adapter<SectionRecyclerViewAdapter.CourseRecyclerViewAdapterViewHolder> {

    public List<CourseDetalsModel.Section> sectionList;
    private onItemClickListener mlistener;
    public Context context;

    public interface onItemClickListener {
        void OnItemClick(int position);
    }

    public void SetOnItemClickListener(onItemClickListener listener) {
        mlistener = listener;
    }

    public static class CourseRecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder {
        //add views
        TextView lessons_name, lessons_time;

        public CourseRecyclerViewAdapterViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            //initail views
            lessons_name = itemView.findViewById(R.id.lessons_name);
            lessons_time = itemView.findViewById(R.id.lessons_time);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(position);
                    }
                }
            });
        }
    }

    public SectionRecyclerViewAdapter(Context context) {
        this.sectionList = new ArrayList<>();
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSectionList(List<CourseDetalsModel.Section> sectionList) {
        this.sectionList = sectionList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseRecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_item, parent, false);
        return new CourseRecyclerViewAdapterViewHolder(v, mlistener);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(@NonNull final CourseRecyclerViewAdapterViewHolder ViewHolder, int position) {
        CourseDetalsModel.Section blog = this.sectionList.get(position);
        ViewHolder.setIsRecyclable(false);
        //processing views
        ViewHolder.lessons_name.setText(blog.title);
        ViewHolder.lessons_time.setText("درس " + blog.lessonsCount + " - " + blog.time);
    }


    @Override
    public int getItemCount() {
        return sectionList.size();
    }

}