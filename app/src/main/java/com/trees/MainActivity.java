package com.trees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button bnt;
    private Button rbt;
    private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnt = (Button) findViewById(R.id.bnt);
        rbt = (Button) findViewById(R.id.rbt);
        bt = (Button) findViewById(R.id.bt);

        bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityBNT();
            }
        });

        rbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityRBT();
            }
        });

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openActivityBTreq();}
        });

    }


    public void openActivityBNT(){
        Intent intent = new Intent(this, bst.class);
        startActivity(intent);
    }

    public void openActivityRBT(){
        Intent intent = new Intent(this, rbt.class);
        startActivity(intent);
    }

    public void openActivityBTreq(){
        Intent intent = new Intent(this, bt_reqt.class);
        startActivity(intent);
    }

}