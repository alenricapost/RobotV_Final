/*
Software Students Development Team
Matheus R. Almeida N00739768
Alenric Apostol N01031550
*/

package robotv.srp.robotv;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Set;

public class SettingsActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    EditText ipAddress;
    Button b1,b2,b3;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        b1 = (Button) findViewById(R.id.buttonon);
        b2= (Button)findViewById(R.id.buttonoff);
        b3 = (Button)findViewById(R.id.visible);

        BA = BluetoothAdapter.getDefaultAdapter();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!BA.isEnabled()) {
                    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOn, 0);
                    Toast.makeText(getApplicationContext(), "Turned on",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BA.disable();
                Toast.makeText(getApplicationContext(), "Turned off" ,Toast.LENGTH_LONG).show();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivityForResult(getVisible, 0);


            }
        });
        ipAddress = (EditText) findViewById(R.id.etIPAddress);
        String IP =ipAddress.getText().toString();
        SharedPreferences.Editor editor = getSharedPreferences("pref", MODE_PRIVATE).edit();
        editor.putString("IP", IP);
        editor.apply();
    }




}

