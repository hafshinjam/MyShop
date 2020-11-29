package com.example.myshop.Remote;

import com.example.myshop.Model.Product;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetProductDeserializer implements JsonDeserializer<List<Product>> {
    @Override
    public List<Product> deserialize(JsonElement json,
                                     Type typeOfT,
                                     JsonDeserializationContext context) throws JsonParseException {
        JsonObject body = json.getAsJsonObject();
        List<Product> list = new ArrayList<>();
        JsonArray productArray = body.getAsJsonArray();
        for (int i = 0; i < productArray.size(); i++) {
            JsonObject productObject = productArray.get(i).getAsJsonObject();
            String id = productObject.get("id").getAsString();
            String name = productObject.get("name").getAsString();
            String permalink = productObject.get("permalink").getAsString();
            String description = productObject.get("description").getAsString();
            long price = productObject.get("price").getAsLong();
            List<String> imageUris = deserializeImages(productObject.get("images").getAsJsonArray());
            boolean isInStock;
            String inStock = productObject.get("stock_status").getAsString();
            if (inStock.equals("instock"))
                isInStock = true;
            else isInStock = false;
            Product product = new Product(id, name, permalink, description, price, imageUris, isInStock);
            list.add(product);
        }
        return list;
    }

    private List<String> deserializeImages(JsonArray imageArray) {
        List<String> list = new ArrayList<>();
        if (imageArray.size() > 0)
            for (int i = 0; i < imageArray.size(); i++) {
                JsonObject imageObject = imageArray.get(i).getAsJsonObject();
                String imageURL = imageObject.get("src").getAsString();

            }
        return list;
    }
}
