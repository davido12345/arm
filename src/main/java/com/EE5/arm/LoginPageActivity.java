package com.EE5.arm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LoginPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        Button Driver = (Button)findViewById(R.id.DriverButton);
        Button Admin = (Button)findViewById(R.id.AdminButton);

        Driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent driver = new Intent(LoginPageActivity.this,Driver_mainpage.class);
                startActivity(driver);
            }
        });

        Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent admin = new Intent(LoginPageActivity.this,Administrator_mainpage.class);
                startActivity(admin);
            }
        });

    }
}




