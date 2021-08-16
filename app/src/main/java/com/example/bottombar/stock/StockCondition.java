package com.example.bottombar.stock;


public class StockCondition {

    private float target_high_price;
    private float target_low_price;

    StockData stock_data;
    public StockCondition(){
    }

    public void SetStockData(StockData sd){
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
