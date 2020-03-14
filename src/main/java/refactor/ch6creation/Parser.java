package refactor.ch6creation;

public class Parser {

//    boolean shouldDecode;

//    public void setShouldDecode(boolean shouldDecode) {
//        this.shouldDecode = shouldDecode;
//    }
//
//    public boolean shouldDecodeNodes() {
//        return shouldDecode;
//    }

    private NodeFactory2 nodeFactory2 =
            new NodeFactory2();

    public NodeFactory2 getNodeFactory2() {
        return nodeFactory2;
    }

    public void setNodeFactory2(NodeFactory2 nodeFactory2) {
        this.nodeFactory2 = nodeFactory2;
    }
}
