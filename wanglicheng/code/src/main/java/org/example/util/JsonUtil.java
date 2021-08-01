package org.example.util;


import com.alibaba.fastjson.JSONObject;
import java.util.*;

public class JsonUtil {

	public static Map<String, Object> json2Map(JSONObject jsondata){
		
		Map<String, Object> map = new HashMap<>();
		Iterator<String> iterator = jsondata.keySet().iterator();
		
		while(iterator.hasNext()){
			String next = iterator.next();
				Object object = jsondata.get(next);
				if(object != null){
					if(object instanceof String){
						String strObject = object.toString().trim();
						if(!strObject.equals("")){
							map.put(next, strObject);
						} else {
							System.out.println(next + " " + strObject);
						}
					} else {
						map.put(next, object);
					}
				}

		}
		
		return map;		
	}

	public static String map2Json(Map<String, Object> map){
		JSONObject jsonObject = new JSONObject();
		
		Set<String> strSet = map.keySet();
		for (String string : strSet) {
			jsonObject.put(string, map.get(string));
		}
		
		String jsonStr = jsonObject.toString();
		
		//jsonStr = jsonStr.replaceAll("\"\\[", "\\[").replaceAll("\"\\]", "\\]");
		
		return jsonStr;
	}
}
