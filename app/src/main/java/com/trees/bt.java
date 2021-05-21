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

import java.util.ArrayList;
import java.util.List;

public class bt extends AppCompatActivity {

    private Button back;
    TextView display;
    int factorT;
    EditText insertedNode;
    private Button baN;
    private Button bdN;
    private Button bfsN;
    private Button bfpN;
    private Button bminN;
    private Button bmaxN;
    private Button binord;
    private Button bpred;
    private Button bpostd;
    private Button bh;

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
        back = (Button) findViewById(R.id.back);
        baN = (Button) findViewById(R.id.Add);
        bdN = (Button) findViewById(R.id.Delete);
        bfpN = (Button) findViewById(R.id.Predecessor);
        bfsN = (Button) findViewById(R.id.Successor);
        bminN = (Button) findViewById(R.id.Min);
        bmaxN = (Button) findViewById(R.id.Max);
        binord = (Button) findViewById(R.id.Inorder);
        bpred = (Button) findViewById(R.id.Preorder);
        bpostd = (Button) findViewById(R.id.Postorder);
        bh = (Button) findViewById(R.id.Height);
        
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityMain();
            }
        });

        baN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                insertedNode = (EditText)findViewById(R.id.addNode);
                display = (TextView)findViewById(R.id.displayText3);
                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(bt.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //display.setText("A node must be inserted!");
                else {
                    /*
                    if(tree.searchTree(Integer.parseInt(insertedNode.getText().toString())) != null){
                        tree.insert(Integer.parseInt(insertedNode.getText().toString()));
                        Toast.makeText(bt.this, "Node " + insertedNode.getText().toString() + " was added!", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(bt.this, "Node " + insertedNode.getText().toString() + " already exists!", Toast.LENGTH_SHORT).show();
                    //display.setText("Node " + insertedNode.getText().toString() + " was added!");*/
                    insertedNode.getText().clear();
                }
            }
        });

        bdN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                insertedNode = (EditText)findViewById(R.id.addNode);
                display = (TextView)findViewById(R.id.displayText3);
                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(bt.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //display.setText("A node must be inserted!");
                else {/*
                    if(tree.root == tree.TNULL) Toast.makeText(bt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                    else {
                        if(tree.searchTree(Integer.parseInt(insertedNode.getText().toString())) == null ){
                            Toast.makeText(bt.this, "Node " + insertedNode.getText().toString() + " does not exist!", Toast.LENGTH_SHORT).show();
                        } else{
                            tree.deleteNode(Integer.parseInt(insertedNode.getText().toString()));
                            Toast.makeText(bt.this, "Node " + insertedNode.getText().toString() + " was deleted!", Toast.LENGTH_SHORT).show();
                            //display.setText("Node " + insertedNode.getText().toString() + " was deleted!");
                        }
                    }*/
                    insertedNode.getText().clear();
                }
            }
        });

        bfsN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                insertedNode = (EditText)findViewById(R.id.addNode);
                display = (TextView)findViewById(R.id.displayText3);
                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(bt.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //display.setText("A node must be inserted!");
                else {/*
                    if (tree.root == tree.TNULL)
                        Toast.makeText(bt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                    else if (tree.searchTree(Integer.parseInt(insertedNode.getText().toString())) == tree.TNULL) {
                        Toast.makeText(bt.this, "Node " + insertedNode.getText().toString() + " does not exist!", Toast.LENGTH_SHORT).show();
                    } else {
                        if(tree.successor(tree.searchTree(Integer.parseInt(insertedNode.getText().toString()))) == null)
                            display.setText("Node " + insertedNode.getText().toString() + " does not have a successor!");
                        else display.setText("The successor of " + insertedNode.getText().toString() + " is: " + tree.successor(tree.searchTree(Integer.parseInt(insertedNode.getText().toString()))).data +
                                (tree.successor(tree.searchTree(Integer.parseInt(insertedNode.getText().toString()))).color == 0? "B" : "R") + ".");
                    }*/

                }
                insertedNode.getText().clear();
            }
        });

        bfpN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                insertedNode = (EditText)findViewById(R.id.addNode);
                display = (TextView)findViewById(R.id.displayText3);

                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(bt.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //display.setText("A node must be inserted!");
                else {/*
                    if (tree.root == tree.TNULL)
                        Toast.makeText(bt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                    else if (tree.searchTree(Integer.parseInt(insertedNode.getText().toString())) == tree.TNULL) {
                        Toast.makeText(bt.this, "Node " + insertedNode.getText().toString() + " does not exist!", Toast.LENGTH_SHORT).show();
                    } else {
                        if(tree.predecessor(tree.searchTree(Integer.parseInt(insertedNode.getText().toString()))) == null)
                            display.setText("Node " + insertedNode.getText().toString() + " does not have a predecessor!");
                        else display.setText("The predecessor of " + insertedNode.getText().toString() + " is: " + tree.predecessor(tree.searchTree(Integer.parseInt(insertedNode.getText().toString()))).data +
                                (tree.predecessor(tree.searchTree(Integer.parseInt(insertedNode.getText().toString()))).color == 0? "B" : "R") + ".");
                    }
                    */
                }
                insertedNode.getText().clear();
            }
        });

        bminN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                display = (TextView)findViewById(R.id.displayText3);
                /*
                if(tree.root == tree.TNULL) Toast.makeText(bt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else display.setText("The minimum node is: " + tree.minimum(tree.root).data + (tree.minimum(tree.root).color == 0? "B" : "R" ));
                */
            }

        });

        bmaxN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                display = (TextView)findViewById(R.id.displayText3);
                /*
                if(tree.root == tree.TNULL) Toast.makeText(bt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else display.setText("The maximum node is: " + tree.maximum(tree.root).data + (tree.maximum(tree.root).color == 0? "B" : "R" ));
                */
            }
        });

        bpred.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                display = (TextView)findViewById(R.id.displayText3);
                /*
                if(tree.root == tree.TNULL) Toast.makeText(bt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    List<String> display = new ArrayList<String>();
                    display = tree.preorder(tree.root, display);
                    display.setText("The preorder display of the tree is: " + display);
                    display.clear();
                }*/
            }
        });

        binord.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                display = (TextView)findViewById(R.id.displayText3);
                /*
                if(tree.root == tree.TNULL) Toast.makeText(bt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    List<String> display = new ArrayList<String>();
                    display = tree.inorder(tree.root, display);
                    display.setText("The inorder display of the tree is: " + display);
                    display.clear();
                }*/
            }
        });

        bpostd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                display = (TextView)findViewById(R.id.displayText3);
                /*
                if(tree.root == tree.TNULL) Toast.makeText(bt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    List<String> display = new ArrayList<String>();
                    display = tree.postorder(tree.root, display);
                    display.setText("The postorder display of the tree is: " + display);
                    display.clear();
                }*/
            }
        });

        bh.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                display = (TextView)findViewById(R.id.displayText3);
                /*
                if(tree.root == tree.TNULL) Toast.makeText(bt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    display.setText("The height of the tree is: " + tree.height(tree.root));
                }*/
            }
        });
    }

    public void openActivityMain(){
        Intent intent = new Intent(this, com.trees.MainActivity.class);
        startActivity(intent);
    }
}