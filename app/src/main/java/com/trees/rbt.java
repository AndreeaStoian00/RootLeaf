package com.trees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class rbt extends AppCompatActivity {

    public class RedBlackTree {
        class Node {
            int data; // holds the key
            Node parent; // pointer to the parent
            Node left; // pointer to left child
            Node right; // pointer to right child
            int color; // 1 . Red, 0 . Black

        }

        private Node root;
        private Node TNULL;

        private List<String> preorder(Node node, List<String> display) {
            if (node != TNULL) {
                display.add(String.valueOf(node.data) + (node.color == 0? "B" : "R"));
                preorder(node.left, display);
                preorder(node.right, display);
            }
            return display;
        }

        private List<String> inorder(Node node, List<String> display) {
            if (node != TNULL) {
                inorder(node.left, display);
                display.add(String.valueOf(node.data) + (node.color == 0? "B" : "R"));
                inorder(node.right, display);
            }
            return display;
        }

        private List<String > postorder(Node node, List<String> display) {
            if (node != TNULL) {
                postorder(node.left, display);
                postorder(node.right, display);
                display.add(String.valueOf(node.data) + (node.color == 0? "B" : "R"));
            }
            return display;
        }

        private Node searchTreeHelper(Node node, int key) {
            if (node == TNULL || key == node.data) {
                return node;
            }

            if (key < node.data) {
                return searchTreeHelper(node.left, key);
            }
            return searchTreeHelper(node.right, key);
        }

        // fix the rb tree modified by the delete operation
        private void fixDelete(Node x) {
            Node s;
            while (x != root && x.color == 0) {
                if (x == x.parent.left) {
                    s = x.parent.right;
                    if (s.color == 1) {
                        // case 3.1
                        s.color = 0;
                        x.parent.color = 1;
                        leftRotate(x.parent);
                        s = x.parent.right;
                    }

                    if (s.left.color == 0 && s.right.color == 0) {
                        // case 3.2
                        s.color = 1;
                        x = x.parent;
                    } else {
                        if (s.right.color == 0) {
                            // case 3.3
                            s.left.color = 0;
                            s.color = 1;
                            rightRotate(s);
                            s = x.parent.right;
                        }

                        // case 3.4
                        s.color = x.parent.color;
                        x.parent.color = 0;
                        s.right.color = 0;
                        leftRotate(x.parent);
                        x = root;
                    }
                } else {
                    s = x.parent.left;
                    if (s.color == 1) {
                        // case 3.1
                        s.color = 0;
                        x.parent.color = 1;
                        rightRotate(x.parent);
                        s = x.parent.left;
                    }

                    if (s.right.color == 0 && s.right.color == 0) {
                        // case 3.2
                        s.color = 1;
                        x = x.parent;
                    } else {
                        if (s.left.color == 0) {
                            // case 3.3
                            s.right.color = 0;
                            s.color = 1;
                            leftRotate(s);
                            s = x.parent.left;
                        }

                        // case 3.4
                        s.color = x.parent.color;
                        x.parent.color = 0;
                        s.left.color = 0;
                        rightRotate(x.parent);
                        x = root;
                    }
                }
            }
            x.color = 0;
        }


        private void rbTransplant(Node u, Node v){
            if (u.parent == null) {
                root = v;
            } else if (u == u.parent.left){
                u.parent.left = v;
            } else {
                u.parent.right = v;
            }
            v.parent = u.parent;
        }

        private void deleteNodeHelper(Node node, int key) {
            // find the node containing key
            Node z = TNULL;
            Node x, y;
            while (node != TNULL){
                if (node.data == key) {
                    z = node;
                }

                if (node.data <= key) {
                    node = node.right;
                } else {
                    node = node.left;
                }
            }

            if (z == TNULL) {
                System.out.println("Couldn't find key in the tree");
                return;
            }

            y = z;
            int yOriginalColor = y.color;
            if (z.left == TNULL) {
                x = z.right;
                rbTransplant(z, z.right);
            } else if (z.right == TNULL) {
                x = z.left;
                rbTransplant(z, z.left);
            } else {
                y = minimum(z.right);
                yOriginalColor = y.color;
                x = y.right;
                if (y.parent == z) {
                    x.parent = y;
                } else {
                    rbTransplant(y, y.right);
                    y.right = z.right;
                    y.right.parent = y;
                }

                rbTransplant(z, y);
                y.left = z.left;
                y.left.parent = y;
                y.color = z.color;
            }
            if (yOriginalColor == 0){
                fixDelete(x);
            }
        }

        // fix the red-black tree
        private void fixInsert(Node k){
            Node u;
            while (k.parent.color == 1) {
                if (k.parent == k.parent.parent.right) {
                    u = k.parent.parent.left; // uncle
                    if (u.color == 1) {
                        // case 3.1
                        u.color = 0;
                        k.parent.color = 0;
                        k.parent.parent.color = 1;
                        k = k.parent.parent;
                    } else {
                        if (k == k.parent.left) {
                            // case 3.2.2
                            k = k.parent;
                            rightRotate(k);
                        }
                        // case 3.2.1
                        k.parent.color = 0;
                        k.parent.parent.color = 1;
                        leftRotate(k.parent.parent);
                    }
                } else {
                    u = k.parent.parent.right; // uncle

                    if (u.color == 1) {
                        // mirror case 3.1
                        u.color = 0;
                        k.parent.color = 0;
                        k.parent.parent.color = 1;
                        k = k.parent.parent;
                    } else {
                        if (k == k.parent.right) {
                            // mirror case 3.2.2
                            k = k.parent;
                            leftRotate(k);
                        }
                        // mirror case 3.2.1
                        k.parent.color = 0;
                        k.parent.parent.color = 1;
                        rightRotate(k.parent.parent);
                    }
                }
                if (k == root) {
                    break;
                }
            }
            root.color = 0;
        }

        public RedBlackTree() {
            TNULL = new Node();
            TNULL.color = 0;
            TNULL.left = null;
            TNULL.right = null;
            root = TNULL;
        }

        // search the tree for the key k
        // and return the corresponding node
        public Node searchTree(int k) {
            return searchTreeHelper(this.root, k);
        }

        // find the node with the minimum key
        public Node minimum(Node node) {
            while (node.left != TNULL) {
                node = node.left;
            }
            return node;
        }

        // find the node with the maximum key
        public Node maximum(Node node) {
            while (node.right != TNULL) {
                node = node.right;
            }
            return node;
        }

        // find the successor of a given node
        public Node successor(Node x) {
            // if the right subtree is not null,
            // the successor is the leftmost node in the
            // right subtree
            if (x.right != TNULL) {
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
            if (x.left != TNULL) {
                return maximum(x.left);
            }

            Node y = x.parent;
            while (y != null && x == y.left) {
                x = y;
                y = y.parent;
            }

            return y;
        }

        // rotate left at node x
        public void leftRotate(Node x) {
            Node y = x.right;
            x.right = y.left;
            if (y.left != TNULL) {
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
        public void rightRotate(Node x) {
            Node y = x.left;
            x.left = y.right;
            if (y.right != TNULL) {
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

        // insert the key to the tree in its appropriate position
        // and fix the tree
        public void insert(int key) {
            // Ordinary Binary Search Insertion
            Node node = new Node();
            node.parent = null;
            node.data = key;
            node.left = TNULL;
            node.right = TNULL;
            node.color = 1; // new node must be red

            Node y = null;
            Node x = this.root;

            while (x != TNULL) {
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

            // if new node is a root node, simply return
            if (node.parent == null){
                node.color = 0;
                return;
            }

            // if the grandparent is null, simply return
            if (node.parent.parent == null) {
                return;
            }

            // Fix the tree
            fixInsert(node);
        }

        public Node getRoot(){
            return this.root;
        }

        // delete the node from the tree
        public void deleteNode(int data) {
            deleteNodeHelper(this.root, data);
        }

        public int height(Node node){
            if(node == null)
            return -1;
            else {
                int l = height(node.left);
                int r = height(node.right);
                return (l < r ? r + 1 : l + 1);
            }
        }

        public int blackHeight(Node node){
            if(node == null)
                return 0;
            int n = blackHeight(node.left);
            return(node.color == 1? n : n+1);

        }

    }

    RedBlackTree tree = new RedBlackTree();
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
    private Button bbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_rbt);

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
        bbh = (Button) findViewById(R.id.blackH);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityMain();
            }
        });
        baN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                insertedNode = (EditText)findViewById(R.id.addNode);
                displayNode = (TextView)findViewById(R.id.displayText2);
                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(rbt.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("A node must be inserted!");
                else {
                    if(tree.searchTree(Integer.parseInt(insertedNode.getText().toString())) != null){
                        tree.insert(Integer.parseInt(insertedNode.getText().toString()));
                        Toast.makeText(rbt.this, "Node " + insertedNode.getText().toString() + " was added!", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(rbt.this, "Node " + insertedNode.getText().toString() + " already exists!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("Node " + insertedNode.getText().toString() + " was added!");
                    insertedNode.getText().clear();
                }
            }
        });

        bdN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                insertedNode = (EditText)findViewById(R.id.addNode);
                displayNode = (TextView)findViewById(R.id.displayText2);
                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(rbt.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("A node must be inserted!");
                else {
                    if(tree.root == tree.TNULL) Toast.makeText(rbt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                    else {
                        if(tree.searchTree(Integer.parseInt(insertedNode.getText().toString())) == null ){
                            Toast.makeText(rbt.this, "Node " + insertedNode.getText().toString() + " does not exist!", Toast.LENGTH_SHORT).show();
                        } else{
                            tree.deleteNode(Integer.parseInt(insertedNode.getText().toString()));
                            Toast.makeText(rbt.this, "Node " + insertedNode.getText().toString() + " was deleted!", Toast.LENGTH_SHORT).show();
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
                displayNode = (TextView)findViewById(R.id.displayText2);
                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(rbt.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("A node must be inserted!");
                else {
                    if (tree.root == tree.TNULL)
                        Toast.makeText(rbt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                    else if (tree.searchTree(Integer.parseInt(insertedNode.getText().toString())) == tree.TNULL) {
                        Toast.makeText(rbt.this, "Node " + insertedNode.getText().toString() + " does not exist!", Toast.LENGTH_SHORT).show();
                    } else {
                        if(tree.successor(tree.searchTree(Integer.parseInt(insertedNode.getText().toString()))) == null)
                            displayNode.setText("Node " + insertedNode.getText().toString() + " does not have a successor!");
                        else displayNode.setText("The successor of " + insertedNode.getText().toString() + " is: " + tree.successor(tree.searchTree(Integer.parseInt(insertedNode.getText().toString()))).data +
                                (tree.successor(tree.searchTree(Integer.parseInt(insertedNode.getText().toString()))).color == 0? "B" : "R") + ".");
                    }

                }
                insertedNode.getText().clear();
            }
        });

        bfpN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                insertedNode = (EditText)findViewById(R.id.addNode);
                displayNode = (TextView)findViewById(R.id.displayText2);

                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(rbt.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("A node must be inserted!");
                else {
                    if (tree.root == tree.TNULL)
                        Toast.makeText(rbt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                    else if (tree.searchTree(Integer.parseInt(insertedNode.getText().toString())) == tree.TNULL) {
                        Toast.makeText(rbt.this, "Node " + insertedNode.getText().toString() + " does not exist!", Toast.LENGTH_SHORT).show();
                    } else {
                        if(tree.predecessor(tree.searchTree(Integer.parseInt(insertedNode.getText().toString()))) == null)
                            displayNode.setText("Node " + insertedNode.getText().toString() + " does not have a predecessor!");
                        else displayNode.setText("The predecessor of " + insertedNode.getText().toString() + " is: " + tree.predecessor(tree.searchTree(Integer.parseInt(insertedNode.getText().toString()))).data +
                                (tree.predecessor(tree.searchTree(Integer.parseInt(insertedNode.getText().toString()))).color == 0? "B" : "R") + ".");
                    }

                }
                insertedNode.getText().clear();
            }
        });

        bminN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText2);
                if(tree.root == tree.TNULL) Toast.makeText(rbt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else displayNode.setText("The minimum node is: " + tree.minimum(tree.root).data + (tree.minimum(tree.root).color == 0? "B" : "R" ));
            }
        });

        bmaxN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText2);
                if(tree.root == tree.TNULL) Toast.makeText(rbt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else displayNode.setText("The maximum node is: " + tree.maximum(tree.root).data + (tree.maximum(tree.root).color == 0? "B" : "R" ));
            }
        });

        bpred.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText2);
                if(tree.root == tree.TNULL) Toast.makeText(rbt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    List<String> display = new ArrayList<String>();
                    display = tree.preorder(tree.root, display);
                    displayNode.setText("The preorder display of the tree is: " + display);
                    display.clear();
                }
            }
        });

        binord.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText2);
                if(tree.root == tree.TNULL) Toast.makeText(rbt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    List<String> display = new ArrayList<String>();
                    display = tree.inorder(tree.root, display);
                    displayNode.setText("The inorder display of the tree is: " + display);
                    display.clear();
                }
            }
        });

        bpostd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText2);
                if(tree.root == tree.TNULL) Toast.makeText(rbt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    List<String> display = new ArrayList<String>();
                    display = tree.postorder(tree.root, display);
                    displayNode.setText("The postorder display of the tree is: " + display);
                    display.clear();
                }
            }
        });

        bh.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText2);
                if(tree.root == tree.TNULL) Toast.makeText(rbt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    displayNode.setText("The height of the tree is: " + tree.height(tree.root));
                }
            }
        });

        bbh.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText2);
                if(tree.root == tree.TNULL) Toast.makeText(rbt.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    displayNode.setText("The black height of the tree is: " + tree.blackHeight(tree.root.left));
                }
            }
        });


    }

    public void openActivityMain(){
        Intent intent = new Intent(this, com.trees.MainActivity.class);
        startActivity(intent);
    }
}