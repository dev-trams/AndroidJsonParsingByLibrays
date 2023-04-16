package com.kbu.exam.jsonbylibrary;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConvertJson {
    private Context context;

    public ConvertJson(Context context) {
        this.context = context;
    }

    @NonNull
    public ArrayList<LottoDTO> onConvertJson(String json) throws JSONException {
        ArrayList<LottoDTO> lottoDTOS = new ArrayList<>();
        LottoDTO lottoDTO = new LottoDTO();
        JSONObject rootObj = new JSONObject(json);
        String status = rootObj.getString((String) rootObj.names().get(1));
        String drwNoDate = rootObj.getString((String) rootObj.names().get(2)); // 날짜
        String drwNo = rootObj.getString((String) rootObj.names().get(10)); // 회차
        String drwNo1 = rootObj.getString((String) rootObj.names().get(13)); //1번 번호
        String drwNo2 = rootObj.getString((String) rootObj.names().get(11)); // 2번 번호
        String drwNo3 = rootObj.getString((String) rootObj.names().get(12)); // 3번 번호
        String drwNo4 = rootObj.getString((String) rootObj.names().get(5)); // 4번 번호
        String drwNo5 = rootObj.getString((String) rootObj.names().get(7)); // 5번 번호
        String drwNo6 = rootObj.getString((String) rootObj.names().get(4)); // 6번 번호
        String bnusNo = rootObj.getString((String) rootObj.names().get(8)); // 보너스 번호
        if(status.equals("success")) {
            lottoDTO.setdrwNo(drwNo);
            lottoDTO.setdrwNoDate(drwNoDate);
            lottoDTO.setdrwtNo1(drwNo1);
            lottoDTO.setdrwtNo2(drwNo2);
            lottoDTO.setdrwtNo3(drwNo3);
            lottoDTO.setdrwtNo4(drwNo4);
            lottoDTO.setdrwtNo5(drwNo5);
            lottoDTO.setdrwtNo6(drwNo6);
            lottoDTO.setBnusNo(bnusNo);
            lottoDTOS.add(lottoDTO);
        } else {
            Toast.makeText(context, "입력하신 회차는 없는 회차이거나 \n오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
        }
        return lottoDTOS;
    }
}
