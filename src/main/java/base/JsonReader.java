package base;


import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonReader {
    private static PropReader propReader=new PropReader();
    private static JSONParser jsonParser=new JSONParser();
    private static JSONObject jsonObject=new JSONObject();
    private static String jsonPath= propReader.getProp("JsonFilePath");

    public static Object[][] getAllUsersData(List<String> keys) throws FileNotFoundException, ParseException {
        FileReader file =new FileReader(jsonPath);
        jsonObject=(JSONObject) jsonParser.parse(file);
        Object[][] usersData= new Object[jsonObject.size()][keys.size()];
        for (int i = 0; i < jsonObject.size(); i++) {
            JSONObject dataSet=(JSONObject)jsonObject.get("user_"+(i+1));
            for (int j = 0; j < keys.size(); j++) {
                usersData[i][j]=dataSet.getAsString(keys.get(j));
            }
        }
        return usersData;
    }


    public static Object[][] getDataUsingStatus(List<String> keys, String status) throws FileNotFoundException, ParseException {
        Object[][] allUsers=getAllUsersData(keys);
        List<List> filteredData=new ArrayList();
        for (int i = 0; i < allUsers.length; i++) {
            for (int j = 0; j < keys.size();  j++) {
                String value=allUsers[i][j].toString();
                if (value.equalsIgnoreCase(status)){
                    filteredData.add(Arrays.stream(allUsers[i]).collect(Collectors.toList()));
                }
            }
        }
        Object[][]loginData=new Object[filteredData.size()][keys.size()];
        for (int i = 0; i < filteredData.size(); i++) {
            loginData[i]=filteredData.get(i).stream().toArray();
        }
       return loginData;
    }

}
