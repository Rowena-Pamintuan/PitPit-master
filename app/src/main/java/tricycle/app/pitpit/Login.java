package tricycle.app.pitpit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class Login extends AppCompatActivity {

    TextInputEditText email,password;
    MaterialButton loginBtn;
    TextView signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        implementation();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(email.getText().toString(),password.getText().toString());
            }
        });
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(Login.this,Register.class);
                startActivity(register);
            }
        });

    }

    private void loginUser(String email, String password) {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = " https://cc84-136-158-35-66.ngrok-free.app/api/user/login";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject JO = new JSONObject(response);
                    if(JO.getString("statusCode").equals("201")){
                        Intent home = new Intent(Login.this, MainActivity.class);
                        startActivity(home);
                        finish();
                    }else{
                        Toast.makeText(Login.this, JO.getString("messages"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.toString());
                Toast.makeText(Login.this, "Something went wrong! = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("email", email);
                MyData.put("password", password);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

    public void implementation(){

        email = (TextInputEditText) findViewById(R.id.email);
        password = (TextInputEditText) findViewById(R.id.password);
        loginBtn = (MaterialButton) findViewById(R.id.loginBtn);
        signupBtn = (TextView) findViewById(R.id.signupBtn);

    }

}