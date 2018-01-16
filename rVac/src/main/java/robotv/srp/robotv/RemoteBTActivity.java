/*
Software Students Development Team
Matheus R. Almeida N00739768
Alenric Apostol N01031550
*/

package robotv.srp.robotv;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import static java.lang.System.exit;

public class RemoteBTActivity extends AppCompatActivity {

    RelativeLayout layout_joystick;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    JoyStick js;
    TextView directiontv;
    String a;
    boolean connected;
    String msg;
    BluetoothFinder finder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_bt);

        directiontv = (TextView) findViewById(R.id.directiontv);

        final Button connect = (Button) findViewById(R.id.bConnect);
        Button close = (Button) findViewById(R.id.bClose);
        String title = getString(R.string.noipsettings);
        String msg = getString(R.string.BTQuestion);
        String yes = getString(R.string.yes);
        String no = getString(R.string.later);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // set title
        alertDialogBuilder.setTitle(title);
        // set dialog message
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent in = new Intent(RemoteBTActivity.this, SettingsActivity.class);
                        startActivity(in);
                    }
                })

                .setNegativeButton(no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });
        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        layout_joystick = (RelativeLayout) findViewById(R.id.layout_joystick1);

        js = new JoyStick(getApplicationContext()
                , layout_joystick, R.drawable.image_button);
        js.setStickSize(150, 150);
        js.setLayoutSize(500, 500);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);

        if (!isBluetoothAvailable())
        {
            alertDialog.show();
        }

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                a = "raspberrypi";
                if (isBluetoothAvailable())
                {
                    try {
                        //finder.findBT(a);
                        findBT();
                        Toast.makeText(getBaseContext(), R.string.trying, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(getBaseContext(), R.string.notestablished, Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    Toast.makeText(getBaseContext(), R.string.connected, Toast.LENGTH_SHORT).show();
                    connected = true;
                }
                else
                    Toast.makeText(getBaseContext(), R.string.bluetoothnotif, Toast.LENGTH_LONG).show();






            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connected){
                    // finder.close();
                    try{
                        Toast.makeText(getBaseContext(), R.string.closing, Toast.LENGTH_LONG).show();
                        sendMsg("stop");
                        mmSocket.close();
                        connected = false;
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getBaseContext(), R.string.noconnection,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        layout_joystick.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                if (arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {

                    int direction = js.get8Direction();
                    if (direction == JoyStick.STICK_UP) {
                        if (connected)
                            sendMsg("up");
                        directiontv.setText(R.string.up);
                    } else if (direction == JoyStick.STICK_UPRIGHT) {

                        directiontv.setText(R.string.upright);
                    } else if (direction == JoyStick.STICK_RIGHT) {
                        if (connected)
                            sendMsg("right");
                        directiontv.setText(R.string.right);
                    } else if (direction == JoyStick.STICK_DOWNRIGHT) {
                        directiontv.setText(R.string.downright);
                    } else if (direction == JoyStick.STICK_DOWN) {
                        if (connected)
                            sendMsg("down");

                        directiontv.setText(R.string.down);
                    } else if (direction == JoyStick.STICK_DOWNLEFT) {
                        directiontv.setText(R.string.downleft);
                    } else if (direction == JoyStick.STICK_LEFT) {
                        if (connected)
                            sendMsg("left");

                        directiontv.setText(R.string.left);
                        //
                    } else if (direction == JoyStick.STICK_UPLEFT) {

                        directiontv.setText(R.string.upleft);
                    } else if (direction == JoyStick.STICK_NONE) {
                        directiontv.setText("");
                    }
                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    directiontv.setText(null);
                }
                return true;
            }
        });




    }

    public static boolean isBluetoothAvailable() {
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return (bluetoothAdapter != null && bluetoothAdapter.isEnabled());
    }

    void findBT() throws IOException
    {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null)
        {
            exit(0);
            //  myLabel.setText("No bluetooth adapter available");
        }

        if(!mBluetoothAdapter.isEnabled())
        {
            // Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            // startActivityForResult(enableBluetooth, 1);

        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices)
            {
                if(device.getName().equals(a))//FireFly-B1A7Change to the name of your bluetooth module (Case sensitive)
                {
                    mmDevice = device;
                    break;
                }
            }
        }
        // myLabel.setText("Bluetooth Device Found");


        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard //SerialPortService ID

        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        try{
            mmSocket.connect();

        }catch(IOException e){
            // Toast.makeText(getBaseContext(), "Connection not established with the robot", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            exit(0);


        }
        finally {
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

        }

        //  beginListenForData();

        // myLabel.setText("Bluetooth Opened");
    }

    void sendMsg(String msg1){
        try{
            mmOutputStream.write(msg1.getBytes());
        }catch (IOException e){
            exit(0);
        }

    }




}
