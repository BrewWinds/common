package kafka.model;

/**
 * @Date: 2019/2/20 16:33
 * @Description:
 */
public class KafkaMessage<T> {

    private int partition;
    private long offset;
    private T message;

    // private PartitionAndOffset

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}
