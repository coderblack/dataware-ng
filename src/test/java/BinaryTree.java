import jdk.nashorn.internal.ir.CallNode;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {
    private Node root;

    public void add(int data){
        Node node = new Node(data);
        if (root == null){
            root = node;
            return;
        }
        findNull(root,node);

    }
    private void findNull(Node temp,Node node){
        if (temp.getData() > node.getData()){
            if (temp.getLeftNode() != null){
                temp = temp.getLeftNode();
                findNull(temp,node);
            }else{
                temp.setLeftNode(node);
            }
        } else{
            if (temp.getRightNode() != null){
                temp = temp.getRightNode();
                findNull(temp,node);
            }else{
                temp.setRightNode(node);
            }
        }
    }

    public void perorderTravel(Node root,List<Integer> list){
        if (root != null){
            list.add(root.getData());
            perorderTravel(root.getLeftNode(),list);
            perorderTravel(root.getRightNode(),list);
        }
    }
    public void inorderTravel(Node root,List<Integer> list){
        if (root != null){
            perorderTravel(root.getLeftNode(),list);
            list.add(root.getData());
            perorderTravel(root.getRightNode(),list);
        }
    }
    public void postorderTravel(Node root,List<Integer> list){
        if (root != null){
            perorderTravel(root.getLeftNode(),list);
            perorderTravel(root.getRightNode(),list);
            list.add(root.getData());
        }
    }



    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.add(4);
        binaryTree.add(5);
        binaryTree.add(2);
        binaryTree.add(6);
        binaryTree.add(8);
        binaryTree.add(7);
        binaryTree.add(1);
        binaryTree.add(0);
        System.out.println(binaryTree.root.getData());
        List<Integer> list = new ArrayList<>();
        binaryTree.perorderTravel(binaryTree.root,list);
        System.out.println(list);
    }
}
