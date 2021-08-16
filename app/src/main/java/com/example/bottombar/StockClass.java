package com.example.bottombar;

import android.content.Context;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


class LoadUrlImage extends AsyncTask<String, Void, Bitmap> {
    ImageView img_view;
    Bitmap bitmap_result;
    public LoadUrlImage(ImageView iv_result){
        this.img_view = iv_result;
    }

    protected Bitmap doInBackground(String... strings) {
        String uri_link = strings[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(uri_link).openStream();
            bitmap = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            Log.e("Error Message", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }


    protected void onPostExecute(Bitmap result) {
        bitmap_result = result;
        String TAG = "Image";
        Log.i(TAG, "Height :" + result.getHeight());
        Log.i(TAG, "Width :" + result.getWidth());
        img_view.setImageBitmap(result);

    }

    public Bitmap getBitmap_result() {
        return bitmap_result;
    }
}

class StockListOld{
    private HashMap<Integer, ArrayList<String>> code_name = new HashMap<Integer, ArrayList<String>>();
    public StockListOld(){

    }

    public void LoadAllStockList() {

    }

    // Only String or Integer
    public HashMap<Integer, ArrayList<String>> SearchStockName(Object stock_name){
        HashMap<Integer, ArrayList<String>> search_result = new HashMap<Integer, ArrayList<String>>();
        if(stock_name instanceof String){
            // Korean or English name
            // 한글이나 영어시 해당 조건으로
            ArrayList<String> ko_en_name = new ArrayList<String>();
            ko_en_name.add("삼성전자");
            ko_en_name.add("samsung_elec");
            search_result.put(5930, ko_en_name);
        }
        else if(stock_name instanceof Integer){
            // Code of stock
        }



        //ArrayList<String> ko_en_name2 = new ArrayList<String>();
        //ko_en_name2.add("애플");
        //ko_en_name2.add("apple");
        //search_result.put(4561, ko_en_name2);

        return search_result;
    }
}

class StockData {

    private String name_kr; // 종목 한글 이름
    private String name_en; // 종목 영어 이름
    private int stock_code; // 종목 코드, 숫자

    private float cur_price; // 현재 가격
    private float prev_price; // 전날 가격

    private LoadUrlImage load_img;
    private Context context;
    public void RegularClass(Context current){
        this.context = current;
    }

    public void SetImage(ImageView img_view){
        String url = "https://www.rspcasa.org.au/wp-content/uploads/2019/01/Adopt-a-cat-or-kitten-from-RSPCA.jpg";
        load_img = (LoadUrlImage) new LoadUrlImage(img_view).execute(url);
    }

    public String GetNameKR(){

        return name_kr;
    }

    public String GetNameEN(){

        return name_en;
    }


    public void GetAllStockInfo(int code) {
        // 모든 정보를 서버로부터 한번에 받아온다. 필요 정보만 나눠 가져오면 오래 걸린다.
        cur_price = 10000;
        prev_price = 9910;
        name_kr = "샘성전자";
        name_en = "samsung elec";
    }


    public float GetCurPrice() { // 현재 주가

        return cur_price;

    }

    public float GetDiffPricePercent() { // 전날 대비 증감
        return cur_price / prev_price * 100;
    }
}

class StockCondition {

    private float target_high_price;
    private float target_low_price;

    StockData stock_data;
    StockCondition(StockData sd){
        stock_data = sd;
    }

    public void SetTargetPrice(float high_price, float low_price){
        target_high_price = high_price;
        target_low_price = low_price;
    }


    public boolean IsHighTargetPrice(){
        if (stock_data.GetCurPrice() > target_high_price){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean IsLowTargetPrice(){
        if (stock_data.GetCurPrice() < target_low_price){
            return true;
        }
        else{
            return false;
        }
    }
}


class StockConditionList {

    private int user_id;
    private int condition_num = 0;

    private HashMap<Integer, StockCondition> condition_list = new HashMap<Integer, StockCondition>();
    private HashMap<Integer, StockData> stock_list = new HashMap<Integer, StockData>();


    public void AddCondition(Integer stock_code) {
//        stock_code = 5930; // 삼성전자코드

        // Stock List
        StockData st;
        st = stock_list.get(stock_code); // 이미 다운로드된 데이터가 있다면 굳이 초기화 할 필요 없이 넘어가고, 만약 없으면 초기화 해야한다.
        if (st == null) {
            st = new StockData();
            st.GetAllStockInfo(stock_code);
            stock_list.put(stock_code, st);
        }


        StockCondition cond = new StockCondition(st); // 불러온 데이터 조건에 추가
        cond.SetTargetPrice(10001, 8000); // 조건 설정

        condition_list.put(condition_num, cond); // 만들어진 조건을 리스트에 저장
        condition_num = condition_num + 1;  // 리스트의 카운트를 증가
    }

    public HashMap<Integer, StockCondition> GetStockConditions(){
        return condition_list;
    }

    public void AddConditionTargetPrice(Integer stock_code, float high_price, float low_price) {
    }
}


class AlertSetting {

    private Object price;
    private Object period;
    private Object change;
    private int conditional;

    HashMap<Integer,Object> priceData = new HashMap<Integer,Object>();
    HashMap<Integer,Object> changeData = new HashMap<Integer,Object>();
    HashMap<Integer,Object> periodData = new HashMap<Integer,Object>();

    public void AlertValue(int val1 , Object val2){

        int i = 1;

        while(true) {
            //키가 존재하면 True  존재하지 않으면 False

            // 1번 Price , 135 들어옴
            //  price : va1 = 1 , val2 = 135

            //priceData.containsKey(1) = 값이없어 그러니까 false가 나올거 !false = true
            if (val1 == 1 ) {
                price = val2;
                priceData.put(i, price);
                if(!changeData.get(i).equals(0)){}
                else{changeData.put(i,0);}
                if(!periodData.get(i).equals(0)){}
                else{periodData.put(i,0);}
                break;
            } else if (val1 == 2) {
                change = val2;
                changeData.put(i, change);
                if(!priceData.get(i).equals(0)){}
                else{priceData.put(i,0);}
                if(!periodData.get(i).equals(0)){}
                else{periodData.put(i,0);}
                break;
            } else if (val1 == 3) {
                period = val2;
                periodData.put(i, period);
                if(!priceData.get(i).equals(0)){}
                else{priceData.put(i,0);}
                if(!changeData.get(i).equals(0)){}
                else{changeData.put(i,0);}
                break;
            }

            i++;

        }
        // 같은 번호의 해시맵끼리 조건을 만들면됨 .


        // 처음에 해당 키값에 값을 넣음.
        // 두번째에 들어왔을떄, 해당 키에 값이 있으면 키값을 증가시킴.
        // 근데 없는 키값이 있으면(?) 같이 증가시켜줘야하는데말이지 ..
        // ex) price 값만들어옴
        // alertData.put("price" , price) 이 들어감.
        // 나머지 두개는 어떻게해?

        //만약 해시맵을 3개로 나눠, price , change , period
        // price 값만 들어갔어
        // priceData.get(1)

        //conditional = val3;

        //val3 1이면 AND 2면 OR

//        if (val3 == 1){
//            alertData.containsKey("price");
//            alertData.containsKey("change");
//            alertData.containsKey("period");
//
//            if(alertData.get("price").equals("134") || alertData.get("change").equals("3")){
//
//            }else if(alertData.get("price").equals("134") || alertData.get("period").equals("20")){
//
//            }
//
//
//
//            //price , change, period
//            //price || change
//            //price || period
//            //change || period
//
//        }else if(val1 == 2){
//
//        }


        // 해당값이 무엇인지 넣어주는거.

        // AND 랑 OR 구분해줘야함 ..
        // 그다음에 위에값들 가지고 조건식을 만들어야함.
        // 만약
        // Price 130 AND Period 30일
        // val1.equals(해당가격) || val3.equals(기간)




    }

    //val1 , val2

    // ANd , OR


}