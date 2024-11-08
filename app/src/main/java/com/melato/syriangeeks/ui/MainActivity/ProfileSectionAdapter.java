package com.melato.syriangeeks.ui.MainActivity;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.model.Section;
import java.util.List;


public class ProfileSectionAdapter extends RecyclerView.Adapter<ProfileSectionAdapter.ProfileSectionAdapterViewHolder> {

    public List<Section> sections;
    private onItemClickListener mlistener;
    public Context context;

    public interface onItemClickListener {
        void OnItemClick(int position);
    }

    public void SetOnItemClickListener(onItemClickListener listener) {
        mlistener = listener;
    }

    public static class ProfileSectionAdapterViewHolder extends RecyclerView.ViewHolder {
        //add views
        TextView sectionTitle;
        ImageView sectionIcon;
        ImageView expandIcon;

        public ProfileSectionAdapterViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            //initail views
            sectionTitle = itemView.findViewById(R.id.section_title);
            sectionIcon = itemView.findViewById(R.id.section_icon);
            expandIcon = itemView.findViewById(R.id.expand_icon);
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

    public ProfileSectionAdapter(Context context, List<Section> Sections) {
        this.sections = Sections;
        this.context = context;
    }

    public void setSections(List<Section> Sections) {
        this.sections = Sections;
        this.notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ProfileSectionAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_item_section, parent, false);
        ProfileSectionAdapterViewHolder LAH = new ProfileSectionAdapterViewHolder(v, mlistener);
        return LAH;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProfileSectionAdapterViewHolder ViewHolder, int position) {
        ViewHolder.setIsRecyclable(false);
        //processing views
        Section section = sections.get(position);
        ViewHolder.sectionTitle.setText(section.getTitle());
        ViewHolder.sectionIcon.setImageResource(section.getIconResId());
        // Set click listener for expand/collapse functionality
        ViewHolder.itemView.setOnClickListener(view -> {
            // Handle expand/collapse here
        });
    }

    @Override
    public int getItemCount() {
        return sections.size();
    }

}