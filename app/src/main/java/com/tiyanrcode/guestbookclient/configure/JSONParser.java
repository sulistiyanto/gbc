package com.tiyanrcode.guestbookclient.configure;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by sulistiyanto on 3/21/2015.
 */
public class JSONParser {

    InputStream inputStream;
    String json;
    JSONObject jsonObject;

    public JSONObject getJsonObject(String url, String method, List<NameValuePair> valuePairs) throws IOException{

        if (method == "POST"){
            DefaultHttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            try {
                post.setEntity(new UrlEncodedFormEntity(valuePairs));
                HttpResponse response = client.execute(post);
                HttpEntity entity = response.getEntity();
                inputStream = entity.getContent();
            } catch (UnsupportedEncodingException e){
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        } else if (method == "GET") {
            DefaultHttpClient client = new DefaultHttpClient();
            String param = URLEncodedUtils.format(valuePairs, "utf-8");
            url += "?" + param;
            HttpGet get = new HttpGet(url);
            HttpResponse response;
            try {
                response = client.execute(get);
                inputStream = response.getEntity().getContent();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line+"\n");
            }
            inputStream.close();
            json = stringBuilder.toString();
            jsonObject = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject;
    }
}
