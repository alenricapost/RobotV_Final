/*
Software Students Development Team
Matheus R. Almeida N00739768
Alenric Apostol N01031550
*/
package robotv.srp.robotv;



import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button register;
    EditText first_name,last_name,username,password,confirm_pw,dob,student_num;
    String First_name,Last_name,Username,Password,Confirm_pw,DOB,Student_num;
    AlertDialog.Builder builder;
    String reg_url = "http://prototypelabflow.esy.es/Register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = (Button)findViewById(R.id.register);
        first_name = (EditText) findViewById(R.id.first_name);
        last_name = (EditText) findViewById(R.id.last_name);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirm_pw = (EditText) findViewById(R.id.confirm_pw);

        student_num = (EditText) findViewById(R.id.student_num);

        builder = new AlertDialog.Builder(RegisterActivity.this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                First_name = first_name.getText().toString();
                Last_name = last_name.getText().toString();
                Username = username.getText().toString();
                Password = password.getText().toString();
                Confirm_pw = confirm_pw.getText().toString();
                Student_num = student_num.getText().toString();

                if(First_name.equals("")||Last_name.equals("")||Username.equals("")||Password.equals("")||Confirm_pw.equals("")||DOB.equals("")||Student_num.equals("")){
                    builder.setTitle("Register Failed");
                    builder.setMessage("Fill all the fields");
                    displayAlert("input_error");
                }
                else{
                    if(!(Password.equals(Confirm_pw))){
                        builder.setTitle("Register Failed");
                        builder.setMessage("Passwords did not match");
                        displayAlert("input_error");
                    }
                    else{
                        StringRequest stringRequest = new StringRequest(Request.Method.POST,reg_url, new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response){
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String code = jsonObject.getString("code");
                                    String message = jsonObject.getString("message");
                                    builder.setTitle("Information");
                                    builder.setMessage(message);
                                    displayAlert(code);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError error){

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError{
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("first_name", First_name);
                                params.put("last_name", Last_name);
                                params.put("username", Username);
                                params.put("password", Password);
                                params.put("student_num", Student_num);
                                return params;
                            }
                        };
                        MySingleton.getMyInstance(RegisterActivity.this).addToRequestque(stringRequest);
                    }
                }
            }
        });
    }

    public void displayAlert(final String code) {
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                if(code.equals("input_error")){
                    password.setText("");
                    confirm_pw.setText("");
                }
                else if(code.equals("reg_success")){
                    finish();
                }
                else if(code.equals("reg_failed")){
                    first_name.setText("");
                    last_name.setText("");
                    username.setText("");
                    password.setText("");
                    confirm_pw.setText("");
                    dob.setText("");
                    student_num.setText("");
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
