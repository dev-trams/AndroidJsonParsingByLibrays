package com.kbu.exam.jsonbylibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    int state = 1;
    EditText editText;
    Button button;
    TextView textView;

    String baseUrl;

    {
        try {
            baseUrl = "https://www.dhlottery.co.kr/common.do/?method="+ URLEncoder.encode("getLottoNumber", "UTF-8") +"&drwNo=";
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit_text);
        button = (Button) findViewById(R.id.btn_load);
        textView = (TextView) findViewById(R.id.text_view);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<LottoDTO> lottoDTOS = new ArrayList<>();
                switch (state) {
                    case 1:
                        //Thread
                        GetLottoThread thread = new GetLottoThread(baseUrl.concat(editText.getText().toString()), MainActivity.this);
                        thread.start();
                        try {
                            thread.join();
                            String json = thread.getResult();
                            ConvertJson convertJson = new ConvertJson(MainActivity.this);
                            lottoDTOS = convertJson.onConvertJson(json);
                            textView.setText(lottoDTOS.get(0).drwNo + "회차(" + lottoDTOS.get(0).drwNoDate + ")" + "당첨번호 \n\n"
                                    + "당첨번호1 :" + lottoDTOS.get(0).drwtNo1
                                    + "\n당첨번호2 :" + lottoDTOS.get(0).drwtNo2
                                    + "\n당첨번호3 :" + lottoDTOS.get(0).drwtNo3
                                    + "\n당첨번호4 :" + lottoDTOS.get(0).drwtNo4
                                    + "\n당첨번호5 :" + lottoDTOS.get(0).drwtNo5
                                    + "\n당첨번호6 :" + lottoDTOS.get(0).drwtNo6
                                    + "\n보너스 번호 :" + lottoDTOS.get(0).bnusNo);
                        } catch (InterruptedException | JSONException e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("ERROR", "InterruptedException | JSONException:MainActivity:62 [".concat(Objects.requireNonNull(e.getMessage())).concat("]"));
                        }

                        break;
                    case 2:
                        //AsyncTask
                        GetLottoAsyncTask asyncTask = new GetLottoAsyncTask(MainActivity.this);
                        try {
                            String json = asyncTask.execute(baseUrl.concat(editText.getText().toString())).get();
                            ConvertJson convertJson = new ConvertJson(MainActivity.this);
                            lottoDTOS = convertJson.onConvertJson(json);
                            textView.setText(lottoDTOS.get(0).drwNo + "회차(" + lottoDTOS.get(0).drwNoDate + ")" + "당첨번호 \n\n"
                                    + "당첨번호1 :" + lottoDTOS.get(0).drwtNo1
                                    + "\n당첨번호2 :" + lottoDTOS.get(0).drwtNo2
                                    + "\n당첨번호3 :" + lottoDTOS.get(0).drwtNo3
                                    + "\n당첨번호4 :" + lottoDTOS.get(0).drwtNo4
                                    + "\n당첨번호5 :" + lottoDTOS.get(0).drwtNo5
                                    + "\n당첨번호6 :" + lottoDTOS.get(0).drwtNo6
                                    + "\n보너스 번호 :" + lottoDTOS.get(0).bnusNo);

                        } catch (ExecutionException | InterruptedException | JSONException e) {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 3:
                        //Volley
                        // Volley 요청 큐를 만듭니다.
                        String url = null;
                        try {
                            url = baseUrl+ URLEncoder.encode(editText.getText().toString(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        // JSON 요청을 만듭니다.
                        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        // JSON 데이터를 가져왔을 때 처리할 코드를 작성합니다.
                                        // 예를 들어, JSON 데이터를 파싱하여 UI를 업데이트할 수 있습니다.
                                        try {
                                            if(!response.getString((String) response.names().get(0)).equals("fail")){
                                            String drwNoDate = response.getString((String) response.names().get(2)); // 날짜
                                            String drwNo = response.getString((String) response.names().get(10)); // 회차
                                            String drwNo1 = response.getString((String) response.names().get(13)); //1번 번호
                                            String drwNo2 = response.getString((String) response.names().get(11)); // 2번 번호
                                            String drwNo3 = response.getString((String) response.names().get(12)); // 3번 번호
                                            String drwNo4 = response.getString((String) response.names().get(5)); // 4번 번호
                                            String drwNo5 = response.getString((String) response.names().get(7)); // 5번 번호
                                            String drwNo6 = response.getString((String) response.names().get(4)); // 6번 번호
                                            String bnusNo = response.getString((String) response.names().get(8)); // 보너스 번호

                                            textView.setText(drwNo + "회차(" + drwNoDate + ")" + "당첨번호 \n\n"
                                                    + "당첨번호1 :" + drwNo1
                                                    + "\n당첨번호2 :" + drwNo2
                                                    + "\n당첨번호3 :" + drwNo3
                                                    + "\n당첨번호4 :" + drwNo4
                                                    + "\n당첨번호5 :" + drwNo5
                                                    + "\n당첨번호6 :" + drwNo6
                                                    + "\n보너스 번호 :" + bnusNo);
                                            }else {
                                                Toast.makeText(MainActivity.this, "회차번호가 잘못됐습니다.", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            throw new RuntimeException(e);
                                        }
                                        }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // 에러가 발생했을 때 처리할 코드를 작성합니다.
                                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                        // 요청을 Volley 요청 큐에 추가합니다.
                        queue.add(request);
                        break;
                    case 4:
                        //FestNetwork
                        try {
                            url = baseUrl+ URLEncoder.encode(editText.getText().toString(), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                        AndroidNetworking.initialize(MainActivity.this);
                        AndroidNetworking.get(url).build().getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    if(!response.getString((String) response.names().get(0)).equals("fail")){
                                        String drwNoDate = response.getString((String) response.names().get(2)); // 날짜
                                        String drwNo = response.getString((String) response.names().get(10)); // 회차
                                        String drwNo1 = response.getString((String) response.names().get(13)); //1번 번호
                                        String drwNo2 = response.getString((String) response.names().get(11)); // 2번 번호
                                        String drwNo3 = response.getString((String) response.names().get(12)); // 3번 번호
                                        String drwNo4 = response.getString((String) response.names().get(5)); // 4번 번호
                                        String drwNo5 = response.getString((String) response.names().get(7)); // 5번 번호
                                        String drwNo6 = response.getString((String) response.names().get(4)); // 6번 번호
                                        String bnusNo = response.getString((String) response.names().get(8)); // 보너스 번호

                                        textView.setText(drwNo + "회차(" + drwNoDate + ")" + "당첨번호 \n\n"
                                                + "당첨번호1 :" + drwNo1
                                                + "\n당첨번호2 :" + drwNo2
                                                + "\n당첨번호3 :" + drwNo3
                                                + "\n당첨번호4 :" + drwNo4
                                                + "\n당첨번호5 :" + drwNo5
                                                + "\n당첨번호6 :" + drwNo6
                                                + "\n보너스 번호 :" + bnusNo);
                                    }else {
                                        Toast.makeText(MainActivity.this, "회차번호가 잘못됐습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });
                        break;
                    case 5:
                        //Retrofit
                        String rootUrl = "https://www.dhlottery.co.kr/common.do/";
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(rootUrl)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        LottoApi lottoApi = retrofit.create(LottoApi.class);

                        Call<LottoDTO> call = lottoApi.getLotto(editText.getText().toString());
                        call.enqueue(new Callback<LottoDTO>() {
                            @Override
                            public void onResponse(Call<LottoDTO> call, retrofit2.Response<LottoDTO> response) {
                                LottoDTO lottoDTOList = response.body();
                                textView.setText(lottoDTOList.drwNo + "회차(" + lottoDTOList.drwNoDate + ")" + "당첨번호 \n\n"
                                        + "당첨번호1 :" + lottoDTOList.drwtNo1
                                        + "\n당첨번호2 :" + lottoDTOList.drwtNo2
                                        + "\n당첨번호3 :" + lottoDTOList.drwtNo3
                                        + "\n당첨번호4 :" + lottoDTOList.drwtNo4
                                        + "\n당첨번호5 :" + lottoDTOList.drwtNo5
                                        + "\n당첨번호6 :" + lottoDTOList.drwtNo6
                                        + "\n보너스 번호 :" + lottoDTOList.bnusNo);
                            }

                            @Override
                            public void onFailure(Call<LottoDTO> call, Throwable t) {
                                Log.e("TAG_ERROR", t.getMessage());
                            }
                        });
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                //thread
                state = 1;
                break;
            case R.id.item2:
                //AsyncTask
                state = 2;
                break;
            case R.id.item3:
                //Volley
                state = 3;
                break;
            case R.id.item4:
                //FestNetwork
                state = 4;
                break;
            case R.id.item5:
                //Retrofit
                state = 5;
                break;
        }
        item.setChecked(true);
        return true;
    }
}