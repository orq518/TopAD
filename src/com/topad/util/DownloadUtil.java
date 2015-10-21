package com.topad.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.concurrent.Executors;

/**
 * Created by njf on 15-10-20.
 */
public class DownloadUtil {
    private final static String Tag = "DownloadUtil"; 

    private static final int connect_time_out = 60 * 1000;
    private static final int socket_time_out = 60 * 1000;


    /**
     *
     * @param downloadUri   下载地址
     * @param localPath     下载路径
     * @param listener
     */
    public static void downloadAsync(final URI downloadUri, final String localPath, final ProgressListener listener) {

        try {

            Executors.newCachedThreadPool().execute(new Runnable() {

                @Override
                public void run() {

                    int count = 0;
                    try {

                        HttpParams params = new BasicHttpParams();
                        HttpConnectionParams.setConnectionTimeout(params, connect_time_out);
                        HttpConnectionParams.setSoTimeout(params, socket_time_out);

                        HttpClient client = new DefaultHttpClient(params);
                        HttpGet get = new HttpGet(downloadUri.toASCIIString());
                        get.addHeader("Connection", "close");
                        DefaultHttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(3, true);
                        ((AbstractHttpClient) client).setHttpRequestRetryHandler(retryHandler);

                        HttpResponse response = client.execute(get);

//						URL uri = new URL(downloadUri.toASCIIString());
//						HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
//						connection.setConnectTimeout(30000);
//						connection.connect();

                        int code = response.getStatusLine().getStatusCode();
                        if (HttpURLConnection.HTTP_OK == code) {
                            int length = (int) response.getEntity().getContentLength();
                            Debugger.d(Tag, "downloadAsync: length of file:" + length);
                            if (null != listener) {
                                listener.transferStarted(localPath, length);
                            }

                            InputStream input = new BufferedInputStream(response.getEntity().getContent());
                            OutputStream output = new FileOutputStream(localPath);

                            byte data[] = new byte[1024];

                            long total = 0;

                            while ((count = input.read(data)) != -1) {
                                total += count;
                                if (null != listener) {
                                    listener.transferred(total);
                                }
                                output.write(data, 0, count);
                                Debugger.d(Tag, "transferred bytes:" + total);
                            }
                            output.flush();
                            output.close();
                            input.close();

                            if (null != listener) {
                                Debugger.d(Tag, "transferred finished:" + total);
                                listener.transferFinished(HttpURLConnection.HTTP_OK, downloadUri.toASCIIString(), localPath, null, null);
                            }
//							connection.disconnect();
                        } else {
                            if (null != listener) {
                                listener.transferFailed(code, localPath);
                            }
                        }

                    } catch (MalformedURLException e) {
                        if (null != listener) {
                            listener.transferFailed(-1, null);
                        }
                        Debugger.e(Tag, e.getMessage());
                    } catch (IOException e) {
                        Debugger.e(Tag, e.getMessage());
                        if (null != listener) {
                            listener.transferFailed(-2, null);
                        }

                    }

                }
            });
        } catch (Exception e) {
            Debugger.e(Tag, e.getMessage());
        }
    }

    public interface ProgressListener {

        /**
         * @param filePath destination path 
         * @param num of file in bytes
         */
        public void transferStarted(String filePath, long num);

        /**
         *
         * @param num number of transferred bytes 
         */
        public void transferred(long num);

        public void transferFinished(int code, String url, String localPath, String fileId, String expires);

        public void transferFailed(int code, String localPath);
    }

}
