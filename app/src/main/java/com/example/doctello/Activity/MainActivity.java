package com.example.doctello.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.doctello.Fragments.HomeFragment;
import com.example.doctello.Interface.JsonApiHolder;
import com.example.doctello.R;
import com.example.doctello.Utils.prefUtils;
import com.example.doctello.Utils.retrofitInstance;
import com.example.doctello.models.ProfileData;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {


    private FusedLocationProviderClient fusedLocationClient;
    private int mrequestCode = 1;
    public Double latitude=0.0;
    public Double longitude=0.0;
    NavigationView navigationView;
    Fragment fragment;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    TextView textViewUsername;
    TextView textViewPhoneNumber;
    JsonApiHolder jsonApiHolder;
    prefUtils sp;
    List<ProfileData> profileDataList;
    ImageView imageViewLogoutButton;
    public static String city = "null";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        sp = new prefUtils(this);

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        View headerView = navigationView.getHeaderView(0);
        textViewUsername = headerView.findViewById(R.id.textViewNavName);
        textViewPhoneNumber = headerView.findViewById(R.id.textViewNavPhoneNumber);
        imageViewLogoutButton = findViewById(R.id.image_view_logout);

        imageViewLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.logoutUser();
                Intent intent = new Intent(MainActivity.this, LoginSignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        setNavProfileDetails();

        setUpToolBar();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame,
                new HomeFragment()).commit();
    }

    private void getLocation() {
        if(ContextCompat.checkSelfPermission(MainActivity.this ,
                Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){
            Toast.makeText(MainActivity.this , "granted" , Toast.LENGTH_SHORT).show();
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null) {
                                longitude = location.getLongitude();
                                latitude = location.getLatitude();
                                getCity();
                            }
                        }
                    });
        }
        else{
            ActivityCompat.requestPermissions(this ,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION} , mrequestCode);
        }
    }

    private void getCity(){
        if(latitude == 0.0 || longitude == 0.0){
            city = null;
        }
        else{
            city = "Ghaziabad";
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == mrequestCode){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this , "Permission Granted" , Toast.LENGTH_SHORT).show();
                getCity();
            }
            else{
                Toast.makeText(this , "Not Granted" , Toast.LENGTH_SHORT).show();
                getCity();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(MainActivity.this);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
//
//            case R.id.nav_movies:
//                fragment = new MoviesFragment();
//                break;
//
//            case R.id.nav_TV_shows:
//                fragment = new TvFragment();
//                break;
//            case R.id.nav_profile:
//                fragment = new ProfileFragment();
//                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.nav_about:
//                fragment = new DevelopersAbout();
//                Toast.makeText(this, "ABOUT US", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.nav_dev:
//                fragment = new DevelopersFragment();
//                Toast.makeText(this, "DEVELOPERS", Toast.LENGTH_SHORT).show();
//                break;
//
//            case R.id.nav_fav:
//                fragment = new FavouriteFragment();
//                Toast.makeText(this, "Favourites", Toast.LENGTH_SHORT).show();
//                break;
        }

        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.fragment_frame, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setNavProfileDetails() {
        Call<List<ProfileData>> call = jsonApiHolder.getProfileDetails(sp.getToken());
        call.enqueue(new Callback<List<ProfileData>>() {
            @Override
            public void onResponse(Call<List<ProfileData>> call, Response<List<ProfileData>> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "An Error Occurred!", Toast.LENGTH_LONG).show();
                    return;
                }
                profileDataList = response.body();
                ProfileData profileData = profileDataList.get(0);
                textViewUsername.setText(profileData.getUserName());
                textViewPhoneNumber.setText(profileData.getUserPhone());
            }

            @Override
            public void onFailure(Call<List<ProfileData>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "An Error Occurred!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void   setUpToolBar() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();//setting up hamburger icon
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
