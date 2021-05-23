package com.example.apipractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText edit;
    TextView text;

    String key = ""; //api신청 후 받은 key 값
    ItemClass AsyncItemClass = new ItemClass();

    ArrayList<String> ClCdNmArray = new ArrayList<>();
    ArrayList<String> AddrArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        edit = (EditText)findViewById(R.id.edit);
        text = (TextView) findViewById(R.id.text);
    }

    //Button을 클릭했을 때 자동으로 호출되는 callback Method
    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.button:

            new Thread(new Runnable() {
                @Override
                public void run() {
                    getXmlData();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text.setText(AsyncItemClass.AddrArray.toString());
                            Log.v("태그", "배열길이는 = " + AsyncItemClass.AddrArray.size());
                            for(int i = 0; i < AsyncItemClass.AddrArray.size() ;i++){
                                Log.v("태그", i+" 번째 Addr : " + AsyncItemClass.AddrArray.get(i));
                                Log.v("태그", i+" 번째 ClCdNm : " + AsyncItemClass.ClCdNmArray.get(i));
                            }
                        }
                    });
                }
            }).start();
            break;
        }
    }


    void getXmlData() {
        StringBuffer buffer = new StringBuffer();
        String str = edit.getText().toString();

        String queryUrl = "http://apis.data.go.kr/B551182/hospInfoService/getHospBasisList?ServiceKey=" + key + "&emdongNm=" + str + "&pageNo=1";
        Log.v("태그", "url" + queryUrl);

        try {
            URL url = new URL(queryUrl);
            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            Log.v("태그", "api 전송후");

            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tag;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;
                    case XmlPullParser.START_TAG:

                        tag = xpp.getName();

                        if (tag.equals("item")) Log.v("태그", "아이템발견!!!!!!!!!!!"); //아이템 발견 => 아이템 시작
                         else if (tag.equals("clCdNm")) {
                            buffer.append("종별코드명: ");
                            xpp.next();
                            buffer.append("\n");
                            ClCdNmArray.add(xpp.getText());
                            Log.v("태그", "종별코드 " + xpp.getText());
                        } else if (tag.equals("addr")) {
                            buffer.append("주소: ");
                            xpp.next();
                            buffer.append("\n");
                            AddrArray.add(xpp.getText());
                            Log.v("태그", "주소 " + xpp.getText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();
                        if (tag.equals("item")) buffer.append("\n");
                        break;
                }
                eventType = xpp.next();
            }

        } catch(Exception e){
            Log.v("태그", "e = " + e);
        }
        buffer.append("파싱 끝");

        AsyncItemClass.ClCdNmArray = ClCdNmArray;
        AsyncItemClass.AddrArray = AddrArray;

    }

}