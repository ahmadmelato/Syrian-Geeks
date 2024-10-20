package com.melato.syriangeeks.ui.MainActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private MainFragment mainFragment;
    private DashbordFragment dashbordFragment;
    private CourseActivitiesFragment courseActivitiesFragment;
    private CoursesFragment coursesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        binding.navView.setNavigationItemSelectedListener(this);
        binding.bottomnavigation.setOnItemSelectedListener(this::onNavigationItemSelected);
        mainFragment = new MainFragment();
        dashbordFragment = new DashbordFragment();
        courseActivitiesFragment = new CourseActivitiesFragment();
        coursesFragment = new CoursesFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment).commit();
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

    }

    public void openDrawerLayout() {
        binding.drawerLayout.open();
    }

    public void openMain() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment).commit();
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int itemId = item.getItemId();
        if (itemId == R.id.item_dashbord) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dashbordFragment).commit();
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            binding.drawerLayout.close();
        } else if (itemId == R.id.item_profile) {
            Toast.makeText(getApplicationContext(), item.getTitle() + "", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.item_courses) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, coursesFragment).commit();
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            binding.drawerLayout.close();
        } else if (itemId == R.id.item_courseactivities) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, courseActivitiesFragment).commit();
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            binding.drawerLayout.close();
            //--------------------------------------------------------------------------------------------------------------
        } else if (itemId == R.id.item_main) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment).commit();
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else if (itemId == R.id.item_Blog) {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else if (itemId == R.id.item_Events) {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else if (itemId == R.id.item_society) {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            return false;
        }
        return true;
    }

}