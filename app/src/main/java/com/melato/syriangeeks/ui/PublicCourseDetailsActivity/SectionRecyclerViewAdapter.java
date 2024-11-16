package com.melato.syriangeeks.ui.PublicCourseDetailsActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.model.CourseDetalsModel;
import com.melato.syriangeeks.ui.PlayVedioActivity.PlayDriveVedioActivity;
import com.melato.syriangeeks.ui.PlayVedioActivity.PlayYouTubeVedioActivity;

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
        RecyclerView lessian_list;

        public CourseRecyclerViewAdapterViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            //initail views
            lessons_name = itemView.findViewById(R.id.lessons_name);
            lessons_time = itemView.findViewById(R.id.lessons_time);
            lessian_list = itemView.findViewById(R.id.lessian_list);

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
        ViewHolder.lessons_time.setText( blog.lessonsCount + " درس" + " - " + blog.time);

        LessonsRecyclerViewAdapter innerAdapter = new LessonsRecyclerViewAdapter(context);
        ViewHolder.lessian_list.setLayoutManager(new LinearLayoutManager(context));
        ViewHolder.lessian_list.setAdapter(innerAdapter);
        innerAdapter.setLessonList(blog.lessons);

        innerAdapter.SetOnItemClickListener(position1 -> {
            System.out.println(blog.lessons.get(position1).lesson_type);
            if(blog.lessons.get(position1).lesson_type != null &&  blog.lessons.get(position1).lesson_type.equals("Youtube")){
                openYouTuber(blog.lessons.get(position1).video_url);
            }else if(blog.lessons.get(position1).lesson_type != null &&  blog.lessons.get(position1).lesson_type.equals("GoogleDrive")){
                openDrive(blog.lessons.get(position1).video_url);
            }else{
                openInBrowser(blog.lessons.get(position1).video_url);
            }
        });
    }

    private void openInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }

    private void openYouTuber(String url) {
        Intent intent = new Intent(context, PlayYouTubeVedioActivity.class);
        intent.putExtra("URL",url);
        context.startActivity(intent);
    }

    private void openDrive(String url) {
        Intent intent = new Intent(context, PlayDriveVedioActivity.class);
        intent.putExtra("URL",url);
        context.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return sectionList.size();
    }

}