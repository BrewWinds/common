package es;

import heartbeatKafka.RequsetFuture;
import org.apache.http.HttpHost;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.*;
import org.elasticsearch.common.Strings;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.DisposableBean;
import sun.reflect.ReflectionFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Based on ES Client 7.2. It provides high level client
 */
public class ESClient implements DisposableBean {

    /**
     * 10.202.116.33:9300,10.202.116.34:9300,10.202.116.35:9300
     * @return
     */

    private static final String HTTP = "http";
    private static final int DEFAULT_PORT = 9300;
    private static final int SOCKET_TIMEOUT = 10000;
    private RestClientBuilder restClientBuilder;
    public final RestClient restClient;

    public static void main(String[] args) {
    }

    public ESClient(){
        this.restClientBuilder = lowLevelRestClient();
        this.restClient = restClientBuilder.build();
    }

    public ESClient(RestClient.FailureListener nodeFailureListener){
        this.restClientBuilder = lowLevelRestClient();
        if(nodeFailureListener!=null) {
            restClientBuilder.setFailureListener(nodeFailureListener);
        }
        this.restClient = restClientBuilder.build();
    }


    public RestClientBuilder lowLevelRestClient(){
        RestClientBuilder restClient = RestClient.builder(
                new HttpHost("10.202.116.33",  DEFAULT_PORT, HTTP),
                new HttpHost("10.202.116.34:", DEFAULT_PORT, HTTP),
                new HttpHost("10.202.116.35:", DEFAULT_PORT, HTTP)
        );
        return restClient;
    }

    /**
     * 客制化 请求配置(超时, 校验等)
     * @return
     */
    public RestClientBuilder lowLvelRest2(){
        RestClientBuilder builder = lowLevelRestClient();
        return builder.setRequestConfigCallback((reqConfigBuilder)-> reqConfigBuilder.setSocketTimeout(SOCKET_TIMEOUT));
    }

    /**
     * 允许更改 http client 配置(例如, 通过ssl加密)
     *
     * @return
     */
    public RestClientBuilder lowLevelRest3(){
        RestClientBuilder builder = lowLevelRestClient();
        return builder.setHttpClientConfigCallback((httpAsyncClientBuilder)->
                httpAsyncClientBuilder.setProxy(new HttpHost("proxy", 9000, "http"))
        );
    }


    @Override
    public void destroy() throws Exception {
        if(restClient!=null){
            restClient.close();
        }
    }

    /**
     * simulate _bulk
     */

    public void simulateBulk(List<String> documents) throws InterruptedException {

        final CountDownLatch latch = new CountDownLatch(documents.size());
        for(int i=0; i < documents.size(); i++){
            Request request = new Request("PUT", "posts/doc/" +i);
            request.setJsonEntity(documents.get(i));

            restClient.performRequestAsync(request, new ResponseListener() {
                @Override
                public void onSuccess(Response response) {
                    latch.countDown();
                }

                @Override
                public void onFailure(Exception e) {
                    latch.countDown();
                }
            });
        }
        latch.await();
    }


    /**
     * Reactor mode
     */

    public void ioReactorMode(){
        lowLevelRestClient().setHttpClientConfigCallback((httpClientBuilder)->{
           return httpClientBuilder.setDefaultIOReactorConfig(
                   IOReactorConfig.custom().setIoThreadCount(1).build());
        });
    }

    /**
     * async
     */
    public void asyn(){
        GetRequest requset = new GetRequest("post", "1");

        String[] includes = new String[]{"message", "*Date"};
        String[] excludes = Strings.EMPTY_ARRAY;
        FetchSourceContext fetchSourceContext =
                new FetchSourceContext(true, includes, excludes);

    }



}
