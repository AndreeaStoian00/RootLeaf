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

public class bst extends AppCompatActivity {

    public class BinarySearchTree {
        class Node {
            int key;
            Node left, right;

            public Node(int item) {
                key = item;
                left = right = null;
            }
        }

        Node root;

        BinarySearchTree() {
            root = null;
        }

        void insert(int key) {
            root = insertRec(root, key);
        }

        Node insertRec(Node root, int key) {

            if (root == null) {
                root = new Node(key);
                return root;
            }

            if (key < root.key)
                root.left = insertRec(root.left, key);
            else if (key > root.key)
                root.right = insertRec(root.right, key);

            return root;
        }

        public void delete(int value) {
            root = deleteRec(root, value);
        }

        private Node deleteRec(Node current, int value) {
            if (current == null) {
                return null;
            }
            if (value == current.key) {
                if (current.left == null && current.right == null) {
                    return null;
                }
                if (current.right == null) {
                    return current.left;
                }
                if (current.left == null) {
                    return current.right;
                }
                int smallestValue = findSmallestValue(current.right);
                current.key = smallestValue;
                current.right = deleteRec(current.right, smallestValue);
                return current;
            }
            if (value < current.key) {
                current.left = deleteRec(current.left, value);
                return current;
            }
            current.right = deleteRec(current.right, value);
            return current;
        }

        List<Integer> inorder(Node root, List<Integer> display) {
            if (root != null) {
                inorder(root.left, display);
                display.add(root.key);
                inorder(root.right, display);
            }
            return display;
        }

        List<Integer> postorder(Node root, List<Integer> display) {
            if (root != null) {
                inorder(root.left, display);
                display.add(root.key);
                inorder(root.right, display);
            }
            return display;
        }

        List<Integer> preorder(Node root, List<Integer> display) {
            if (root != null) {
                inorder(root.left, display);
                inorder(root.right, display);
                display.add(root.key);
            }
            return display;
        }

        private int findSmallestValue(Node root) {
            return root.left == null ? root.key : findSmallestValue(root.left);
        }

        private int findBiggestValue(Node root){
            return root.right == null? root.key : findBiggestValue(root.right);
        }

        public boolean containsNode(int value) {
            return containsNodeRecursive(root, value);
        }

        private boolean containsNodeRecursive(Node current, int value) {
            if (current == null) {
                return false;
            }
            if (value == current.key) {
                return true;
            }
            return value < current.key ? containsNodeRecursive(current.left, value) : containsNodeRecursive(current.right, value);
        }

        int maxDepth(Node node)
        {
            if (node == null)
                return 0;
            else
            {

                int lDepth = maxDepth(node.left);
                int rDepth = maxDepth(node.right);

                if (lDepth > rDepth)
                    return (lDepth + 1);
                else
                    return (rDepth + 1);
            }
        }

        public Node findMaximum(Node root)
        {
            Node temp = root;
            while (temp.right != null) {
                temp = temp.right;
            }

            return temp;
        }

        public Node findPredecessor(Node root, Node prec, int key)
        {
            if (root == null) {
                return prec;
            }

            if (root.key == key)
            {
                if (root.left != null) {
                    return findMaximum(root.left);
                }
            }

            else if (key < root.key) {
                return findPredecessor(root.left, prec, key);
            } else {
                prec = root;
                return findPredecessor(root.right, prec, key);
            }
            return prec;
        }

        public Node findMinimum(Node root)
        {
            Node temp = root;
            while (temp.left != null) {
                temp = temp.left;
            }
            return temp;
        }

        public Node findSuccessor(Node root, Node succ, int key)
        {
            if (root == null) {
                return null;
            }

            if (root.key == key)
            {
                if (root.right != null) {
                    return findMinimum(root.right);
                }
            }

            else if (key < root.key)
            {
                succ = root;
                return findSuccessor(root.left, succ, key);
            }

            else {
                return findSuccessor(root.right, succ, key);
            }

            return succ;
        }
    }

