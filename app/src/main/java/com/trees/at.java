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

public class at extends AppCompatActivity {

    class AVLTree {

        public AVLTree()
        {
            root = null;
        }
        /* Function to check if tree is empty */
        public boolean isEmpty()
        {
            return root == null;
        }

        class Node {
            int key, height;
            Node left, right;

            Node(int d) {
                key = d;
                height = 1;
            }
        }

        Node root;

        // A utility function to get height of the tree
        int height(Node N) {
            if (N == null)
                return 0;
            return N.height;
        }

        // A utility function to get maximum of two integers
        int max(int a, int b) {
            return (a > b) ? a : b;
        }

        // A utility function to right rotate subtree rooted with y
        // See the diagram given above.
        Node rightRotate(Node y) {
            Node x = y.left;
            Node T2 = x.right;

            // Perform rotation
            x.right = y;
            y.left = T2;

            // Update heights
            y.height = max(height(y.left), height(y.right)) + 1;
            x.height = max(height(x.left), height(x.right)) + 1;

            // Return new root
            return x;
        }

        // A utility function to left rotate subtree rooted with x
        // See the diagram given above.
        Node leftRotate(Node x) {
            Node y = x.right;
            Node T2 = y.left;

            // Perform rotation
            y.left = x;
            x.right = T2;

            // Update heights
            x.height = max(height(x.left), height(x.right)) + 1;
            y.height = max(height(y.left), height(y.right)) + 1;

            // Return new root
            return y;
        }

        // Get Balance factor of node N
        int getBalance(Node N) {
            if (N == null)
                return 0;
            return height(N.left) - height(N.right);
        }

        Node insert(Node node, int key) {

            /* 1.  Perform the normal BST insertion */
            if (node == null)
                return (new Node(key));

            if (key < node.key)
                node.left = insert(node.left, key);
            else if (key > node.key)
                node.right = insert(node.right, key);
            else // Duplicate keys not allowed
                return node;

            /* 2. Update height of this ancestor node */
            node.height = 1 + max(height(node.left),
                    height(node.right));

            /* 3. Get the balance factor of this ancestor
                  node to check whether this node became
                  unbalanced */
            int balance = getBalance(node);

            // If this node becomes unbalanced, then there
            // are 4 cases Left Left Case
            if (balance > 1 && key < node.left.key)
                return rightRotate(node);

            // Right Right Case
            if (balance < -1 && key > node.right.key)
                return leftRotate(node);

            // Left Right Case
            if (balance > 1 && key > node.left.key) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }

            // Right Left Case
            if (balance < -1 && key < node.right.key) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }

