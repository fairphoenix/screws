package com.shkabara.screws;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * Created by anatoliy on 20.11.2014.
 */
public class HttpClientTest {

    public static void main(String[] args) throws IOException, InterruptedException {

        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

        ConnectionKeepAliveStrategy myStrategy = new MyStrategy(cm);

        HttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setKeepAliveStrategy(myStrategy)
                .build();

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(30);
        for(int i = 0; i < 30; i++) {
            executorService.scheduleAtFixedRate(new Task(httpClient, cm), 10, 10, TimeUnit.MILLISECONDS);
        }
        System.out.println("Stop");
    }

    public static class Task implements Runnable{
        private HttpClient httpClient;
        PoolingHttpClientConnectionManager cm;

        public Task(HttpClient httpClient, PoolingHttpClientConnectionManager cm) {
            this.httpClient = httpClient;
            this.cm = cm;
        }

        @Override
        public void run() {
            try {
                HttpGet httpGet = new HttpGet("http://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&sensor=true_or_false");
                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();
                String json = IOUtils.toString(entity.getContent());
                //System.out.println(json);
                System.out.println(Thread.currentThread().getName() + " " + cm.getTotalStats().toString());
                cm.closeExpiredConnections();
                //Thread.sleep(1000);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public static class MyStrategy implements ConnectionKeepAliveStrategy{

        PoolingHttpClientConnectionManager cm;

        public MyStrategy(PoolingHttpClientConnectionManager cm) {
            this.cm = cm;
        }

        @Override
        public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
            return 1;
        }
    }
}
