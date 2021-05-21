package com.trees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button bnt;
    private Button rbt;
    private Button bt;
    private Button st;
    private Button at;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_main);

        bnt = (Button) findViewById(R.id.bnt);
        rbt = (Button) findViewById(R.id.rbt);
        bt = (Button) findViewById(R.id.bt);
        st = (Button) findViewById(R.id.splay);
        at = (Button) findViewById(R.id.avl);

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

        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openActivityST();}
        });

        at.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openActivityAT();}
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

    public void openActivityST(){
        Intent intent = new Intent(this, st.class);
        startActivity(intent);
    }

    public void openActivityAT(){
        Intent intent = new Intent(this, at.class);
        startActivity(intent);
    }
}