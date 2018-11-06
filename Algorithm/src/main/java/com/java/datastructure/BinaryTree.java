package com.java.datastructure;

public class BinaryTree {
    public class TreeNode<T>{
        T data;
        TreeNode<T> left;
        TreeNode<T> right;
        public TreeNode(T data){
            this.data=data;
        }
    }

    public TreeNode invertTree(TreeNode root){
        if (root==null)
            return null;
        TreeNode temp=root.left;
        root.left=root.right;
        root.right=temp;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }
}
