package com.melato.syriangeeks.ui.PublicProfileFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.model.ProfileModel;
import com.melato.syriangeeks.ui.MainActivity.DialogExpertise;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class PublicExperienceRecyclerViewAdapter extends RecyclerView.Adapter<PublicExperienceRecyclerViewAdapter.CourseRecyclerViewAdapterViewHolder> {

    public List<ProfileModel.Experience> datumList;
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
        TextView name_job, date_start_end, about, name_type;

        public CourseRecyclerViewAdapterViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            //initail views
            name_job = itemView.findViewById(R.id.name_job);
            date_start_end = itemView.findViewById(R.id.date_start_end);
            about = itemView.findViewById(R.id.about);
            name_type = itemView.findViewById(R.id.name_type);

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

    public PublicExperienceRecyclerViewAdapter(Context context) {
        this.datumList = new ArrayList<>();
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDatumList(List<ProfileModel.Experience> datumList) {
        this.datumList = datumList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseRecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_experience_list_item, parent, false);
        return new CourseRecyclerViewAdapterViewHolder(v, mlistener);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(@NonNull final CourseRecyclerViewAdapterViewHolder ViewHolder, int position) {
        ProfileModel.Experience item = this.datumList.get(position);
        ViewHolder.setIsRecyclable(false);
        //processing views
        ViewHolder.name_job.setText(item.title);
        ViewHolder.name_type.setText(item.name);
        ViewHolder.date_start_end.setText((item.start_date != null ? item.start_date : "") + " - " + (item.end_date != null ? item.end_date : "حاضر"));
        ViewHolder.about.setText((item.description != null ? item.description : ""));
    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

}