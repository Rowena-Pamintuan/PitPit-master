package tricycle.app.pitpit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

public class booking extends AppCompatActivity {

    CardView pickup_location_id, dropoff_location_id;
    ArrayList<PlaceModel> dataModelArrayList;
    TextView pickup_address,dropoff_address;
    String stepLocation = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Open Full Screen Dialog on Click
        // Set the OnClickListener on the CardView
         pickup_location_id = findViewById(R.id.pickup_location_id);
         dropoff_location_id = findViewById(R.id.dropoff_location_id);
        pickup_address = findViewById(R.id.pickup_address);
        dropoff_address = findViewById(R.id.dropoff_address);


        pickup_location_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepLocation = "Pickup";
                openPickupDialog();
//
            }

        });

        dropoff_location_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepLocation = "DropOff";
                openPickupDialog();
            }
        });

    }

    private void openPickupDialog() {
        final Dialog dialog = new Dialog(booking.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choose_pickup_location);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }

        TextView closePickupDialog = dialog.findViewById(R.id.close_pickup_dialog);
        Button searchPlaceBtn = dialog.findViewById(R.id.searchPlaceBtn);
        ListView places = dialog.findViewById(R.id.places);
        TextInputEditText searchPlaces = dialog.findViewById(R.id.searchPlaces);

        places.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

         if(stepLocation == "Pickup"){
               pickup_address.setText(dataModelArrayList.get(i).getValue() +", "+ dataModelArrayList.get(i).getSubtext());
           }
           if(stepLocation == "DropOff"){
               dropoff_address.setText(dataModelArrayList.get(i).getValue() +", "+ dataModelArrayList.get(i).getSubtext());
           }
           stepLocation = "";
                dialog.dismiss();
            }
        });

        searchPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue MyRequestQueue = Volley.newRequestQueue(booking.this);
    String urlHost = "https://serpapi.com/search.json?";
    String engine = "engine=google_maps_autocomplete";
    String q = "&q="+searchPlaces.getText().toString();
    String hl = "&hl=en";
    String gl = "&gl=us";
    String ll = "&ll=@14.599512,120.984222,14z";
    String api_key = "&api_key=ff627bec0fd8d62aad82692fb1b6d37bdd662f49cec7875685ba8dc8106db906";

    String url = urlHost+engine+q+hl+gl+ll+api_key;
                StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("resultAPI",response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            dataModelArrayList = new ArrayList<>();
                            JSONArray dataArray  = jsonObject.getJSONArray("suggestions");

                            for (int i = 0; i < dataArray.length(); i++) {
PlaceModel placeModel = new PlaceModel();
                                JSONObject dataobj = dataArray.getJSONObject(i);


                                if (dataobj.has("value")) {
                                    placeModel.setValue(dataobj.getString("value"));
                                }
                                if (dataobj.has("subtext")) {
                                    placeModel.setSubtext(dataobj.getString("subtext"));
                                }



//                                Log.d("resultdwad");

                                dataModelArrayList.add(placeModel);
                            }

                            PlacesAdapter placesAdapter = new PlacesAdapter(booking.this, dataModelArrayList);
                            places.setAdapter(placesAdapter);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
//                            e.printStackTrace();
//                            Log.d("error",e);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error",error.toString());
                        Toast.makeText(booking.this, "Something went wrong! = " + error, Toast.LENGTH_SHORT).show();
                    }
                });
                MyRequestQueue.add(MyStringRequest);
            }
        });
        closePickupDialog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Close the dialog
                dialog.dismiss();
            }
        });

        dialog.show();

    }


//    private void openDropoffDialog() {
//        final Dialog dialog = new Dialog(booking.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.choose_pickup_location);
//        Window window = dialog.getWindow();
//        if (window != null) {
//            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//        }
//
//        // Find the close_pickup_dialog view by its ID
//        TextView closePickupDialog = dialog.findViewById(R.id.close_pickup_dialog);
//        closePickupDialog.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                // Close the dialog
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }
}