package com.tiyanrcode.guestbookclient.getdata;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.tiyanrcode.guestbookclient.configure.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sulistiyanto on 3/21/2015.
 */
public class GetDataGuest extends AsyncTask<Object, Object, Object> {

    private JSONParser jsonParser;
    JsonObjectResult jsonObjectResult;
    Context context;
    ProgressDialog progressDialog;

    public  void init(Context context, JsonObjectResult jsonObjectResult, String book_id, String url){
        this.context = context;
        this.jsonObjectResult = jsonObjectResult;
        GetDataGuest getDataGuest = this;
        getDataGuest.execute(url, book_id, "");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Menerima Data", "aaa");
        progressDialog.setMessage("Silahkan Tunggu...");
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Object... params) {
        JSONObject jsonObject = null;
        String url = (String) params[0];
        String bookId = (String) params[1];
        Log.i("url", url);
        Log.i("bookId", bookId);
        jsonParser = new JSONParser();
        List<NameValuePair> dataJson = new ArrayList<NameValuePair>();
        dataJson.add(new BasicNameValuePair("book_id", bookId));
        try {
            jsonObject = jsonParser.getJsonObject(url, "POST", dataJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

        if (o != null) {
            JSONObject jsonObject = (JSONObject) o;
            jsonObjectResult.gotJsonObject(jsonObject);
        }
    }

    public static abstract  class JsonObjectResult {
        public abstract void gotJsonObject(JSONObject object);
    }
}
