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
import com.melato.syriangeeks.model.BlogModel;
import com.melato.syriangeeks.model.CourseModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class BlogRecyclerViewAdapter extends RecyclerView.Adapter<BlogRecyclerViewAdapter.CourseRecyclerViewAdapterViewHolder> {

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
        TextView blog_name, blog_date;

        public CourseRecyclerViewAdapterViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            //initail views
            blog_name = itemView.findViewById(R.id.blog_name);
            blog_date = itemView.findViewById(R.id.blog_date);

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

    public BlogRecyclerViewAdapter(Context context) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_item, parent, false);
        CourseRecyclerViewAdapterViewHolder LAH = new CourseRecyclerViewAdapterViewHolder(v, mlistener);
        return LAH;
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(@NonNull final CourseRecyclerViewAdapterViewHolder ViewHolder, int position) {
        BlogModel.Blog blog = this.blogList.get(position);
        ViewHolder.setIsRecyclable(false);
        //processing views
        ViewHolder.blog_name.setText(blog.title);
        ViewHolder.blog_date.setText(new SimpleDateFormat("dd MMMM yyyy", new Locale("ar")).format(blog.created_at));
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

}