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
import com.melato.syriangeeks.ui.MainViewModel;

import java.util.ArrayList;
import java.util.List;


public class LessonsRecyclerViewAdapter extends RecyclerView.Adapter<LessonsRecyclerViewAdapter.CourseRecyclerViewAdapterViewHolder> {

    public List<CourseDetalsModel.Lesson> lessonList;
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

    public LessonsRecyclerViewAdapter(Context context, List<CourseDetalsModel.Section> sectionList) {
        this.lessonList = new ArrayList<>();
        this.sectionList = sectionList;
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
        CourseDetalsModel.Lesson lesson = this.lessonList.get(position);
        ViewHolder.setIsRecyclable(false);
        //processing views
        ViewHolder.lessons_name.setText(lesson.title);
        if (lesson.video_url != null) {
            ViewHolder.lessons_finish.setVisibility(View.VISIBLE);
            ViewHolder.lessons_finish.setChecked(lesson.is_completed);
            System.out.println(lesson.is_completed);
            ViewHolder.lessons_finish.setOnClickListener(v -> {
                lesson.is_completed = !lesson.is_completed;
                List<String> strings = new ArrayList<>();
                for (CourseDetalsModel.Section section : sectionList) {
                    for (CourseDetalsModel.Lesson less : section.lessons) {
                        if (less.is_completed)
                            strings.add(less.code);
                    }
                }
                MainViewModel.course_lecture_progress(context, lesson.code, strings);
            });
        }
    }


    @Override
    public int getItemCount() {
        return lessonList.size();
    }

}