package com.melato.syriangeeks.ui.MainActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.data.ClientAPI;
import com.melato.syriangeeks.model.CourseModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.ArrayList;


public class CourseRecyclerViewAdapter extends RecyclerView.Adapter<CourseRecyclerViewAdapter.CourseRecyclerViewAdapterViewHolder> {

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
        ImageView img;

        public CourseRecyclerViewAdapterViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            //initail views
            course_name = itemView.findViewById(R.id.course_name);
            teacher_name = itemView.findViewById(R.id.teacher_name);
            course_days = itemView.findViewById(R.id.course_days);
            course_hour = itemView.findViewById(R.id.course_hour);
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

    public CourseRecyclerViewAdapter(Context context) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, parent, false);
        return new CourseRecyclerViewAdapterViewHolder(v, mlistener);
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
        //loadImage(ClientAPI.BASE_URL+"/storage/"+courseModel..original,ViewHolder.img);
        //ViewHolder.course_hour.setText(courseModel.getTitle());
    }

    private void loadImage(String url, ImageView img) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.img_course) // Optional: Placeholder while loading
                .error(R.drawable.img_course) // Optional: Image to show on error
                .into(img, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
    }

    @Override
    public int getItemCount() {
        return CourseModels.size();
    }

}