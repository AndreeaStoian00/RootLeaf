package com.trees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class bt_reqt extends AppCompatActivity {

    private Button tobt;
    EditText factorT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_bt_reqt);

        factorT = (EditText) findViewById(R.id.factorT);
        tobt = (Button) findViewById(R.id.tobt);


        tobt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(factorT.getText().toString()))
                    Toast.makeText(bt_reqt.this, "A factor must be inserted!", Toast.LENGTH_SHORT).show();
                else {
                    String fT = factorT.getText().toString();
                    Intent intent = new Intent(bt_reqt.this, bt.class);
                    intent.putExtra("FACTORT", fT);
                    startActivity(intent);
                }
            }
        });
    }

    public void openActivityBT(){
        Intent intent = new Intent(this, bt.class);
        startActivity(intent);
    }
}