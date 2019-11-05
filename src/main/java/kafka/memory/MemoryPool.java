package kafka.memory;

import java.nio.ByteBuffer;

/**
 * @Date: 2019/8/2 10:11
 * @Description:
 */
public interface MemoryPool {

    ByteBuffer tryAllocate(int sizeBytes);

    void release(ByteBuffer previousAllocated);

    long size();

    long availableMemory();

    boolean isOutOfMemory();

    MemoryPool NONE = new MemoryPool() {

        @Override
        public ByteBuffer tryAllocate(int sizeBytes) {
            return ByteBuffer.allocate(sizeBytes);
        }

        @Override
        public void release(ByteBuffer previousAllocated) {

        }

        @Override
        public long size() {
            return Long.MAX_VALUE;
        }

        @Override
        public long availableMemory() {
            return Long.MAX_VALUE;
        }

        @Override
        public boolean isOutOfMemory() {
            return false;
        }

        @Override
        public String toString() {
            return "NONE";
        }
    };
}
