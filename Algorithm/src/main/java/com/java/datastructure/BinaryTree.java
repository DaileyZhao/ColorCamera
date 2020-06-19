package com.java.datastructure;

/**
 * 二叉树
 */
public class BinaryTree {
    public class TreeNode<T> {
        T data;
        TreeNode<T> left;
        TreeNode<T> right;

        public TreeNode(T data) {
            this.data = data;
        }
    }

    /**
     * 反转二叉树
     * @param root
     * @return
     */
    public TreeNode reverseTree(TreeNode root) {
        if (root == null)
            return null;
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        reverseTree(root.left);
        reverseTree(root.right);
        return root;
    }
}
