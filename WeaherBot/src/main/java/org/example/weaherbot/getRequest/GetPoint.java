package org.example.weaherbot.getRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.example.weaherbot.query.Coord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.runtime.ObjectMethods;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetPoint {
    private static final String FIRST_REFERENCE = "https://api.openweathermap.org/data/2.5/weather?q=", SECOND_REFERENCE = "&units=metric&appid=52ae6e93b7c6d42c718d3157aad611a5";

    public GetPoint(){}
    public Coord getLocation(String city) throws IOException {
        URL url = new URL(FIRST_REFERENCE+city+SECOND_REFERENCE);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String readNext;
            while ((readNext = reader.readLine()) != null){
                builder.append(readNext);
            }

            readNext = builder.toString();
            return deserealize(readNext);
        }
        catch (Exception e){

        }
        //потребує змін
        return null;
    }
    private Coord deserealize(String request){
        Gson gson = new Gson();
        ObjectMapper mapper = new ObjectMapper();
        try{
            JsonNode node = mapper.readTree(request);
            return gson.fromJson(node.toString(),Coord.class);
        }
        catch (JsonProcessingException e){

        }
        //потребує змін
        return null;
    }
}
