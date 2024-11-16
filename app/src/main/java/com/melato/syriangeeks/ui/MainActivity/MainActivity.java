package com.melato.syriangeeks.ui.MainActivity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.navigation.NavigationView;
import com.melato.syriangeeks.R;
import com.melato.syriangeeks.databinding.ActivityMainBinding;
import com.melato.syriangeeks.ui.PublicCourseDetailsActivity.PublicCourseDetailsFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;

    private MainFragment mainFragment;
    private DashbordFragment dashbordFragment;
    private CourseActivitiesFragment courseActivitiesFragment;
    private MyCoursesFragment myCoursesFragment;
    private CertificatesFragment certificatesFragment;
    private ReferenceFragment referenceFragment;
    private LeaderboardFragment leaderboardFragment;
    private NotificationsFragment notificationsFragment;
    private ProfileFragment profileFragment;
    private PublicCoursesFragment publicCoursesFragment;
    private PublicBlogFragment publicBlogFragment;
    private PublicEventsFragment publicEventsFragment;
    private OnBackPressedCallback onBackPressedCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
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
        myCoursesFragment = new MyCoursesFragment();
        certificatesFragment = new CertificatesFragment();
        referenceFragment = new ReferenceFragment();
        leaderboardFragment = new LeaderboardFragment();
        notificationsFragment = new NotificationsFragment();
        profileFragment = new ProfileFragment();
        publicCoursesFragment = new PublicCoursesFragment();
        publicBlogFragment = new PublicBlogFragment();
        publicEventsFragment = new PublicEventsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment).commit();
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (binding.drawerLayout.isOpen()) {
                    binding.drawerLayout.close();
                } else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    if (fragmentManager.getBackStackEntryCount() == 1) {
                        fragmentManager.popBackStack();
                        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                        binding.bottomnavigation.setSelectedItemId(R.id.item_main);
                    } else if (fragmentManager.getBackStackEntryCount() >= 1) {
                        fragmentManager.popBackStack();
                    } else {
                        MainActivity.this.finish();
                    }
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

//        mainViewModel.working.observe(this, working -> {
//            if (working != null) {
////                binding.mainprogress.setVisibility(working.isProgressing());
////                binding.btuLogin.setVisibility(working.isFinish());
////                binding.createAccount.setEnabled(working.isBooleanFinish());
////                binding.btuLoginGuest.setEnabled(working.isBooleanFinish());
//                if (!working.isRunning())
//                    Toast.makeText(getApplicationContext(), working.getsSmg(), Toast.LENGTH_SHORT).show();
//            }
//        });

        MainViewModel.userLiveData.observe(this, userModel -> {
            if (userModel == null) {
                binding.navView.getMenu().findItem(R.id.nav_divider).setVisible(false);
                binding.navView.getMenu().findItem(R.id.signout).setVisible(false);
            } else {
                binding.navView.getMenu().findItem(R.id.nav_divider).setVisible(true);
                binding.navView.getMenu().findItem(R.id.signout).setVisible(true);

            }
        });

        mainViewModel.getData(getApplicationContext());

    }


    public void openDrawerLayout() {
        binding.drawerLayout.open();
    }


    public void openPublicEventsDetailsFragment(String data) {
        PublicEventsDetailsFragment fragment = PublicEventsDetailsFragment.newInstance(data);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void openPublicCourseDetailsFragment(String state,int id) {
        PublicCourseDetailsFragment fragment = PublicCourseDetailsFragment.newInstance(state,id);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void openPublicBlogDetailsFragment(int id) {
        PublicBlogDetailsFragment fragment = PublicBlogDetailsFragment.newInstance(id);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void backPressed() {
        onBackPressedCallback.handleOnBackPressed();
    }

    public void openNotifications() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, notificationsFragment).addToBackStack(null).commit();
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.item_dashbord) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, dashbordFragment).addToBackStack(null).commit();
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            binding.drawerLayout.close();
        } else if (itemId == R.id.item_profile) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).addToBackStack(null).commit();
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            binding.drawerLayout.close();
        } else if (itemId == R.id.item_courses) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myCoursesFragment).addToBackStack(null).commit();
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            binding.drawerLayout.close();
        } else if (itemId == R.id.item_courseactivities) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, courseActivitiesFragment).addToBackStack(null).commit();
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            binding.drawerLayout.close();
        } else if (itemId == R.id.item_certificates) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, certificatesFragment).addToBackStack(null).commit();
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            binding.drawerLayout.close();
        } else if (itemId == R.id.item_reference) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, referenceFragment).addToBackStack(null).commit();
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            binding.drawerLayout.close();
        } else if (itemId == R.id.item_leaderboard) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, leaderboardFragment).addToBackStack(null).commit();
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            binding.drawerLayout.close();
        } else if (itemId == R.id.signout) {
            binding.drawerLayout.close();
            mainViewModel.logout(getApplicationContext());
            //--------------------------------------------------------------------------------------------------------------
        } else if (itemId == R.id.item_main) {
            getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment).commit();
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else if (itemId == R.id.item_Blog) {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, publicBlogFragment).addToBackStack(null).commit();
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else if (itemId == R.id.item_Events) {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, publicEventsFragment).addToBackStack(null).commit();
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else if (itemId == R.id.item_society) {
            binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else if (itemId == R.id.exit) {
            finish();
        } else {
            return false;
        }
        return true;
    }

    public void openAllCousres() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, publicCoursesFragment).addToBackStack(null).commit();
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void openAllBlog() {

        binding.bottomnavigation.setSelectedItemId(R.id.item_Blog);
    }

    public void openAllEvents() {
        binding.bottomnavigation.setSelectedItemId(R.id.item_Events);
    }

    public MainViewModel getMainViewModel() {
        return mainViewModel;
    }
}