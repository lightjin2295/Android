package com.example.bottombar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textViewName;
    TextView textViewStock;
    TextView textViewStockName;
    ItemsModel itemsModel;
    Button alertButton;

    Object result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        imageView = findViewById(R.id.imageView);
        textViewName = findViewById(R.id.textViewName);
        textViewStock = findViewById(R.id.textViewStock);
        textViewStockName = findViewById(R.id.textViewStockName);
        alertButton = findViewById(R.id.alertButton);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            itemsModel = (ItemsModel) intent.getSerializableExtra("items");
            ArrayList<String> result = (ArrayList) intent.getSerializableExtra("return");
            System.out.println("result" +" " + result);
//            imageView.setImageResource(itemsModel.getImages());
            textViewName.setText(itemsModel.getName());
            //textViewStock.setText(itemsModel.getEmail());
            textViewStock.setText(result.get(0));
            textViewStockName.setText(result.get(1));

        }

        Intent myIntent = new Intent(ThirdActivity.this,ThirdActivity.class);
        myIntent.putExtra("name",itemsModel.getName());
        Log.i("###",itemsModel.getName());





    }
}