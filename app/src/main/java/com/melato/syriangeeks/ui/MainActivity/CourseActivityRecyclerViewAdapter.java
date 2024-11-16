package com.melato.syriangeeks.ui.MainActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.data.ClientAPI;
import com.melato.syriangeeks.model.BlogModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CourseActivityRecyclerViewAdapter extends RecyclerView.Adapter<CourseActivityRecyclerViewAdapter.CourseRecyclerViewAdapterViewHolder> {

    public List<BlogModel.Blog> blogList;
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
        TextView course_name, course_active_time, course_active_task, course_active_test, course_active_all_point, course_active_my_point, progressText;
        ProgressBar course_active_progrss;

        public CourseRecyclerViewAdapterViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            //initail views
            course_name = itemView.findViewById(R.id.course_name);
            course_active_time = itemView.findViewById(R.id.course_active_time);
            course_active_task = itemView.findViewById(R.id.course_active_task);
            course_active_test = itemView.findViewById(R.id.course_active_test);
            course_active_all_point = itemView.findViewById(R.id.course_active_all_point);
            course_active_my_point = itemView.findViewById(R.id.course_active_my_point);

            course_active_progrss = itemView.findViewById(R.id.course_active_progrss);
            progressText = itemView.findViewById(R.id.progressText);

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

    public CourseActivityRecyclerViewAdapter(Context context) {
        this.blogList = new ArrayList<>();
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setBlogList(List<BlogModel.Blog> blogList) {
        this.blogList = blogList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseRecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_activities_item, parent, false);
        CourseRecyclerViewAdapterViewHolder LAH = new CourseRecyclerViewAdapterViewHolder(v, mlistener);
        return LAH;
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(@NonNull final CourseRecyclerViewAdapterViewHolder ViewHolder, int position) {
        BlogModel.Blog blog = this.blogList.get(position);
        ViewHolder.setIsRecyclable(false);
        //processing views
//        ViewHolder.blog_name.setText(blog.title);
//        ViewHolder.blog_date.setText(new SimpleDateFormat("dd MMMM yyyy", new Locale("ar")).format(blog.created_at));
//        loadImage(ClientAPI.BASE_URL + "/storage/" + blog.icon_image.original, ViewHolder.img);
    }

    private void loadImage(String url, ImageView img) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.img_blog_logo) // Optional: Placeholder while loading
                .error(R.drawable.img_blog_logo) // Optional: Image to show on error
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
        return blogList.size();
    }

}