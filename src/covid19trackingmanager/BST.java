package covid19trackingmanager;

public class BST {
    
    //Node containing data and ability to go left and right
    class Node {
        int data;
        Node left;
        Node right;
        
        public Node(int data) {
            this.data = data;
            left = null;
            right = null;
            
        }
        
    }
    
    //the beginning node of BST
    private Node root;
    
    //Constructor for an empty tree
    public BST() {
        root = null;
    }
    
    //make empty
    public void makeEmpty() {
        root = null;
    }
    
    //insertHelper is a helper method to insert
    public Node insertHelper(int data, Node root) {
        //if it is empty, simply make the root the new data and return it
        if (root == null) {
            root = new Node(data);
            return root;
        }
        //checking if it is to the left
        else if (data < root.data) {
            root.left = insertHelper(data, root.left);
        }
        //or to the right
        else {
            root.right = insertHelper(data, root.right);
        }
        
        return root;
    }
    
    //Insert to a tree using the helper insertRecursion method
    public void insert(int data) {
        root = insertHelper(data, root);
    }
    
    //Finding the minimum value
    public int findMinValue(Node root) {
        Node current = root;
        
        while (current.left != null) {
            current = current.left;
        }
        return current.data;
    }
    
    //Finding the maximum value
    public int findMaxValue(Node root) {
        Node current = root;
        
        while (current.right != null) {
            current = current.right;
        }
        return current.data;
    }
    
    //helper method to delete
    public Node deleteHelper(int data, Node root) {
        
        if (root == null) {
            return root;
        }
        
        else if (data > root.data) {
            root.right = deleteHelper(data, root.right);
        }
        else if (data < root.data) {
            root.left = deleteHelper(data, root.left);
        }
        
        else {
            if (root.right == null) {
                return root.left;
            }
            else if (root.left == null) {
                return root.right;
            }
            
            root.data = findMinValue(root.right);
            root.right = deleteHelper(root.data, root.right);
        }
        
        return root;
    }
    
    //delete method
    public void delete(int data) {
        root = deleteHelper(data, root);
    }
    
    //finding method
    public boolean find(Node root, int data) {
        boolean found = false;
        
        if (root == null) {
            found = false;
        }
        //checking if it is to the left
        else if (data < root.data) {
            root.left = insertHelper(data, root.left);
        }
        //or to the right
        else if (data > root.data) {
            root.right = insertHelper(data, root.right);
        }
        
        else {
            found = true;
        }
        return found;
    }
}
