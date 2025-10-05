package com.swaglabs.datareader;

import com.jayway.jsonpath.JsonPath;
import com.swaglabs.utils.logs.LogsManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JsonReader {
    private final String TEST_DATA_PATH = "src/test/java/resources/Test_Data/";
    static String jsonReader;
    String jsonFileName;
    public JsonReader(String jsonFileName) {
        this.jsonFileName = jsonFileName;
        try {
            JSONObject data = (JSONObject) new JSONParser().parse(new FileReader(TEST_DATA_PATH + jsonFileName + ".json"));
            jsonReader = data.toJSONString();
        } catch (Exception e) {
            LogsManager.error("Error reading json file:", jsonFileName, e.getMessage());
            jsonReader = "{}";
        }
    }
    public static String getJsonData(String jsonPath) {
        try {
            return JsonPath.read(jsonReader, jsonPath);
        } catch (Exception e) {
            LogsManager.error("Error reading json file for path:", jsonPath, e.getMessage());
            return "";
        }
    }
}
