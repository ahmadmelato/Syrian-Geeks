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
import com.melato.syriangeeks.model.DashbordModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DashbordCourseRecyclerViewAdapter extends RecyclerView.Adapter<DashbordCourseRecyclerViewAdapter.CourseRecyclerViewAdapterViewHolder> {

    public List<DashbordModel.Course> CourseModels;
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
        TextView course_name;
        ImageView img;

        public CourseRecyclerViewAdapterViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            //initail views
            course_name = itemView.findViewById(R.id.course_name);
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

    public DashbordCourseRecyclerViewAdapter(Context context) {
        this.CourseModels = new ArrayList<>();
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCourseModels(List<DashbordModel.Course> CourseModels) {
        this.CourseModels = CourseModels;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseRecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_dcourse_item, parent, false);
        return new CourseRecyclerViewAdapterViewHolder(v, mlistener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final CourseRecyclerViewAdapterViewHolder ViewHolder, int position) {
        DashbordModel.Course courseModel = this.CourseModels.get(position);
        ViewHolder.setIsRecyclable(false);
        //processing views
        ViewHolder.course_name.setText(courseModel.title);
        loadImage(courseModel.image,ViewHolder.img);
        //ViewHolder.course_hour.setText(courseModel.getTitle());
    }

    private void loadImage(String url, ImageView img) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.img_my_course) // Optional: Placeholder while loading
                .error(R.drawable.img_my_course) // Optional: Image to show on error
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