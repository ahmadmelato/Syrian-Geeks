package com.melato.syriangeeks.ui.MainActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.melato.syriangeeks.R;
import com.melato.syriangeeks.data.ClientAPI;
import com.melato.syriangeeks.databinding.ActivityPublicBlogDetailsBinding;
import com.melato.syriangeeks.databinding.ActivityPublicEventsDetailsActivityBinding;
import com.melato.syriangeeks.model.BlogDetalsModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class PublicBlogDetailsActivity extends AppCompatActivity  implements View.OnClickListener{

    private ActivityPublicBlogDetailsBinding binding;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_public_blog_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mainViewModel.working.observe(this, working -> {
            if (working != null) {
                binding.mainprogress.setVisibility(working.isProgressing());
                binding.main.setVisibility(working.isFinish());
                if (!working.isRunning())
                    Toast.makeText(getApplicationContext(), working.getsSmg(), Toast.LENGTH_SHORT).show();
            }
        });

        WebSettings webSettings = binding.web.getSettings();

        // تمكين JavaScript إذا لزم الأمر
        webSettings.setJavaScriptEnabled(true);

        // إعدادات العرض
        webSettings.setLoadWithOverviewMode(true); // تحميل الصفحة بمقياس مناسب
        webSettings.setUseWideViewPort(false);
        webSettings.setBuiltInZoomControls(true); // تمكين أدوات التكبير
        webSettings.setDisplayZoomControls(false);

        mainViewModel.blogdetailsModelLiveData.observe(this, blogDetalsModel -> {
            binding.blogName.setText(blogDetalsModel.title);
            binding.blogDate.setText(new SimpleDateFormat("dd MMMM yyyy", new Locale("ar")).format(blogDetalsModel.created_at));
            binding.web.loadData("<html dir='rtl' lang='ar'><body>" + blogDetalsModel.description + "</body></html>", "text/html", "UTF-8");
            System.out.println(ClientAPI.BASE_URL + "/storage" + blogDetalsModel.meta_image.original);
            loadImage(ClientAPI.BASE_URL + "/storage/" + blogDetalsModel.meta_image.original);
            binding.web.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            binding.web.setTextDirection(View.TEXT_DIRECTION_RTL);

        });

        binding.toolbarBack.setOnClickListener(this);
        mainViewModel.getBlogsDetails(getApplicationContext(), Objects.requireNonNull(getIntent().getExtras()).getInt("id"));
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
                .into(binding.img, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                    }
                });
    }

}