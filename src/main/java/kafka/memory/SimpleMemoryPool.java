package kafka.memory;

import sun.management.Sensor;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Date: 2019/8/2 10:24
 * @Description:
 */
public class SimpleMemoryPool /*implements MemoryPool*/ {

////
//    final long sizeBytes;
////    final boolean strict;
////    final AtomicLong availableMemory;
////    final int maxSingleAllocationSize;
////    final AtomicLong startOfNoMemPeriod = new AtomicLong();
////    volatile Sensor oomTimeSensor;
//
//    @Override
//    public ByteBuffer tryAllocate(int sizeBytes) {
//        return null;
//    }
//
//    @Override
//    public void release(ByteBuffer previousAllocated) {
//
//    }
//
//    @Override
//    public long size() {
//        return 0;
//    }
//
//    @Override
//    public long availableMemory() {
//        return 0;
//    }
//
//    @Override
//    public boolean isOutOfMemory() {
//        return false;
//    }
//}
}
