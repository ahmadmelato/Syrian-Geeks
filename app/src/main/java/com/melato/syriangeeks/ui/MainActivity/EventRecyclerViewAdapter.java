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
import com.melato.syriangeeks.model.EventModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.CourseRecyclerViewAdapterViewHolder> {

    public List<EventModel.Item> itemList;
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
        TextView event_name, event_dis, event_location, event_date;
        ImageView img;

        public CourseRecyclerViewAdapterViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            //initail views
            event_name = itemView.findViewById(R.id.event_name);
            event_dis = itemView.findViewById(R.id.event_dis);
            event_location = itemView.findViewById(R.id.event_location);
            event_date = itemView.findViewById(R.id.event_date);
            img = itemView.findViewById(R.id.img);
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

    public EventRecyclerViewAdapter(Context context) {
        this.itemList = new ArrayList<>();
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setEventList(List<EventModel.Item> itemList) {
        this.itemList = itemList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseRecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_item, parent, false);
        CourseRecyclerViewAdapterViewHolder LAH = new CourseRecyclerViewAdapterViewHolder(v, mlistener);
        return LAH;
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(@NonNull final CourseRecyclerViewAdapterViewHolder ViewHolder, int position) {
        EventModel.Item item = this.itemList.get(position);
        ViewHolder.setIsRecyclable(false);
        //processing views
        ViewHolder.event_name.setText(item.title);
        ViewHolder.event_dis.setText(item.getContent());
        ViewHolder.event_location.setText(item.address);
        String dayString=new SimpleDateFormat("dd", Locale.US).format(item.start_date);
        String monthString=new SimpleDateFormat(" MMMM ", new Locale("ar")).format(item.start_date);
        String yearString=new SimpleDateFormat("yyyy", Locale.US).format(item.start_date);
        ViewHolder.event_date.setText(dayString + monthString + yearString);
        loadImage(ClientAPI.BASE_URL + "/storage/" + item.image.original, ViewHolder.img);
    }

    private void loadImage(String url, ImageView img) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.img_events) // Optional: Placeholder while loading
                .error(R.drawable.img_events) // Optional: Image to show on error
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
        return itemList.size();
    }

}