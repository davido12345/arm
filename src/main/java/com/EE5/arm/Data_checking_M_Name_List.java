package com.EE5.arm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.EE5.arm.Databases.InfoArrays;
import com.EE5.arm.Databases.Keys;
import com.EE5.arm.Databases.Links;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Data_checking_M_Name_List extends AppCompatActivity {
    public static int machineSelected;
    ListView listView;
    String TAG = "David: ";
    public ArrayList<String> MachineList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_checking);
        listView = findViewById(R.id.listView);
        MachineList.clear();
        Context context = getApplicationContext();

        //ListView
        setListView();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, MachineList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


        //When an item is clicked we must redirect to a new page for the sessions of this one
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                machineSelected = position+1;
                Log.d(TAG, "The machine selected is "+machineSelected);
                String name = MachineList.get(position); //The name of the machine which is clicked.
                Log.d(TAG, " How much data received: "+ InfoArrays.id_MowerS.size());
                Intent intent = new Intent(Data_checking_M_Name_List.this, Data_checking_Session_List.class);
                startActivity(intent);

                /*if(name.equals("A-1"))
                {
                    Intent intent = new Intent(Data_checking_M_Name_List.this, Data_page.class);
                    startActivity(intent);
                }*/
            }
        });
    }

    public void setListView(){
        for(int i =0; i<InfoArrays.name_Mower.size(); i++) {
            Log.d(TAG, "Adding ArrayList Elements");
            MachineList.add(InfoArrays.name_Mower.get(i));
            Log.d(TAG, "Done Adding ArrayList Elements");
        }
        Log.d(TAG, MachineList.size()+" Is the size of machinelist");
        //ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, MachineList);
        //listView.setAdapter(arrayAdapter);
    }
    //Code to send a JSON volley to the DB
    public void JSonVolley(final String url) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "got a response");
                //manipulate response
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        try {
                            JSonToArray(jsonObject, url);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(jsonArrayRequest);
    }

    //Code to transfer retrieved JSON data into arraylists in the application.
    public void JSonToArray (JSONObject jsonObject, String url) throws Exception {
        if (url.equals(Links.allMowerData)) {
            InfoArrays.type_Mower.add(jsonObject.getString(Keys.Type));
            Log.d(TAG, "TYPE INPUT");
            InfoArrays.id_Mower.add(jsonObject.getInt(Keys.id_Mower));
            InfoArrays.id_workGroup.add(jsonObject.getInt(Keys.id_workGround));
            InfoArrays.name_Mower.add(jsonObject.getString(Keys.name_Mower));
            Log.d(TAG, "Name INPUT");

        } else if (url.equals(Links.allSessionData)) {

            InfoArrays.id_dataSD.add(jsonObject.getInt(Keys.session_data_id));
            InfoArrays.id_MowerSD.add(jsonObject.getString(Keys.session_mower_id));
            InfoArrays.timeSD.add(jsonObject.getString(Keys.session_data_time));
            InfoArrays.GPS_XSD.add(jsonObject.getDouble(Keys.session_data_Gps_x));
            InfoArrays.GPS_YSD.add(jsonObject.getDouble(Keys.session_data_Gps_y));
            InfoArrays.JoystickSD.add(jsonObject.getDouble(Keys.session_data_Joystic));
            InfoArrays.Oil_TempSD.add(jsonObject.getDouble(Keys.session_data_Oil_temp));
            InfoArrays.Acc_X_1SD.add(jsonObject.getDouble(Keys.session_data_Acc_x_1));
            InfoArrays.Acc_Y_1SD.add(jsonObject.getDouble(Keys.session_data_Acc_y_1));
            InfoArrays.Acc_Z_1SD.add(jsonObject.getDouble(Keys.session_data_Acc_z_1));
            InfoArrays.Pitch_1SD.add(jsonObject.getDouble(Keys.session_data_Pitch_1));
            InfoArrays.Yaw_1SD.add(jsonObject.getDouble(Keys.session_data_Yaw_1));
            InfoArrays.Roll_1SD.add(jsonObject.getDouble(Keys.session_data_Roll_1));

        } else if (url.equals(Links.allSessions)) {

            InfoArrays.id_sess.add(jsonObject.getInt(Keys.id_sess));
            InfoArrays.id_MowerS.add(jsonObject.getInt(Keys.id_Mower));
            InfoArrays.dateS.add(jsonObject.getString(Keys.session_date));
            InfoArrays.startTimeS.add(jsonObject.getString(Keys.session_startTime));
            InfoArrays.Duration.add(jsonObject.getString(Keys.session_Duration));
        }
        //Log.d(TAG, "getting size :" + InfoArrays.firstNames.size());
    }


}
