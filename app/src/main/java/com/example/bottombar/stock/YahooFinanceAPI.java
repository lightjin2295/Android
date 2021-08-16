package com.example.bottombar.stock;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class YahooFinanceAPI extends AsyncTask<String, Void, JSONObject> {

    String stock_url;
    String company_symbol_name;
    ArrayList<String> search_params;

    JSONObject json_result = null;

    String func = "stock/v2/get-chart?";
    String param_interval = "interval=";
    String val_interval = "1d";
    String param_symbol = "symbol=";
    String param_range = "range=";
    String val_range = "1d";
    String param_search = "q=";
    String val_search;
    String stock_type = "EQUITY"; // INDEX EQUITY

    String func_selector;

    private static final int BUFFER_SIZE = 4096;

    private Context context;
    public YahooFinanceAPI(Context current_context){
        this.context = current_context;
    }

    @Override
    protected JSONObject doInBackground(String... str) {
        func_selector = str[0];
        company_symbol_name = str[1];

        if(search_params == null){
            search_params = new ArrayList<String>();
        }else{
            search_params.clear();
        }
        // ********* getCurrentStatus() ***********
        // interval: One of the following is allowed 1m|2m|5m|15m|60m|1d
        // range: One of the following is allowed 1d|5d|1mo|3mo|6mo|1y|2y|5y|10y|ytd|max.
        // val_interval = search_params.get(0);
        // val_range = search_params.get(1);
        // stock_type = search_params.get(2); // INDEX EQUITY
        for (int i = 2; i < str.length; i++){
//            System.out.println("STR: " + str[i]);
            search_params.add(str[i]);
        }


        stock_url = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/";
        Log.d("url", stock_url);
//        getSummary();


//        getChart("10m", "1d");
        switch (func_selector){
            case "0":
                return getCurrentStatus();
            case "1":
                return searchStocks();
            case "2":
                return getKoStockList();
        }

        return getCurrentStatus();
//        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        json_result = result;
    }

    public JSONObject getAllJsonDoc(){
        JSONObject json = null;
        try {

            URL url_text = new URL(stock_url); // 파싱하고자하는 URL

            HttpURLConnection http=(HttpURLConnection)url_text.openConnection();
            http.addRequestProperty("x-rapidapi-key", "19fc289b0bmsh229e1e7f38185ddp11b8f1jsn61816018aeeb");
            http.addRequestProperty("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com");
            http.setRequestMethod("GET");
            http.setDoInput(true);
            http.connect();
            InputStream is = http.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String currentLine;


            try{
                while ((currentLine = bufferedReader.readLine()) != null){
                    stringBuilder.append(currentLine);

//                    System.out.println(currentLine);
                }

                JSONTokener token = new JSONTokener(stringBuilder.toString());
                json = new JSONObject(token);

                System.out.println("JSON loading finished");

            }
            catch (IOException error){

            }catch (JSONException error){

            }



//            String tmp = json.get("quotes").toString();
//            System.out.println(tmp);

        } catch (Exception e) {
            Log.e("dd", "Error in network call", e);
        }

        return json;
    }

    public JSONObject getKoStockList(){
        stock_url = "http://kind.krx.co.kr/corpgeneral/corpList.do?method=download&searchType=13";
        File saveDir = context.getFilesDir();
        JSONObject json = null;
        try {

            URL url_text = new URL(stock_url); // 파싱하고자하는 URL

            HttpURLConnection http=(HttpURLConnection)url_text.openConnection();
            http.setRequestMethod("GET");
            http.setDoInput(true);
            http.connect();
//            InputStream is = http.getInputStream();
//
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
//            StringBuilder stringBuilder = new StringBuilder();
//            String currentLine;


            // get redirect url from "location" header field
            String newUrl = http.getHeaderField("Location");

            System.out.println(newUrl);

            // open the new connnection again
            http = (HttpURLConnection) new URL(newUrl).openConnection();

            int responseCode = http.getResponseCode();
            // always check HTTP response code first
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = "";
                String disposition = http.getHeaderField("Content-Disposition");
                String contentType = http.getContentType();
                int contentLength = http.getContentLength();

                if (disposition != null) {
                    // extracts file name from header field
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 10,
                                disposition.length() - 1);
                    }
                } else {
                    // extracts file name from URL
                    fileName = stock_url.substring(stock_url.lastIndexOf("/") + 1,
                            stock_url.length());
                }

                fileName = "stock_list_ko.xls";

                System.out.println("Content-Type = " + contentType);
                System.out.println("Content-Disposition = " + disposition);
                System.out.println("Content-Length = " + contentLength);
                System.out.println("fileName = " + fileName);

                // opens input stream from the HTTP connection
                InputStream inputStream = http.getInputStream();
                String saveFilePath = saveDir + File.separator + fileName;



                // opens an output stream to save into file
                FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();

                System.out.println("File downloaded: " + saveFilePath);
            } else {
                System.out.println("No file to download. Server replied HTTP code: " + responseCode);
            }
            http.disconnect();



        } catch (Exception e) {
            Log.e("dd", "Error in network call", e);
        }

        return json;
    }


    public JSONObject getCurrentStatus(){
        // https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/get-charts

//        interval: One of the following is allowed 1m|2m|5m|15m|60m|1d
//        range: One of the following is allowed 1d|5d|1mo|3mo|6mo|1y|2y|5y|10y|ytd|max.
        val_interval = search_params.get(0);
        val_range = search_params.get(1);
        stock_type = search_params.get(2); // INDEX EQUITY

        if(stock_type == "INDEX"){
            func = "market/get-charts?";
        }
        else if (stock_type == "EQUITY"){
            func = "stock/v2/get-chart?";
        }
        else{
            func = "stock/v2/get-chart?";
        }

        stock_url = stock_url + func + param_interval + val_interval;
        stock_url = stock_url + "&" + param_symbol + company_symbol_name;
        stock_url = stock_url + "&" + param_range + val_range;
        stock_url = stock_url + "&region=US";

        JSONObject json = null;
        HashMap<String, String> cur_stat = new HashMap<String, String>();

//        JSONObject json_collected = new JSONObject();

        try {
            json = getAllJsonDoc();

//            if(json != null){
//                JSONObject json_chart = null;
//                json_chart = json.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONObject("meta");
//
//                double cur_stock_price = json_chart.getDouble("regularMarketPrice");
//                double prev_stock_price = json_chart.getDouble("chartPreviousClose");
//                String stock_currency = json_chart.get("currency").toString();
//                String time_zone = json_chart.get("exchangeTimezoneName").toString();
//
//                double daily_change = cur_stock_price - prev_stock_price;
//                double daily_change_percent = daily_change / prev_stock_price * 100;
//
//                json_collected.put("regularMarketPrice", cur_stock_price);
//                json_collected.put("chartPreviousClose", prev_stock_price);
//                json_collected.put("dailyChange", daily_change);
//                json_collected.put("dailyChangePercent", daily_change_percent);
//                json_collected.put("currency", stock_currency);
//                json_collected.put("exchangeTimezoneName", time_zone);
//
//
////                int pre_start_time = json_chart.getJSONObject("currentTradingPeriod").getJSONObject("pre").getInt("start");
//
//                JSONObject market_time = json_chart.getJSONObject("currentTradingPeriod");
//                json_collected.put("currentTradingPeriod", market_time);
//
//                JSONArray timestamp = null;
//                timestamp = json.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONArray("timestamp");
//                json_collected.put("timestamp", timestamp);
//
//                // Add Close Price
//                JSONArray close_price_by_time = null;
//                close_price_by_time = json.getJSONObject("chart").getJSONArray("result").getJSONObject(0).getJSONObject("indicators").getJSONArray("quote").getJSONObject(0).getJSONArray("close");
//                json_collected.put("closePrice", close_price_by_time);
//
//                System.out.println(cur_stock_price);
//                System.out.println(prev_stock_price);
//                System.out.println(stock_currency);
////
//                System.out.println(json_collected.toString());
//            }


        } catch (Exception e) {
            Log.e("API", "Error in json call", e);
        }

        return json;
    }


    public JSONObject searchStocks(){
        func = "auto-complete?";

        stock_url = stock_url + func;
        stock_url = stock_url + "&" + param_search + company_symbol_name;
        stock_url = stock_url + "&region=US";

        JSONObject json = null;
        HashMap<String, String> cur_stat = new HashMap<String, String>();

        JSONObject json_collected = new JSONObject();

        try {
            json = getAllJsonDoc();
            if(json != null){
                // Do something
                json_collected.put("quotes", json.getJSONArray("quotes"));
            }

        } catch (Exception e) {
            Log.e("API", "Error in json call", e);
        }

        return json_collected;
    }


    public void getSummary(){

    }
}
