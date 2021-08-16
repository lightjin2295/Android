package com.example.bottombar.stock;

import android.content.Context;
import android.widget.ImageView;

import com.example.bottombar.file_io.FileIO;
import com.example.bottombar.utils.LoadUrlImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class StockData {

    private String name_kr; // 종목 한글 이름
    private String name_en; // 종목 영어 이름
    private String stock_symbol; // 종목 코드, 숫자
    private String stock_type; // INDEX, EQUITY

    private double cur_price; // 현재 가격
    private double prev_price; // 전날 가격

    private String currency; // 화폐종류 ex) USD,...

    private String current_time;
    private String time_zone;
    public StockData(){
        name_kr = "";
        name_en = "";
        stock_symbol = "";
        current_time = "";
        cur_price = (double) 0.0;
        prev_price = (double) 0.0;
    }


    private LoadUrlImage load_img;
    private Context context;
    public StockData(Context current){
        this.context = current;
    }

    public void SetImage(ImageView img_view){
        String url = "https://www.rspcasa.org.au/wp-content/uploads/2019/01/Adopt-a-cat-or-kitten-from-RSPCA.jpg";
        load_img = (LoadUrlImage) new LoadUrlImage(img_view).execute(url);
    }

    public void SetNameKr(String name){
        name_kr = name;
    }
    public void SetNameEn(String name){
        name_en = name;
    }
    public void SetStockSymbol(String symbol){
        stock_symbol = symbol;
    }
    public void SetStockType(String type) {stock_type = type;}
    public void SetCurPrice(double price){
        cur_price = price;
    }
    public void SetPrevPrice(double price){
        prev_price = price;
    }


    public String GetNameKr(){
        return name_kr;
    }
    public String GetNameEn(){
        return name_en;
    }
    public String GetStockSymbol(){
        return stock_symbol;
    }
    public String GetStockType() {return stock_type;}
    public double GetCurPrice(){
        return cur_price;
    }
    public double GetPrevPrice(){
        return prev_price;
    }
    public double GetDailyChange(){
        return (cur_price - prev_price);
    }
    public double GetDailyChangePercent(){
        return ((cur_price - prev_price) / prev_price) * 100;
    }



    public void LoadAllInfoFromServer(String symbol, String interval, String range, String type){
        // interval: One of the following is allowed 1m|2m|5m|15m|60m|1d
        // range: One of the following is allowed 1d|5d|1mo|3mo|6mo|1y|2y|5y|10y|ytd|max.
        // type: INDEX EQUITY
        String[] send_data = {"0", symbol, interval, range, type} ; //getCurrentStatus

        try {
            JSONObject json_collected = (JSONObject) new YahooFinanceAPI(this.context).execute(send_data).get();
            if(json_collected != null){
                JSONObject json_chart = null;
                json_chart = json_collected.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONObject("meta");


                cur_price = json_chart.getDouble("regularMarketPrice");// 현재 가격
                prev_price = json_chart.getDouble("previousClose"); // 전날 가격

                currency = json_chart.get("currency").toString();
                time_zone = json_chart.get("exchangeTimezoneName").toString();

                current_time = json_chart.get("regularMarketTime").toString();

//                int pre_start_time = json_chart.getJSONObject("currentTradingPeriod").getJSONObject("pre").getInt("start");

                JSONObject market_time = json_chart.getJSONObject("currentTradingPeriod");

                JSONArray timestamp = null;
                timestamp = json_collected.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONArray("timestamp");
                // Add Close Price
                JSONArray close_price_by_time = null;
                close_price_by_time = json_collected.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("close");


//                System.out.println("555555555555555555");
//                System.out.println(cur_price);
//                System.out.println(prev_price);
//                System.out.println(currency);
//
//                System.out.println(json_collected.toString());

                String file_name = symbol + "_" + interval + "_" + range + "_" + current_time + ".json";
                FileIO file_io = new FileIO(this.context);
                file_io.Write(json_collected, file_name);
//                JSONObject jjj = (JSONObject) new FileIO(this.context).execute(json_collected).get();
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



}
