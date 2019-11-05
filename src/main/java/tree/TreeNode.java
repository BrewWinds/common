package tree;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import reflect.Fields;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Date: 2019/8/19 11:22
 * @Description:
 */
//public class TreeNode<T extends Serializable & Comparable<T>> extends AbstractNode{
//
//
//    private static final long serialVersionUID = 8528610398074110944L;
//
//    public static final String DEFAULT_ROOT_ID = "__ROOT__";
//
//    private final List<TreeNode<T>> children = Lists.newArrayList();
//
//    private TreeNode(T nid, T pid, int orders, boolean enabled){
//        super(nid, pid, orders, enabled, null);
//        this.available = enabled;
//    }
//
//    private TreeNode(AbstractNode<T> node){
//        super(node.getNid(), node.getPid(), node.getOrders(), node.isAvailable(), node);
//        super.available = node.isAvailable();
//    }
//
//    public List<TreeNode<T>> getChildren(){
//        return children;
//    }
//
//    public static <T extends Serializable & Comparable<T>> TreeNode<T>
//        createRoot(T nid, T pid, int orders){
//        return new TreeNode<>(nid, pid, orders, true);
//    }
//
//
//    private <E extends AbstractNode<T>> List<AbstractNode<T>> before(List<E> nodes){
//
//        List<AbstractNode<T>> list = Lists.newArrayListWithCapacity(nodes.size());
//
//        for(AbstractNode<T> node : nodes){
//            if(node instanceof TreeNode){
//                list.addAll(((TreeNode) node).flatInherit());
//            }else{
//                list.add(node);
//            }
//        }
//
//        if(CollectionUtils.isNotEmpty(this.children)){
//            List<FlatNode<T>> flat = this.flatInherit();
//            list.addAll(flat.subList(1, flat.size()));
//            this.children.clear();
//        }
//
//        return list;
//    }
//
//    public <E extends AbstractNode<T>> TreeNode<T> mount(List<E> list, boolean ignoreOrphan){
////        Set<T> nodeNids = Sets.newHashSet(this.nid);
////
////        List<AbstractNode<T>> nodes = before(list);
////
////        for(AbstractNode<T> n : nodes){
////            if(!nodeNids.add(n.getNid())){
////                throw new RuntimeException("duplicate node: "+n.getNid());
////            }
////        }
////
////        this.level = 1; // 以此节点为根构建节点树
////        this.path = null;
////        this.leftLeafCount = 0;
//////        this.mount0(nodes, ignoreOrphan, this.nid);
////
////
////        // 检查是否存在孤儿节点
////        if(!ignoreOrphan && CollectionUtils.isNotEmpty(nodes)){
////            List<T> nids = nodes.stream().map(AbstractNode::getNid)
////                    .collect(Collectors.toList());
////            throw new RuntimeException("invalid orphan node: "+nids);
////        }
////
////        count();
////        return this;
//
//    }
//
//    private <E extends AbstractNode<T>> void mount0(List<E> nodes, boolean ignoreOrphan, T mountPidIfNull){
//
//        Set<Integer> uniqueOrders = Sets.newHashSet();
//
//        for(Iterator<E> iter = nodes.iterator(); iter.hasNext();){
//            AbstractNode<T> node = iter.next();
//
//            if(!ignoreOrphan && super.isEmpty(node.getPid())){
////                Fields.put(node, "pid", mountPidIfNull);
//            }
//
//            if(this.nid.equals(node.getPid())){
//                if(CollectionUtils.isNotEmpty(this.path)
//                    && this.path.contains(this.nid)){
//                    throw new RuntimeException("节点循环依赖: "+node.getNid());
//                }
//
//                if(!uniqueOrders.add(node.getOrders())){
//                    throw new RuntimeException("兄弟节点次序重复：" +node.getNid());
//                }
//
//                TreeNode<T> child = new TreeNode<>(node);
//                child.available = this.available && child.isEnabled();
//
//                // 子节点路径=节点路径+自身节点
//                Collections.addAll(this.path, this.nid);
//                child.level = this.level + 1;
//                this.children.add(child);
//
//                iter.remove();
//            }
//
//        }
//
//        if(CollectionUtils.isNotEmpty(this.children)){
//            this.children.sort(Comparator.comparing(TreeNode::getOrders));
//
//            for(TreeNode<T> n : this.children){
//                n.mount0(nodes, ignoreOrphan, mountPidIfNull);
//            }
//        }
//        Collections.addAll(this.path, this.nid);
//    }
//
//    public <E extends AbstractNode<T>> TreeNode<T> mount(List<E> nodes){
////        mount(nodes, false);
//        return this;
//    }
//
////    public <E extends AbstractNode<T>> TreeNode<T> mount(List<E> list, boolean ignoreOrphan){
//////        HashSet<T> nodeNids = (HashSet<T>) Sets.newHashSet(this.nid);
////
//////        List<AbstractNode<T>> nodes = before(list);
//////
//////        for(AbstractNode<T> n : nodes){
//////            if(!nodeNids.add(n.getNid())){
//////                throw new RuntimeException("duplicate node: "+n.getNid());
//////            }
//////        }
//////
//////        this.level = 1;
//////        this.path = null;
//////        this.leftLeafCount = 0;
////////        this.mount0(nodes, ignoreOrphan, this.nid);
//////
//////        if(!ignoreOrphan && CollectionUtils.isNotEmpty(nodes)){
//////            List<T> nids = nodes.stream().map(AbstractNode::getNid)
//////                    .collect(Collectors.toList());
//////            throw new RuntimeException("invalid orphan node: "+nids);
//////        }
//////
//////        count();
//////        return this;
////        return null;
////    }
//
//
//
//    private void count(){
//        if(CollectionUtils.isNotEmpty(this.children)){
//            int maxChilTreeDepth = 0;
//            int sumTreeNodeCont = 0;
//            int sumChildLeafCount = 0;
//
//            TreeNode<T> child;
//
//            for(int i=0; i<this.children.size(); i++){
//                child = this.children.get(i);
//
//                if(i==0){
//                    child.leftLeafCount = this.leftLeafCount;
//                }else{
//                    TreeNode<T> preSibling = this.children.get(i-1);
//                    child.leftLeafCount = preSibling.leftLeafCount + preSibling.childLeafCount;
//                }
//
//                child.count();
//
//                sumChildLeafCount += child.childLeafCount;
//                maxChilTreeDepth = NumberUtils.max(
//                        new int[] {maxChilTreeDepth, child.treeMaxDepth}
//                );
//                sumTreeNodeCont += child.treeNodeCount;
//            }
//
//            this.childLeafCount = sumChildLeafCount;
//            this.treeMaxDepth = maxChilTreeDepth + 1;
//            this.treeNodeCount = sumTreeNodeCont + 1;
//        }else{
//            this.treeNodeCount = 1;
//            this.childLeafCount = 1;
//            this.treeMaxDepth = 1;
//        }
//    }
//
//
//    public List<FlatNode<T>> flatInherit(){
//        List<FlatNode<T>> collect = Lists.newArrayList();
//        inherit(collect);
//        return collect;
//    }
//
//    public List<FlatNode<T>> flatHierarchy(){
//        List<FlatNode<T>> collect = Lists.newArrayList(new FlatNode<>(this));
//        hierarchy(collect);
//        return collect;
//    }
//
//    private void inherit(List<FlatNode<T>> collect){
//        collect.add(new FlatNode<>(this));
//        if(CollectionUtils.isNotEmpty(this.children)){
//            for(TreeNode<T> n : this.children){
//                n.inherit(collect);
//            }
//        }
//    }
//
//    private void hierarchy(List<FlatNode<T>> collect){
//
//        if(CollectionUtils.isNotEmpty(this.children)){
//            for(TreeNode<T> n : this.children){
//                collect.add(new FlatNode<>(n));
//            }
//            for(TreeNode<T> n : this.children){
//                n.hierarchy(collect);
//            }
//        }
//    }
//}
