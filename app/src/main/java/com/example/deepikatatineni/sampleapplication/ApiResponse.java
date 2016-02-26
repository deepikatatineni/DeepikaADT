package com.example.deepikatatineni.sampleapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by deepikatatineni on 2/26/16.
 */
public class ApiResponse {
    public class ApiData {
        public String title;
        public String imgUrl;
    }

    public ArrayList<ApiData> mImageData;


    private String mStrJSONData;

    public void parse(String json) {
        Log.i("RsponseParser : ", "Rsponse from network : " + json);
    }

    public void parse(JSONObject json) {
        try {
            JSONArray arr = json.getJSONArray("results");
            mImageData = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                ApiData data = new ApiData();
                JSONObject eachElement = arr.getJSONObject(i);
                data.title = eachElement.getString("title");
                Log.i("ImageUrl", "EachELement : " + eachElement.toString());
//                JSONArray arrObj = eachElement.getJSONArray("multimedia");
//                if (arrObj!=null) {
//                    data.imgUrl = getImageUrl(arrObj);
//                }
                Log.i("parser", "Title : " + data.title);
                mImageData.add(data);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private String getImageUrl(JSONArray json) throws JSONException {
        String imageUrl = "";
        JSONObject eachImageObject = null;
        if (json != null) {
            for (int i = 0; i < json.length(); i++) {
                String format = json.getJSONObject(i).getString("format");
                Log.i("ImageUrl", "format : " + format);

            }
        }

        return imageUrl;
    }


}



