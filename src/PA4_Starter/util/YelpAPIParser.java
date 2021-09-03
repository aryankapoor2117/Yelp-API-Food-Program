package PA4_Starter.util;

import com.google.gson.*;
import PA4_Starter.models.Location;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

public class YelpAPIParser {
    public static Location getLocation(String restaurant, Location location) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            String builder = "https://api.yelp.com/v3/businesses/search" + "?term=" + restaurant +
                    "YOUR QUERY TERMS";
            Request request = new Request.Builder().url(builder).method("GET", null).addHeader(
                    "Authorization",
                    "EChXPzvE3rU7HLzfJUUQzHaMLP2YLZrSnrLDpWh_PUq-WnqAmpi93sA2h_PEHZYKcgn8urY5ASvKgaueQKKU2stAp-NQhKJ75frKvsypvOOfWr7jxbU5kJgNbF6iX3Yx")
                    .build();
            Response response = client.newCall(request).execute();

            Gson gson = new GsonBuilder().registerTypeAdapter(Location.class, new MyDeserializer()).create();
            return gson.fromJson(Objects.requireNonNull(response.body()).string(), Location.class);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }
}

// Code adapted from https://stackoverflow.com/questions/23070298/get-nested-json-object-with-gson-using-retrofit
class MyDeserializer implements JsonDeserializer<Location> {
    @Override
    public Location deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonElement content = je.getAsJsonObject().getAsJsonArray("businesses").get(0).getAsJsonObject()
                .get("coordinates");

        return new Gson().fromJson(content, Location.class);
    }
}
