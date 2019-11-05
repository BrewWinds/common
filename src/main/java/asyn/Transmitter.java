package asyn;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.function.BiFunction;

/**
 * @Date: 2019/1/21 10:06
 * @Description:
 */
public class Transmitter<T> {

    private final Queue<T> queue = new ConcurrentLinkedQueue();
    private volatile boolean end = false;
    private final AsynThread asynThread;

    public Transmitter(int batchSize, long sleepTimeMillis,
                       long period, ExecutorService executor, BiFunction bifun){
        this.asynThread = new AsynThread(batchSize, sleepTimeMillis,
                period, executor, bifun);
    }

    public boolean put(T t){
        return this.queue.offer(t);
    }

    public boolean put(T... ts){
        if(ts==null || ts.length==0){
            return false;
        }
        boolean flag = true;
        for( T t : ts){
            flag &= this.queue.offer(t);
        }
        return flag;
    }


    private class AsynThread extends Thread{

        private long sleepTimeMillis;
        private long period;
        private int batchSize;
        private final ExecutorService executor;
        private boolean destroy = false;
        private BiFunction<List<T>, Boolean, Runnable> bifun;

        private long lastExecTime;

        public AsynThread(int batchSize, long sleepTimeMillis, long period,
                          ExecutorService executor, BiFunction bifun){
            this.batchSize = batchSize;
            this.sleepTimeMillis = sleepTimeMillis;
            this.period = period;
            this.bifun = bifun;
            if(executor == null){
                destroy = true;
                this.executor = new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(),
                        120, TimeUnit.SECONDS, new SynchronousQueue<>());
            }else{
                this.executor = executor;
                destroy = false;
            }

            this.setName("-AsynTransmitter Thread-");
            this.setDaemon(true);
            this.start();

        }

        @Override
        public void run() {
            List<T> list = new ArrayList();
            T t;
            for(;;){
                if(end && queue.isEmpty() && period * 2 >= calc()){
                    if(destroy){
                        try {
                            executor.shutdown();
                        }catch(Exception ignore){
                            //ignore
                        }
                    }
                    break;
                }

                if(!queue.isEmpty()){
                    for(int n = batchSize - list.size(), i=0; i<n; i++){
                        //poll 不阻塞, 空直接返回 null
                        t = queue.poll();
                        if(t == null){
                            return;
                        }
                        list.add(t);
                    }
                }

                if(end || list.size() == batchSize || System.currentTimeMillis() - lastExecTime >= period){
                    if(!list.isEmpty()){
                        bifun.apply(list, end && queue.isEmpty());
                        list = new ArrayList<>();
                        refresh();
                    }else{
                        try {
                            TimeUnit.MILLISECONDS.sleep(sleepTimeMillis);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            }
        }

        public long calc(){
            return System.currentTimeMillis() - lastExecTime;
        }

        public void refresh(){
            lastExecTime = System.currentTimeMillis();
        }

    }


    public synchronized void terminate(){
        if(end){
            return;
        }
        //TODO verify whether need to refresh again.
        end = true;
        this.asynThread.refresh();
    }
}
