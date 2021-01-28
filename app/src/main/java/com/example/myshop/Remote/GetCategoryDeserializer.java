package com.example.myshop.Remote;

import com.example.myshop.Data.Model.Category;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetCategoryDeserializer implements JsonDeserializer<List<Category>> {

    @Override
    public List<Category> deserialize(JsonElement json,
                                      Type typeOfT,
                                      JsonDeserializationContext context) throws JsonParseException {
        List<Category> categoryList = new ArrayList<>();
        JsonArray categoryArray = json.getAsJsonArray();
        for (int i = 0; i < categoryArray.size(); i++) {
            JsonObject categoryObject = categoryArray.get(i).getAsJsonObject();
            String id = categoryObject.get("id").getAsString();
            String categoryName = categoryObject.get("name").getAsString();
            String photoUri = getCategoryPhotoUriFromJson(categoryObject.get("image").getAsJsonObject());
            String parentCategoryId = categoryObject.get("parent").getAsString();
            Category category = new Category(categoryName, id, photoUri, parentCategoryId);
            categoryList.add(category);
        }
        return categoryList;
    }

    private String getCategoryPhotoUriFromJson(JsonObject image) {
        return image.get("src").getAsString();
    }
}
