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

public class st extends AppCompatActivity {

    public class SplayTree {

        class Node {
            int data; // holds the key
            Node parent; // pointer to the parent
            Node left; // pointer to left child
            Node right; // pointer to right child

            public Node(int data) {
                this.data = data;
                this.parent = null;
                this.left = null;
                this.right = null;
            }
        }

        private Node root;

        public SplayTree() {
            root = null;
        }

        private Node searchTreeHelper(Node node, int key) {
            if (node == null || key == node.data) {
                return node;
            }

            if (key < node.data) {
                return searchTreeHelper(node.left, key);
            }
            return searchTreeHelper(node.right, key);
        }

        private void deleteNodeHelper(Node node, int key) {
            Node x = null;
            Node t = null;
            Node s = null;
            while (node != null) {
                if (node.data == key) {
                    x = node;
                }

                if (node.data <= key) {
                    node = node.right;
                } else {
                    node = node.left;
                }
            }

            if (x == null) {
                System.out.println("Couldn't find key in the tree");
                return;
            }
            // split operation
            splay(x);
            if (x.right != null) {
                t = x.right;
                t.parent = null;
            } else {
                t = null;
            }
            s = x;
            s.right = null;
            x = null;

            // join operation
            if (s.left != null) { // remove x
                s.left.parent = null;
            }
            root = join(s.left, t);
            s = null;
        }

        // rotate left at node x
        private void leftRotate(Node x) {
            Node y = x.right;
            x.right = y.left;
            if (y.left != null) {
                y.left.parent = x;
            }
            y.parent = x.parent;
            if (x.parent == null) {
                this.root = y;
            } else if (x == x.parent.left) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
            y.left = x;
            x.parent = y;
        }

        // rotate right at node x
        private void rightRotate(Node x) {
            Node y = x.left;
            x.left = y.right;
            if (y.right != null) {
                y.right.parent = x;
            }
            y.parent = x.parent;
            if (x.parent == null) {
                this.root = y;
            } else if (x == x.parent.right) {
                x.parent.right = y;
            } else {
                x.parent.left = y;
            }
            y.right = x;
            x.parent = y;
        }

        // Splaying operation. It moves x to the root of the tree
        private void splay(Node x) {
            while (x.parent != null) {
                if (x.parent.parent == null) {
                    if (x == x.parent.left) {
                        // zig rotation
                        rightRotate(x.parent);
                    } else {
                        // zag rotation
                        leftRotate(x.parent);
                    }
                } else if (x == x.parent.left && x.parent == x.parent.parent.left) {
                    // zig-zig rotation
                    rightRotate(x.parent.parent);
                    rightRotate(x.parent);
                } else if (x == x.parent.right && x.parent == x.parent.parent.right) {
                    // zag-zag rotation
                    leftRotate(x.parent.parent);
                    leftRotate(x.parent);
                } else if (x == x.parent.right && x.parent == x.parent.parent.left) {
                    // zig-zag rotation
                    leftRotate(x.parent);
                    rightRotate(x.parent);
                } else {
                    // zag-zig rotation
                    rightRotate(x.parent);
                    leftRotate(x.parent);
                }
            }
        }

        // joins two trees s and t
        private Node join(Node s, Node t) {
            if (s == null) {
                return t;
            }

            if (t == null) {
                return s;
            }
            Node x = maximum(s);
            splay(x);
            x.right = t;
            t.parent = x;
            return x;
        }

        List<Integer> inorder(Node r, List<Integer> display)
        {
            if (r != null)
            {
                inorder(r.left, display);
                display.add(r.data);
                inorder(r.right,display);
            }
            return display;
        }

        List<Integer> preorder(Node r, List<Integer> display)
        {
            if (r != null)
            {
                display.add(r.data);
                preorder(r.left, display);
                preorder(r.right,display);
            }
            return display;
        }

        List<Integer> postorder(Node r, List<Integer> display)
        {
            if (r != null)
            {
                postorder(r.left, display);
                postorder(r.right,display);
                display.add(r.data);
            }
            return display;
        }

        // search the tree for the key k
        // and return the corresponding node
        public Node searchTree(int k) {
            Node x = searchTreeHelper(root, k);
            if (x != null) {
                splay(x);
            }
            return x;
        }

        // find the node with the minimum key
        public Node minimum(Node node) {
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }

        // find the node with the maximum key
        public Node maximum(Node node) {
            while (node.right != null) {
                node = node.right;
            }
            return node;
        }

        // find the successor of a given node
        public Node successor(Node x) {
            // if the right subtree is not null,
            // the successor is the leftmost node in the
            // right subtree
            if (x.right != null) {
                return minimum(x.right);
            }

            // else it is the lowest ancestor of x whose
            // left child is also an ancestor of x.
            Node y = x.parent;
            while (y != null && x == y.right) {
                x = y;
                y = y.parent;
            }
            return y;
        }

        // find the predecessor of a given node
        public Node predecessor(Node x) {
            // if the left subtree is not null,
            // the predecessor is the rightmost node in the
            // left subtree
            if (x.left != null) {
                return maximum(x.left);
            }

            Node y = x.parent;
            while (y != null && x == y.left) {
                x = y;
                y = y.parent;
            }

            return y;
        }

