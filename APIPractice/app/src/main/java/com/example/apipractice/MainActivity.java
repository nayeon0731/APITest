package com.example.apipractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    TextView hospName, hospAddr;
    Button button;
    Item item; //이거 일단 한번도 안썼음, 객체
    ArrayList<Item> itemArrayList = new ArrayList<Item>();// 이거는 api받아온 데이터 아이템 하나하나 배열로 만들어 놓음
    ArrayList<Item> itemViewArrayList = new ArrayList<Item>();
    RecyclerView recyclerView;

    private String key = ""; //api신청 후 받은 key 값

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);

        //여기는 뭐하는지 모르겠고
        edit = (EditText)findViewById(R.id.edit);
        hospName = (TextView) findViewById(R.id.hospName);
        hospAddr = (TextView) findViewById(R.id.hospAddr);
        button = (Button) findViewById(R.id.button);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        recyclerView = findViewById(R.id.hospList) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        HospViewAdapter adapter = new HospViewAdapter(this, itemArrayList);
        recyclerView.setAdapter(adapter);

        /*
        adapter.setOnItemClickListener(new HospViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //아이템 클릭 이벤트를 MainActivity에서 처리
                Intent intent = new Intent(MainActivity.this, MoreInfoActivity.class);
                Log.d("액티비티", "화면 전환 성공");
                intent.putExtra("name", itemViewArrayList.get(position).getHospitalName());
                Log.d("액티비티", "데이터 보내기");
                startActivity(intent);
            }
        });
        */
        showList();

    }

    void showList() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button: //button 클릭 시 (검색 버튼)
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                getXmlData(); //버튼 클릭시 파싱시작, 파싱내용 items에 객체 배열로 저장

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        for(int i = 0; i<itemArrayList.size(); i++){
                                            Log.v("태그", i+" 번째 주소 : " + itemArrayList.get(i).hospitalAddr);
                                            Log.v("태그", i+" 번째 이름 : " + itemArrayList.get(i).hospitalName);
                                        }

                                        HospViewAdapter adapter = new HospViewAdapter(itemArrayList);
                                        recyclerView.setAdapter(adapter);

                                        for (int i=0; i<itemArrayList.size(); i++) {
                                            Log.v("??", "잘 되나?");
                                            itemViewArrayList.add(i, itemArrayList.get(i));
                                            Log.v("??", "잘 되나?");
                                            itemViewArrayList.add(i, item);

                                             itemViewArrayList.add(i, item);
                                        }
                                    }
                                });
                            }
                        }).start();
                        break;
                }
            }
        });
    }


    void getXmlData() {
        String hospitalName = null, hospitalAddr = null, hospitalXPos=null, hospitalYPos=null, hospitalPostNo=null,
        hospitalUrl=null, hospitalclCd=null, hospitalYkiho = null, hospitalTelNo = null, hospitalclCdNm = null, hospitalSidoCd = null,
        hospitalSdioCdNm = null, hospitalSgguCdNm = null;

        String str = edit.getText().toString();

        String queryUrl = "http://apis.data.go.kr/B551182/hospInfoService/getHospBasisList?ServiceKey=" + key + "&emdongNm=" + str + "&pageNo=1";
        Log.v("태그", "url" + queryUrl);

        //파싱할때만 쓰이는 임시 items
        ArrayList<Item> tmpItmes = new ArrayList<Item>();
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
                        break;
                    case XmlPullParser.START_TAG:

                        tag = xpp.getName();

                        if (tag.equals("item")) {
                            // 아이템 시작
                            hospitalName = null;
                            hospitalAddr = null;
                            hospitalXPos = null;
                            hospitalYPos = null;
                            hospitalPostNo = null;
                            hospitalUrl = null;
                            hospitalclCd = null;
                            hospitalYkiho = null;
                            hospitalTelNo = null;
                            hospitalclCdNm = null;
                            hospitalSidoCd = null;
                            hospitalSdioCdNm = null;
                            hospitalSgguCdNm = null;
                        }
                        else if (tag.equals("addr")) {
                            // 주소
                            xpp.next();
                            hospitalAddr = xpp.getText();
                            Log.v("태그", "주소 " + xpp.getText());
                        } else if (tag.equals("yadmNm")) {
                            // 병원명
                            xpp.next();
                            hospitalName = xpp.getText();
                        } else if (tag.equals("XPos")) {
                            // x좌표
                            xpp.next();
                            hospitalXPos = xpp.getText();
                        }
                        else if (tag.equals("YPos")) {
                            // y좌표
                            xpp.next();
                            hospitalYPos = xpp.getText();
                        }
                        else if (tag.equals("postNo")) {
                            // 우편번호
                            xpp.next();
                            hospitalPostNo = xpp.getText();
                        }
                        else if (tag.equals("hospUrl")) {
                            // 홈페이지
                            xpp.next();
                            hospitalUrl = xpp.getText();
                        }
                        else if (tag.equals("clCd")) {
                            // 종별코드
                            xpp.next();
                            hospitalclCd = xpp.getText();
                        }
                        else if (tag.equals("ykiho")) {
                            // 암호화된 요양기호
                            xpp.next();
                            hospitalYkiho = xpp.getText();
                        }
                        else if (tag.equals("telno")) {
                            // 전화번호
                            xpp.next();
                            hospitalTelNo = xpp.getText();
                        }
                        else if (tag.equals("clCdNm")) {
                            // 종별코드명
                            xpp.next();
                            hospitalclCdNm = xpp.getText();
                        }
                        else if (tag.equals("yadmNm")) {
                            // 병원명
                            xpp.next();
                            hospitalName = xpp.getText();
                        }
                        else if (tag.equals("sidoCd")) {
                            // 시도코드
                            xpp.next();
                            hospitalSidoCd = xpp.getText();
                        }
                        else if (tag.equals("sidoCdNm")) {
                            // 시도명
                            xpp.next();
                            hospitalSdioCdNm = xpp.getText();
                        }
                        else if (tag.equals("sgguCdNm")) {
                            // 시군구명
                            xpp.next();
                            hospitalSgguCdNm = xpp.getText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();
                        if (tag.equals("item")){
                            // 아이템 하나 끝
                            tmpItmes.add(new Item(hospitalName, hospitalAddr, hospitalXPos, hospitalYPos,
                                    hospitalPostNo, hospitalUrl, hospitalclCd, hospitalYkiho, hospitalTelNo,
                                    hospitalclCdNm, hospitalSidoCd, hospitalSdioCdNm, hospitalSgguCdNm)); //api로 받아온 정보 객체로 저장. 이거를 itemArrayList에 저장.
                        }
                        break;
                }
                eventType = xpp.next();
            }

        } catch(Exception e){
            Log.v("태그", "e = " + e);
        }
        //임시 아이템을 items에게 넘겨줌
        itemArrayList = tmpItmes;
    }

}