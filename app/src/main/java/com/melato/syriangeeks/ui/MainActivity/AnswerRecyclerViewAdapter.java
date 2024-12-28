package com.melato.syriangeeks.ui.MainActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.model.AnswerModel;
import com.melato.syriangeeks.model.QuestionModel;
import com.melato.syriangeeks.ui.PublicCourseDetailsActivity.LessonsRecyclerViewAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class AnswerRecyclerViewAdapter extends RecyclerView.Adapter<AnswerRecyclerViewAdapter.CourseRecyclerViewAdapterViewHolder> {

    public List<AnswerModel.Datum> datumList;
    private onItemClickListener mlistener;
    public Context context;
    private PeopleQuationFragment peopleQuationFragment;

    public interface onItemClickListener {
        void OnItemClick(int position);
    }

    public void SetOnItemClickListener(onItemClickListener listener) {
        mlistener = listener;
    }

    public static class CourseRecyclerViewAdapterViewHolder extends RecyclerView.ViewHolder {
        //add views
        TextView content_item, member_item, date_item,answer_item,btuShowReplay;
        CircleImageView profile_image;
        RecyclerView list_recycler_view;
        TextView edtReplay;

        public CourseRecyclerViewAdapterViewHolder(View itemView, final onItemClickListener listener) {
            super(itemView);
            //initail views
            content_item = itemView.findViewById(R.id.content_item);
            member_item = itemView.findViewById(R.id.member_item);
            date_item = itemView.findViewById(R.id.date_item);
            answer_item = itemView.findViewById(R.id.answer_item);
            btuShowReplay = itemView.findViewById(R.id.btuShowReplay);
            profile_image = itemView.findViewById(R.id.profile_image);
            list_recycler_view = itemView.findViewById(R.id.list_recycler_view);
            edtReplay = itemView.findViewById(R.id.edtReplay);
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

    public AnswerRecyclerViewAdapter(Context context,PeopleQuationFragment peopleQuationFragment) {
        this.datumList = new ArrayList<>();
        this.context = context;
        this.peopleQuationFragment = peopleQuationFragment;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDatumList(List<AnswerModel.Datum> datumList) {
        this.datumList = datumList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseRecyclerViewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item, parent, false);
        return new CourseRecyclerViewAdapterViewHolder(v, mlistener);
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void onBindViewHolder(@NonNull final CourseRecyclerViewAdapterViewHolder ViewHolder, int position) {
        AnswerModel.Datum item = this.datumList.get(position);
        ViewHolder.setIsRecyclable(false);
        //processing views
        if(item.user != null) {
            ViewHolder.content_item.setText(item.user.name_ar);
        }


        CommentRecyclerViewAdapter innerAdapter = new CommentRecyclerViewAdapter(context);
        ViewHolder.list_recycler_view.setLayoutManager(new LinearLayoutManager(context));
        ViewHolder.list_recycler_view.setAdapter(innerAdapter);
        innerAdapter.setDatumList(item.reply);

        String dayString=new SimpleDateFormat("dd", Locale.US).format(item.created_at);
        String monthString=new SimpleDateFormat(" MMMM ", new Locale("ar")).format(item.created_at);
        String yearString=new SimpleDateFormat("yyyy", Locale.US).format(item.created_at);
        ViewHolder.date_item.setText(dayString + monthString + yearString);

        ViewHolder.answer_item.setText(item.getAnswer());
        if(item.reply.isEmpty()){
           ViewHolder.btuShowReplay.setVisibility(View.GONE);
        }
        ViewHolder.btuShowReplay.setOnClickListener(v -> {
            if(ViewHolder.list_recycler_view.getVisibility() == View.VISIBLE) {
                ViewHolder.list_recycler_view.setVisibility(View.GONE);
                ViewHolder.btuShowReplay.setText("عرض الردود");
            }else{
                ViewHolder.list_recycler_view.setVisibility(View.VISIBLE);
                ViewHolder.btuShowReplay.setText("اخفاء الردود");
            }

        });

        ViewHolder.edtReplay.setOnClickListener(v -> peopleQuationFragment.showCreateCommanitDialog(item.id));
        //ViewHolder.member_item.setText(blog.title);
        //loadImage(ClientAPI.BASE_URL + "/storage/" + blog.icon_image.original, ViewHolder.img);
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