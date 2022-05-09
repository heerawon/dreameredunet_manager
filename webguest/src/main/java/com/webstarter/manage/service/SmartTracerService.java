package com.webstarter.manage.service;


import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


@Log4j2
@Service
public class SmartTracerService {
//https://surebiz.surem.com/member/newLogin/downFile/SureM_KAKAO_BIZ_API_v3.2.1.pdf
    public static String loadApi() throws Exception{
        HttpURLConnection conn = null;
        JSONObject responseJson = null;
        String resStr = "";
        try {
            URL url = new URL ("http://info.sweettracker.co.kr/tracking/5?t_key=1lZKh4YxQ5zYsdXQSTDEmA&t_code=04&t_invoice=555462246565");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                resStr = response.toString();
                log.info("response :::::"+response.toString());
            }
        } catch (MalformedURLException e) {
            log.info("SuremBisService" + e.toString());
        } catch (IOException e) {
            log.info("SuremBisService" + e.toString());
        } catch (JSONException e) {
            log.info("SuremBisService" + e.toString());


        }catch (Exception e){
            log.info("SuremBisService" + e.toString());

        }
        return resStr;
    }


}
