package com.tiyanrcode.guestbookclient.configure;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by sulistiyanto on 3/21/2015.
 */
public class ClientToServer {

    public  static  final  int HTTP_TIMEOUT = 30 * 1000;
    private  static HttpClient httpClient;
    static FileInputStream fileInputStream = null;
    static  String sResponse;

    private  static  HttpClient getHttpClient(){
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
            final HttpParams httpParams = httpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(httpParams, HTTP_TIMEOUT);
        }
        return httpClient;
    }

    public String executionHttpPost(String url, ArrayList<NameValuePair> postParameters) throws Exception{
        BufferedReader bufferedReader = null;
        try {
            HttpClient httpClient = getHttpClient();
            HttpPost httpPost = new HttpPost(url);
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            httpPost.setEntity(formEntity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            StringBuffer stringBuffer = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line =bufferedReader.readLine()) != null){
                stringBuffer.append(line + NL);
            }
            bufferedReader.close();
            String result = stringBuffer.toString();
            return  result;
        } finally {
            if ((bufferedReader != null)) {
                bufferedReader.close();
            }
        }
    }

    public static String executionHttpGet (String url) throws Exception {
        BufferedReader bufferedReader = null;
        try {
            HttpClient httpClient1 = getHttpClient();
            HttpGet httpGet = new HttpGet();
            httpGet.setURI(new URI(url));
            HttpResponse httpResponse = httpClient1.execute(httpGet);
            bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            StringBuffer stringBuffer = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + NL);
            }
            bufferedReader.close();
            String result = stringBuffer.toString();
            return  result;
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
    }

}
