
package robotv.srp.robotv;
/*
Software Students Development Team
Matheus R. Almeida N00739768
Alenric Apostol N01031550
*/

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    CheckBox remember_me;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefsEditor;
    Boolean saveLogin;
    int j;

    TextView register;
    Button loginbtn;
    EditText username, password;
    String Username, Password;
    String login_url = "http://robotv.esy.es/Login.php";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        register = (TextView)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }

        });


        builder = new AlertDialog.Builder(MainActivity.this);
        loginbtn = (Button)findViewById(R.id.loginbtn);
        username = (EditText)findViewById(R.id.user);
        password = (EditText)findViewById(R.id.password);


        loginbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (username.getText().toString().equals("testuser") && password.getText().toString().equals("123123")) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MainActivity.this, "Invalid User or Password",Toast.LENGTH_LONG).show();

                }
            }

        });
        /*loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(username.getWindowToken(), 0);
                Username = username.getText().toString();
                Password = password.getText().toString();
                if(Username.equals("")||Password.equals("")){
                    builder.setTitle("Login Failed");
                    displayAlert("Enter a valid username and password");
                }
                else{
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, login_url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response){
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");
                                if(code.equals("login_failed")) {
                                    loginPrefsEditor.clear();
                                    loginPrefsEditor.commit();
                                    builder.setTitle("Login Failed");
                                    displayAlert(jsonObject.getString("message"));
                                } else{
                                    loginPrefsEditor.putBoolean("saveLogin", true);
                                    loginPrefsEditor.putString("username", Username);
                                    loginPrefsEditor.putString("password", Password);
                                    loginPrefsEditor.commit();
                                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("first_name", jsonObject.getString("first_name"));
                                    bundle.putString("last_name", jsonObject.getString("last_name"));
                                    bundle.putString("username", jsonObject.getString("username"));
                                    bundle.putString("student_num", jsonObject.getString("student_num"));
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error){
                            Toast.makeText(LoginActivity.this, "Error",Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("username", Username);
                            params.put("password", Password);
                            return params;
                        }
                    };
                    MySingleton.getMyInstance(LoginActivity.this).addToRequestque(stringRequest);
                }
            }
        });
*/
        remember_me = (CheckBox)findViewById(R.id.remember_me);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            username.setText(loginPreferences.getString("username", ""));
            password.setText(loginPreferences.getString("password", ""));
            remember_me.setChecked(true);
        }
    }

    public void displayAlert(String message){
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                username.setText("");
                password.setText("");
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    // Handle the Back Key
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_sad_on_exit)
                .setTitle(getString(R.string.exit_prompt_title))
                .setMessage(getString(R.string.exit_prompt_msg))
                .setPositiveButton(getString(R.string.exit_prompt_yes), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton(getString(R.string.exit_prompt_no), null)
                .show();
    }
}