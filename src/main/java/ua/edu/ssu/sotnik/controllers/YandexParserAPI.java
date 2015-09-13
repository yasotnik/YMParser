package ua.edu.ssu.sotnik.controllers;

import com.google.gson.*;
import ua.edu.ssu.sotnik.models.Category;
import ua.edu.ssu.sotnik.models.Product;

import java.io.*;
import java.net.*;

public class YandexParserAPI implements  IProductParser{

    private static final String versionAPI = "1";
    private static final String requestURL = "http://market.icsystem.ru/v";
    private static final String answerFormatAPI = ".json";
    private static final String geoIdUkraineAPI = "?geo_id=187";
    private static final String geoIdSumyAPI = "?geo_id=965";

    Product product = new Product();



    @Override
    public String openConnection(String urlAPIRequest) {
        URL url;
        HttpURLConnection connection;
        BufferedReader bufRd;
        String jsonStr = "";
        try {
            url = new URL(urlAPIRequest);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            bufRd = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            // GET input JSON as String
            jsonStr = bufRd.readLine();
            System.out.println("#LOG: URL:" + urlAPIRequest);
            connection.disconnect();
            bufRd.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return jsonStr;
        }
    }

    @Override
    public Product getProductById(int productID) {
        product.setAllNull();
        String jsonStr;
        String urlAPIRequest = requestURL + versionAPI + "/model/" + productID + answerFormatAPI + geoIdSumyAPI;
        try {
            jsonStr = openConnection(urlAPIRequest);
            // GET JSON String as JsonElement
            JsonElement jsonElement = new JsonParser().parse(jsonStr);
            // GET JsonObject from JsonObject
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            // GET JsonObject model from JsonObject
                jsonObject = jsonObject.getAsJsonObject("model");
            // Passing data from JSON to Product
            if (jsonObject.get("id").isJsonNull())
                product.setId(product.getNoNumInformation());
            else
                product.setId(jsonObject.get("id").getAsInt());
            if (jsonObject.get("categoryId").isJsonNull())
                product.setCategoryId(product.getNoNumInformation());
            else
                product.setCategoryId(jsonObject.get("categoryId").getAsInt());
            if (jsonObject.get("name").isJsonNull())
                product.setName(product.getNoTxtInformation());
            else
                product.setName(jsonObject.get("name").toString().replaceAll("\"", ""));
            if (jsonObject.get("description").isJsonNull())
                product.setDescription(product.getNoTxtInformation());
            else
                product.setDescription(jsonObject.get("description").toString().replaceAll("\"", ""));
            if (jsonObject.get("link").isJsonNull())
                product.setProductURL(product.getNoTxtInformation());
            else
                product.setProductURL(jsonObject.get("link").toString().replaceAll("\"", ""));
            if (jsonObject.get("rating").isJsonNull())
                product.setRating(product.getNoNumInformation());
            else
                product.setRating(jsonObject.get("rating").getAsFloat());
            // Parsing imgURL from JSON object model
            JsonObject jsonObjectURL = jsonObject;
            jsonObjectURL = jsonObjectURL.getAsJsonObject("mainPhoto");
            if (jsonObjectURL.get("url").isJsonNull())
                product.setImgURL(product.getNoTxtInformation());
            else
                product.setImgURL(jsonObjectURL.get("url").toString().replaceAll("\"", ""));
            // Parsing avgPrice + curName from JSON object model
            JsonObject jsonObjectPrice = jsonObject;
            jsonObjectPrice = jsonObjectPrice.getAsJsonObject("prices");
            if (jsonObjectPrice.get("avg").isJsonNull())
                product.setPriceAvg(product.getNoNumInformation());
            else
                product.setPriceAvg(jsonObjectPrice.get("avg").getAsInt());
            if (jsonObjectPrice.get("curName").isJsonNull())
                product.setCurName(product.getNoTxtInformation());
            else
                product.setCurName(jsonObjectPrice.get("curName").toString().replaceAll("\"", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public Product getProductByName(String productName) {
        product.setAllNull();
        String jsonStr;
        productName = productName.replaceAll(" ","+");
        String urlAPIRequest = requestURL + versionAPI + "/search" + answerFormatAPI + geoIdSumyAPI + "&check_spelling=1&text=" + productName;
        try {
            jsonStr = openConnection(urlAPIRequest);
            // GET JSON String as JsonElement
            JsonElement jsonElement = new JsonParser().parse(jsonStr);
            // GET JsonObject from JsonObject
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            jsonObject = jsonObject.getAsJsonObject("searchResult");
            JsonArray jsonArray = jsonObject.getAsJsonArray("results");
            jsonObject = jsonArray.get(0).getAsJsonObject();
            jsonObject = jsonObject.getAsJsonObject("model");
            if (jsonObject.get("id").isJsonNull())
                product.setId(product.getNoNumInformation());
            else {
                product.setId(jsonObject.get("id").getAsInt());
            }
            if (jsonObject.get("categoryId").isJsonNull())
                product.setCategoryId(product.getNoNumInformation());
            else
                product.setCategoryId(jsonObject.get("categoryId").getAsInt());
            if (jsonObject.get("name").isJsonNull())
                product.setName(product.getNoTxtInformation());
            else
                product.setName(jsonObject.get("name").toString().replaceAll("\"", ""));
            if (jsonObject.get("description").isJsonNull())
                product.setDescription(product.getNoTxtInformation());
            else
                product.setDescription(jsonObject.get("description").toString().replaceAll("\"", ""));
            if (jsonObject.get("link").isJsonNull())
                product.setProductURL(product.getNoTxtInformation());
            else
                product.setProductURL(jsonObject.get("link").toString().replaceAll("\"", ""));
            System.out.println("#LOG: Rating ?null: " + jsonObject.get("rating").isJsonNull());
            if (jsonObject.get("rating").isJsonNull()) {

                product.setRating(product.getNoNumInformation());
            }
            else {
                if (jsonObject.get("rating").getAsFloat() < 0) {
                    product.setRating(product.getNoNumInformation());
                }
                else {
                    product.setRating(jsonObject.get("rating").getAsFloat());
                }
            }
            // Parsing imgURL from JSON object model
            JsonObject jsonObjectURL = jsonObject;
            jsonObjectURL = jsonObjectURL.getAsJsonObject("mainPhoto");
            if (jsonObjectURL.get("url").isJsonNull())
                product.setImgURL(product.getNoTxtInformation());
            else
                product.setImgURL(jsonObjectURL.get("url").toString().replaceAll("\"", ""));
            // Parsing Price + currency
            if (jsonObject.get("offersCount").getAsInt() == 0) {
                // NO OFFERS
                System.out.println("#LOG: No offers!");
                jsonObject = jsonArray.get(1).getAsJsonObject();
                if (jsonObject.getAsJsonObject("offer").isJsonNull()) {
                    product.setCurName(product.getNoCurInfo());
                    product.setPriceAvg(product.getNoNumInformation());
                }
                else {
                    jsonObject = jsonObject.getAsJsonObject("offer");
                    JsonObject jsonObjectPrice = jsonObject;
                    jsonObjectPrice = jsonObjectPrice.getAsJsonObject("price");
                    if (jsonObjectPrice.get("value").isJsonNull())
                        product.setPriceAvg(product.getNoNumInformation());
                    else
                        product.setPriceAvg(jsonObjectPrice.get("value").getAsInt());
                    if (jsonObjectPrice.get("currencyName").isJsonNull())
                        product.setCurName(product.getNoCurInfo());
                    else
                        product.setCurName(jsonObjectPrice.get("currencyName").toString().replaceAll("\"", ""));
                }
            }
            else {
                System.out.println("#LOG: Offers!" + jsonObject.get("offersCount").getAsInt());
                if (jsonObject.get("prices").isJsonNull()) {
                    product.setPriceAvg(product.getNoNumInformation());
                    product.setCurName(product.getNoCurInfo());
                }
                else {
                    jsonObject = jsonObject.getAsJsonObject("prices");
                    product.setPriceAvg(jsonObject.get("avg").getAsInt());
                    product.setCurName(jsonObject.get("curName").toString().replaceAll("\"", ""));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public Category getCategories() {
        String categoriesURL = requestURL + versionAPI + "/category" + answerFormatAPI + "?count=16";
        String jsonStr;
        jsonStr = openConnection(categoriesURL);
        System.out.println(jsonStr);
        Category c = new Category();
        return c;
    }
}

