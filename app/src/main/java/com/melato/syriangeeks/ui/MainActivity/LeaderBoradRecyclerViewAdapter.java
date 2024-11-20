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

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.model.CertificateModel;
import com.melato.syriangeeks.model.LeaderBoardModel;
import com.melato.syriangeeks.ui.ShowCertificateActivity.ShowCertificateActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class LeaderBoradRecyclerViewAdapter extends RecyclerView.Adapter<LeaderBoradRecyclerViewAdapter.CourseRecyclerViewAdapterViewHolder> {

    public List<LeaderBoardModel.Datum> datumList;
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
        TextView student_name, student_postion, student_country, student_point;

        public CourseRecyclerViewAdapterViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            //initail views
            student_name = itemView.findViewById(R.id.student_name);
            student_postion = itemView.findViewById(R.id.student_postion);
            student_country = itemView.findViewById(R.id.student_country);
            student_point = itemView.findViewById(R.id.student_point);

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

    public LeaderBoradRecyclerViewAdapter(Context context) {
        this.datumList = new ArrayList<>();
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDatumList(List<LeaderBoardModel.Datum> datumList) {
        this.datumList = datumList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseRecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_item, parent, false);
        return new CourseRecyclerViewAdapterViewHolder(v, mlistener);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(@NonNull final CourseRecyclerViewAdapterViewHolder ViewHolder, int position) {
        LeaderBoardModel.Datum item = this.datumList.get(position);
        ViewHolder.setIsRecyclable(false);
        //processing views
        if(item.user != null) {
            ViewHolder.student_name.setText(item.user.name_ar);
             ViewHolder.student_country.setText(item.user.country);
            ViewHolder.student_point.setText(item.points+" نقطة");
            ViewHolder.student_postion.setText(String.valueOf(position + 1));
        }

    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

}