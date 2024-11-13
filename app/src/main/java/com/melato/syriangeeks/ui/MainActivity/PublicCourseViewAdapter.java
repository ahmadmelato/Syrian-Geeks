package com.melato.syriangeeks.ui.MainActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.model.CourseModel;

import java.util.ArrayList;
import java.util.List;


public class PublicCourseViewAdapter extends RecyclerView.Adapter<PublicCourseViewAdapter.CourseRecyclerViewAdapterViewHolder> {

    public List<CourseModel.Datum> CourseModels;
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
        TextView course_name, teacher_name, course_days, course_hour;

        public CourseRecyclerViewAdapterViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            //initail views
            course_name = itemView.findViewById(R.id.course_name);
            teacher_name = itemView.findViewById(R.id.teacher_name);
            course_days = itemView.findViewById(R.id.course_days);
            course_hour = itemView.findViewById(R.id.course_hour);

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

    public PublicCourseViewAdapter(Context context) {
        this.CourseModels = new ArrayList<>();
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCourseModels(List<CourseModel.Datum> CourseModels) {
        this.CourseModels = CourseModels;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseRecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_course_item, parent, false);
        CourseRecyclerViewAdapterViewHolder LAH = new CourseRecyclerViewAdapterViewHolder(v, mlistener);
        return LAH;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final CourseRecyclerViewAdapterViewHolder ViewHolder, int position) {
        CourseModel.Datum courseModel = this.CourseModels.get(position);
        ViewHolder.setIsRecyclable(false);
        //processing views
        ViewHolder.course_name.setText(courseModel.getTitle());
        ViewHolder.teacher_name.setText(courseModel.getInstructor_name());
        ViewHolder.course_hour.setText(courseModel.getTotal_sales() + " ساعة");
        //ViewHolder.course_hour.setText(courseModel.getTitle());
    }

    @Override
    public int getItemCount() {
        return CourseModels.size();
    }

}