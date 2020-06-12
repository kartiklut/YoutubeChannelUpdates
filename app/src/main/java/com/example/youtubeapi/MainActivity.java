package com.example.youtubeapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;

    private static String GOOGLE_YOUTUBE_API_KEY="AIzaSyDt0oGkMmFY0yGHaD0SsieK3-EQCJhorTE";
    private static String CHANNEL_ID="UCLA_DiR1FfKNvjuUpBHmylQ";
    private static String CHANNEL_GET_URL="https://www.googleapis.com/youtube/v3/activities?part=snippet%2CcontentDetails&channelId=UCLA_DiR1FfKNvjuUpBHmylQ&maxResults=50&key=AIzaSyDt0oGkMmFY0yGHaD0SsieK3-EQCJhorTE";

    private RecyclerView mlist_videos;
    private VideoPostAdapter videoPostAdapter=null;
    private ArrayList<YoutubeDataModel> mListData=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mlist_videos=findViewById(R.id.mlist_videos);
        initList(mListData);

        //requestQueue= Volley.newRequestQueue(MainActivity.this);
        requestQueue=VoleySingleton.getInstance(this).getRequestQueue();
        sendApiRequest();
    }

    private void sendApiRequest() {
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, CHANNEL_GET_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    JSONArray array=response.getJSONArray("items");
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject jsonObject=array.getJSONObject(i);
                        String title=jsonObject.getJSONObject("snippet").getString("title");
                        String description=jsonObject.getJSONObject("snippet").getString("description");
                        String publishedAt=jsonObject.getJSONObject("snippet").getString("publishedAt");
                        String thumbnail=jsonObject.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url");
                        YoutubeDataModel youtubeDataModel=new YoutubeDataModel();
                        youtubeDataModel.setMdescription(description);
                        youtubeDataModel.setMpublishedAt(publishedAt);
                        youtubeDataModel.setMtitle(title);
                        youtubeDataModel.setThumbnail(thumbnail);
                        mListData.add(youtubeDataModel);
                        Log.d("List",title);
                    }
                    videoPostAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Log.d("in sendApiRequest","Here!!!!");
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));

        requestQueue.add(request);
    }

    private void initList(ArrayList<YoutubeDataModel> mListData) {

        mlist_videos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        videoPostAdapter=new VideoPostAdapter(this, mListData);
        mlist_videos.setAdapter(videoPostAdapter);
        Log.d("in initList","Here!!!!");
    }
}
