package tree;


import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;

/**
 * @Date: 2019/8/19 11:36
 * @Description:
 */
//public class FlatNode<T extends Serializable & Comparable<T>> extends AbstractNode<T>{
//
//    private static final long serialVersionUID = 2432819891945273024L;
//
//    private final boolean leaf;
//
////    public FlatNode(TreeNode<T> node){
////        super(node.getNid(), node.getPid(), node.getOrders(),
////                node.isEnabled(), node.getAttach());
////
////
////        super.available = node.isAvailable();
////        super.level = node.getLevel();
////        super.path = node.getPath();
////
////        super.treeNodeCount = node.treeNodeCount;
////        super.childLeafCount = node.childLeafCount;
////        super.leftLeafCount = node.leftLeafCount;
////        super.treeMaxDepth = node.treeMaxDepth;
////
////        this.leaf = CollectionUtils.isEmpty(node.getChildren());
////
////    }
//
//    public boolean isLeaf(){
//        return leaf;
//    }
//}
