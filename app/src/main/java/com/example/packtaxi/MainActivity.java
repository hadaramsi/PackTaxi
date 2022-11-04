package com.example.packtaxi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private NavController navCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavHostFragment nav_host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.base_navhost);
        navCtrl = nav_host.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navCtrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.base_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(!super.onOptionsItemSelected(item)) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    NavDestination myFragment = navCtrl.getCurrentDestination();
//                    if (myFragment.getId() == R.id.reportsListFragment) {
//                        finish();
//                        return true;
//                    }
//                    if(myFragment.getId() == R.id.mainScreenFragment) {
//                        finish();
//                        return true;
//                    }
                    navCtrl.navigateUp();
                    return true;
                case R.id.aboutAs_menu:
                    navCtrl.navigate(mainScreenFragmentDirections.actionFragmentMainScreenToAboutUsFragment());
                    return true;
//                case R.id.log_out_menu_LogOut:
//                    Model.getInstance().logOutUser(new Model.logOutUserListener() {
//                        @Override
//                        public void onComplete() {
//                            navctrl.navigate(myReportsFragmentDirections.actionGlobalMainScreenFragment());
//                        }
//                    });
//                    return true;
//                case R.id.myProfileMenu_myProfile:
//                    navctrl.navigate(MapFragmentDirections.actionGlobalMyProfileFragment());
//                    return true;
//                case R.id.myReportsmenu_myReport:
//                    navctrl.navigate(MapFragmentDirections.actionGlobalMyReportsFragment());
//                    return true;
                case R.id.back_menu:
//                    navctrl.navigateUp();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        return true;
    }
}