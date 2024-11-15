package com.melato.syriangeeks.ui.PublicCourseDetailsActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.model.CourseDetalsModel;

import java.util.ArrayList;
import java.util.List;


public class LessonsRecyclerViewAdapter extends RecyclerView.Adapter<LessonsRecyclerViewAdapter.CourseRecyclerViewAdapterViewHolder> {

    public List<CourseDetalsModel.Lesson> lessonList;
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
        TextView lessons_name;
        CheckBox lessons_finish;

        public CourseRecyclerViewAdapterViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            //initail views
            lessons_name = itemView.findViewById(R.id.lessons_name);
            lessons_finish = itemView.findViewById(R.id.lessons_finish);
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

    public LessonsRecyclerViewAdapter(Context context) {
        this.lessonList = new ArrayList<>();
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setLessonList(List<CourseDetalsModel.Lesson> lessonList) {
        this.lessonList = lessonList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseRecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_item, parent, false);
        return new CourseRecyclerViewAdapterViewHolder(v, mlistener);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(@NonNull final CourseRecyclerViewAdapterViewHolder ViewHolder, int position) {
        CourseDetalsModel.Lesson blog = this.lessonList.get(position);
        ViewHolder.setIsRecyclable(false);
        //processing views
        ViewHolder.lessons_name.setText(blog.title);
        if(blog.video_url!=null){
            ViewHolder.lessons_finish.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return lessonList.size();
    }

}