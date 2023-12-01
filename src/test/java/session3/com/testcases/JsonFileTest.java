package session3.com.testcases;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.testng.annotations.Test;

import selenium4.com.helpers.*;

public class JsonFileTest {
	
    //@Test
    public void testReadDataFromJsonPath() {
    	JsonHelpers jsonHelpers = new JsonHelpers();
    	jsonHelpers.setJsonFile(Helpers.getCurrentDir() + "src/test/resources/datajson/book.json");
    	
    	Object id = jsonHelpers.getData("$.id");
    	System.out.println("--- Value: " + (Integer)id);

    	Object title = jsonHelpers.getData("$['title']");
    	System.out.println("--- Value: " + (String)title);

    	Object starring = jsonHelpers.getData("$['starring']");
    	List<?> lstStarring = (List<?>) starring;
    	for(Object s : lstStarring) {
    		System.out.println("--- Value: " + (String)s);
    	}
    	
    	Object b1 = jsonHelpers.getData("$['b1']");
    	System.out.println("--- Value: " + (Boolean)b1);

    	Object price = jsonHelpers.getData("$['price range']");
    	Map<?, ?> m = (Map<?, ?>) price;
		Set<?> keys = m.keySet();    	       
        for (Object k : keys) {
        	System.out.println("--- Key: " + (String)k);
        	System.out.println("--- Value: " + m.get(k));
        }
    }

    @Test
    public void testReadDataFromJackson() {
    	String jsonPath = "src/test/resources/datajson/book.json";
    	JsonHelpers jsonHelpers = new JsonHelpers();
    	jsonHelpers.getMapFromJsonFile(Helpers.getCurrentDir() + jsonPath);
    	Object obj1 = jsonHelpers.get("id");
    	if (obj1 instanceof Integer) {    		
    		Integer i = (Integer)obj1;
    		System.out.println("--- Integer: " + i);
    	}
    	Object obj2 = jsonHelpers.get("title");
    	if (obj2 instanceof String) {    		
    		String s = (String)obj2;
    		System.out.println("--- String: " + s);
    	}
    	Object obj3 = jsonHelpers.get("starring");
    	System.out.println("--- class: " + obj3.getClass().toString());
    	if (obj3 instanceof ArrayList) {
    		ArrayList<?> listOfValues = (ArrayList<?>) obj3;
    		for(int i=0; i<listOfValues.size(); i++) {  
                System.out.println(String.valueOf(listOfValues.get(i)));  
            }  
    	}
    	Object obj4 = jsonHelpers.get("b1");
    	if (obj4 instanceof Boolean) {    		
    		Boolean s = (Boolean)obj4;
    		System.out.println("--- Boolean: " + s);
    	}    	
    	Object obj5 = jsonHelpers.get("price range");
    	if (obj5 instanceof LinkedHashMap) { 
    		System.out.println("--- LinkedHashMap: ");
    		LinkedHashMap<?, ?> mapOfValues = (LinkedHashMap<?, ?>) obj5;
    		Set<?> keys = mapOfValues.keySet();    	       
            for (Object key : keys) {
                System.out.println(key + " -- " + mapOfValues.get(key));
            }
    	}    	
    }
}