            /* return the (unchanged) node pointer */
            return node;
        }

        /* Given a non-empty binary search tree, return the
        node with minimum key value found in that tree.
        Note that the entire tree does not need to be
        searched. */
        Node minValueNode(Node node) {
            Node current = node;

            /* loop down to find the leftmost leaf */
            while (current.left != null)
                current = current.left;

            return current;
        }

        Node maxValueNode(Node node) {
            Node current = node;

            /* loop down to find the leftmost leaf */
            while (current.right != null)
                current = current.right;

            return current;
        }


        Node deleteNode(Node root, int key) {
            // STEP 1: PERFORM STANDARD BST DELETE
            if (root == null)
                return root;

            // If the key to be deleted is smaller than
            // the root's key, then it lies in left subtree
            if (key < root.key)
                root.left = deleteNode(root.left, key);

                // If the key to be deleted is greater than the
                // root's key, then it lies in right subtree
            else if (key > root.key)
                root.right = deleteNode(root.right, key);

                // if key is same as root's key, then this is the node
                // to be deleted
            else {

                // node with only one child or no child
                if ((root.left == null) || (root.right == null)) {
                    Node temp = null;
                    if (temp == root.left)
                        temp = root.right;
                    else
                        temp = root.left;

                    // No child case
                    if (temp == null) {
                        temp = root;
                        root = null;
                    } else // One child case
                        root = temp; // Copy the contents of
                    // the non-empty child
                } else {

                    // node with two children: Get the inorder
                    // successor (smallest in the right subtree)
                    Node temp = minValueNode(root.right);

                    // Copy the inorder successor's data to this node
                    root.key = temp.key;

                    // Delete the inorder successor
                    root.right = deleteNode(root.right, temp.key);
                }
            }

            // If the tree had only one node then return
            if (root == null)
                return root;

            // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
            root.height = max(height(root.left), height(root.right)) + 1;

            // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
            // this node became unbalanced)
            int balance = getBalance(root);

            // If this node becomes unbalanced, then there are 4 cases
            // Left Left Case
            if (balance > 1 && getBalance(root.left) >= 0)
                return rightRotate(root);

            // Left Right Case
            if (balance > 1 && getBalance(root.left) < 0) {
                root.left = leftRotate(root.left);
                return rightRotate(root);
            }

            // Right Right Case
            if (balance < -1 && getBalance(root.right) <= 0)
                return leftRotate(root);

            // Right Left Case
            if (balance < -1 && getBalance(root.right) > 0) {
                root.right = rightRotate(root.right);
                return leftRotate(root);
            }

            return root;
        }

        List<Integer> inorder(Node r, List<Integer> display)
        {
            if (r != null)
            {
                inorder(r.left, display);
                display.add(r.key);
                inorder(r.right,display);
            }
            return display;
        }

        List<Integer> preorder(Node r, List<Integer> display)
        {
            if (r != null)
            {
                display.add(r.key);
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
                display.add(r.key);
            }
            return display;
        }

        public boolean search(int val)
        {
            return search(root, val);
        }
        private boolean search(Node r, int val)
        {
            boolean found = false;
            while ((r != null) && !found)
            {
                int rval = r.key;
                if (val < rval)
                    r = r.left;
                else if (val > rval)
                    r = r.right;
                else
                {
                    found = true;
                    break;
                }
                found = search(r, val);
            }
            return found;
        }

        public Node findPredecessor(Node root, Node prec, int key)
        {
            if (root == null) {
                return prec;
            }

            if (root.key == key)
            {
                if (root.left != null) {
                    return maxValueNode(root.left);
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

        public Node findSuccessor(Node root, Node succ, int key)
        {
            if (root == null) {
                return null;
            }

            if (root.key == key)
            {
                if (root.right != null) {
                    return minValueNode(root.right);
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

    AVLTree tree = new AVLTree();
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
        setContentView(R.layout.activity_at);

        back = (Button) findViewById(R.id.back);
        baN = (Button) findViewById(R.id.Add);
        bdN = (Button) findViewById(R.id.Delete);
        bfpN = (Button) findViewById(R.id.Predecessor);
        bfsN = (Button) findViewById(R.id.Successor);
        bminN = (Button) findViewById(R.id.Min);
        bmaxN = (Button) findViewById(R.id.Max);
        binord = (Button) findViewById(R.id.Inorder);
        bpred = (Button) findViewById(R.id.Preorder);
        bpostd = (Button) findViewById(R.id.InorderBT);
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
                displayNode = (TextView)findViewById(R.id.displayText4);
                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(at.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("A node must be inserted!");
                else {
                    if(tree.search(Integer.parseInt(insertedNode.getText().toString())) == false){
                        tree.root = tree.insert(tree.root, Integer.parseInt(insertedNode.getText().toString()));
                        Toast.makeText(at.this, "Node " + insertedNode.getText().toString() + " was added!", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(at.this, "Node " + insertedNode.getText().toString() + " already exists!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("Node " + insertedNode.getText().toString() + " was added!");
                    insertedNode.getText().clear();
                }
            }
        });

        bdN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                insertedNode = (EditText)findViewById(R.id.addNode);
                displayNode = (TextView)findViewById(R.id.displayText4);
                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(at.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("A node must be inserted!");
                else {
                    if(tree.isEmpty()) Toast.makeText(at.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                    else {
                        if(tree.search(Integer.parseInt(insertedNode.getText().toString())) == false ){
                            Toast.makeText(at.this, "Node " + insertedNode.getText().toString() + " does not exist!", Toast.LENGTH_SHORT).show();
                        } else{
                            //tree.delete(Integer.parseInt(insertedNode.getText().toString()));
                            Toast.makeText(at.this, "Node " + insertedNode.getText().toString() + " was deleted!", Toast.LENGTH_SHORT).show();
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
                displayNode = (TextView)findViewById(R.id.displayText4);
                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(at.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("A node must be inserted!");
                else if (tree.root == null)
                    Toast.makeText(at.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else if (tree.search(Integer.parseInt(insertedNode.getText().toString())) == false) {
                    Toast.makeText(at.this, "Node " + insertedNode.getText().toString() + " does not exist!", Toast.LENGTH_SHORT).show();
                } else {
                    if(tree.findSuccessor(tree.root, null, Integer.parseInt(insertedNode.getText().toString())) == null)
                        displayNode.setText("Node " + insertedNode.getText().toString() + " does not have a successor!");
                    else displayNode.setText("The successor of " + insertedNode.getText().toString() + " is: " + tree.findSuccessor(tree.root, null, Integer.parseInt(insertedNode.getText().toString())).key + ".");
                }
                insertedNode.getText().clear();
            }
        });

        bfpN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                insertedNode = (EditText)findViewById(R.id.addNode);
                displayNode = (TextView)findViewById(R.id.displayText4);

                if(TextUtils.isEmpty(insertedNode.getText().toString()))
                    Toast.makeText(at.this, "A node must be inserted!", Toast.LENGTH_SHORT).show();
                    //displayNode.setText("A node must be inserted!");
                else {
                    if (tree.root == null)
                        Toast.makeText(at.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                    else if (tree.search(Integer.parseInt(insertedNode.getText().toString())) == false) {
                        Toast.makeText(at.this, "Node " + insertedNode.getText().toString() + " does not exist!", Toast.LENGTH_SHORT).show();
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
                displayNode = (TextView)findViewById(R.id.displayText4);
                if(tree.root == null) Toast.makeText(at.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else displayNode.setText("The minimum node is: " + tree.minValueNode(tree.root).key + ".");

            }

        });

        bmaxN.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText4);
                if(tree.root == null) Toast.makeText(at.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else displayNode.setText("The maximum node is: " + tree.maxValueNode(tree.root).key + ".");
            }
        });

        bpred.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText4);
                if(tree.root == null) Toast.makeText(at.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    List<Integer> display = new ArrayList<Integer>();
                    display = tree.preorder(tree.root, display);
                    displayNode.setText("The preorder of the tree is: " + display);
                    display.clear();
                }
            }
        });

        binord.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText4);

                if(tree.root == null) Toast.makeText(at.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    List<Integer> display = new ArrayList<Integer>();
                    display = tree.inorder(tree.root, display);
                    displayNode.setText("The inorder of the tree is: " + display);
                    display.clear();
                }
            }
        });

        bpostd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText4);

                if(tree.root == null) Toast.makeText(at.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    List<Integer> display = new ArrayList<Integer>();
                    display = tree.postorder(tree.root, display);
                    displayNode.setText("The postorder of the tree is: " + display);
                    display.clear();
                }
            }
        });

        bh.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                displayNode = (TextView)findViewById(R.id.displayText4);

                if(tree.root == null) Toast.makeText(at.this, "The tree is empty!", Toast.LENGTH_SHORT).show();
                else {
                    displayNode.setText("The height of the tree is: " + tree.height(tree.root));
                }
            }
        });
    }

    public void openActivityMain(){
        Intent intent = new Intent(this, com.trees.MainActivity.class);
        startActivity(intent);
    }
}