package concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.BiFunction;

/**
 * @Auther:
 * @Date: 2018/11/13 17:50
 * @Description:
 *
 * 1. 外部put, end 方法.
 * 2. 线程内部pool
 *
 */
public final class TransferStation<T> {

    private volatile boolean isEnd = false;
    private BlockingQueue<T> queue = new LinkedBlockingQueue();
    private final AsynTask task;

    public TransferStation(BiFunction<List<T>, Boolean, Runnable> processor){
        this(processor, 2000, 1000, null);
    }


    public TransferStation(BiFunction<List<T>, Boolean, Runnable> processor, int thresholdSize, int period, ThreadPoolExecutor executor){
        this.task = new AsynTask(processor, thresholdSize, period, executor);
    }

    // 外部只能put
    public boolean put(T t){
        return this.queue.offer(t);
    }

    // 调用end方法
    public synchronized void end(){
        if(isEnd){
            return;
        }
        this.task.refresh();
        this.isEnd = true;
        this.task.refresh();
    }


    private final class AsynTask extends Thread{

        private BiFunction<List<T>, Boolean, Runnable> processor;
        final int thresholdSize;
        final int period;
        final int sleepTimeInMills;
        final boolean requireDestroyWhenEnd;
        private ThreadPoolExecutor executor;

        long lastConsumeTime = System.currentTimeMillis();

        AsynTask(BiFunction<List<T>, Boolean, Runnable> function, int thresholdSize, int period,
                 ThreadPoolExecutor executor) {

            this.processor = function;
            this.thresholdSize = thresholdSize;
            this.period = period;
            this.sleepTimeInMills = 10;

            if(executor == null){
                this.requireDestroyWhenEnd = true;
                this.executor = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(),
                        120,  TimeUnit.SECONDS, new SynchronousQueue());
            }else{
                this.requireDestroyWhenEnd = false;
                this.executor = executor;
            }

            super.setName("AsynTask");
            super.setDaemon(true);
            super.start();
        }

        @Override
        public void run() {
            T t;
            List<T> list = new ArrayList<>(thresholdSize);
            for(;;){
                if(isEnd && queue.isEmpty() && last() > 2 * period){
                    if(requireDestroyWhenEnd) {
                        try {
                            executor.shutdown();
                        } catch (Exception e) {
                            e.printStackTrace();
                            ;
                        }
                    }
                    break;
                }

                // 执行的時候, 不断往里加, 直到触发条件， 调用线程. 异步线程去处理.
                if(!queue.isEmpty()){
                    for(int n = thresholdSize - list.size(), i=0; i<n; i++){
                        t = queue.poll();
                        if(t==null){
                            break;
                        }
                        list.add(t);
                    }
                }

                if(list.size() == thresholdSize || (!list.isEmpty() && (isEnd || last() > period))){
                    executor.submit(processor.apply(list, isEnd && queue.isEmpty()));
                    list = new ArrayList<>(thresholdSize);
                    refresh();
                }else{
                    try{
                        Thread.sleep(sleepTimeInMills);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

            }

        }

        public void refresh(){
            this.lastConsumeTime = System.currentTimeMillis();
        }

        public long last(){
            return System.currentTimeMillis() - lastConsumeTime;
        }
    }
}
