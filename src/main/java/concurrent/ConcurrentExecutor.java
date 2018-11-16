package concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Date: 2018/11/16 17:31
 * @Description:
 */
public class ConcurrentExecutor {

    private static final Logger logger = LoggerFactory.getLogger(ConcurrentExecutor.class);

    public void asynTask(Runnable action, int times) {

        if (times < 0) {
            return;
        }

        Executor executor = Executors.newFixedThreadPool(times);

        IntStream.range(0, times).mapToObj(
                x -> CompletableFuture.runAsync(action, executor)
        ).collect(
                Collectors.toList()
        ).stream().forEach(
                CompletableFuture::join
        );

        logger.info("");
    }


}
