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
    private static String CHANNEL_GET_URL_NASA="https://www.googleapis.com/youtube/v3/activities?part=snippet%2CcontentDetails&channelId=UCLA_DiR1FfKNvjuUpBHmylQ&maxResults=50&key=AIzaSyDt0oGkMmFY0yGHaD0SsieK3-EQCJhorTE";
    private static String CHANNEL_GET_URL_SPACEX="https://www.googleapis.com/youtube/v3/activities?part=snippet%2CcontentDetails&channelId=UCtI0Hodo5o5dUb67FeUjDeA&maxResults=10&key=AIzaSyDt0oGkMmFY0yGHaD0SsieK3-EQCJhorTE";
    private static String CHANNEL_GET_URL_ESA="https://www.googleapis.com/youtube/v3/activities?part=snippet%2CcontentDetails&channelId=UCIBaDdAbGlFDeS33shmlD0A&maxResults=10&key=AIzaSyDt0oGkMmFY0yGHaD0SsieK3-EQCJhorTE";
    private static String CHANNEL_GET_URL_CSA="https://www.googleapis.com/youtube/v3/activities?part=snippet%2CcontentDetails&channelId=UCdNtqpHlU1pCaVy2wlzxHKQ&maxResults=10&key=AIzaSyDt0oGkMmFY0yGHaD0SsieK3-EQCJhorTE\n";

    private RecyclerView mlist_videos,mlist_videos_spacex,mlist_videos_esa,mlist_videos_csa;
    private VideoPostAdapter videoPostAdapter=null;
    private VideoPostAdapterSpaceX videoPostAdapterSpaceX=null;
    private VideoPostAdapterESA videoPostAdapterESA=null;
    private VideoPostAdapterCSA videoPostAdapterCSA=null;


    private ArrayList<YoutubeDataModel> mListData=new ArrayList<>();
    private ArrayList<YoutubeDataModel> mListDataSPACEX=new ArrayList<>();
    private ArrayList<YoutubeDataModel> mListDataESA=new ArrayList<>();
    private ArrayList<YoutubeDataModel> mListDataCSA=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mlist_videos=findViewById(R.id.mlist_videos);
        mlist_videos_spacex=findViewById(R.id.mlist_videos_spacex);
        mlist_videos_esa=findViewById(R.id.mlist_videos_esa);
        mlist_videos_csa=findViewById(R.id.mlist_videos_csa);
        initList(mListData);
        initListSPACEX(mListDataSPACEX);
        initListESA(mListDataESA);
        initListCSA(mListDataCSA);

        //requestQueue= Volley.newRequestQueue(MainActivity.this);
        requestQueue=VoleySingleton.getInstance(this).getRequestQueue();
        sendApiRequestNASA();
        sendApiRequestSPACEX();
        sendApiRequestESA();
        sendApiRequestCSA();
    }

    private void sendApiRequestCSA() {

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, CHANNEL_GET_URL_CSA, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    JSONArray array=response.getJSONArray("items");
                    for(int i=0;i<array.length();i+=2)
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
                        mListDataCSA.add(youtubeDataModel);
                        Log.d("List",title);
                    }
                    videoPostAdapterCSA.notifyDataSetChanged();
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

    private void initListCSA(ArrayList<YoutubeDataModel> mListDataCSA) {

        mlist_videos_csa.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        videoPostAdapterCSA=new VideoPostAdapterCSA(this, mListDataCSA);
        mlist_videos_csa.setAdapter(videoPostAdapterCSA);
    }

    private void sendApiRequestESA() {
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, CHANNEL_GET_URL_ESA, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    JSONArray array=response.getJSONArray("items");
                    for(int i=0;i<array.length();i+=2)
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
                        mListDataESA.add(youtubeDataModel);
                        Log.d("List",title);
                    }
                    videoPostAdapterESA.notifyDataSetChanged();
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

    private void initListESA(ArrayList<YoutubeDataModel> mListDataESA) {
        mlist_videos_esa.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        videoPostAdapterESA=new VideoPostAdapterESA(this, mListDataESA);
        mlist_videos_esa.setAdapter(videoPostAdapterESA);
    }

    private void initListSPACEX(ArrayList<YoutubeDataModel> mListDataSPACEX) {
        mlist_videos_spacex.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        videoPostAdapterSpaceX=new VideoPostAdapterSpaceX(this, mListDataSPACEX);
        mlist_videos_spacex.setAdapter(videoPostAdapterSpaceX);
    }

    private void sendApiRequestSPACEX() {
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, CHANNEL_GET_URL_SPACEX, null, new Response.Listener<JSONObject>() {
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
                        mListDataSPACEX.add(youtubeDataModel);
                        Log.d("List",title);
                    }
                    videoPostAdapterSpaceX.notifyDataSetChanged();
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

    private void sendApiRequestNASA() {
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, CHANNEL_GET_URL_NASA, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    JSONArray array=response.getJSONArray("items");
                    for(int i=0;i<array.length();i+=4)
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
