/*
Software Students Development Team
Matheus R. Almeida N00739768
Alenric Apostol N01031550
*/
package robotv.srp.robotv;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView full_name, Username, Student_num, myAppointment, bookSchedule;
    String json_url = "http://prototypelabflow.esy.es/test.php";
    private DrawerLayout mdrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    NavigationView navigationView;
    Toolbar toolbar;
    String test, student_Num, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        full_name = (TextView) findViewById(R.id.full_name);
        Username = (TextView) findViewById(R.id.username);
        Student_num = (TextView) findViewById(R.id.student_num);


        SharedPreferences sharedPref = getSharedPreferences("data", Context.MODE_PRIVATE);

        String first_name = sharedPref.getString("first_name", "");
        String last_name = sharedPref.getString("last_name", "");
        String username = sharedPref.getString("username", "");
        String student_num = sharedPref.getString("student_num", "");

        full_name.setText(first_name +" " +last_name);
        Username.setText(username);
        Student_num.setText(student_num);



        /*
        student_Num = student_num.getText().toString();
        name = full_name.getText().toString();
        */


        mdrawerLayout = (DrawerLayout)findViewById(R.id.activity_profile);
        mDrawerToggle = new ActionBarDrawerToggle(this, mdrawerLayout,R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mdrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_account:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        break;
                    case R.id.nav_rc:
                        Intent requestIntent = new Intent(getApplicationContext(), RemoteControl.class);
                        startActivity(requestIntent);
                        break;
                    case R.id.nav_bt:
                        Intent btIntent = new Intent(getApplicationContext(), RemoteBTActivity.class);
                        startActivity(btIntent);
                        break;
                    case R.id.nav_set:
                        Intent SettingIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                        startActivity(SettingIntent);
                        break;
                    case R.id.nav_about:
                        Intent usIntent = new Intent(getApplicationContext(), AboutUsActivity.class);
                        startActivity(usIntent);
                        break;
                    case R.id.nav_logout:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        break;

                }

                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}