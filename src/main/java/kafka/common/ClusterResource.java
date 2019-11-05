package kafka.common;

/**
 * @Date: 2019/4/19 16:00
 * @Description:
 */
public class ClusterResource {

    private final String clusterId;

    public ClusterResource(String clusterId){
        this.clusterId = clusterId;
    }

    public String getClusterId() {
        return clusterId;
    }
}
