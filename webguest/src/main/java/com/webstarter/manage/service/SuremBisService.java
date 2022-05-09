package com.webstarter.manage.service;


import com.google.gson.*;
import lombok.extern.log4j.Log4j2;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


@Log4j2
@Service
public class SuremBisService {
//https://surebiz.surem.com/member/newLogin/downFile/SureM_KAKAO_BIZ_API_v3.2.1.pdf
    public static void callApiZoomLink(String teacherName, String startDateTime, String zoomLink, List<String> cellPhone ) throws Exception{
        HttpURLConnection conn = null;
        JSONObject responseJson = null;
        try {
            URL url = new URL ("https://rest.surem.com/biz/at/v2/json");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String jsonInputString = "{\n" +
                    "    \"usercode\":\"dreamer12\",\n" +
                    "    \"deptcode\":\"D3-KK2-2F\",\n" +
                    "    \"yellowid_key\":\"d595e6207ece7181824511828cdb5a71698face1\",\n" +
                    "    \"messages\":[ \n" ;

            for (int i=0 ; i< cellPhone.size();i++) {
                String messages ="" ;


                if(i== cellPhone.size()-1){
                    messages =
                            "        {\n" +
                                    "            \"callphone\" : \"82-"+cellPhone.get(i).substring(1)+"\",\n" +
                                    "            \"template_code\":\"send_zoomlink\",            \n" +
                                    "            \"text\":\"안녕하세요! 아이들의 꿈을 응원하는 드리머에듀넷 "+teacherName+" 강사입니다.\n\n수업 정보를 아래와 같이 보내드리오니 시간에 맞게 접속해주세요!\n\n강의일시 : "+startDateTime+"\n접속주소 : "+zoomLink+"\n\n감사합니다.\"\n" +
                                    "        }\n" ;
                }else{
                    messages =
                            "        {\n" +
                                    "            \"callphone\" : \"82-"+cellPhone.get(i).substring(1)+"\",\n" +
                                    "            \"template_code\":\"send_zoomlink\",            \n" +
                                    "            \"text\":\"안녕하세요! 아이들의 꿈을 응원하는 드리머에듀넷 "+teacherName+" 강사입니다.\n\n수업 정보를 아래와 같이 보내드리오니 시간에 맞게 접속해주세요!\n\n강의일시 : "+startDateTime+"\n접속주소 : "+zoomLink+"\n\n감사합니다.\"\n" +
                                    "        },\n" ;
                }

                jsonInputString += messages;
            }

            String endString =
                    "    ]\n" +
                    "}";

            jsonInputString += endString;

            log.info("jsonInputString:::::"+jsonInputString);

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                log.info("response:::::" + response.toString());
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
    }
    public static void callApiSubscribe( List<String> cellPhone ) {
        HttpURLConnection conn = null;
        JSONObject responseJson = null;
        try {
            URL url = new URL ("https://rest.surem.com/biz/at/v2/json");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String jsonInputString = "{\n" +
                    "    \"usercode\":\"dreamer12\",\n" +
                    "    \"deptcode\":\"D3-KK2-2F\",\n" +
                    "    \"yellowid_key\":\"d595e6207ece7181824511828cdb5a71698face1\",\n" +
                    "    \"messages\":[ \n" ;

            for (int i=0 ; i< cellPhone.size();i++) {
                String messages ="" ;


                if(i== cellPhone.size()-1){
                    messages =
                            "        {\n" +
                                    "            \"callphone\" : \"82-"+cellPhone.get(i).substring(1)+"\",\n" +
                                    "            \"template_code\":\"ex_date\",            \n" +
                                    "            \"text\":\"안녕하세요! \n 아이들의 꿈을 응원하는 드리머에듀넷입니다:)\n\n드리머에듀넷 이용 기간이 얼마 남지 않아 알려드립니다.\n\n우리 아이들의 꿈을 응원하기 위해\n드리머에듀넷과 꾸준히 함께해주시면 감사하겠습니다!\n\n마이페이지-결제 정보로 https://dreameredunet.com/mypage/subscribe" +
                                    "들어가셔서 확인이 가능합니다.\n\n아이들의 꿈을 응원하는 드리머에듀넷이 되겠습니다.\n감사합니다.\"\n" +
                                    "        }\n" ;
                }else{
                    messages =
                            "        {\n" +
                                    "            \"callphone\" : \"82-"+cellPhone.get(i).substring(1)+"\",\n" +
                                    "            \"template_code\":\"ex_date\",            \n" +
                                    "            \"text\":\"안녕하세요! \n 아이들의 꿈을 응원하는 드리머에듀넷입니다:)\n\n드리머에듀넷 이용 기간이 얼마 남지 않아 알려드립니다.\n\n우리 아이들의 꿈을 응원하기 위해\n드리머에듀넷과 꾸준히 함께해주시면 감사하겠습니다!\n\n마이페이지-결제 정보로 https://dreameredunet.com/mypage/subscribe" +
                                    "들어가셔서 확인이 가능합니다.\n\n아이들의 꿈을 응원하는 드리머에듀넷이 되겠습니다.\n감사합니다.\"\n" +
                                    "        },\n" ;
                }

                jsonInputString += messages;
            }

            String endString =
                    "    ]\n" +
                            "}";

            jsonInputString += endString;

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (JSONException e) {
        }catch (Exception e){
        }
    }


}
