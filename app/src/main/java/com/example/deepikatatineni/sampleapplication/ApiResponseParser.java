package com.example.deepikatatineni.sampleapplication;

import android.os.AsyncTask;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by deepikatatineni on 2/26/16.
 */
public class ApiResponseParser extends AsyncTask<Void, Void, Void> {

    private RequestCompletedListener mListenerInstance;
    private String mErrorCode = null;
    private JSONObject mJsonData;
    private String mUrl;

    public ApiResponseParser(String pUrl, RequestCompletedListener pListener) {
        this.mUrl = pUrl;
        this.mListenerInstance = pListener;
    }


    public interface RequestCompletedListener {
        void onSuccess(JSONObject jsonData);

        void onError(String error);
    }


    @Override
    protected Void doInBackground(Void... voids) {
        if (TextUtils.isEmpty(this.mUrl)) {
            this.mErrorCode = "Invalid url : " + this.mUrl;
            return null;
        }

        makeHttpRequest();

        return null;
    }


    private void makeHttpRequest() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(this.mUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(connection.getInputStream());
            String strinResponse = readInputStream(in);
            if (!TextUtils.isEmpty(strinResponse)) {
//                ApiResponse response = new ApiResponse();
//                response.parse(strinResponse);
                mJsonData = new JSONObject(strinResponse);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            this.mErrorCode = e.getMessage();
        } catch (IOException ex) {
            ex.printStackTrace();
            this.mErrorCode = ex.getMessage();
        } catch (JSONException e) {
            e.printStackTrace();
            this.mErrorCode = e.getMessage();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }


    private String readInputStream(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (in != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String nextline = "";
            while ((nextline = reader.readLine()) != null) {
                sb.append(nextline);
            }
        }
        return sb.toString();
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        if (mListenerInstance != null) {
            if (!TextUtils.isEmpty(this.mErrorCode)) {
                mListenerInstance.onError(mErrorCode);
            } else {
                mListenerInstance.onSuccess(mJsonData);
            }
        }
    }
}
