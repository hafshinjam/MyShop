package com.example.myshop.Data.Model;

import java.io.Serializable;
import java.util.List;

public class ProductAttributes implements Serializable {
    private int mId;
    private String mAttributeName;
    private List<String> mListOfOptions;

    public ProductAttributes() {
    }

    public ProductAttributes(int id, String attributeName, List<String> listOfOptions) {
        mId = id;
        mAttributeName = attributeName;
        mListOfOptions = listOfOptions;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getAttributeName() {
        return mAttributeName;
    }

    public void setAttributeName(String attributeName) {
        mAttributeName = attributeName;
    }

    public List<String> getListOfOptions() {
        return mListOfOptions;
    }

    public void setListOfOptions(List<String> listOfOptions) {
        mListOfOptions = listOfOptions;
    }
}
