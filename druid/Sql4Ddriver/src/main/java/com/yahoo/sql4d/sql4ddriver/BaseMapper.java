/**
 * Copyright 2014 Yahoo! Inc. Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 * CONDITIONS OF ANY KIND, either express or implied. See the License for the 
 * specific language governing permissions and limitations under the License. 
 * See accompanying LICENSE file.
 */
package com.yahoo.sql4d.sql4ddriver;

import com.yahoo.sql4d.query.RequestType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Base class for bean mapping.
 * @author srikalyan
 */
public class BaseMapper {
    public List<String> baseFieldNames = new ArrayList<>();

    /**
     * @param jsonAllRows
     */
    public void map(JSONArray jsonAllRows) {
        if (jsonAllRows.length() == 0) {
            return;
        }
        JSONObject sample = jsonAllRows.getJSONObject(0);
        if (sample.has("event")) {// GroupBy
            extractAndMap(null, jsonAllRows, RequestType.GROUPBY);
        } else if (sample.has("result")) {// Could be timeseries/topN/select
            if (sample.optJSONObject("result") != null) {// Timeseries/select
                if (sample.optJSONObject("result").optJSONArray("events") != null) {//select
                    extractAndMap(null, sample.optJSONObject("result").optJSONArray("events"), RequestType.SELECT);
                } else {// Timeseries.
                    extractAndMap(null, jsonAllRows, RequestType.TIMESERIES);
                }
            } else if (sample.optJSONArray("result") != null) {// TopN
                // There should be only 1 item in here.
                JSONObject jsonItem = jsonAllRows.getJSONObject(0);
                JSONArray result = jsonItem.getJSONArray("result");
                extractAndMap(jsonItem.optString("timestamp"), result, RequestType.TOPN);
            }
        }
    }

    protected void extractAndMap(String timestamp, JSONArray jsonAllRows, RequestType requestType) {
        // Empty
    }
    
    protected JSONObject dataItemAt(JSONObject jsonItem, RequestType requestType) {
        JSONObject eachRow = null;    
        if (requestType == RequestType.GROUPBY || requestType == RequestType.SELECT) {
            eachRow = jsonItem.getJSONObject("event");
        } else if (requestType == RequestType.TIMESERIES) {
            eachRow = jsonItem.getJSONObject("result");
        } else if (requestType == RequestType.TOPN) {
            eachRow = jsonItem;
        } 
        return eachRow;
    }
    
    protected void fillHeaders(JSONObject eachRow) {
        baseFieldNames.add("timestamp");
        if (eachRow != null) {
            baseFieldNames.addAll(eachRow.keySet());
        }
    }
    
    /**
     * More granular(sets the property of a bean based on a json key value
     * pair).
     *
     * @param bean
     * @param key
     * @param value
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static void applyKVToBean(Object bean, String key, Object value) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method getterMethod = bean.getClass().getMethod(Util.getterMethodName(key));
        Method setterMethod = bean.getClass().getMethod(Util.setterMethodName(key), getterMethod.getReturnType());
        setterMethod.invoke(bean, value);
    }

}
