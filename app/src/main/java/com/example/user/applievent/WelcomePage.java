package com.example.user.applievent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //test commit numéro 3
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

    }

    public void gotoGroupCreation(View view)
    {
        Intent intent = new Intent(WelcomePage.this, GroupCreationPage.class);
        startActivity(intent);
    }
}
