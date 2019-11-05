package tree;


import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @Date: 2019/8/19 11:22
 * @Description:
 */
public abstract class AbstractNode<T extends Serializable & Comparable<T>>
        implements Serializable{

    private static final long serialVersionUID = -8007956974345796565L;

    protected final T nid;
    protected final T pid;
    protected final int orders; //sequence in sibling
    protected final boolean enabled;
    protected final AbstractNode<T> attach; //

    protected boolean available;
    protected int level;
    protected List<T> path;

    protected int childLeafCount;
    protected int leftLeafCount;
    protected int treeNodeCount;
    protected int treeMaxDepth;

    public AbstractNode(T nid, T pid, int orders, boolean enabled,
                        AbstractNode<T> attach){
        this.nid = nid;
        this.pid = pid;
        this.orders = orders;
        this.enabled = enabled;
        this.attach = innerMostAttach(attach);
        this.available = enabled;
    }

    private AbstractNode<T> innerMostAttach(AbstractNode<T> attach){
        if(attach == null || attach.attach == null){
            return attach;
        }else{
            return innerMostAttach(attach.attach);
        }
    }

    public boolean isEmpty(T id){
        if(id instanceof CharSequence){
            return StringUtils.isBlank((CharSequence) id);
        }
        return id == null;
    }

    public boolean isNotEmpty(T id){
        return !isEmpty(id);
    }

    public T getNid() {
        return nid;
    }

    public T getPid() {
        return pid;
    }

    public int getOrders() {
        return orders;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public AbstractNode<T> getAttach() {
        return attach;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<T> getPath() {
        return path;
    }

    public void setPath(List<T> path) {
        this.path = path;
    }

    public int getChildLeafCount() {
        return childLeafCount;
    }

    public void setChildLeafCount(int childLeafCount) {
        this.childLeafCount = childLeafCount;
    }

    public int getLeftLeafCount() {
        return leftLeafCount;
    }

    public void setLeftLeafCount(int leftLeafCount) {
        this.leftLeafCount = leftLeafCount;
    }

    public int getTreeNodeCount() {
        return treeNodeCount;
    }

    public void setTreeNodeCount(int treeNodeCount) {
        this.treeNodeCount = treeNodeCount;
    }

    public int getTreeMaxDepth() {
        return treeMaxDepth;
    }

    public void setTreeMaxDepth(int treeMaxDepth) {
        this.treeMaxDepth = treeMaxDepth;
    }
}
