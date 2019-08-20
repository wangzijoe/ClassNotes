package com.example.tree;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class SortBinaryTree {

    /**
     * 树每一个节点
     */
    @Data
    class Node {
        private int val;
        private Node left;
        private Node right;

        Node(int val) {
            this.val = val;
        }

        private boolean hasRight() {
            return null != this.right;
        }

        private boolean hasLeft() {
            return null != this.left;
        }

        private boolean greatThan(Node node) {
            return this.val > node.getVal();
        }
    }

    // 树的根节点
    private Node root;

    //新节点与当前节点比较，进行添加
    private void add(Node current, Node node) {
        if (node.greatThan(current)) {
            if (current.hasRight()) {
                current = current.getRight();
                add(current, node);
            } else {
                current.setRight(node);
            }
        } else {
            if (current.hasLeft()) {
                current = current.getLeft();
                add(current, node);
            } else {
                current.setLeft(node);
            }
        }
    }

    // 创建树，遍历数组
    private void createTree(int[] arr) {
        if (arr.length == 0) throw new AssertionError();
        this.root = new Node(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            add(this.root, new Node(arr[i]));
        }
    }

    public static void main(String[] args) {
        SortBinaryTree sortBinaryTree = new SortBinaryTree();
        int[] arr = new int[]{6, 4, 5, 1, 3, 2, 9, 0, 8, 7};
        sortBinaryTree.createTree(arr);
        log.info(JSON.toJSONString(sortBinaryTree.getRoot()));
        sortBinaryTree.inorderTraversal(sortBinaryTree.getRoot());
    }

    /**
     * 中序遍历
     */
    private void inorderTraversal(Node node) {
        if (null != node) {
            inorderTraversal(node.getLeft());
            log.info(JSON.toJSONString(node));
            inorderTraversal(node.getRight());
        }
    }

}
