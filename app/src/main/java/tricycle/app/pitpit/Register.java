package tricycle.app.pitpit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    TextView loginBtn;
    MaterialButton registerBtn;
RadioGroup gender;
    TextInputEditText firstname, lastname, email, number, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        implementation();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(Register.this, Login.class);
                startActivity(login);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = gender.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);

                String fname = firstname.getText().toString();
                String lname = lastname.getText().toString();
                String emailAddress = email.getText().toString();
                String phone = number.getText().toString();
                String pass = password.getText().toString();
               String gender =radioButton.getText().toString();

                createAccount(fname,lname,emailAddress,phone,pass,gender);
            }
        });

    }

    public void createAccount(String firstname, String lastname, String email, String number, String password, String gender){

        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "https://77be-136-158-17-171.ngrok-free.app/api/user/register";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("statusCode").equals("201")){
                        Toast.makeText(Register.this, "Register successfull", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Register.this, jsonObject.getString("messages"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.toString());
                Toast.makeText(Register.this, "Something went wrong! = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> RegisterData = new HashMap<String, String>();
                RegisterData.put("email", email);
                RegisterData.put("password", password);
                RegisterData.put("firstname", firstname);
                RegisterData.put("lastname", lastname);
                RegisterData.put("phone", number);
                RegisterData.put("gender", gender);
                return RegisterData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    public void implementation(){
        loginBtn = (TextView) findViewById(R.id.loginBtn);
        registerBtn = (MaterialButton) findViewById(R.id.registerBtn);
        gender = (RadioGroup) findViewById(R.id.gender);
        firstname = (TextInputEditText) findViewById(R.id.firstname);
        lastname = (TextInputEditText) findViewById(R.id.lastname);
        email = (TextInputEditText) findViewById(R.id.email);
        number = (TextInputEditText) findViewById(R.id.number);
        password = (TextInputEditText) findViewById(R.id.password);
    }
}