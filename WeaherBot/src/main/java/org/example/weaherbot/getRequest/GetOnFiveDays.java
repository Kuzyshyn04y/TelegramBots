package org.example.weaherbot.getRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.example.weaherbot.query.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GetOnFiveDays {
    private static final String FIRST_REFERENCE = "https://api.openweathermap.org/data/2.5/forecast?lat=", SECOND_REFERENCE = "&appid=52ae6e93b7c6d42c718d3157aad611a5";
    private Example main;

    public GetOnFiveDays(){}

    public void createRequest(String city) throws IOException {
        java.util.List<String> every3hours = new ArrayList<>();
        GetPoint point = FactoryRequest.getPointEntity();
        Coord coords = point.getLocation(city);
        URL url = new URL(FIRST_REFERENCE + coords.getLat()+"&lon="+coords.getLon()+SECOND_REFERENCE);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String jsonLine;
            while ((jsonLine = reader.readLine()) != null){
                builder.append(jsonLine);
            }
            jsonLine = builder.toString();
            deserealize(jsonLine);
        }
        catch (Exception e){

        }
    }

    public String createMessage(){
        String message = "";
        double raining;
        City city = main.getCity();
        Rain rain;
        Main temp;
        java.util.List<List> list = main.getList();
        StringBuilder builder = new StringBuilder(city.getName()+"\n");
        builder.append("*Температура*|*Відчувається як*|*ймовірність*|\n" +
                       "  *повітря*  |               |  *опадів*   |\n");
        for(List line:list){
            temp = line.getMain();
            raining = 0;
             if(line.getRain() != null){
                 rain = line.getRain();
                 raining = rain.get3h();
             }
            message = String.format("|%+11f|%+15f|%11f%%|\n", temp.getTemp(), temp.getFeelsLike(), raining*100);
            builder.append(message);
        }
        return builder.toString();
    }



    private void deserealize(String request){
        Gson gson = new Gson();
        ObjectMapper mapper = new ObjectMapper();
        try{
            JsonNode node = mapper.readTree(request);
            main = gson.fromJson(node.toString(),Example.class);
        }
        catch (JsonProcessingException e){

        }
    }
}
