package com.example.currencies;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout captions = findViewById(R.id.captions);
        ConstraintLayout activity_main = findViewById(R.id.layout_main);
        ListView listView = findViewById(R.id.list);
        Currencies currencies = new Currencies();

        Thread currencyThread = new Thread(() -> {
            try {
                currencies.getCurrencies();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            currencies.parse();

        }, "Thread Currencies");


        currencyThread.start();
        try {
            currencyThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(currencies.converter("KRW", 100));

        List<String> listData = new ArrayList<>(currencies.data.Valute.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, listData);

        for (Map.Entry<String, Currencies.DataCurrencies.ValuteMap> entry : currencies.data.Valute.entrySet()){
            System.out.println("Key = " + entry.getKey() +
                    ", Value = " + entry.getValue().Value);
        }
        listView.setAdapter(adapter);
    }
 }