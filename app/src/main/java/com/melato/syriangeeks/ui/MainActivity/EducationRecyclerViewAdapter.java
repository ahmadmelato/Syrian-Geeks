package com.melato.syriangeeks.ui.MainActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.model.CertificateModel;
import com.melato.syriangeeks.model.ProfileModel;
import com.melato.syriangeeks.ui.ShowCertificateActivity.ShowCertificateActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class EducationRecyclerViewAdapter extends RecyclerView.Adapter<EducationRecyclerViewAdapter.CourseRecyclerViewAdapterViewHolder> {

    public List<ProfileModel.Institute> datumList;
    private onItemClickListener mlistener;
    public Context context;
    private DialogInstitutes dialogInstitutes;

    public interface onItemClickListener {
        void OnItemClick(int position);
    }

    public void SetOnItemClickListener(onItemClickListener listener) {
        mlistener = listener;
    }

    public static class CourseRecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder {
        //add views
        TextView name, date_start_end, about;
        FloatingActionButton editfbtu;

        public CourseRecyclerViewAdapterViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            //initail views
            name = itemView.findViewById(R.id.name);
            date_start_end = itemView.findViewById(R.id.date_start_end);
            about = itemView.findViewById(R.id.about);
            editfbtu = itemView.findViewById(R.id.editfbtu);

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

    public EducationRecyclerViewAdapter(Context context,DialogInstitutes dialogInstitutes) {
        this.datumList = new ArrayList<>();
        this.context = context;
        this.dialogInstitutes = dialogInstitutes;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDatumList(List<ProfileModel.Institute> datumList) {
        this.datumList = datumList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseRecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.education_list_item, parent, false);
        return new CourseRecyclerViewAdapterViewHolder(v, mlistener);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(@NonNull final CourseRecyclerViewAdapterViewHolder ViewHolder, int position) {
        ProfileModel.Institute item = this.datumList.get(position);
        ViewHolder.setIsRecyclable(false);
        //processing views
        ViewHolder.name.setText(item.name);
        ViewHolder.date_start_end.setText((item.start_date != null ? item.start_date : "") + " - " + (item.end_date != null ? item.end_date : "حاضر") );
        ViewHolder.about.setText((item.description != null ? item.description : "") );

        ViewHolder.editfbtu.setOnClickListener(v -> {
            dialogInstitutes.show(item);
        });
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
        return datumList.size();
    }

}