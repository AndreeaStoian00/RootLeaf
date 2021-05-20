package com.trees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class bt extends AppCompatActivity {

    private Button back;
    TextView display;
    int factorT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_bt);

        back = (Button) findViewById(R.id.back);
        display = (TextView) findViewById(R.id.displayText3);
        factorT = Integer.parseInt(getIntent().getStringExtra("FACTORT"));
        display.setText("The T factor is: " + factorT + ".");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityMain();
            }
        });
    }

    public void openActivityMain(){
        Intent intent = new Intent(this, com.trees.MainActivity.class);
        startActivity(intent);
    }
}