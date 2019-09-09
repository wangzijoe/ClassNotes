package com.example.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JsonToSql {

    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\17923\\Desktop\\item.json";
        formatJsonToSql(path);
    }


    private static void formatJsonToSql(String path) throws Exception {
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(fileReader);

        String insert_into = " insert into ";
        String table = file.getName().substring(0, file.getName().lastIndexOf("."));
        String space = " ";
        String values = " values ";
        String left_bracket = " ( ";
        String right_bracket = " ) ";


        for (int i = 0; i < jsonArray.size(); i++) {
            List<String> columns = new ArrayList<>();
            List<String> objects = new ArrayList<>();
            JsonElement jsonElement = jsonArray.get(i);
            JsonObject jsonObj = jsonElement.getAsJsonObject();
            Set<String> keySet = jsonObj.keySet();
            for (String key : keySet) {
                columns.add(key);
                JsonElement val = jsonObj.get(key);
                boolean isNull = val.isJsonNull();
                String asString = isNull ? "''" : convertVal(val.getAsString());

                objects.add(asString);
            }
            String columnStr = String.join(" , ", columns);
            String valuesStr = String.join(" , ", objects);
            String sql =
                    insert_into
                            + space
                            + table
                            + space
                            + columnStr
                            + values
                            + left_bracket
                            + valuesStr
                            + right_bracket
                            + ";";
            System.out.println(sql);
        }
    }

    private static String convertVal(String val) {
        boolean isNumeric = StringUtils.isNumeric(val);
        return isNumeric ? val : "'" + val + "'";
    }
}
