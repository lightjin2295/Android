package com.example.bottombar.stock;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import jxl.Sheet;
import jxl.Workbook;

public class StockList{
    private HashMap<String, ArrayList<String>> code_name = new HashMap<String, ArrayList<String>>();

    private Context context;
    public StockList(Context current_context){
        this.context = current_context;
    }

//    DBHelper dbHelper;
//    DBQueries dbQueries;


    public ArrayList<StockData> SearchStockKoEn(String stock_str){
        ArrayList<StockData> stock_list_en;
        ArrayList<StockData> stock_list_ko;

        // 1. Search Korean List first.
        stock_list_ko = SearchStockInKoList(stock_str);
        // 2. If there is result, then search symbols
        int num = stock_list_ko.size();
        //0816 TEST COMMIT
        if(num > 0){
            String symbols = "";
            for(StockData stock : stock_list_ko){
                symbols += stock.GetStockSymbol();
                symbols += ",";
            }
            stock_list_en = SearchStockInYahoo(symbols);

            // Save English name to Korean List
            for(int i = 0; i < num; i++){
                for(StockData stock_en : stock_list_en){
                    if(stock_list_ko.get(i).GetStockSymbol() == stock_en.GetStockSymbol()){
                        stock_list_ko.get(i).SetNameEn(stock_en.GetNameEn());
                        break;
                    }
                }
            }

            return stock_list_ko;
        }
        // 3. If there is no result in Korean list
        else {
            stock_list_en = SearchStockInYahoo(stock_str);
            num = stock_list_en.size();
            if(num < 1){
                stock_list_en = new ArrayList<StockData>();
                return stock_list_en;
            }
            for(int i = 0; i < num; i++){
                // Search Korean Name by Symbol
                String symbol = stock_list_en.get(i).GetStockSymbol();

                // KS: KOSPI, KQ: KOSDAQ
                if(symbol.contains(".KQ") || symbol.contains(".KS") || symbol.contains(".kq") || symbol.contains(".ks")){
                    String cut_name = symbol.substring(0, symbol.lastIndexOf(".")); // 005930.KR -> 005930
                    stock_list_ko = SearchStockInKoList(cut_name);
                } else{

                }
                // Save Korean name to English list
                for(StockData stock_ko : stock_list_ko){
                    stock_list_en.get(i).SetNameKr(stock_ko.GetNameKr());
                }
            }

            return stock_list_en;
        }

    }

    public ArrayList<StockData> SearchStockInYahoo(String stock_str){
        ArrayList<StockData> yahoo_stock_list = new ArrayList<StockData>();
        String[] send_data = {"1", stock_str} ;
        try {
            JSONObject json_collected = (JSONObject) new YahooFinanceAPI(this.context).execute(send_data).get();

            JSONArray json_arr = json_collected.getJSONArray("quotes");
            int num = json_arr.length();

            for(int i = 0; i < num; i++){
                boolean isYahooFinance = json_arr.getJSONObject(i).getBoolean("isYahooFinance");
                if(isYahooFinance){
                    StockData yahoo_stock = new StockData(this.context);
                    if(json_arr.getJSONObject(i).has("longname")){
                        yahoo_stock.SetNameEn(json_arr.getJSONObject(i).getString("longname"));
                    }else if(json_arr.getJSONObject(i).has("shortname")){
                        yahoo_stock.SetNameEn(json_arr.getJSONObject(i).getString("shortname"));
                    }
                    yahoo_stock.SetStockSymbol(json_arr.getJSONObject(i).getString("symbol"));
                    yahoo_stock.SetStockType(json_arr.getJSONObject(i).getString("quoteType"));
                    yahoo_stock_list.add(yahoo_stock);
                }
                else {

                }
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return yahoo_stock_list;
    }


    // Only String or Integer
    public ArrayList<StockData> SearchStockInKoList(String stock_name){
//        HashMap<String, ArrayList<String>> search_result = new HashMap<String, ArrayList<String>>();
        ArrayList<StockData> search_result = new ArrayList<StockData>();


        HashMap<String, ArrayList<String>> stock_all_list = ReadStockList();

        for (Map.Entry<String, ArrayList<String>> entry : stock_all_list.entrySet()) {

            if (entry.getValue().get(0).contains(stock_name)) { // KOREAN

//                search_result.put(entry.getKey(), entry.getValue());
            } else if (entry.getValue().get(1).contains(stock_name)) { // ENGLISH

            }
            else if(entry.getKey().contains(stock_name)){ // CODE, SYMBOL

            }
            else{
                continue;
            }
            StockData stock_ko = new StockData(this.context);
            stock_ko.SetStockSymbol(entry.getKey());
            stock_ko.SetNameKr(entry.getValue().get(0));
            search_result.add(stock_ko);
        }

//        ArrayList<String> ko_en_name = new ArrayList<String>();
//        ko_en_name.add("삼성전자");
//        ko_en_name.add("samsung_elec");
//        search_result.put("5930", ko_en_name);
//
//        ArrayList<String> ko_en_name2 = new ArrayList<String>();
//        ko_en_name2.add("애플");
//        ko_en_name2.add("apple");
//        search_result.put("4561", ko_en_name2);

        return search_result;
    }


    // Read Stock List from Excel file
    public HashMap<String, ArrayList<String>> ReadStockList() {
        HashMap<String, ArrayList<String>> all_list = new HashMap<String, ArrayList<String>>();
        try {
            //파일읽기

            InputStream is = this.context.getResources().getAssets().open("company_list.xls");

            //엑셀파일
            Workbook wb = Workbook.getWorkbook(is);

            //엑셀파일이 있다면
            if(wb != null) {
                System.out.println("Stock List Loaded");
                Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                if(sheet != null) {
                    int colTotal = sheet.getColumns();    // 전체 컬럼
                    int rowIndexStart = 1;                  // row 인덱스 시작
                    int rowTotal = sheet.getColumn(colTotal-1).length;

//                    StringBuilder sb;
                    for(int row=rowIndexStart; row<rowTotal; row++) {
//                        sb = new StringBuilder();

//                        //col: 컬럼순서, contents: 데이터값
//                        for(int col=0; col < colTotal; col++) {
//                            String contents = sheet.getCell(col, row).getContents();
//
//                            Log.d("Main", col + "번째: " + contents);
//                        }

                        String stock_code_string = sheet.getCell(1, row).getContents(); // Code such as 005930
                        ArrayList<String> ko_en_name = new ArrayList<String>();
                        ko_en_name.add(sheet.getCell(0, row).getContents()); // Korean name
                        ko_en_name.add(""); // English name

//                        System.out.println(sheet.getCell(1, row).getContents());
                        all_list.put(stock_code_string, ko_en_name);

                    }//내부 for
                }//외부 for
            }
            else{
                System.out.println("No Stock List File");
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

        return all_list;
    } //readExcel()
}
