package play.android.com.sudokusolver;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    PuzzleView maze;
    RequestQueue queue;
    ProgressBar pbar;
    String URL="https://cookies-security.appspot.com/solve";
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    JSONObject makejson()
    {
        JSONObject j=new JSONObject();
        int [][]a= {{0,0,6,0,0,0,4,0,0},{0,7,0,6,2,9,0,0,0},{0,3,0,0,7,0,0,0,0},{0,0,2,0,0,0,0,0,3},{0,4,5,9,0,7,8,2,0},{8,0,0,0,0,0,9,0,0},{0,0,0,0,9,0,0,6,0},{0,0,0,8,6,4,0,5,0},{0,0,3,0,0,0,7,0,0}};
        try {
            j.put("array", new JSONArray(a));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return j;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        maze= (PuzzleView) findViewById(R.id.pz);
        pbar= (ProgressBar) findViewById(R.id.pbar);
queue= Volley.newRequestQueue(this);
pbar.setVisibility(View.VISIBLE);

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.POST, URL,makejson(),  new Response.Listener<JSONObject>() {



            @Override
            public void onResponse(JSONObject response) {

                pbar.setVisibility(View.INVISIBLE);
                String res= null;
                try {
                    res = response.getString("array");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String[]array=  res.split("\\n");
              for (int i=0;i<array.length;i++)
              {
                  String[]num=array[i].split(",");
                  for(int j=0;j<num.length;j++)
                      maze.Matrix.get(i).set(j,Integer.parseInt(num[j]));
                  maze.Matrix.set(i,maze.Matrix.get(i));
              }
                maze.setdraw();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, "some error occured", Toast.LENGTH_SHORT).show();
             error.printStackTrace();
            }
        }
        ){
            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=utf-8");
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(60000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

             queue.add(request);
    }




}
