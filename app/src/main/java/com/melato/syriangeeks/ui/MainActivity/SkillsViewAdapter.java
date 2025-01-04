package com.melato.syriangeeks.ui.MainActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.model.ProfileModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class SkillsViewAdapter extends RecyclerView.Adapter<SkillsViewAdapter.CourseRecyclerViewAdapterViewHolder> {

    public List<ProfileModel.Skill> skillList;
    private onItemClickListener mlistener;
    public ProfileFragment context;
    public Context pucontext;

    public interface onItemClickListener {
        void OnItemClick(int position);
    }

    public void SetOnItemClickListener(onItemClickListener listener) {
        mlistener = listener;
    }

    public static class CourseRecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder {
        //add views
        private TextView value;
        private ImageButton cancel;

        public CourseRecyclerViewAdapterViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            //initail views
            value = itemView.findViewById(R.id.value);
            cancel = itemView.findViewById(R.id.cancel);
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

    public SkillsViewAdapter(ProfileFragment context) {
        this.skillList = new ArrayList<>();
        this.context = context;
    }

    public SkillsViewAdapter(Context context) {
        this.skillList = new ArrayList<>();
        this.pucontext = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSkillList(List<ProfileModel.Skill> skillList) {
        this.skillList = skillList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseRecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.skill_list_item, parent, false);
        return new CourseRecyclerViewAdapterViewHolder(v, mlistener);
    }

    @SuppressLint({"SetTextI18n", "RecyclerView"})
    @Override
    public void onBindViewHolder(@NonNull final CourseRecyclerViewAdapterViewHolder ViewHolder, int position) {
        ProfileModel.Skill skill = this.skillList.get(position);
        ViewHolder.setIsRecyclable(false);
        //processing views
        ViewHolder.value.setText(skill.value);

        ViewHolder.cancel.setVisibility(View.GONE);
        if (context != null) {
            ViewHolder.cancel.setVisibility(View.VISIBLE);
            ViewHolder.cancel.setOnClickListener(v -> {
                skillList.remove(position);
                context.viewModel.store_skills(context.requireContext(), skillList);
            });
            ViewHolder.value.setOnClickListener(v -> {
                String url = ViewHolder.value.getText().toString();
                if (URLUtil.isValidUrl(url)) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(browserIntent);
                }
            });
        } else {
            ViewHolder.value.setOnClickListener(v -> {
                String url = ViewHolder.value.getText().toString();
                if (URLUtil.isValidUrl(url)) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    pucontext.startActivity(browserIntent);
                }
            });
        }
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
        return skillList.size();
    }

}