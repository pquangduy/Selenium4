package selenium4.com.helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import selenium4.com.constants.FrameworkConstants;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JsonHelpers {

    //Jackson
    private Map<String, Object> CONFIGMAP;

    //Json Path
    private BufferedReader bufferedReader;
    private StringBuffer stringBuffer;
    private DocumentContext jsonContext;
    private String lines;
    private String jsonFilePathDefault;

    public JsonHelpers() {
	    try {
	    	jsonFilePathDefault = Helpers.getCurrentDir() + FrameworkConstants.JSON_DATA_FILE_PATH;
	    	System.out.println("--- Default json file: " + jsonFilePathDefault);
	        CONFIGMAP = new ObjectMapper().readValue(new File(jsonFilePathDefault), new TypeReference<HashMap<String, Object>>() {});
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

    }

    public void getMapFromJsonFile(String jsonPath) {
        try {
            CONFIGMAP = new ObjectMapper().readValue(new File(jsonPath), new TypeReference<HashMap<String, Object>>() {});
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object get(String key) {
        if (Objects.isNull(key) || Objects.isNull(CONFIGMAP.get(key.toLowerCase()))) {
            try {
                throw new Exception("Key name " + key + " is not found. Please check config.json");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return CONFIGMAP.get(key.toLowerCase());
    }

    public StringBuffer readJsonFile(String jsonPath) {
        try {
            bufferedReader = new BufferedReader(new FileReader(jsonPath));
            stringBuffer = new StringBuffer();
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer;
    }

    public void setJsonFile(String jsonPath) {
        try {
            stringBuffer = readJsonFile(jsonPath);
            jsonContext = JsonPath.parse(stringBuffer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getJsonDataSourceString() {
        if (stringBuffer == null) {
            try {
                stringBuffer = readJsonFile(jsonFilePathDefault);
                jsonContext = JsonPath.parse(stringBuffer.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } 
        }
        return stringBuffer.toString();
    }

    public Object getData(String key) {
        if (jsonContext == null) {
            getJsonDataSourceString();
        }
        return jsonContext.read(key);
    }
}
