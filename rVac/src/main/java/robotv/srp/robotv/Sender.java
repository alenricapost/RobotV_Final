/*
Software Students Development Team
Matheus R. Almeida N00739768
Alenric Apostol N01031550
*/

package robotv.srp.robotv;



import android.os.StrictMode;

import java.io.*;
import java.net.*;

import static java.lang.System.exit;

class Sender extends Socket {
    static Socket socket = null;
    static PrintWriter out = null;
    static BufferedReader remoteInput = null;
    static String ipAddress = null;
    // public static int exit = 0;
    int num =0;

    public Sender() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            // if(InetAddress.getByName(getIpAddress()).isReachable(0)) {

            //    if(InetAddress.getByName(getIpAddress()).isReachable(0)) {
            //       exit = 1;
            //     setExit(exit);
            //}
            try{
                socket = new Socket(getIpAddress(), 40093);
            }
            catch (ConnectException e){
                //    setExit(1);
                exit(0);
            }
            out = new PrintWriter(socket.getOutputStream(), true);
            remoteInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));



        } catch (IOException e) {

            e.printStackTrace();
            // exit = 1;
            //setExit(1);
            exit(-1);

        }
      /* finally{

            if (socket!=null){
                try{
                    socket.close();
                }catch (Exception e) {
                    exit = true;
                    setExit(exit);
                }
            }
        }*/

    }



    public static void setIpAddress(String ipAddress) {
        Sender.ipAddress = ipAddress;
    }



    public static String getIpAddress() {
        return ipAddress;
    }

    void send(String msg) {
        out.println(msg);

    }

    //  public static void setExit(int exit){Sender.exit = exit;}

    // public static int getExit(){ return exit;}

    /*method to get the msg from server
     * @return String msg
     * */
    String getit() {
        String msg = null;
        try {
            msg = remoteInput.readLine();
            return msg;

        } catch (Exception e) {
            // TODO: handle exception
        }
        return msg = "nothing received";
    }

    /*method to close the socket*/
    void done() {
        try {
            Sender.socket.close();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public static void main(String argv[]) throws IOException {


    }
}