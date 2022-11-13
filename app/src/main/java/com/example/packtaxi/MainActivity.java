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
import com.example.packtaxi.model.Model;

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
                    if (myFragment.getId() == R.id.signUpFragment) {
                        finish();
                        return true;
                    }
                    if(myFragment.getId() == R.id.fragmentSignUpAsDriver) {
                        finish();
                        return true;
                    }
                    if(myFragment.getId() == R.id.fragmentSignUpAsSender) {
                        finish();
                        return true;
                    }
                    navCtrl.navigateUp();
                    return true;
                case R.id.aboutAs_menu:
                    navCtrl.navigate(mainScreenFragmentDirections.actionFragmentMainScreenToAboutUsFragment2());
                    return true;
                case R.id.logout_driver:
//                    Model.getInstance().logOutUser(new Model.logOutUserListener() {
//                        @Override
//                        public void onComplete() {
//                            navctrl.navigate(myReportsFragmentDirections.actionGlobalMainScreenFragment());
//                        }
//                    });
                    return true;
                case R.id.logout_sender:
//                    Model.getInstance().logOutUser(new Model.logOutUserListener() {
//                        @Override
//                        public void onComplete() {
//                            navctrl.navigate(myReportsFragmentDirections.actionGlobalMainScreenFragment());
//                        }
//                    });
                    return true;
                case R.id.logout_manager:
//                Model.getInstance().logOutUser(new Model.logOutUserListener() {
//                        @Override
//                        public void onComplete() {
//                            navctrl.navigate(myReportsFragmentDirections.actionGlobalMainScreenFragment());
//                        }
//                    });
                    return true;
                case R.id.my_profile_driver:
                    navCtrl.navigate(mainScreenDriverFragmentDirections.actionMainScreenDriverFragmentToDriverProfileFragment());
                    return true;
                case R.id.my_profile_sender:
                    navCtrl.navigate(mainScreenSenderFragmentDirections.actionMainScreenSenderFragmentToSenderProfileFragment());
                    return true;
                case R.id.add_delivery_point:
                    navCtrl.navigate(managerMainScreenFragmentDirections.actionManagerMainScreenFragmentToAddingDeliveryPointFragment());
                    return true;
                case R.id.add_drive_driver:
                    navCtrl.navigate(mainScreenDriverFragmentDirections.actionMainScreenDriverFragmentToAddFutureRoudFragment());
                    return true;
                case R.id.search_delivery_point:
                    // TODO:
                    return true;
                case R.id.add_pack_sender:
                    navCtrl.navigate(mainScreenSenderFragmentDirections.actionMainScreenSenderFragmentToAddPackageFragment());
                    return true;
                case R.id.back_menu:
                    navCtrl.navigateUp();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        return true;
    }
}