package com.example.packtaxi;
import okhttp3.OkHttpClient;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.example.packtaxi.model.Model;
import okhttp3.Callback;
import okhttp3.Call;
import okhttp3.Request;
import java.io.IOException;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private NavController navCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavHostFragment nav_host = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.base_navhost);
        navCtrl = nav_host.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navCtrl);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://192.168.1.179:5000/").build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("TAG","onFailure is fine ++++++++++++++++++++++");
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.d("TAG","onResponse is fine ++++++++++++++++++++++");

                final String responseData = response.body().string();
//                MainActivity.this.runOnUiThread(() -> textView_response.setText(responseData));
                Log.d("TAG", responseData);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(!super.onOptionsItemSelected(item)) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    NavDestination myFragment = navCtrl.getCurrentDestination();

                    if (myFragment.getId() == R.id.mainScreenSenderFragment) {
                        finish();
                        return true;
                    }
                    if(myFragment.getId() == R.id.mainScreenDriverFragment) {
                        finish();
                        return true;
                    }
                    if(myFragment.getId() == R.id.mangerMainScreenFragment) {
                        finish();
                        return true;
                    }
                    navCtrl.navigateUp();
                    return true;
                case R.id.logout_driver:
                    Model.getInstance().logOutUser(new Model.logOutUserListener() {
                        @Override
                        public void onComplete() {
                            navCtrl.navigate(mainScreenDriverFragmentDirections.actionMainScreenDriverFragmentToFragmentLogin());
                        }
                    });
                    return true;
                case R.id.logout_sender:
                    Model.getInstance().logOutUser(new Model.logOutUserListener() {
                        @Override
                        public void onComplete() {
                            navCtrl.navigate(mainScreenSenderFragmentDirections.actionMainScreenSenderFragmentToFragmentLogin());
                        }
                    });
                    return true;
                case R.id.logout_manager:
                Model.getInstance().logOutUser(new Model.logOutUserListener() {
                        @Override
                        public void onComplete() {
                            navCtrl.navigate(managerMainScreenFragmentDirections.actionMangerMainScreenFragmentToFragmentLogin());
                        }
                    });
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