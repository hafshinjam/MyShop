package com.example.myshop.Remote;

import com.example.myshop.Model.Product;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.List;

public class GetProductDeserializer implements JsonDeserializer<Product> {
    @Override
    public Product deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject productObject = json.getAsJsonObject();
        String id = productObject.get("id").getAsString();
        String name = productObject.get("name").getAsString();
        String permalink = productObject.get("permalink").getAsString();
        String description = productObject.get("description").getAsString();
        long price = productObject.get("price").getAsLong();
        List<String> imageUris = GetProductsDeserializer.deserializeImages(productObject.get("images").getAsJsonArray());
        boolean isInStock;
        String inStock = productObject.get("stock_status").getAsString();
        if (inStock.equals("instock"))
            isInStock = true;
        else isInStock = false;
        return new Product(id,name,permalink,description,price,imageUris,isInStock);
    }
}
