package com.example.tony.ring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DrawRing ring = (DrawRing)findViewById(R.id.ringCircle);
        DrawRing ring = new DrawRing(this);
        LinearLayout layout = (LinearLayout) findViewById(R.id.root);
        layout.addView(ring);
    }
}
