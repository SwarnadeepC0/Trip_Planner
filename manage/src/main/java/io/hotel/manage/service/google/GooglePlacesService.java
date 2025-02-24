package io.hotel.manage.service.google;

import io.hotel.manage.models.Place;
import org.json.*;
import com.google.gson.JsonObject;
import io.hotel.manage.service.PlacesService;
import io.hotel.manage.service.query.Query;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

public class GooglePlacesService implements PlacesService {
    private final String apiKey;
    private final WebClient webClient;

    public GooglePlacesService(String apiKey) {
        this.webClient = WebClient.create("https://places.googleapis.com/v1");
        this.apiKey = apiKey;
    }

    @Override
    public Iterator<io.hotel.manage.models.Place> getPlaces(Query query) {

        //OkHttpClient client = new OkHttpClient();

        // JSON request body
        String json = getSearchRequest(query);
        List<Place> placesList = new ArrayList<>();
        String response =  webClient.post()
                .uri("/places:searchNearby")
                .header("Content-Type", "application/json")
                .header("X-Goog-Api-Key", this.apiKey)
                .header("X-Goog-FieldMask", "places.formattedAddress,places.id,places.googleMapsUri,places.rating,places.primaryType,places.editorialSummary.text")
                .bodyValue(json)
                .retrieve()
                .bodyToMono(String.class).block();
        JSONObject jsonObject = new JSONObject(response);
        JSONArray places = jsonObject.getJSONArray("places");
        for(int i = 0; i < places.length(); i++)
        {
            JSONObject object = places.getJSONObject(i);
            placesList.add(adaptPlaceToApiPlace(object));
        }
        return placesList.iterator();
    }

    private String getSearchRequest(Query query) {
        return "{"
                + "\"includedTypes\": "+ query.getPlaceTypes().stream().map(type-> "\""+type.name()+"\"").toList() +","
                + "\"maxResultCount\": 10,"
                + "\"locationRestriction\": {"
                + "  \"circle\": {"
                + "    \"center\": {\"latitude\": " + Double.parseDouble(query.getLatitude()) + ", \"longitude\": " + Double.parseDouble(query.getLongtitude()) + "},"
                + "    \"radius\": " + Double.parseDouble(query.getRadius())
                + "  }"
                + "}"
                + "}";
    }


    private io.hotel.manage.models.Place adaptPlaceToApiPlace(JSONObject object) {
        io.hotel.manage.models.Place place1 = new io.hotel.manage.models.Place();
        place1.setPlaceId(object.getString("id"));
        place1.setName(object.getString("formattedAddress"));
        if(object.has("rating")) {
            place1.setRating(Double.toString(object.getDouble("rating")));
        }else{
            place1.setRating("N/A");
        }
        place1.setLocationUri(object.getString("googleMapsUri"));
        if(object.has("editorialSummary")) {
            place1.setSummary(object.getJSONObject("editorialSummary").getString("text"));
        }else{
            String review = "good";
            if(object.has("rating") && object.getDouble("rating")>4.0){
                review = "excellent";
            }else if(object.has("rating") && object.getDouble("rating")>3.0){
                review = "great";
            }
            place1.setSummary("The "+object.getString("primaryType")+ " is "+ review + " according to the people visited recently. ");
        }
        place1.setType(object.getString("primaryType"));
        return place1;
    }


}
