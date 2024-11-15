package com.melato.syriangeeks.ui.MainActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.model.CourseModel;
import com.melato.syriangeeks.model.MyCourseModel;

import java.util.ArrayList;
import java.util.List;


public class MyCourseViewAdapter extends RecyclerView.Adapter<MyCourseViewAdapter.CourseRecyclerViewAdapterViewHolder> {

    public List<MyCourseModel.Datum> CourseModels;
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
        TextView course_name, course_time;
        ProgressBar courseProgressBar;
        Button btuStart;
        ImageView img;

        public CourseRecyclerViewAdapterViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            //initail views
            course_name = itemView.findViewById(R.id.course_name);
            course_time = itemView.findViewById(R.id.course_time);
            courseProgressBar = itemView.findViewById(R.id.courseProgressBar);
            btuStart = itemView.findViewById(R.id.btuStart);
            img = itemView.findViewById(R.id.img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public MyCourseViewAdapter(Context context) {
        this.CourseModels = new ArrayList<>();
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCourseModels(List<MyCourseModel.Datum> CourseModels) {
        this.CourseModels = CourseModels;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseRecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_course_item, parent, false);
        return new CourseRecyclerViewAdapterViewHolder(v, mlistener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final CourseRecyclerViewAdapterViewHolder ViewHolder, int position) {
        MyCourseModel.Datum courseModel = this.CourseModels.get(position);
        ViewHolder.setIsRecyclable(false);
        //processing views
        ViewHolder.course_name.setText(courseModel.course.title);
        ViewHolder.courseProgressBar.setProgress(courseModel.progress);
        //ViewHolder.course_time.setText(courseModel. + " ساعة");
        if (courseModel.approved != 1) {
            ViewHolder.btuStart.setText("بانتظار الموافقة");
            ViewHolder.btuStart.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return CourseModels.size();
    }

}