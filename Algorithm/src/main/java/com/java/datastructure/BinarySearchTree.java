package com.java.datastructure;

/**
 * 二叉搜索树
 * <p>
 * 左子树的所有节点的值均小于它的根节点的值，右子树的值均大于根节点的值
 * <p>
 * 遍历二叉树
 * 中序遍历：左子树-->根节点-->右子树
 * 前序遍历：根节点-->左子树——>右子树
 * 后序遍历：左子树-->右子树-->根节点
 *
 */
public class BinarySearchTree implements Tree {
    TreeNode root;

    @Override
    public TreeNode find(int key) {
        TreeNode current = root;
        while (current != null) {
            if (current.data > key) {
                current = current.left;
            } else if (current.data < key) {
                current = current.right;
            } else {
                return current;
            }
        }
        return null;
    }

    @Override
    public boolean insert(int key) {
        TreeNode treeNode = new TreeNode(key);
        if (root == null) {
            root = treeNode;
            return true;
        } else {
            TreeNode current = root;
            TreeNode parentNode = null;
            while (current != null) {
                parentNode = current;
                if (current.data > key) {
                    current = current.left;
                    if (current == null) {
                        parentNode.left = treeNode;
                        return true;
                    }
                } else {
                    current = current.right;
                    if (current == null) {
                        parentNode.right = treeNode;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean delete(int key) {
        TreeNode current = root;
        TreeNode parent = root;
        boolean isLeftChild = false;
        while (current.data != key) {//查找要删除的节点
            parent = current;
            if (current.data > key) {
                current = current.left;
                isLeftChild = true;
            } else {
                current = current.right;
                isLeftChild = false;
            }
            if (current == null) {
                return false;
            }
        }
        if (current.left == null && current.right == null) {//当前节点没有子节点
            if (current == root) {
                root = null;
            } else if (isLeftChild) {
                parent.left = null;
            } else {
                parent.right = null;
            }
            return true;
        }
        if (current.left == null && current.right != null) {
            if (current == root) {
                root = current.right;
            } else if (isLeftChild) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
            return true;
        } else if (current.left != null && current.right == null) {
            if (current == root) {
                root = current.left;
            } else if (isLeftChild) {
                parent.left = current.left;
            } else {
                parent.right = current.left;
            }
            return true;
        }
        if (current.left != null && current.right != null) {
            TreeNode successor = getSuccessor(current);
            if (current == root) {
                root = successor;
            } else if (isLeftChild) {
                parent.left = successor;
            } else {
                parent.right = successor;
            }
            successor.left = current.left;
        }
        return false;
    }

    TreeNode getSuccessor(TreeNode delNode) {
        TreeNode successorParent = delNode;
        TreeNode successor = delNode;
        TreeNode current = delNode.right;
        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.left;
        }

        if (successor != delNode.right) {
            successorParent.left = successor.right;
            successor.right = delNode.right;
        }

        return successor;
    }

    class TreeNode {
        int data;
        TreeNode left;
        TreeNode right;

        public TreeNode(int data) {
            this.data = data;
        }
    }
}

interface Tree {
    /**
     * 查找操作
     *
     * @param key
     * @return
     */
    BinarySearchTree.TreeNode find(int key);

    /**
     * 插入操作
     *
     * @param key
     * @return
     */
    boolean insert(int key);

    /**
     * 删除操作
     *
     * @param key
     * @return
     */
    boolean delete(int key);
}
