package org.example.dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.example.config.ESConfig;
import org.example.model.HttpClientUtil;
import org.example.util.JsonUtil;
import org.example.model.SearchDataResult;

import java.util.*;

public abstract class KBESDao {

    protected String indexName = "kb_zx";
    protected String typeName = "";

    private String ES_URL = null;

    protected void setIndexAndTypeName(String indexName, String typeName){
        this.indexName = indexName;
        this.typeName = typeName;
        this.ES_URL = ESConfig.gkg_search_host_url + "/" + indexName + "/" + typeName;
    }

    protected String getESURL(){
        return this.ES_URL;
    }

    public void pitchAdd(List<Map<String, Object>> dataMapList) throws Exception {
        String businessEsUrl = this.getESURL() + "/_bulk";

        String createStr = "{\"create\":{\"_index\":\"" + this.indexName + "\",\"_type\":\"" + this.typeName
                + "\",\"_id\":\"%s\"}}\n";

        StringBuilder sbuilder = new StringBuilder();

        for (int i = 0; i < dataMapList.size(); i++) {
            Map<String, Object> data = dataMapList.get(i);

            String id = data.get("id").toString();
            sbuilder.append(String.format(createStr, id));

            data.put("create_time", System.currentTimeMillis());

            sbuilder.append(JsonUtil.map2Json(data) + "\n");
        }

        String str = HttpClientUtil.doJsonPost(businessEsUrl, sbuilder.toString());
        System.out.println(str);
    }

    public void delete(String id) throws Exception {
        String deleteEsUrl = this.ES_URL +  "/" + id;
        HttpClientUtil.httpDelete(deleteEsUrl);
    }

    public abstract SearchDataResult search(Map<String, Object> conditionMap, int from, int size) throws Exception;

    public void cleanIndex() throws Exception {
        String businessEsUrl = this.ES_URL +  "/" + "_search";

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("from", 0);
        jsonObject.put("size", 10000);
        jsonObject.put("query", new JSONObject().put("match_all", new JSONObject()));

        String str = HttpClientUtil.doJsonPost(businessEsUrl, jsonObject.toJSONString());

        JSONObject resultJSONObject = JSONObject.parseObject(str);

        JSONObject hitsJsonObject = resultJSONObject.getJSONObject("hits");

        // int total = hitsJsonObject.getInt("total");
        JSONArray jsonArray = hitsJsonObject.getJSONArray("hits");
        for (int i = 0; i < jsonArray.size(); i++) {
            this.delete(jsonArray.getJSONObject(i).getString("_id"));
        }
    }

    protected abstract void dataCehck(Map<String, Object> dataMap);

    protected SearchDataResult esStr2Business(String esStr) throws Exception {
        SearchDataResult dataResult = new SearchDataResult();
        List<Map<String, Object>> dataList = new ArrayList<>();
        JSONObject esJsonObject = JSONObject.parseObject(esStr);
        JSONObject hitsJsonObject = esJsonObject.getJSONObject("hits");
        int total = hitsJsonObject.getJSONObject("total").getInteger("value");

        if (total == 0) {
            return dataResult;
        }
        JSONArray jsonArray = hitsJsonObject.getJSONArray("hits");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject _sourceJsonObject = jsonArray.getJSONObject(i).getJSONObject("_source");

            Map<String, Object> dataMap = this.source2Map(_sourceJsonObject, jsonArray.getJSONObject(i).get("_id").toString());
            this.dataCehck(dataMap);
            dataList.add(dataMap);
        }
        dataResult.setCount(jsonArray.size());
        dataResult.setTotal(total);

        dataResult.setDatas(dataList);
        return dataResult;
    }

    protected Map<String, Object> source2Map(JSONObject _sourceJsonObject, String id) {
        Map<String, Object> dataMap = JsonUtil.json2Map(_sourceJsonObject);
        dataMap.put("id", id);

        return dataMap;
    }

    protected void checkDataBase_Data(Map<String, Object> dataMap){
        Map<String, Object> voDataMap = new HashMap<>();

        for(String str : dataMap.keySet()){
            int index = str.indexOf("_");
            if(index > 0){
                String c = str.substring(index + 1, index + 2);
                String newName = str.substring(0, index) + c.toUpperCase() + str.substring(index + 2);
                voDataMap.put(newName, dataMap.get(str));
            }
        }

        dataMap.putAll(voDataMap);
    }
}
