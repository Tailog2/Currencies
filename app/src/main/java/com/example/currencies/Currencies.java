package com.example.currencies;


import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class Currencies {
    public DataCurrencies data;
    private String responseBody;

    public void getCurrencies () throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        String url = "https://www.cbr-xml-daily.ru/daily_json.js";
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            responseBody = response.body().string();
        }
    }

    public void parse(){
        Gson gson = new Gson();
        data = gson.fromJson(responseBody, DataCurrencies.class);
    }

    public Double converter (String currencyName, double amount){
        if (data != null){
            double newValue = 0;
            newValue = amount / Objects.requireNonNull(data.Valute.get(currencyName)).Value;
            return Math.round(newValue*100.0) / 100.0;
        }
        else{
            return null;
        }
    }

    public static class DataCurrencies {
        public String Date;
        public String PreviousDate;
        public String PreviousURL;
        public String Timestamp;
        Map<String, ValuteMap> Valute;

        static class ValuteMap {
            public String ID;
            public String NumCode;
            public String CharCode;
            public int Nominal;
            public String Name;
            public double Value;
            public double Previous;
        }
    }
}
