package kafka.common;

/**
 * @Date: 2019/4/19 15:52
 * @Description:
 */
public class Node {

    private static final Node NO_NODE = new Node(-1, "",  -1);

    private final int id;
    private final String idString;
    private final String host;
    private final int port;
    private final String rack;

    private Integer hash;

    public Node(int id, String host, int port ) {
        this(id, host, port, null);
    }

    public Node(int id, String host, int port, String rack) {
        this.id = id;
        this.idString = Integer.toString(id);
        this.host = host;
        this.port = port;
        this.rack = rack;
    }

    public static Node noNode(){
        return NO_NODE;
    }


}
