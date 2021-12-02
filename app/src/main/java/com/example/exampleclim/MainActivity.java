package com.example.exampleclim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{
    String id = "";
    private ArrayList<String> times = new ArrayList<String>();
    public AlbumsAdapter adapter;
    public List<ModelClim> albumList;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, albumList);
        recyclerView = (RecyclerView) findViewById(R.id.lt_clim);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(8), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        String[] valores = {"México","London","China"};

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(valores[i] == valores[0]){
                    id = "1699805";
                    update(id,"México");
                }else if(valores[i] == valores[1]){
                    id = "4030939";
                    update(id,"London");

                }else if(valores[i] == valores[2]){
                    id = "3530839";
                    update(id,"China");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                Log.d("","Error");
            }
        });


        }


    public void update(String id,String city) {
        String myUrl = "http://api.openweathermap.org/data/2.5/forecast?id="+id+"&appid=4c317899fd1c13002d8d10b06aa02ea3";
        albumList.clear();
        StringRequest myRequest = new StringRequest(Request.Method.GET, myUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject myJsonObject = new JSONObject(response.toString());
                            JSONArray arr= myJsonObject.getJSONArray("list");
                            String urlImg = "http://openweathermap.org/img/wn/";
                            for(int i=0;i<arr.length();i++) {
                                JSONObject jb1 = arr.getJSONObject(i);
                                JSONArray question = jb1.getJSONArray("weather");
                                String jb2 = question.getString(0);
                                JSONObject jsonObject = new JSONObject(jb2);
                                String description = jsonObject.getString("description");
                                String icon = jsonObject.getString("icon");
                                String hours = jb1.getString("dt_txt");
                                ModelClim a = new ModelClim(city,hours,description,urlImg+icon+".png");
                                albumList.add(a);
                                adapter = new AlbumsAdapter(MainActivity.this, albumList);
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(
                                MainActivity.this,
                                volleyError.getMessage(),
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(myRequest);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}