        // insert the key to the tree in its appropriate position
        public void insert(int key) {
            Node node = new Node(key);
            Node y = null;
            Node x = this.root;

            while (x != null) {
                y = x;
                if (node.data < x.data) {
                    x = x.left;
                } else {
                    x = x.right;
                }
            }

            // y is parent of x
            node.parent = y;
            if (y == null) {
                root = node;
            } else if (node.data < y.data) {
                y.left = node;
            } else {
                y.right = node;
            }

            // splay node
            splay(node);
        }

        // delete the node from the tree
        void deleteNode(int data) {
            deleteNodeHelper(this.root, data);
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

    }

    SplayTree tree = new SplayTree();
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
        setContentView(R.layout.activity_st);

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
                displayNode = (TextView)findViewById(R.id.displayText5);
                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(st.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("A node must be inserted!");
                else {
                    if(tree.searchTree(Integer.parseInt(insertedNode.getText().toString())) == null){
                        tree.insert(Integer.parseInt(insertedNode.getText().toString()));
                        Toast.makeText(st.this, "Node " + insertedNode.getText().toString() + " was added!", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(st.this, "Node " + insertedNode.getText().toString() + " already exists!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("Node " + insertedNode.getText().toString() + " was added!");
                    insertedNode.getText().clear();
                }
            }
        });

        bdN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                insertedNode = (EditText)findViewById(R.id.addNode);
                displayNode = (TextView)findViewById(R.id.displayText5);
                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(st.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("A node must be inserted!");
                else {
                    if(tree.root == null) Toast.makeText(st.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                    else {
                        if(tree.searchTree(Integer.parseInt(insertedNode.getText().toString())) == null ){
                            Toast.makeText(st.this, "Node " + insertedNode.getText().toString() + " does not exist!", Toast.LENGTH_SHORT).show();
                        } else{
                            tree.deleteNode(Integer.parseInt(insertedNode.getText().toString()));
                            Toast.makeText(st.this, "Node " + insertedNode.getText().toString() + " was deleted!", Toast.LENGTH_SHORT).show();
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
                displayNode = (TextView)findViewById(R.id.displayText5);
                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(st.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("A node must be inserted!");
                else {
                    if (tree.root == null)
                        Toast.makeText(st.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                    else if (tree.searchTree(Integer.parseInt(insertedNode.getText().toString())) == null) {
                        Toast.makeText(st.this, "Node " + insertedNode.getText().toString() + " does not exist!", Toast.LENGTH_SHORT).show();
                    } else {
                        if(tree.successor(tree.searchTree(Integer.parseInt(insertedNode.getText().toString()))) == null)
                            displayNode.setText("Node " + insertedNode.getText().toString() + " does not have a successor!");
                        else displayNode.setText("The successor of " + insertedNode.getText().toString() + " is: " + tree.successor(tree.searchTree(Integer.parseInt(insertedNode.getText().toString()))).data + ".");
                    }

                }
                insertedNode.getText().clear();
            }
        });

        bfpN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                insertedNode = (EditText)findViewById(R.id.addNode);
                displayNode = (TextView)findViewById(R.id.displayText5);
                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(st.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("A node must be inserted!");
                else {
                    if (tree.root == null)
                        Toast.makeText(st.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                    else if (tree.searchTree(Integer.parseInt(insertedNode.getText().toString())) == null) {
                        Toast.makeText(st.this, "Node " + insertedNode.getText().toString() + " does not exist!", Toast.LENGTH_SHORT).show();
                    } else {
                        if(tree.predecessor(tree.searchTree(Integer.parseInt(insertedNode.getText().toString()))) == null)
                            displayNode.setText("Node " + insertedNode.getText().toString() + " does not have a predecessor!");
                        else displayNode.setText("The predecessor of " + insertedNode.getText().toString() + " is: " + tree.predecessor(tree.searchTree(Integer.parseInt(insertedNode.getText().toString()))).data + ".");
                    }
                }
                insertedNode.getText().clear();
            }
        });

        bminN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText5);
                if(tree.root == null) Toast.makeText(st.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else displayNode.setText("The minimum node is: " + tree.minimum(tree.root).data + ".");
            }

        });

        bmaxN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText5);
                if(tree.root == null) Toast.makeText(st.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else displayNode.setText("The maximum node is: " + tree.maximum(tree.root).data + ".");
            }
        });

        bpred.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText5);
                if(tree.root == null) Toast.makeText(st.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    List<Integer> display = new ArrayList<Integer>();
                    display = tree.preorder(tree.root, display);
                    displayNode.setText("The preorder displayNode of the tree is: " + display);
                    display.clear();
                }
            }
        });

        binord.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText5);
                if(tree.root == null) Toast.makeText(st.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    List<Integer> display = new ArrayList<Integer>();
                    display = tree.inorder(tree.root, display);
                    displayNode.setText("The inorder displayNode of the tree is: " + display);
                    display.clear();
                }
            }
        });

        bpostd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText5);
                if(tree.root == null) Toast.makeText(st.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    List<Integer> display = new ArrayList<Integer>();
                    display = tree.postorder(tree.root, display);
                    displayNode.setText("The postorder displayNode of the tree is: " + display);
                    display.clear();
                }
            }
        });

        bh.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText5);
                if(tree.root == null) Toast.makeText(st.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    displayNode.setText("The height of the tree is: " + tree.maxDepth(tree.root));
                }
            }
        });
    }

    public void openActivityMain(){
        Intent intent = new Intent(this, com.trees.MainActivity.class);
        startActivity(intent);
    }
}