package com.melato.syriangeeks.ui.MainActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.data.ClientAPI;
import com.melato.syriangeeks.databinding.ActivityCourseDetailsBinding;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class PublicCourseDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityCourseDetailsBinding binding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_course_details);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mainViewModel.working.observe(this, working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.main1.setVisibility(working.isFinish());
                if (!working.isRunning())
                    Toast.makeText(getApplicationContext(), working.getsSmg(), Toast.LENGTH_SHORT).show();
            }
        });

        mainViewModel.coursedetailsModelLiveData.observe(this, courseDetalsModel -> {
            binding.blogName.setText(courseDetalsModel.title);
            binding.teacherName.setText(courseDetalsModel.instructor_name);
//            binding.blogDate.setText(new SimpleDateFormat("dd MMMM yyyy", new Locale("ar")).format(courseDetalsModel.created_at));
//            binding.web.loadData("<html dir='rtl' lang='ar'><body>" + courseDetalsModel.description + "</body></html>", "text/html", "UTF-8");
            loadImage(ClientAPI.BASE_URL + "/storage/" + courseDetalsModel.thumbnail_image.original);

        });

        binding.toolbarBack.setOnClickListener(this);


        mainViewModel.getCourseDetails(getApplicationContext(), Objects.requireNonNull(getIntent().getExtras()).getInt("id"));

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toolbar_back) {
            finish();
        }
    }

    private void loadImage(String url) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.blog_logo) // Optional: Placeholder while loading
                .error(R.drawable.blog_logo) // Optional: Image to show on error
                .into(binding.img);
    }
}