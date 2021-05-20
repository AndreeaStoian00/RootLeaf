package com.trees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class bt_reqt extends AppCompatActivity {
    private Button tobt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bt_reqt);

        tobt = (Button) findViewById(R.id.tobt);

        tobt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openActivityBT(); }
        });
    }

    public void openActivityBT(){
        Intent intent = new Intent(this, bt.class);
        startActivity(intent);
    }
}