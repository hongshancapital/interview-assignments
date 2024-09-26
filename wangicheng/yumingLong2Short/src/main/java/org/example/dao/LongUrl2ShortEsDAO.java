package org.example.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.example.model.HttpClientUtil;
import org.example.model.SearchDataResult;

import java.util.Map;

public class LongUrl2ShortEsDAO extends KBESDao {

    public LongUrl2ShortEsDAO(){
        super.setIndexAndTypeName("long2short","_doc");
    }

    @Override
    public SearchDataResult search(Map<String, Object> conditionMap, int from, int size) throws Exception {
        String businessEsUrl = super.getESURL() + "/" + "_search";

        JSONArray mustJSONArray = new JSONArray();
        if (conditionMap.containsKey("long_url")) {
            mustJSONArray.add(new JSONObject().fluentPut("term",
                    new JSONObject().fluentPut("long_url", conditionMap.get("long_url"))));
        }

        if (conditionMap.containsKey("short_url")) {
            mustJSONArray.add(new JSONObject().fluentPut("term",
                    new JSONObject().fluentPut("short_url", conditionMap.get("short_url"))));
        }

        JSONObject searchJsonObject = new JSONObject();
        searchJsonObject.put("from", from);
        searchJsonObject.put("size", size);

        JSONObject boolJsonObject = new JSONObject();
        if(mustJSONArray.size() > 0){
            boolJsonObject.put("bool", new JSONObject().fluentPut("must", mustJSONArray));
            searchJsonObject.fluentPut("query", boolJsonObject);
        } else {
            searchJsonObject.fluentPut("query", new JSONObject().fluentPut("match_all", new JSONObject()));
        }

        String str = HttpClientUtil.doJsonPost(businessEsUrl, searchJsonObject.toJSONString());
        SearchDataResult esSearchDataResult = this.esStr2Business(str);
        return esSearchDataResult;
    }

    @Override
    protected void dataCehck(Map<String, Object> dataMap) {
        super.checkDataBase_Data(dataMap);
    }
}
