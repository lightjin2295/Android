package com.example.bottombar;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;


import com.example.bottombar.stock.StockCondition;
import com.example.bottombar.stock.StockConditionGroup;
import com.example.bottombar.stock.StockData;
import com.example.bottombar.stock.StockList;
import com.example.bottombar.stock.YahooFinanceAPI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class StockActivity extends AppCompatActivity {


    //private List<String> items = Arrays.asList("APPLE","삼성","SAMSUNG","TSLA");

//    int images[] = {R.drawable.aapl,R.drawable.samsung,R.drawable.tsla};
    String emails[] = {"This is aapl","This is samsung","This is tsla"};
//    String names[] = {"aapl","samsung","Tsla"};

    List<ItemsModel> listItems = new ArrayList<>();

    ListView listView;

    CustomAdapter customAdapter;

    ArrayAdapter<String> adapter;
    Object stock ;
    Object returnResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        listView = findViewById(R.id.listview);

//        for(int i = 0; i< names.length; i++){
//            //ItemsModel 에 추가
//            ItemsModel itemsModel = new ItemsModel(names[i],emails[i]);
//
//            listItems.add(itemsModel);
//
//            //현재는 리스트로 name , email에 값을 넣어서 itemsModel에 추가해주고있음. 20210617
//            //지금 검색리스트를 뿌려줘야 되는 상황은, 검색창에 값을 입력할때마다 일치하는 값들을 뿌려줘야함.
//
//        }
//
//        customAdapter = new CustomAdapter(listItems, this);
//
//        listView.setAdapter(customAdapter);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search,menu);

        MenuItem menuItem = menu.findItem(R.id.search_view);

        //TextView resultTextView = findViewById(R.id.textView);
        //resultTextView.setText(getResult());

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            //    검색에서 확인을 눌렀을시
            public boolean onQueryTextSubmit(String newText) {

                StockSearch(newText);

                //resultTextView.setText(search(newText));
//                stock = (Object)newText;
//                System.out.println("Stcok: " +stock);
//
//                StockListOld sl = new StockListOld();
//                HashMap<Integer, ArrayList<String>> stock_list = sl.SearchStockName(stock);
//
//                // 조건
//                StockConditionList scl = new StockConditionList();
//
//                Integer stock_code = null; // 종목 코드
//                //Integer stock_code = 5930; // 종목 코드
//
//
//                // 검색된 주식 목록을 가져와서 코드, 이름 등을 사용한다.
//                Set<Integer> stock_keys = stock_list.keySet();
//                for(Integer key: stock_keys){
//                    System.out.println("Code: " + key);
//                    ArrayList<String> stock_names = stock_list.get(key);
//                    System.out.println("Ko: " + stock_names.get(0));
//                    System.out.println("En: " + stock_names.get(1));
//                    System.out.println("key: " + key);
//
//                    stock_code = key; // 가져온 코드를 아래에서 사용
//                }
//
//
//                Collection<ArrayList<String>> result = stock_list.values();
//                System.out.println("stock_list" + stock_list.get(stock_code));

                return true;
            }


            //데이터 검색할때

            long lastClickedTime = 0;
            long deleyTime = 5000;
            long nowTime = System.currentTimeMillis();

            @Override
            public boolean onQueryTextChange(String newText) {


//                customAdapter.getFilter().filter(newText);
//                //검색 0617
//
//
//                if(lastClickedTime == 0){
//                    System.out.println("처음시작");
//                    lastClickedTime = nowTime;
//                    StockSearch(newText);
//                }else{
//                    if(lastClickedTime + deleyTime < nowTime){
//                        System.out.println("딜레이중입니다");
//                        System.out.println("마지막클릭시간" + lastClickedTime);
//                        System.out.println("딜레이시간" + deleyTime);
//                        System.out.println("현재시간" + nowTime);
//                        lastClickedTime = nowTime;
//                    }else{
//                        System.out.println("딜레이후 시작");
//                        System.out.println("마지막클릭시간" + lastClickedTime);
//                        System.out.println("딜레이시간" + deleyTime);
//                        System.out.println("현재시간" + nowTime);
//
//                        lastClickedTime = nowTime;
//                        StockSearch(newText);
//                    }
//                }
//
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        StockSearch(newText);
//                    }
//                }, 3000); //딜레이 타임 조절



//                StockSearch(newText);
//
//                for(int i = 0; i< names.length; i++){
//                    //ItemsModel 에 추가
//                    ItemsModel itemsModel = new ItemsModel(names[i],emails[i]);
//
//                    listItems.add(itemsModel);
//
//                    //현재는 리스트로 name , email에 값을 넣어서 itemsModel에 추가해주고있음. 20210617
//                    //지금 검색리스트를 뿌려줘야 되는 상황은, 검색창에 값을 입력할때마다 일치하는 값들을 뿌려줘야함.
//
//                }

//                customAdapter = new CustomAdapter(listItems, this);
//
//                listView.setAdapter(customAdapter);

                return false;
            }
        });

        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    //박이진
    //야후의경우에는 3개까지만 검색되도록 되어있음.
    //인터벌을 1초정도줘서
    private void StockSearch(String symbol){
        Log.e("Main"," data search"+symbol);
        StockList sl = new StockList(this);
        sl.ReadStockList();
        int size;

        String stock_name_ex = symbol ;

        ArrayList<StockData> stock_list;
        stock_list = sl.SearchStockKoEn(stock_name_ex);

        System.out.println("stockSize: " + stock_list.size());

        if(stock_list.size() > 4){
            size = 4;
        }else{
            size = stock_list.size();
        }

        System.out.println("listItems1 : " + listItems);

        if(listItems.size() > 0) {
            listItems.clear();
//            adapter.notifyDataSetChanged();
            System.out.println("listItems2 : " + listItems);
        }
//      너무많은 리스트가 나오는걸 방지하기 위해서 size (4개)로 지정하여 검색되게함.
        for(int i = 0; i< size; i++){
//            stock_list.get(i).LoadAllInfoFromServer(stock_list.get(i).GetStockSymbol(), "1m","5d", stock_list.get(i).GetStockType());
            System.out.println("Symbol: " + stock_list.get(i).GetStockSymbol());
            System.out.println("Ko: " + stock_list.get(i).GetNameKr());
            System.out.println("En: " + stock_list.get(i).GetNameEn());
            System.out.println("Type: " + stock_list.get(i).GetStockType());
//            System.out.println("Current Price: " + stock_list.get(i).GetCurPrice());
            //검색화면에서는 이름이랑 Symbol만 보여주게해야함.
            //클릭시에 가격이랑 퍼센트(?)를 보여주게해야함.


            System.out.println("listItems3 : " + listItems);

            ItemsModel itemsModel = new ItemsModel(stock_list.get(i).GetStockSymbol(),stock_list.get(i).GetNameEn());

            listItems.add(itemsModel);
            customAdapter = new CustomAdapter(listItems, this);
            listView.setAdapter(customAdapter);

            System.out.println("listItems4 : " + listItems);
        }
//            System.out.println("names.length: " + names.length);
//        for(int i = 0; i< size; i++){
//            //ItemsModel 에 추가
//            ItemsModel itemsModel = new ItemsModel(stock_list.get(i).GetStockSymbol(),emails[i]);
//
//            listItems.add(itemsModel);
//
//            //현재는 리스트로 name , email에 값을 넣어서 itemsModel에 추가해주고있음. 20210617
//            //지금 검색리스트를 뿌려줘야 되는 상황은, 검색창에 값을 입력할때마다 일치하는 값들을 뿌려줘야함.
//
//            customAdapter = new CustomAdapter(listItems, this);
//
//            listView.setAdapter(customAdapter);
//
//        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        System.out.println("id: " +id);
        if(id == R.id.search_view){

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*private void StockTest(Object stock){
        // 검색
        StockList sl = new StockList();


        // Integer: code
        // ArrayList<String>: Array of  Korean name, English name  (length:2)
        HashMap<Integer, ArrayList<String>> stock_list = sl.SearchStockName(stock);

        // 조건
        StockConditionList scl = new StockConditionList();

        Integer stock_code = 5930; // 종목 코드

        // 검색된 주식 목록을 가져와서 코드, 이름 등을 사용한다.
        Set<Integer> stock_keys = stock_list.keySet();
        for(Integer key: stock_keys){
            System.out.println("Code: " + key);
            ArrayList<String> stock_names = stock_list.get(key);
            System.out.println("Ko: " + stock_names.get(0));
            System.out.println("En: " + stock_names.get(1));
            stock_code = key; // 가져온 코드를 아래에서 사용
        }


        // 목표 금액을 설정한다.
        float high_price = 120000;
        float low_price = 20000;
        scl.AddConditionTargetPrice(stock_code, high_price, low_price);


        // 조건들이 저장 되있는 목록.
        // 접근은 위와 같은 방식으로 한다.
        HashMap<Integer, StockCondition> condition_list =  scl.GetStockConditions();

    }*/

    public class CustomAdapter extends BaseAdapter implements Filterable {

        private List<ItemsModel> itemsModelsl;
        private List<ItemsModel> itemsModelListFiltered;
        private Context context;

        public CustomAdapter(List<ItemsModel> itemsModelsl, Context context) {
            this.itemsModelsl = itemsModelsl;
            this.itemsModelListFiltered = itemsModelsl;
            this.context = context;
        }

        @Override
        public int getCount() {
            return itemsModelListFiltered.size();
        }

        @Override
        public Object getItem(int position) {
            return itemsModelListFiltered.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.row_items,null);

            TextView names = view.findViewById(R.id.name);
            TextView emails = view.findViewById(R.id.currentPrice);
            TextView stockName = view.findViewById(R.id.textViewStock);
//            ImageView imageView = view.findViewById(R.id.images);

            names.setText(itemsModelListFiltered.get(position).getName());
            emails.setText(itemsModelListFiltered.get(position).getEmail());
//            imageView.setImageResource(itemsModelListFiltered.get(position).getImages());


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StockListOld sl = new StockListOld();
                    //stock = "aapl";
                    stock = itemsModelListFiltered.get(position).getName();
                    System.out.println("stock: " + stock);
                    HashMap<Integer, ArrayList<String>> stock_list = sl.SearchStockName(stock);
                    // 검색창에있는 name을 가지고 주식이름 불러오기
                    System.out.println("key: " + stock_list);
                    // 조건
                    StockConditionList scl = new StockConditionList();

                    Integer stock_code = null; // 종목 코드
                    //Integer stock_code = 5930; // 종목 코드
                    //Integer finalStock_code = stock_code;

                    // 검색된 주식 목록을 가져와서 코드, 이름 등을 사용한다.
                    Set<Integer> stock_keys = stock_list.keySet();
                    for(Integer key: stock_keys){
                        System.out.println("Code: " + key);
                        ArrayList<String> stock_names = stock_list.get(key);
                        System.out.println("Ko: " + stock_names.get(0));
                        System.out.println("En: " + stock_names.get(1));
                        System.out.println("key: " + key);

                        stock_code = key; // 가져온 코드를 아래에서 사용
                    }

                    Collection<ArrayList<String>> result = stock_list.values();
                    System.out.println("stock_list" + stock_list.get(stock_code));


                    Log.e("main activity","item clicked");
                    Intent myIntent = new Intent(StockActivity.this,ThirdActivity.class);
                    myIntent.putExtra("items",itemsModelListFiltered.get(position));
                    myIntent.putExtra("return",stock_list.get(stock_code));
                    System.out.println("stock_list2" + stock_list.get(stock_code));


                    startActivity(myIntent);
                    //putExtra("items",itemsModelListFiltered.get(position)));

                    //startActivity(new Intent(StockActivity.this,ThirdActivity.class).putExtra("items",itemsModelListFiltered.get(position)));

                }
            });

            return view;
        }



        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    // 검색된 값을 itemModelsl 에 뿌려준다
                   FilterResults filterResults = new FilterResults();
                    if(constraint == null || constraint.length() == 0){
                        filterResults.count = itemsModelsl.size();
                        filterResults.values = itemsModelsl;

                    }else{
                        List<ItemsModel> resultsModel = new ArrayList<>();
                        String searchStr = constraint.toString().toLowerCase();

                        for(ItemsModel itemsModel:itemsModelsl){
                            if(itemsModel.getName().contains(searchStr) || itemsModel.getEmail().contains(searchStr)){
                                resultsModel.add(itemsModel);

                            }
                            filterResults.count = resultsModel.size();
                            filterResults.values = resultsModel;
                        }


                    }

                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    itemsModelListFiltered = (List<ItemsModel>) results.values;
                    notifyDataSetChanged();

                }
            };
            return filter;
        }
    }

}