package robotv.srp.robotv;
/*
Software Students Development Team
Matheus R. Almeida N00739768
Alenric Apostol N01031550
*/

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import static java.lang.System.exit;



public class BluetoothFinder {

    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;

    public BluetoothFinder(){

    }

    void findBT(String a) throws IOException
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

    void close(){
        try{
            mmSocket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    void send(String msg){
        try{
            mmOutputStream.write(msg.getBytes());
        }catch (IOException e){
            exit(0);
        }

    }
}
