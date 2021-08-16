package com.example.bottombar.stock;

import android.content.Context;

import java.util.HashMap;

public class StockConditionGroup {

    private Context context;
    public StockConditionGroup(Context current){
        this.context = current;
    }

    private int user_id;
    private int condition_num = 0;

    private HashMap<Integer, StockCondition> condition_list = new HashMap<Integer, StockCondition>();
    private HashMap<String, StockData> stock_list = new HashMap<String, StockData>();


    public void AddCondition(String stock_code) {
//        stock_code = 5930; // 삼성전자코드

        // Stock List
        StockData st;
        st = stock_list.get(stock_code); // 이미 다운로드된 데이터가 있다면 굳이 초기화 할 필요 없이 넘어가고, 만약 없으면 초기화 해야한다.
        if (st == null) {
            st = new StockData(this.context);
            stock_list.put(stock_code, st);
        }


        StockCondition cond = new StockCondition(); // 불러온 데이터 조건에 추가
        cond.SetStockData(st);
        cond.SetTargetPrice(10001, 8000); // 조건 설정

        condition_list.put(condition_num, cond); // 만들어진 조건을 리스트에 저장
        condition_num = condition_num + 1;  // 리스트의 카운트를 증가
    }

    public void AddConditionTargetPrice(String stock_code, float high_price, float low_price) {
//        stock_code = 5930; // 삼성전자코드

        // Stock List
        StockData st;
        st = stock_list.get(stock_code); // 이미 다운로드된 데이터가 있다면 굳이 초기화 할 필요 없이 넘어가고, 만약 없으면 초기화 해야한다.
        if (st == null) {
            st = new StockData(this.context);
            stock_list.put(stock_code, st);
        }


        StockCondition cond = new StockCondition(); // 불러온 데이터 조건에 추가
        cond.SetStockData(st);
        cond.SetTargetPrice(high_price, low_price); // 조건 설정

        condition_list.put(condition_num, cond); // 만들어진 조건을 리스트에 저장
        condition_num = condition_num + 1;  // 리스트의 카운트를 증가
    }

    public HashMap<Integer, StockCondition> GetStockConditions(){
        return condition_list;
    }
}