    BinarySearchTree tree = new BinarySearchTree();
    private Button back;
    EditText insertedNode;
    TextView displayNode;
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
        setContentView(R.layout.activity_bst);

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
                displayNode = (TextView)findViewById(R.id.displayText);
                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(bst.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("A node must be inserted!");
                    else {
                        if(tree.containsNode(Integer.parseInt(insertedNode.getText().toString())) == false){
                            tree.insert(Integer.parseInt(insertedNode.getText().toString()));
                            Toast.makeText(bst.this, "Node " + insertedNode.getText().toString() + " was added!", Toast.LENGTH_SHORT).show();
                        } else Toast.makeText(bst.this, "Node " + insertedNode.getText().toString() + " already exists!", Toast.LENGTH_SHORT).show();
                        //displayNode.setText("Node " + insertedNode.getText().toString() + " was added!");
                        insertedNode.getText().clear();
                }
            }
        });

        bdN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                insertedNode = (EditText)findViewById(R.id.addNode);
                displayNode = (TextView)findViewById(R.id.displayText);
                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(bst.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("A node must be inserted!");
                else {
                    if(tree.root == null) Toast.makeText(bst.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                        else {
                            if(tree.containsNode(Integer.parseInt(insertedNode.getText().toString())) == false){
                                Toast.makeText(bst.this, "Node " + insertedNode.getText().toString() + " does not exist!", Toast.LENGTH_SHORT).show();
                            } else{
                                tree.delete(Integer.parseInt(insertedNode.getText().toString()));
                                Toast.makeText(bst.this, "Node " + insertedNode.getText().toString() + " was deleted!", Toast.LENGTH_SHORT).show();
                                //displayNode.setText("Node " + insertedNode.getText().toString() + " was deleted!");
                            }
                        }
                    insertedNode.getText().clear();
                }
            }
        });

        bfsN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                insertedNode = (EditText)findViewById(R.id.addNode);
                displayNode = (TextView)findViewById(R.id.displayText);
                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(bst.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("A node must be inserted!");
                else {
                    if (tree.root == null)
                        Toast.makeText(bst.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                    else if (tree.containsNode(Integer.parseInt(insertedNode.getText().toString())) == false) {
                        Toast.makeText(bst.this, "Node " + insertedNode.getText().toString() + " does not exist!", Toast.LENGTH_SHORT).show();
                    } else {
                        if(tree.findSuccessor(tree.root, null, Integer.parseInt(insertedNode.getText().toString())) == null)
                            displayNode.setText("Node " + insertedNode.getText().toString() + " does not have a successor!");
                        else displayNode.setText("The successor of " + insertedNode.getText().toString() + " is: " + tree.findSuccessor(tree.root, null, Integer.parseInt(insertedNode.getText().toString())).key + ".");
                    }

                }
                insertedNode.getText().clear();
            }
        });

        bfpN.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                insertedNode = (EditText) findViewById(R.id.addNode);
                displayNode = (TextView) findViewById(R.id.displayText);
                if (TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(bst.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("A node must be inserted!");
                else {
                    if (tree.root == null)
                        Toast.makeText(bst.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                    else if (tree.containsNode(Integer.parseInt(insertedNode.getText().toString())) == false) {
                        Toast.makeText(bst.this, "Node " + insertedNode.getText().toString() + " does not exist!", Toast.LENGTH_SHORT).show();
                    } else {
                        if(tree.findPredecessor(tree.root, null, Integer.parseInt(insertedNode.getText().toString())) == null)
                            displayNode.setText("Node " + insertedNode.getText().toString() + " does not have a predecessor!");
                        else displayNode.setText("The predecessor of " + insertedNode.getText().toString() + " is: " + tree.findPredecessor(tree.root, null, Integer.parseInt(insertedNode.getText().toString())).key + ".");
                    }

                }
                insertedNode.getText().clear();
            }
        });

        bminN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText);
                if(tree.root == null) Toast.makeText(bst.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else displayNode.setText("The minimum node is: " + tree.findSmallestValue(tree.root));
            }
        });

        bmaxN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText);
                if(tree.root == null) Toast.makeText(bst.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else displayNode.setText("The maximum node is: " + tree.findBiggestValue(tree.root));
            }
        });

        binord.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText);
                if(tree.root == null) Toast.makeText(bst.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    List<Integer> display = new ArrayList<Integer>();
                    display = tree.inorder(tree.root, display);
                    displayNode.setText("The inorder display of the tree is: " + display);
                    display.clear();
                }
            }
        });

        bpred.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText);
                if(tree.root == null) Toast.makeText(bst.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    List<Integer> display = new ArrayList<Integer>();
                    display = tree.preorder(tree.root, display);
                    displayNode.setText("The preorder display of the tree is: " + display);
                    display.clear();
                }
            }
        });

        bpostd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText);
                if(tree.root == null) Toast.makeText(bst.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    List<Integer> display = new ArrayList<Integer>();
                    display = tree.postorder(tree.root, display);
                    displayNode.setText("The postorder display of the tree is: " + display);
                    display.clear();
                }
            }
        });

        bh.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText);
                if(tree.root == null) Toast.makeText(bst.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else displayNode.setText("The height of the tree is: " + tree.maxDepth(tree.root));
            }
        });
    }

    public void openActivityMain(){
        Intent intent = new Intent(this, com.trees.MainActivity.class);
        startActivity(intent);
    }
}