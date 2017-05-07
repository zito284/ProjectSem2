/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Zito
 */
public class StringHelper {
    public static String getStringByMap(HashMap<String,String> listMap){
        String list = "";
        for(Map.Entry<String,String> entry: listMap.entrySet()){
            if(list.equals(""))
                list += "'";
            
            if(list.equals("'"))
                list +=   entry.getValue();
            else
                list += "," + entry.getValue(); 
        }
        if(list.equals(""))
            list = "''";
        else
            list += "'";
        return list;
    }
}
